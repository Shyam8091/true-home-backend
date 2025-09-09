package com.example.true_home.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
public class CommonEntity {

    @Column(name = "created_by")
    private int created_by;
    @Column(name = "updated_by")
    private int updated_by;
    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false, insertable = false)
    private Timestamp createdAt;
    @Column(name = "updated_at", updatable = false, insertable = false)
    private Timestamp updatedAt;

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(int updated_by) {
        this.updated_by = updated_by;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

}
