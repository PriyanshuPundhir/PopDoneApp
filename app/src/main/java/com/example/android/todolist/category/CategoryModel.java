package com.example.android.todolist.category;

public class CategoryModel {
    private String docID;
    private String name;

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public CategoryModel(String docID, String name) {
        this.docID = docID;
        this.name = name;
    }
}
