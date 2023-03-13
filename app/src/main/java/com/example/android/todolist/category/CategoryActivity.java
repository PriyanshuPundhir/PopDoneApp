package com.example.android.todolist.category;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.todolist.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private GridView catView;
    public static List<CategoryModel> catList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        catView=findViewById(R.id.catGridView);
        loadCategories();
        CategoryAdapter categoryAdapter=new CategoryAdapter(catList);
        catView.setAdapter(categoryAdapter);
    }

    private void loadCategories() {
        catList.clear();//Here we can use firebase database also in order to fill these rows
        catList.add(new CategoryModel("1","Reminder Service"));
    }
}