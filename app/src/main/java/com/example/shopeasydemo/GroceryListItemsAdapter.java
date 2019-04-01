package com.example.shopeasydemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GroceryListItemsAdapter extends RecyclerView.Adapter<GroceryListItemsAdapter.MyViewHolder> {
    private ArrayList<String> arrayListGroceryItems;
    private Context context;

    public GroceryListItemsAdapter( Context context, ArrayList<String> arrayListGrocery) {
        this.context = context;
        this.arrayListGroceryItems = arrayListGrocery;
    }

    @Override
    public GroceryListItemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView =inflater.inflate(R.layout.recycler_grocery_list_items,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(GroceryListItemsAdapter.MyViewHolder myViewHolder, final int position) {
        String item = arrayListGroceryItems.get(position);
        TextView textView = myViewHolder.tv;
        textView.setText(item);
        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("Clicked Item #" + position);
            }
        });

    }
    @Override
    public int getItemCount() {
        return arrayListGroceryItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.groceryListItem);
        }
    }
}
