package com.example.shopeasydemo;

import java.io.Serializable;
import java.util.ArrayList;

public class ListItem implements Serializable {
    private String itemName;
    private String category;
    public ListItem(String itemName, String category){
        this.itemName = itemName;
        this.category = category;
    }
    public String getItemName(){return itemName;}
    public String getCategory(){return category;}
}
