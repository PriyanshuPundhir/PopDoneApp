package com.example.android.todolist.category;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.todolist.MainActivity;
import com.example.android.todolist.R;

import java.util.List;

public class CategoryAdapter  extends BaseAdapter {
    private List<CategoryModel> cat_List;

    public CategoryAdapter(List<CategoryModel> cat_List) {
        this.cat_List = cat_List;
    }

    @Override
    public int getCount() {
        return cat_List.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;

        if(convertView == null)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_layout,parent,false);
        }
        else
        {
            view = convertView;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("CAT_INDEX",position);
                view.getContext().startActivity(intent);
            }
        });

        ((TextView) view.findViewById(R.id.catName)).setText(cat_List.get(position).getName());

        return view;
    }
}
