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

public class RecipeListRecyclerAdapter extends RecyclerView.Adapter<RecipeListRecyclerAdapter.MyViewHolder> {
    private ArrayList<GroceryList> arrayListGroceryList;
    private Context context;

    public RecipeListRecyclerAdapter( Context context, ArrayList<GroceryList> arrayListGrocery) {
        this.context = context;
        this.arrayListGroceryList = arrayListGrocery;
    }

    @Override
    public RecipeListRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView =inflater.inflate(R.layout.recipe_recycler_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeListRecyclerAdapter.MyViewHolder myViewHolder, final int position) {
        GroceryList recipe = arrayListGroceryList.get(position);
        TextView textView = myViewHolder.tv;
        textView.setText(recipe.getListName());
        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GroceryListActivity.class);

                i.putExtra("groceryList", arrayListGroceryList.get(position));
                context.startActivity(i);
            }
        });

    }
    @Override
    public int getItemCount() {
        return arrayListGroceryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
