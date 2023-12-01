package com.example.expensetracker.entity.expense;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
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
public class Expense implements Serializable {

  private static final long serialVersionUID = 3964630884707812925L;
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.AUTO)
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
  private String description;

  @Column
  @Enumerated(EnumType.STRING)
  private Month month;

  @Column
  private String expenseId;


  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "category_name", referencedColumnName = "category_name")
  private ExpenseCategory category;

  @Column
  private Double amount;


}
