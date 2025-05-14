package com.example.true_home.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.StringJoiner;

@Entity
@Table(name = "T_IMAGES")
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Lob
//    @Column(name = "image_data", columnDefinition = "MEDIUMBLOB")
//    private byte[] imageData;

    @Lob
    @Column(name = "image_data")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    @JsonIgnore
    private Listing listing;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Listing getListing() {
        return listing;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Image.class.getSimpleName() + "{", "}")
                .add("\"id\":" + id)
                .add("\"imageUrl\":\"" + imageUrl + "\"")
                .add("\"listing\":" + listing)
                .toString();
    }
}
