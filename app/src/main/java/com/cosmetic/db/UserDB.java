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
 * Created by gimjihyeon on 2017. 11. 2..
 */

public class UserDB {
    private String urlPath;
    //화장품의 정보를 MySQL에 저장할 php를 포함한 도메인 주소를 입력한다.
    private final String user_UrlPath =
            "http://zizin1318.cafe24.com/user/user.php";

    /*--DB cosmetic 접속하여 cosmetic 테이블에 db저장할 데이터*/
    private long userID;
    private String userName;
    private String userProfile;
    private int userBoardCnt;
    private int userCosCnt;
    private int userAutoLogin;
    private String userInterestBrand;
    private ArrayList<String> results;

    public ArrayList<String> userDBManager(long userID, String userName, int userBoardCnt, int userCosCnt,
                                           String userProfile, int userAutoLogin, String userInterestBrand) {
        urlPath = user_UrlPath;
        this.userID = userID;
        this.userName = userName;
        this.userBoardCnt = userBoardCnt;
        this.userCosCnt = userCosCnt;
        this.userProfile = userProfile;
        this.userAutoLogin = userAutoLogin;
        this.userInterestBrand = userInterestBrand;
        try {
            results = new InputUser().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return results;
    }

    class InputUser extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            try {
                URL url = new URL(urlPath);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setRequestMethod("POST");

                String param = "userID" + userID +
                        "&userName=" + userName
                        + "&userBoardCnt=" + userBoardCnt
                        + "&userCosCnt=" + userCosCnt
                        + "&userProfile=" + userProfile
                        + "&userAutoLogin=" + userAutoLogin +
                        "&userInterestBrand=" + userInterestBrand;

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
