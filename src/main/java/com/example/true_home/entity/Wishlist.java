package com.example.true_home.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Entity
@Table(name = "T_WISHLIST")
@Data
public class Wishlist extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    private User account;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Listing product;

    @Column(name = "addedDate")
    private LocalDateTime addedDate;


    @Override
    public String toString() {
        return new StringJoiner(", ", Wishlist.class.getSimpleName() + "{", "}")
                .add("\"id\":" + id)
                .add("\"account\":" + account)
                .add("\"product\":" + product)
                .add("\"addedDate\":" + addedDate)
                .toString();
    }
}
