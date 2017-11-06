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
        db.execSQL("CREATE TABLE COSMETIC (_cosNum INTEGER PRIMARY KEY AUTOINCREMENT," +
                " cosName TEXT, cosBrand TEXT, cosMainCate TEXT, cosMidCate TEXT, cosExpDate INTEGER, userID TEXT);");
    }
    //DB업그레이드를 위해 버전이 변경될 때 호출되는 함수

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(String cosName, String cosBrand, String cosMainCate, String cosMidCate, int cosExpDate, String userID) {
        //읽고 쓰기가 가능하게  DB열기
        SQLiteDatabase db = getWritableDatabase();
        //DB에 입력한 값으로 행 추가
       //Cosmetic table
        db.execSQL("INSERT INTO COSMETIC VALUES(null, '" + cosName + "', '" + cosBrand + "', '" + cosMainCate + "', '"
                + cosMidCate + "', "+cosExpDate+", '"+userID+"');");
        db.close();
    }

    public void delete(String cosName) {
        SQLiteDatabase db = getWritableDatabase();
        //입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM COSMETIC WHERE cosName='" + cosName + "';");
        db.close();
    }

    public String getResult() {
        //읽기가 가능하게 DB열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        //DB에 있는 데이터를 쉽게 처리하기 위해 Cussor을 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM COSMETIC", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0) + " : "
                    + cursor.getString(1) + " ->"
                    + cursor.getString(2)+ " , "
                    + cursor.getString(3)+ " , "
                    + cursor.getString(4)+"\n";

        }
        return result;
    }
}
