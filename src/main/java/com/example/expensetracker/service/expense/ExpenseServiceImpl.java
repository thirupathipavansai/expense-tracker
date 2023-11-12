package com.example.expensetracker.service.expense;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.stereotype.Service;

import com.example.expensetracker.controller.exception.InvalidExpenseCategoryException;
import com.example.expensetracker.entity.category.ExpenseCategory;
import com.example.expensetracker.entity.expense.Expense;
import com.example.expensetracker.entity.expense.ExpenseSolrFieldNames;
import com.example.expensetracker.entity.expense.Month;
import com.example.expensetracker.miscellaneous.CategoryErrorMessage;
import com.example.expensetracker.repository.ExpenseCategoryRepository;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.request.CreateExpenseWebRequest;
import com.example.expensetracker.request.UpdateExpenseWebRequest;
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

  @Override
  @Cacheable(value = "expenseCache", key = "'expense-' + #expenseWebRequest.username")
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
      log.info("Saving expense {} ", expense);
      try {
        SolrInputDocument solrInputDocument1 = getExpenseSolrDocument(expense);
        cloudSolrClient.add(solrInputDocument1);
        cloudSolrClient.commit();
      }catch (SolrServerException b){
        System.out.println("Exception : " + b);
      }
      expenseRepository.save(expense);
      return expense; // Return the saved Expense object.
    }
    return null; // Return null when expenseWebRequest is null.
  }

  private SolrInputDocument getExpenseSolrDocument(Expense expense) {
    SolrInputDocument solrInputDocument = new SolrInputDocument();
    solrInputDocument.setField(ExpenseSolrFieldNames.PAYMENT_TYPE, expense.getPaymentType());
    solrInputDocument.setField(ExpenseSolrFieldNames.AMOUNT, expense.getAmount());
    solrInputDocument.setField(ExpenseSolrFieldNames.CATEGORY, expense.getCategory());
    solrInputDocument.setField(ExpenseSolrFieldNames.CREATED_BY, expense.getCreatedBy());
    solrInputDocument.setField(ExpenseSolrFieldNames.CREATED_DATE, expense.getCreatedDate());
    solrInputDocument.setField(ExpenseSolrFieldNames.DESCRIPTION, expense.getDescription());
    solrInputDocument.setField(ExpenseSolrFieldNames.MONTH, expense.getMonth());
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
    Page<Expense> expenses = expenseRepository.findBycreatedByAndMonth(createdBy, month, pageable);
    return new PageImpl<>(expenses.getContent(), pageable, expenses.getTotalElements());
  }


}
