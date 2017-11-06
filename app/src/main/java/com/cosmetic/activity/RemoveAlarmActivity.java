package com.cosmetic.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.cosmetic.Navigator;
import com.cosmetic.R;
/*
*
 * Created by gimjihyeon on 2017. 11. 7..
 */


public class RemoveAlarmActivity extends Activity {

    private Intent intent;
    private PendingIntent ServicePending;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmremove);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.touchlove);
        mediaPlayer.start(); // prepare(); 나 create() 를 호출할 필요 없음


        Button.OnClickListener bClickListener = v -> {
            switch (v.getId()) {
                case R.id.changeSet:

                    Navigator.goMain(getApplicationContext());
                    break;
                case R.id.removeAlarm:

                    mediaPlayer.stop();
                    removeAlarm();
                    break;


            }
        };


        findViewById(R.id.removeAlarm).setOnClickListener(bClickListener);
    }

    void removeAlarm() {

        intent = new Intent("AlarmReceiver");
        //PendingIntent.getBroadcast(Context context, int requestCod, Intent intent, int flag);
        ServicePending = PendingIntent.getBroadcast(
                RemoveAlarmActivity.this, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("ServicePending : ", "" + ServicePending.toString());

        Toast.makeText(getBaseContext(), "알람이 해제되었습니다", Toast.LENGTH_SHORT).show();

        alarmManager.cancel(ServicePending);
    }

}
