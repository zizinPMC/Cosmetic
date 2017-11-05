package com.cosmetic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by gimjihyeon on 2017. 10. 28..
 */

public class LoginActivity extends Activity {

    SessionCallback callback;
    private long userID ;
    private String userName = "";
    private String profileUrl = "";
    private int userBoardCnt = 0, userCosCnt = 0, autoLogin = 0;
    private String interestBrand = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //profileImg = (ImageView) findViewById(R.id.kakaoImg);
        /**카카오톡 로그아웃 요청**/
        //한번 로그인이 성공하면 세션 정보가 남아있어서 로그인창이 뜨지 않고 바로 onSuccess()메서드를 호출합니다.
        //테스트 하시기 편하라고 매번 로그아웃 요청을 수행하도록 코드를 넣었습니다 ^^
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                //로그아웃 성공 후
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "로그아웃 성공", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //간편로그인시 호출 ,없으면 간편로그인시 로그인 성공화면으로 넘어가지 않음
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            UserManagement.requestMe(new MeResponseCallback() {

                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;
                    Logger.d(message);

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                }

                @Override
                public void onNotSignedUp() {
                }

                @Override
                public void onSuccess(UserProfile userProfile) {

                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.

                    Logger.v("User profile "+userProfile.toString());


                    System.out.println("프로필이미지 섬네일===>"+userProfile.getProfileImagePath());
                    String kakaoID = String.valueOf(userProfile.getId()); // userProfile에서 ID값을 가져옴
                    String kakaoNickname = userProfile.getNickname();     // Nickname 값을 가져옴
                    System.out.println("카카오아이디  :  "+kakaoID);
                    System.out.println("카카오닉네  :  "+kakaoNickname);
                    // 관심있는 화장품 브랜드 선택(다중선택 가능)
                    final List<String> list = new ArrayList<String>();

                    final String[] brand_items = new String[]{"이니스프리", "미샤", "어퓨", "아리따움", "올리브영", "홀리카홀리카", "에뛰드하우스", "스킨푸드"};


                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                    dialog.setTitle("관심브랜드를 선택해주세요.")
                            .setMultiChoiceItems(
                                    brand_items,
                                    new boolean[]{false, false, false, false, false, false, false, false}
                                    , (dialogInterface, which, isChecked) -> {
                                        if (isChecked) {
                                            Toast.makeText(getApplicationContext(), brand_items[which], Toast.LENGTH_SHORT).show();
                                            list.add(brand_items[which]);
                                        } else {
                                            list.remove(brand_items[which]);
                                        }
                                    }
                            )
                            .setPositiveButton("확인", (dialogInterface, i) -> {
                                String selectedItem = "";
                                for (String item : list) {
                                    selectedItem += item + ", ";
                                }
                                //선택된 관심브랜드 toast로 나옴 - selectedItem  - startActivityForResult 로 넘기기

                                //userID = String.valueOf(userProfile.getId());
                                userID = userProfile.getId();
                                userName = userProfile.getNickname();
                                profileUrl = userProfile.getProfileImagePath();
                                interestBrand = selectedItem;
                                Toast.makeText(getApplicationContext(), userID + ", " + userName + ", " + profileUrl + "," + userBoardCnt + ", " + interestBrand, Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);


                                intent.putExtra("userID", userID);
                                intent.putExtra("userName", userName);
                                intent.putExtra("userBoardCnt", userBoardCnt);
                                intent.putExtra("userCosCnt", userCosCnt);
                                intent.putExtra("ProfileUrl", profileUrl);
                                intent.putExtra("autoLogin", autoLogin);
                                intent.putExtra("interestBrand", interestBrand);

                                startActivity(intent);

                                    finish();

                                }
                            })
                            .setNeutralButton("취소", (dialogInterface, i) -> Toast.makeText(getApplicationContext(), "취소버튼 누름누름", Toast.LENGTH_SHORT).show());
                    dialog.create();
                    dialog.show();


                }
            });


        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            // 세션 연결이 실패했을때
            // 어쩔때 실패되는지는 테스트를 안해보았음 ㅜㅜ
        }
    }
}
