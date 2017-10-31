package com.cosmetic.db;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by yujeen on 2017. 9. 28..
 */

public class BoardWriteDB {
    private String urlPath;
    //회원가입의 정보를 MySQL에 저장할 php를 포함한 도메인 주소를 입력한다.
    private final String board_write_UrlPath=
            "http://zizin1318.cafe24.com/board/board_write.php";

    /*--DB user에 접속하여 회원가입에 관한 db저장할 데이터*/
    private String boardCategory;
    private String boardWriter;
    private String boardTitle;
    private String boardContent;
    private String boardDate;
    private String boardMdate;
    private int boardHits;
    private int boardGood;
    private ArrayList<String> results;
    public ArrayList<String> comBoardDBManager(String boardCategory, String boardWriter, String boardTitle, String boardContent, String boardDate, String boardMdate, int boardHits, int boardGood) {
        urlPath = board_write_UrlPath;
        this.boardCategory = boardCategory;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardDate = boardDate;
        this.boardMdate = boardMdate;
        this.boardHits = boardHits;
        this.boardGood = boardGood;
        try{
            results = new BoardWrite().execute().get();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
        }
        return results;
    }
    class BoardWrite extends AsyncTask<Void,Void,ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            try {
                URL url = new URL(urlPath);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setRequestMethod("POST");

                String param = "boardCategory=" + boardCategory
                        + "&boardWriter=" + boardWriter
                        + "&boardTitle=" + boardTitle
                        + "&boardContent=" + boardContent
                        + "&boardDate=" + boardDate
                        + "&boardMdate=" + boardMdate
                        + "&boardHits=" + boardHits
                        + "&boardGood=" + boardGood;

                OutputStream outputStream = con.getOutputStream();
                outputStream.write(param.getBytes());
                outputStream.flush();
                outputStream.close();

                BufferedReader rd = null;
                rd = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String line = null;
                while ((line = rd.readLine())!= null) {
                    Log.d("BufferedReader", line);
                }
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> qResults) {
            super.onPostExecute(qResults);
        }
    }


}
