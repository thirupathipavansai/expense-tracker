package com.example.expensetracker.service.expense;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrServerException;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.expensetracker.entity.expense.Expense;
import com.example.expensetracker.entity.expense.Month;
import com.example.expensetracker.request.CreateExpenseWebRequest;
import com.example.expensetracker.request.UpdateExpenseWebRequest;

public interface ExpenseService {
  /**
   * create an expense
   *
   * @param expense
   * @return
   */
  Expense addExpense(CreateExpenseWebRequest expense) throws IOException, SolrServerException;

  /**
   * update existing expense
   *
   * @param request
   * @return
   */
  Expense updateExpense(UpdateExpenseWebRequest request);

  /**
   * get expenses by month
   *
   * @param createdBy
   * @param month
   * @param pageable
   * @return
   */
  Page<Expense> getExpensesByMonth(String createdBy, Month month, Pageable pageable);


  /**
   * get expenses by user
   *
   * @param createdBy
   * @param pageable
   * @return
   */
  Page<Expense> getExpensesByUserName(String createdBy, Pageable pageable);

  /**
   * Download Expense
   */
   void downloadExpense(String createdBy, Month month, HttpServletResponse servletResponse) throws Exception;
}

