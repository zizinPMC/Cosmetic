package com.cosmetic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.cosmetic.Navigator;
import com.cosmetic.R;
import com.cosmetic.log.Logger;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
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
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getUserInfoFromKakao(){
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onNotSignedUp() {

            }

            @Override
            public void onSuccess(UserProfile result) {
                Logger.v("User profile "+result.toString());
                Navigator.goMain(LoginActivity.this);

            }
        });
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            getUserInfoFromKakao();

           /* UserManagement.requestMe(new MeResponseCallback() {

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
                            ).setNeutralButton("취소", (dialogInterface, i) -> Toast.makeText(getApplicationContext(), "취소버튼 누름누름", Toast.LENGTH_SHORT).show());
                    dialog.create();
                    dialog.show();
*/

         /*       }
            });*/


        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }
}
