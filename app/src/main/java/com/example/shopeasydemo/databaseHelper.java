package com.example.shopeasydemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class databaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String TABLE_NAME = "allLists";
    private static final String COL1 = "listItems";
    private static final String COL2 = "itemCategory";
    SQLiteDatabase db;

    public databaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
        db = getWritableDatabase();
        onCreate(db);


    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS allLists (groceryLists TEXT)";
        db.execSQL(createTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { onCreate(db); }

    public void addGroceryList(String groceryList){
        ContentValues contentValues = new ContentValues();
        contentValues.put("groceryLists", groceryList);
        db.insert("allLists", null, contentValues);
        System.out.println("added to all list");
        String createTable = "CREATE TABLE ["+ groceryList +"] (listItems TEXT, itemsCategory TEXT)";
        db.execSQL(createTable);
        System.out.println("added its own table");
    }
    public void addListItem(String groceryList, String listItem, String itemCategory){
        ContentValues contentValues = new ContentValues();
        contentValues.put("listItems", listItem);
        contentValues.put("itemsCategory", itemCategory);
        db.insert("["+groceryList+"]", null, contentValues);
    }

    public Cursor getAllLists(){
        String query = "SELECT * FROM allLists";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public void dropData(){
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
    public void dropTable(String tableName){
        db.execSQL("DROP TABLE IF EXISTS [" + tableName +"]");
    }
    public void deleteListItem(String listItem, String groceryList){
        db.execSQL("DELETE FROM [" + groceryList +"] WHERE listItems = \"" + listItem + "\"");
    }
    public void deleteList(String groceryLists){
        db.execSQL("DELETE FROM [allLists] WHERE groceryLists = \"" + groceryLists + "\"");
    }
    public ArrayList<ListItem> getListIngredients(String listName) {
        ArrayList<ListItem> listofItems = new ArrayList<>();
        String query = "SELECT * FROM [" + listName + "]";
        Cursor data = db.rawQuery(query,null);
        while(data.moveToNext()){
            listofItems.add(new ListItem(data.getString(0), data.getString(1)));
        }
        return listofItems;
    }
}
