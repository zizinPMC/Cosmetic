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
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by gimjihyeon on 2017. 10. 19..
 */

public class CosmeticWriteDB {
    private String urlPath;
    //화장품의 정보를 MySQL에 저장할 php를 포함한 도메인 주소를 입력한다.
    private final String user_UrlPath =
            "http://zizin1318.cafe24.com/cosmetic/cosmetic_write.php";

    /*--DB cosmetic 접속하여 cosmetic 테이블에 db저장할 데이터*/
    private int cosNo;
    private String userID;
    private String cosName;
    private String cosBrand;
    private String cosMainCategory;
    private String cosMidCategory;
    private int cosIsOpen;
    private Date cosOpenDate;
    private int cosExpDday;
    private String cosPicUrl;
    private ArrayList<String> results;

    public ArrayList<String> cosmeticDBManager(int cosNo, String userID, String cosName, String cosBrand,
                                               String cosMainCategory, String cosMidCategory, int cosIsOpen,
                                               Date cosOpenDate, int cosExpDday, String cosPicUrl) {
        urlPath = user_UrlPath;
        this.cosNo = cosNo;
        this.userID = userID;
        this.cosName = cosName;
        this.cosBrand = cosBrand;
        this.cosMainCategory = cosMainCategory;
        this.cosMidCategory = cosMidCategory;
        this.cosIsOpen = cosIsOpen;
        this.cosOpenDate = cosOpenDate;
        this.cosExpDday = cosExpDday;
        this.cosPicUrl = cosPicUrl;
        try {
            results = new InputCosmetic().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return results;
    }

    class InputCosmetic extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            try {
                URL url = new URL(urlPath);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setRequestMethod("POST");

                String param = "cosNo" + cosNo +
                        "&userID=" + userID
                        + "&cosName=" + cosName
                        + "&cosBrand=" + cosBrand
                        + "&cosMainCategory=" + cosMainCategory
                        + "&cosMidCategory=" + cosMidCategory +
                        "&cosIsOpen=" + cosIsOpen +
                        "&cosOpenDate=" + cosOpenDate +
                        "&cosExpDday=" + +cosExpDday +
                        "&cosPicUrl=" + cosPicUrl;

                OutputStream outputStream = con.getOutputStream();
                outputStream.write(param.getBytes());
                outputStream.flush();
                outputStream.close();

                BufferedReader rd = null;
                rd = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String line = null;
                while ((line = rd.readLine()) != null) {
                    Log.d("BufferedReader", line);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
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


