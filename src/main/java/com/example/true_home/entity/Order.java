package com.example.true_home.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Entity
@Table(name = "T_ORDERS")
@Data
public class Order extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    private User account;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Listing product;

    @Column(name = "orderDate")
    private LocalDateTime orderDate;

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "{", "}")
                .add("\"id\":" + id)
                .add("\"account\":" + account)
                .add("\"product\":" + product)
                .add("\"orderDate\":" + orderDate)
                .toString();
    }
}
