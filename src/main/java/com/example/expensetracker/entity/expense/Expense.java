package com.example.expensetracker.entity.expense;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.expensetracker.entity.category.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@EnableJpaAuditing
public class Expense {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  @CreatedDate
  private String createdDate;

  @Column
  @CreatedBy
  private String createdBy;

  @Column
  @UpdateTimestamp
  private Timestamp updatedDate;

  @Column
  private String updatedBy;

  @Column
  @Enumerated(EnumType.STRING)
  private PaymentType paymentType;

  @Column
  private String Description;

  @Column
  @Enumerated(EnumType.STRING)
  private Month month;


  @ManyToOne
  @JoinColumn(name = "category_id")
  @Lazy
  @Enumerated(EnumType.STRING)
  private ExpenseCategory categoryName;

  @Column
  private Double amount;


}
