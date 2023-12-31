package com.example.expensetracker.service.expense;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.expensetracker.controller.exception.InvalidExpenseCategoryException;
import com.example.expensetracker.entity.category.ExpenseCategory;
import com.example.expensetracker.entity.expense.Expense;
import com.example.expensetracker.entity.expense.ExpenseParameters;
import com.example.expensetracker.entity.expense.ExpenseSolrFieldNames;
import com.example.expensetracker.entity.expense.Month;
import com.example.expensetracker.miscellaneous.CategoryErrorMessage;
import com.example.expensetracker.repository.ExpenseCategoryRepository;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.request.CreateExpenseWebRequest;
import com.example.expensetracker.request.UpdateExpenseWebRequest;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExpenseServiceImpl implements ExpenseService {

  @Autowired
  ExpenseRepository expenseRepository;

  @Autowired
  ExpenseCategoryRepository expenseCategoryRepository;

  @Autowired
  @Qualifier("expenseCollectionClient")
  private CloudSolrClient cloudSolrClient;


  private static final List<String> HEADER_LIST = ImmutableList.<String>builder()
      .add(ExpenseParameters.AMOUNT, ExpenseParameters.CREATED_BY, ExpenseParameters.CREATED_DATE,
          ExpenseParameters.DESCRIPTION, ExpenseParameters.MONTH, ExpenseParameters.PAYMENT_TYPE).build();

  @Override
  //  @Cacheable(value = "expenseCache", key = "'expense-' + #expenseWebRequest.username")
  @Transactional
  public Expense addExpense(CreateExpenseWebRequest expenseWebRequest) throws IOException, SolrServerException {

    if (Objects.nonNull(expenseWebRequest)) {
      Expense expense = new Expense();
      BeanUtils.copyProperties(expenseWebRequest, expense);
      ExpenseCategory expenseCategory =
          expenseCategoryRepository.findByCategoryName(expenseWebRequest.getExpenseCategory());
      if (Objects.isNull(expenseCategory)) {
        throw new InvalidExpenseCategoryException(CategoryErrorMessage.INVALID_CATEGORY_NAME);
      }
      expenseCategory.setCategoryName(expenseWebRequest.getExpenseCategory());
      expense.setCategory(expenseCategory);
      expense.setCreatedBy(expenseWebRequest.getUsername());
      LocalDate currentDate = LocalDate.now();
      expense.setCreatedDate(currentDate.toString());
      //      log.info("Saving expense {} ", expense);
      expenseRepository.save(expense);
      /*
       // TODO need to update in solr in future
      try {
        SolrInputDocument solrInputDocument1 = getExpenseSolrDocument(expense);
        cloudSolrClient.add(solrInputDocument1);
        cloudSolrClient.commit();
      } catch (SolrServerException b) {
        System.out.println("Exception : " + b);
      }*/
      return expense; // Return the saved Expense object.
    }
    return null; // Return null when expenseWebRequest is null.
  }

  private SolrInputDocument getExpenseSolrDocument(Expense expense) {
    SolrInputDocument solrInputDocument = new SolrInputDocument();
    solrInputDocument.setField(ExpenseSolrFieldNames.PAYMENT_TYPE, expense.getPaymentType().name());
    solrInputDocument.setField(ExpenseSolrFieldNames.AMOUNT, expense.getAmount());
    solrInputDocument.setField(ExpenseSolrFieldNames.CATEGORY, expense.getCategory().getCategoryName().name());
    solrInputDocument.setField(ExpenseSolrFieldNames.CREATED_BY, expense.getCreatedBy());
    solrInputDocument.setField(ExpenseSolrFieldNames.UPDATED_BY, expense.getCreatedBy());
    solrInputDocument.setField(ExpenseSolrFieldNames.CREATED_DATE, expense.getCreatedDate());
    solrInputDocument.setField(ExpenseSolrFieldNames.UPDATED_DATE, expense.getUpdatedDate());
    solrInputDocument.setField(ExpenseSolrFieldNames.DESCRIPTION, expense.getDescription());
    solrInputDocument.setField(ExpenseSolrFieldNames.MONTH, expense.getMonth().name());
    return solrInputDocument;
  }

  @Override
  public Expense updateExpense(UpdateExpenseWebRequest request) {
    if (Objects.nonNull(request)) {
      Optional<Expense> expense = expenseRepository.findById(request.getId());
      if (expense.isPresent()) {
        Date date = new Date();
        BeanUtils.copyProperties(request, expense.get());
        expense.get().setUpdatedBy(request.getUsername());
      }
      log.info("Updating expense {} ", expense);
      return expenseRepository.save(expense.get());
    }
    return null;
  }

  @Override
  public Page<Expense> getExpensesByMonth(String createdBy, Month month, Pageable pageable) {
    Page<Expense> expenses = expenseRepository.findByCreatedByAndMonth(createdBy, month, pageable);
    return new PageImpl<>(expenses.getContent(), pageable, expenses.getTotalElements());
  }

  @Override
  public Page<Expense> getExpensesByUserName(String createdBy, Pageable pageable) {
    Page<Expense> expenses = expenseRepository.findByCreatedBy(createdBy, pageable);
    return new PageImpl<>(expenses.getContent(), pageable, expenses.getTotalElements());
  }

  @Override
  public void downloadExpense(String createdBy, Month month, HttpServletResponse servletResponse) throws Exception {
    List<Expense> expenses = expenseRepository.findByCreatedByAndMonth(createdBy, month);
    if (CollectionUtils.isNotEmpty(expenses)) {
      SXSSFWorkbook workbook = generateWorkbookForExpense(HEADER_LIST, expenses);
      generateFileTemplate("Expenses", workbook, servletResponse);
    }
  }

  @Override
  public void deleteExpense(String createdBy, List<Long> Ids) {
    List<Expense> expenses = expenseRepository.findByCreatedByAndIdIn(createdBy, Ids);
    if (CollectionUtils.isNotEmpty(expenses)) {
      expenseRepository.deleteAllById(Ids);
    }
  }

  @Override
  public List<Object> getExpenseAndScale(String createdBy, Long year) {
    return expenseRepository.getTotalExpensesByUserAndYear(createdBy, year);
  }


  public static SXSSFWorkbook generateWorkbookForExpense(List<String> headerSet, List<Expense> expenses) {
    SXSSFWorkbook workbook = new SXSSFWorkbook();
    Sheet brandSheet = workbook.createSheet("ExpenseData");
    Row row;
    int cellIndex1 = 0;
    int rowindex = 1;
    row = brandSheet.createRow((short) 0);
    for (String header : headerSet) {
      Cell cell = row.createCell((short) cellIndex1++);
      cell.setCellType(CellType.STRING);
      cell.setCellValue(header);
    }
//    for (int i = 0; i < headerSet.size(); i++) {
//      brandSheet.autoSizeColumn(i);
//    }
    for (Expense expense : expenses) {
      int cellIndex = 0;
      row = brandSheet.createRow((short) rowindex++);


      Cell cell1 = row.createCell((short) cellIndex++);
      cell1.setCellType(CellType.STRING);
      cell1.setCellValue(expense.getAmount());

      Cell cell2 = row.createCell((short) cellIndex++);
      cell2.setCellType(CellType.STRING);
      cell2.setCellValue(expense.getCreatedBy());

      Cell cell3 = row.createCell((short) cellIndex++);
      cell3.setCellType(CellType.STRING);
      cell3.setCellValue(expense.getCreatedDate());

      Cell cell4 = row.createCell((short) cellIndex++);
      cell4.setCellType(CellType.STRING);
      cell4.setCellValue(expense.getDescription());

      Cell cell5 = row.createCell((short) cellIndex++);
      cell5.setCellType(CellType.STRING);
      cell5.setCellValue(expense.getMonth().name());

      Cell cell6 = row.createCell((short) cellIndex);
      cell6.setCellType(CellType.STRING);
      cell6.setCellValue(expense.getPaymentType().name());
    }
    return workbook;
  }

  private static void generateFileTemplate(String filename, Workbook workbook, HttpServletResponse servletResponse)
      throws Exception {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    workbook.write(byteArrayOutputStream);
    //    BeanUtilsConfigurer.configure();
    byte[] bytes = byteArrayOutputStream.toByteArray();
    writeXlsFileContentToStream(servletResponse, bytes, filename);
  }

  private static void writeXlsFileContentToStream(HttpServletResponse httpServletResponse, byte[] fileContent,
      String fileName) throws IOException {
    httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
    httpServletResponse.setContentLength(fileContent.length);
    httpServletResponse.getOutputStream().write(fileContent);
    httpServletResponse.getOutputStream().flush();
  }



}
