package com.cosmetic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cosmetic.R;
import com.cosmetic.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;


/**
 * Created by gimjihyeon on 2017. 10. 28..
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 1001;

    // Firebase - Realtime Database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    // Firebase - Authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;

    // Views
    private SignInButton mBtnGoogleSignIn; // 로그인 버튼
    private Button mBtnGoogleSignOut; // 로그아웃 버튼
    private TextView mTxtProfileInfo; // 사용자 정보 표시
    private ImageView mImgProfile; // 사용자 프로필 이미지 표시

    // Values
    public String firebaseKey; // Firebase Realtime Database 에 등록된 Key 값
    public String userName; // 사용자 이름
    public String userPhotoUrl; // 사용자 사진 URL
    public String userEmail; // 사용자 이메일주소
    public String userEmailID; // email 주소에서 @ 이전까지의 값.
    public String fcmToken;

    User userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        //initFirebaseDatabase();
        initFirebaseAuth();
        initValues();

    }

    private void initViews() {


        mBtnGoogleSignIn = (SignInButton) findViewById(R.id.btn_google_signin);
        mBtnGoogleSignOut = (Button) findViewById(R.id.btn_google_signout);
        mBtnGoogleSignIn.setOnClickListener(this);
        mBtnGoogleSignOut.setOnClickListener(this);

        mTxtProfileInfo = (TextView) findViewById(R.id.txt_profile_info);
        mImgProfile = (ImageView) findViewById(R.id.img_profile);
    }

    /*private void initFirebaseDatabase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("message");
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User userData = dataSnapshot.getValue(User.class);
                userData.firebaseKey = dataSnapshot.getKey();
                mAdapter.add(userData);
                //mListView.smoothScrollToPosition(mAdapter.getCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String firebaseKey = dataSnapshot.getKey();
                int count = mAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    if (mAdapter.getItem(i).firebaseKey.equals(firebaseKey)) {
                        mAdapter.remove(mAdapter.getItem(i));
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }*/

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                updateProfile();
            }
        };
    }

    private void initValues() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            userName = user.getDisplayName();
            System.out.println("--->User : userName = "+userName);
            fcmToken = FirebaseInstanceId.getInstance().getToken();
            userEmail = user.getEmail();
            userEmailID = userEmail.substring(0, userEmail.indexOf('@'));
            userPhotoUrl = user.getPhotoUrl().toString();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference();
            userData = new User(userName, userPhotoUrl, userEmail, userEmailID, fcmToken);
            mDatabaseReference.child("users").child(userEmailID).setValue(userData);

        }

    }

    private void updateProfile() {
        System.out.println("--->updateProfile");
        FirebaseUser user = mAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        if (user == null) {
            // 비 로그인 상태 (메시지를 전송할 수 없다.)
            System.out.println("--->updateProfile : user == null");
            mBtnGoogleSignIn.setVisibility(View.VISIBLE);
            mBtnGoogleSignOut.setVisibility(View.GONE);
            mTxtProfileInfo.setVisibility(View.GONE);
            mImgProfile.setVisibility(View.GONE);
        } else {

            System.out.println("--->updateProfile : user != null");
            // 로그인 상태
            mBtnGoogleSignIn.setVisibility(View.GONE);
            mBtnGoogleSignOut.setVisibility(View.VISIBLE);
            mTxtProfileInfo.setVisibility(View.VISIBLE);
            mImgProfile.setVisibility(View.VISIBLE);

            //userName = user.getDisplayName(); // 채팅에 사용 될 닉네임 설정
            System.out.println("--->updateProfile : before picasso userName = " +userName);
            /*StringBuilder profile = new StringBuilder();
            profile.append(userName).append("\n").append(user.getEmail());
            mTxtProfileInfo.setText(profile);


            //mAdapter.setEmail(email);
            //mAdapter.notifyDataSetChanged();

            Picasso.with(this).load(user.getPhotoUrl()).into(mImgProfile);*/
            fcmToken = FirebaseInstanceId.getInstance().getToken();
            userEmail = user.getEmail();
            userEmailID = userEmail.substring(0, userEmail.indexOf('@'));
            userPhotoUrl = user.getPhotoUrl().toString();

            /*System.out.println("--->updateProfile : userName = " +userName);
            System.out.println("--->updateProfile : userPhotoUrl = " +userPhotoUrl);
            System.out.println("--->updateProfile : userEmail= " +userEmail);
            System.out.println("--->updateProfile : userEmailID = " +userEmailID);
            System.out.println("--->updateProfile : fcmToken = " +fcmToken);*/

            userData = new User(userName, userPhotoUrl, userEmail, userEmailID, fcmToken);
            mDatabaseReference.child("users").child(userEmailID).setValue(userData);


        }
    }

    private void signIn() {
        System.out.println("--->signIn");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        System.out.println("--->signOut");
        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateProfile();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println("--->onActivityResult....onComplete");
                                    System.out.println("--->updateProfile : userName = " +userName);
                                    System.out.println("--->updateProfile : userPhotoUrl = " +userPhotoUrl);
                                    System.out.println("--->updateProfile : userEmail= " +userEmail);
                                    System.out.println("--->updateProfile : userEmailID = " +userEmailID);
                                    System.out.println("--->updateProfile : fcmToken = " +fcmToken);
                                    Intent intent = new Intent(getApplication(), MainActivity.class);
                                    startActivity(intent);
                                    //finish();
                                }
                            }
                        });
            } else {
                updateProfile();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_google_signin:
                signIn();
                break;
            case R.id.btn_google_signout:
                signOut();
                break;
        }
    }
}
