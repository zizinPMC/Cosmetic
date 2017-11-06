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

    public void insert(String cosPic,String cosName, String cosBrand, String cosMainCate, String cosMidCate, int cosExpDate, String userID) {
        //읽고 쓰기가 가능하게  DB열기
        SQLiteDatabase db = getWritableDatabase();
        //DB에 입력한 값으로 행 추가
        //Cosmetic table
        db.execSQL("INSERT INTO COSMETICS VALUES(null,'" + cosPic+ "', '" +cosName + "', '"
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
    public String getItem() {
        //읽기가 가능하게 DB열기
        SQLiteDatabase db = getReadableDatabase();

        String result = "";

        //DB에 있는 데이터를 쉽게 처리하기 위해 Cussor을 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM COSMETICS", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(2) +  "\n\n"   //cos name
                    + cursor.getString(6) + "\n";   //cos dday
        }
        return result;
    }
    public String getPic() {
        //읽기가 가능하게 DB열기
        SQLiteDatabase db = getReadableDatabase();

        String result = "";

        //DB에 있는 데이터를 쉽게 처리하기 위해 Cussor을 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM COSMETICS", null);
        while (cursor.moveToNext()) {
            result =cursor.getString(1) + " \n";       //cospic
        }
        return result;
    }
    public String getCosName() {
        //읽기가 가능하게 DB열기
        SQLiteDatabase db = getReadableDatabase();

        String result = "";

        //DB에 있는 데이터를 쉽게 처리하기 위해 Cussor을 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM COSMETICS", null);
        while (cursor.moveToNext()) {
            result =cursor.getString(2) + " \n";       //cospic
        }
        return result;
    }
    public String getCosDday() {
        //읽기가 가능하게 DB열기
        SQLiteDatabase db = getReadableDatabase();

        String result = "";

        //DB에 있는 데이터를 쉽게 처리하기 위해 Cussor을 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM COSMETICS", null);
        while (cursor.moveToNext()) {
            result =cursor.getString(6) + " \n";       //cospic
        }
        return result;
    }

    public String getItem2() {
        //읽기가 가능하게 DB열기
        SQLiteDatabase db = getReadableDatabase();

        String result = "";

        //DB에 있는 데이터를 쉽게 처리하기 위해 Cussor을 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM COSMETICS", null);
        while (cursor.moveToNext()) {
            result +=cursor.getString(1) + " \n"+       //cospic
                    cursor.getString(2) +  "\n\n"   //cos name
                    + cursor.getString(6) + "\n";   //cos dday
        }
        return result;
    }
    public String getResult() {
        //읽기가 가능하게 DB열기
        SQLiteDatabase db = getReadableDatabase();

        String result = "";

        //DB에 있는 데이터를 쉽게 처리하기 위해 Cussor을 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM COSMETICS", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0) + " : "
                    + cursor.getString(1) + " ,"
                    + cursor.getString(2) + " , "
                    + cursor.getString(3) + " , "
                    + cursor.getString(4) + " , "
                    + cursor.getString(5) + " , "
                    + cursor.getString(6) + " , "
                    + cursor.getString(7) + "\n";

        }
        return result;
    }
}
