package com.example.kodyar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseList_1 extends SQLiteOpenHelper {

    private final String TABLE_NAME = "TABLE_NAME";
    private final String ID = "ID";
    private final String ADDRESS = "ADDRESS";
    private final String SUBJECT = "SUBJECT";
    private final String KOD = "KOD";
    private final String ABOUT = "ABOUT";


    private String dataBaseQuery;

    public DataBaseList_1(@Nullable Context context) {
        super(context, "subjectList", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dataBaseQuery = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ADDRESS + " TEXT, " + SUBJECT + " TEXT, " + KOD  + " TEXT, " + ABOUT + " TEXT)";
        db.execSQL(dataBaseQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addSubject(SubjectList list){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ADDRESS, list.getAddress());
        cv.put(SUBJECT, list.getSubject());
        cv.put(KOD, list.getKod());
        cv.put(ABOUT, list.getAbout());

        db.insert(TABLE_NAME, null, cv);
    }

    public List<SubjectList> listList() {
        dataBaseQuery = "SELECT * FROM " + TABLE_NAME;
        return getList(dataBaseQuery);
    }

    private List<SubjectList> getList(String query) {

        List<SubjectList> subjectLists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String address = cursor.getString(1);
                String subject = cursor.getString(2);
                String kod = cursor.getString(3);
                String about = cursor.getString(4);


                SubjectList subjectList = new SubjectList(id, address, subject ,kod , about);
                subjectLists.add(subjectList);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return subjectLists;


    }

    public List<SubjectList> searchName(String address,String subject, String kod , String about) {
        dataBaseQuery = "SELECT * FROM " + TABLE_NAME + " WHERE (";

        boolean hasInfo;


        dataBaseQuery += ADDRESS + "= '" + address + "'";
        hasInfo = true;

        if (!(subject.equals(""))){
            dataBaseQuery += " AND ";
            dataBaseQuery += SUBJECT + "= '" + subject + "'";
            hasInfo = true;
        }
        else {
            hasInfo = false;
        }
        if(hasInfo){
            dataBaseQuery += " AND ";
        }

        if (!(kod.equals(""))){
            dataBaseQuery += KOD + "= '" + kod + "'";
            hasInfo = true;
        }
        else {
            hasInfo = false;
        }
        if (hasInfo){
            dataBaseQuery += " AND ";
        }
        if (!(about.equals(""))){
            dataBaseQuery += KOD + "= '" + kod + "' )";
        }

        return getList(dataBaseQuery);

    }

    ////////////////DELETE & UPDATE

    public void deleteName(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME , "id=?" , new String[] {id});

    }

    public void updateName(SubjectList subjectList){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ADDRESS , subjectList.getAddress());
        cv.put(SUBJECT, subjectList.getSubject());
        cv.put(KOD, subjectList.getKod());
        cv.put(ABOUT, subjectList.getAbout());


        db.update(TABLE_NAME , cv , "id=?" , new String[] {subjectList.getId()+""});

    }
    public List<SubjectList> getMeByAddress(String enter) {
        dataBaseQuery = "SELECT * FROM " + TABLE_NAME + " WHERE (";
        if (!(enter.equals(""))) {
            dataBaseQuery += ADDRESS + "= '" + enter + "' )";
        }
        return getList(dataBaseQuery);
    }
    public List<SubjectList> getMeBySubject(String enter) {
        dataBaseQuery = "SELECT * FROM " + TABLE_NAME + " WHERE (";
        if (!(enter.equals(""))) {
            dataBaseQuery += SUBJECT + "= '" + enter + "' )";
        }
        return getList(dataBaseQuery);
    }
}
