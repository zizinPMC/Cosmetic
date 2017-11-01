package com.cosmetic;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;

/**
 * Created by yujeen on 2017. 10. 28..
 */

public class BoardReadFragment extends Fragment {
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
    ArrayList<HashMap<String,String>> tipList;

    /*,,,
                            ,,,*/
    public static TextView tcategory;
    public static TextView twriter;
    public static TextView ttitle;
    public static TextView tdate;
    public static TextView thits;
    public static TextView tgood;
    public static TextView tcontents;

    public static String category;
    public static String writer;
    public static String title;
    public static String date ;
    public static String hits;
    public static String good;
    public static String contents;

    public static BoardReadFragment newInstance() {
        BoardReadFragment fragment = new BoardReadFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_board_read, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*@BindView(R.id.cbr_tv_category) TextView tcategory;
    @BindView(R.id.cbr_tv_writer) TextView twriter;
    @BindView(R.id.cbr_tv_title) TextView ttitle;
    @BindView(R.id.cbr_tv_date) TextView tdate;
    @BindView(R.id.cbr_tv_hits) TextView thits;
    @BindView(R.id.cbr_tv_good) TextView tgood;
    @BindView(R.id.cbr_tv_contents) TextView tcontents;*/

        tcategory = (TextView)getActivity().findViewById(R.id.cbr_tv_category) ;
        twriter = (TextView)getActivity().findViewById(R.id.cbr_tv_writer) ;
        ttitle = (TextView)getActivity().findViewById(R.id.cbr_tv_title) ;
        tdate = (TextView)getActivity().findViewById(R.id.cbr_tv_date) ;
        thits = (TextView)getActivity().findViewById(R.id.cbr_tv_hits) ;
        tgood = (TextView)getActivity().findViewById(R.id.cbr_tv_good) ;
        tcontents = (TextView)getActivity().findViewById(R.id.cbr_tv_contents) ;

        tipList = new ArrayList<HashMap<String, String>>();
        getData("http://zizin1318.cafe24.com/board/board_content_read.php?num=3", tipList, getContext());

        //System.out.println("-------->"+category + "," + writer + "," + contents);
        /*tcategory.setText(category);
        twriter.setText(writer);
        ttitle.setText(title);
        tdate.setText(date);
        thits.setText(hits);
        tgood.setText(good);
        tcontents.setText(contents);*/
    }
    protected static void showList(ArrayList<HashMap<String,String>> tipList, Context context){
        try {
            System.out.println("-------->showlist start" + myJSON);
            JSONObject jsonObject = new JSONObject(myJSON);
            jsonArray = jsonObject.getJSONArray(TAG_RESULTS);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject c = jsonArray.getJSONObject(i);
                System.out.println("-------->getString start");
                category = c.getString(TAG_CATEGORY);

                System.out.println("-------->category" + category);
                 writer = c.getString(TAG_WRITER);
                 title = c.getString(TAG_TITLE);
                 date = c.getString(TAG_DATE).substring(0,10);
                 hits = c.getString(TAG_HITS);
                 good = c.getString(TAG_GOOD);
                 contents = c.getString(TAG_CONTENTS);

                /*HashMap<String,String> data = new HashMap<String,String>();
                System.out.println("-------->put start");
                data.put(TAG_CATEGORY,category);
                data.put(TAG_WRITER,writer);
                data.put(TAG_TITLE,title);
                data.put(TAG_DATE,date);
                data.put(TAG_HITS,hits);
                data.put(TAG_GOOD,good);
                data.put(TAG_CONTENTS,contents);
                tipList.add(data);*/
            }
            /*System.out.println("-------->listAdapter start");
            ListAdapter adapter = new SimpleAdapter(
                    context, tipList,R.layout.activity_board_read,
                    new String[]{TAG_CATEGORY,TAG_WRITER,TAG_TITLE,TAG_DATE,TAG_HITS,TAG_GOOD,TAG_CONTENTS},
                    new int[]{R.id.cbr_tv_category,R.id.cbr_tv_writer,R.id.cbr_tv_title,R.id.cbr_tv_date
                            ,R.id.cbr_tv_hits,R.id.cbr_tv_good,R.id.cbr_tv_contents}
            );
*/
            //listView.setAdapter(adapter);
            tcategory.setText(category);
        twriter.setText(writer);
        ttitle.setText(title);
        tdate.setText(date);
        thits.setText(hits);
        tgood.setText(good);
        tcontents.setText(contents);

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
