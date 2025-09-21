package com.example.kodyar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseName extends SQLiteOpenHelper {

    private final String TABLE_NAME = "TABLE_NAME";
    private final String ID = "ID";
    private final String NAME = "NAME";
    private final String CATEGORY = "CATEGORY";

    private String dataBaseQuery;

    public DataBaseName(@Nullable Context context) {
        super(context, "name", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dataBaseQuery = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + CATEGORY + " TEXT)";
        db.execSQL(dataBaseQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addName(Name name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, name.getName());
        cv.put(CATEGORY, name.getCategory());

       db.insert(TABLE_NAME, null, cv);
    }

    public List<Name> listName() {
        dataBaseQuery = "SELECT * FROM " + TABLE_NAME;
        return getListName(dataBaseQuery);
    }

    private List<Name> getListName(String query) {

        List<Name> nameList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);


                Name customerView = new Name(id, name, category);
                nameList.add(customerView);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return nameList;


    }

    public List<Name> getMe(String enter) {
        dataBaseQuery = "SELECT * FROM " + TABLE_NAME + " WHERE (";

        if (!(enter.equals(""))) {
            dataBaseQuery += CATEGORY + "= '" + enter + "' )";
        }
        return getListName(dataBaseQuery);
    }

    public List<Name> searchName(String name, String category) {
        dataBaseQuery = "SELECT * FROM " + TABLE_NAME + " WHERE (";

        boolean hasInfo;

        if (!(name.equals(""))) {
            dataBaseQuery += NAME + "= '" + name + "'";
            hasInfo = true;
        } else {
            hasInfo = false;
        }

        if (hasInfo && !(category.equals(""))) {
            dataBaseQuery += " AND ";
            dataBaseQuery += CATEGORY + "= '" + category + "' )";
        }
            return getListName(dataBaseQuery);

    }

    ////////////////DELETE & UPDATE

    public void deleteName(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME , "id=?" , new String[] {id});

    }

    public void updateName(Name name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(NAME , name.getName());
        cv.put(CATEGORY, name.getCategory());


        db.update(TABLE_NAME , cv , "id=?" , new String[] {name.getId()+""});

    }
}
