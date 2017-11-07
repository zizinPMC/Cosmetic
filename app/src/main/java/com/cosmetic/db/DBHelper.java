package com.cosmetic.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gimjihyeon on 2017. 11. 6..
 */

public class DBHelper extends SQLiteOpenHelper {
    //DBHelper 생성자로 관리할 db이름과 버전정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //DB를 새로 생성할때 호출되는 함수

    @Override
    public void onCreate(SQLiteDatabase db) {
        //새로운 테이블 생성
        db.execSQL("CREATE TABLE COSMETICS (_cosNum INTEGER PRIMARY KEY AUTOINCREMENT,cosPic TEXT, cosName TEXT, cosBrand TEXT, cosMainCate TEXT, cosMidCate TEXT, cosExpDate INTEGER, userID TEXT);");
    }
    //DB업그레이드를 위해 버전이 변경될 때 호출되는 함수

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(String cosPic, String cosName, String cosBrand, String cosMainCate, String cosMidCate, int cosExpDate, String userID) {
        //읽고 쓰기가 가능하게  DB열기
        SQLiteDatabase db = getWritableDatabase();
        //DB에 입력한 값으로 행 추가
        //Cosmetic table
        db.execSQL("INSERT INTO COSMETICS VALUES(null,'" + cosPic + "', '" + cosName + "', '"
                + cosBrand + "', '" + cosMainCate + "', '"
                + cosMidCate + "', " + cosExpDate + ", '" + userID + "');");
        db.close();
    }

    public void delete(String cosName) {
        SQLiteDatabase db = getWritableDatabase();
        //입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM COSMETICS WHERE cosName='" + cosName + "';");
        db.close();
    }

    //DB에 있는 데이터를 쉽게 처리하기 위해 Cussor을 사용하여 테이블에 있는 모든 데이터 출력
    public String getItem() {
        //읽기가 가능하게 DB열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("SELECT * FROM COSMETICS", null);
        while (cursor.moveToNext()) {
            result = cursor.getString(2) + "\n";   //cos name
        }
        return result;
    }

    public int numColum() {
        SQLiteDatabase db = getReadableDatabase();
        int i;
        Cursor cursor = db.rawQuery("SELECT * FROM COSMETICS", null);
        for (i = 0; i < 7; i++) {
            if (cursor.toString().equals(null)) {
                return i;
            }
        }
        return i;
    }

    public String getCosDB() {
        SQLiteDatabase db = getReadableDatabase();
        int i = 0;

        Cursor cursor = db.rawQuery("SELECT * FROM COSMETICS WHERE _cosNum=" + i + ";", null);
        for (int num = 0; num < numColum(); i++) {
            if (cursor.toString().equals(null) == false) {
                String result = "이름 : " + getCosName(i); /*+ "\n" + "D -  " + getCosDday();*/
                return result;
            }
        }
        return "";
    }

    public String getPic() {
        //읽기가 가능하게 DB열기
        SQLiteDatabase db = getReadableDatabase();

        String result = "";

        //DB에 있는 데이터를 쉽게 처리하기 위해 Cussor을 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM COSMETICS", null);
        while (cursor.moveToNext()) {
            result = cursor.getString(1) + " \n";       //cospic
        }
        return result;
    }

    public String getCosName(int a) {
        //읽기가 가능하게 DB열기
        SQLiteDatabase db = getReadableDatabase();

        String result = "";

        //DB에 있는 데이터를 쉽게 처리하기 위해 Cussor을 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM COSMETICS WHERE _cosNum=" + a + ";", null);
        while (cursor.moveToNext()) {
            result = cursor.getString(2);       //cospic
        }
        return result;
    }

    public String getCosDday(int a) {
        //읽기가 가능하게 DB열기
        SQLiteDatabase db = getReadableDatabase();

        String result = "";

        //DB에 있는 데이터를 쉽게 처리하기 위해 Cussor을 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM COSMETICS WHERE _cosNum=" + a + ";", null);
        while (cursor.moveToNext()) {
            result = cursor.getString(6);       //cospic
        }
        return result;
    }


    public String getResult(int a) {
        //읽기가 가능하게 DB열기
        SQLiteDatabase db = getReadableDatabase();

        String result = "";

        //DB에 있는 데이터를 쉽게 처리하기 위해 Cussor을 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM COSMETICS WHERE _cosNum=" + a + ";", null);
        while (cursor.moveToNext()) {
            result += "이름 : " +cursor.getString(2) + "\n"+" D- day : "
                    + cursor.getString(6);

        }
        return result;
    }
}
