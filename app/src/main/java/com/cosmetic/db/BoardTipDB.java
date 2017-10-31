package com.cosmetic.db;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cosmetic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yujeen on 2017. 10. 19..
 */

public class BoardTipDB //extends AppCompatActivity{
{
    public static String myJSON;

    private static final String TAG_RESULTS = "result";
    //private static final String TAG_CATEGORY = "boardCategory";

    //게시판 글 읽기를 위해 글 번호를 받아옴 ---테스트
    private static final String TAG_NUM = "boardNum";
    private static final String TAG_TITLE = "boardTitle";
    private static final String TAG_WRITER = "boardWriter";
    private static final String TAG_DATE = "boardDate";
    private static final String TAG_HITS = "boardHits";

    public static JSONArray jsonArray = null;

    protected static void showList(ArrayList<HashMap<String,String>> tipList, Context context, ListView listView){
        try {
            JSONObject jsonObject = new JSONObject(myJSON);
            jsonArray = jsonObject.getJSONArray(TAG_RESULTS);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject c = jsonArray.getJSONObject(i);
                //String category = c.getString(TAG_CATEGORY);

                String num = c.getString(TAG_NUM);
                String writer = c.getString(TAG_WRITER);
                String title = c.getString(TAG_TITLE);
                String date = c.getString(TAG_DATE).substring(0,10);
                String hits = c.getString(TAG_HITS);


                HashMap<String,String> data = new HashMap<String,String>();

                //data.put(TAG_CATEGORY,category);
                data.put(TAG_NUM,num);
                data.put(TAG_WRITER,writer);
                data.put(TAG_TITLE,title);
                data.put(TAG_DATE,date);
                data.put(TAG_HITS,hits);

                tipList.add(data);
            }
            ListAdapter adapter = new SimpleAdapter(
                    context, tipList, R.layout.board_listview_item,
                    new String[]{TAG_WRITER,TAG_TITLE,TAG_DATE,TAG_HITS},
                    new int[]{R.id.cb_tv_writer,R.id.cb_tv_title,R.id.cb_tv_date,R.id.cb_tv_hits}
            );

            listView.setAdapter(adapter);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void getData(String url, ArrayList<HashMap<String,String>> tipList, Context context, ListView listView){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];

                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json=bufferedReader.readLine())!=null){
                        sb.append(json+"\n");
                    }
                    return sb.toString().trim();
                }catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                myJSON = s;
                showList(tipList,context,listView);
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }


}
