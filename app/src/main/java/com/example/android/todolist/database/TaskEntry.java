package com.example.android.todolist.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "task")
public class TaskEntry implements Serializable
{

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private int priority;

    @ColumnInfo(name = "Mobile_Number")
    private String mobileNo;
    @ColumnInfo(name = "Name1")
    private String name1;
    @ColumnInfo(name = "Name2")
    private String name2;
    @ColumnInfo(name = "Product_Description")
    private String product;
    @ColumnInfo(name = "Time")
    private int time;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Ignore
    public TaskEntry(String description,String name1,String name2,String mobileNo, int priority, Date updatedAt,int time) {
        this.description = description;
        this.priority = priority;
        this.updatedAt = updatedAt;
        this.name1 = name1;
        this.name2 = name2;
        this.mobileNo = mobileNo;
        this.time = time;
    }

    public TaskEntry(int id, String description,String name1,String name2,String mobileNo,
                     int priority, Date updatedAt,int time) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.updatedAt = updatedAt;
        this.name1 = name1;
        this.name2 = name2;
        this.mobileNo = mobileNo;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
   }
}
