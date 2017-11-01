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

public class BoardReadDB //extends AppCompatActivity{
{
    public static String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_CATEGORY = "boardCategory";
    private static final String TAG_TITLE = "boardTitle";
    private static final String TAG_WRITER = "boardWriter";
    private static final String TAG_DATE = "boardDate";
    private static final String TAG_HITS = "boardHits";
    private static final String TAG_GOOD = "boardGood";
    private static final String TAG_CONTENTS = "boardContent";

    public static JSONArray jsonArray = null;
    //ArrayList<HashMap<String,String>> tipList;
    public static ListView listView;
    /*@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_comboard_item);

        tipList1 = new ArrayList<HashMap<String, String>>();
        getData("http://zizin1318.cafe24.com/board/board_read.php",tipList1,getApplicationContext());
    }*/
    protected static void showList(ArrayList<HashMap<String,String>> tipList, Context context){
        try {
            System.out.println("-------->showlist start" + myJSON);
            JSONObject jsonObject = new JSONObject(myJSON);
            jsonArray = jsonObject.getJSONArray(TAG_RESULTS);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject c = jsonArray.getJSONObject(i);
                System.out.println("-------->getString start");
                String category = c.getString(TAG_CATEGORY);

                System.out.println("-------->category" + category);
                String writer = c.getString(TAG_WRITER);
                String title = c.getString(TAG_TITLE);
                String date = c.getString(TAG_DATE).substring(0,10);
                String hits = c.getString(TAG_HITS);
                String good = c.getString(TAG_GOOD);
                String contents = c.getString(TAG_CONTENTS);

                HashMap<String,String> data = new HashMap<String,String>();
                System.out.println("-------->put start");
                data.put(TAG_CATEGORY,category);
                data.put(TAG_WRITER,writer);
                data.put(TAG_TITLE,title);
                data.put(TAG_DATE,date);
                data.put(TAG_HITS,hits);
                data.put(TAG_GOOD,good);
                data.put(TAG_CONTENTS,contents);
                tipList.add(data);
            }
            System.out.println("-------->listAdapter start");
            ListAdapter adapter = new SimpleAdapter(
                    context, tipList,R.layout.activity_board_read,
                    new String[]{TAG_CATEGORY,TAG_WRITER,TAG_TITLE,TAG_DATE,TAG_HITS,TAG_GOOD,TAG_CONTENTS},
                    new int[]{R.id.cbr_tv_category,R.id.cbr_tv_writer,R.id.cbr_tv_title,R.id.cbr_tv_date
                            ,R.id.cbr_tv_hits,R.id.cbr_tv_good,R.id.cbr_tv_contents}
            );

            //listView.setAdapter(adapter);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void getData(String url, ArrayList<HashMap<String,String>> tipList, Context context){
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
                System.out.println("-------->boardread DB get data return : "+s);
                showList(tipList,context);
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }


}
