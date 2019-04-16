package com.example.shopeasydemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class GroceryListItemsAdapter extends RecyclerView.Adapter<GroceryListItemsAdapter.MyViewHolder> {
    private ArrayList<ListItem> arrayListGroceryItems;
    private Context context;
    private GroceryList groceryList;

    public GroceryListItemsAdapter( Context context, ArrayList<ListItem> arrayListGrocery, GroceryList groceryList) {
        this.context = context;
        this.arrayListGroceryItems = arrayListGrocery;
        this.groceryList = groceryList;
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
        final String itemName = arrayListGroceryItems.get(position).getItemName();
        String itemCategory = arrayListGroceryItems.get(position).getCategory();
        final String groceryListName = groceryList.getListName();
        System.out.println("Position: " + position + " Item Name: " + itemName + " Item Category: " + itemCategory);
        TextView textView = myViewHolder.tv;
        textView.setText(itemName +" " +itemCategory);
        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("Clicked Item #" + position);
            }
        });
        Button deleteListItemButton = myViewHolder.deleteBt;
        deleteListItemButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               MainActivity.db.deleteListItem(itemName,groceryListName);
               arrayListGroceryItems.remove(position);
               notifyDataSetChanged();
            }
        });

    }
    @Override
    public int getItemCount() {
        return arrayListGroceryItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        Button deleteBt;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.groceryListItem);
            deleteBt = (Button) itemView.findViewById(R.id.deleteListItemButton);
        }
    }
}
