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
    public static int logout = 0; //마이페이지에서 로그아웃버튼을 눌러서 로그인액티비티로 넘어온것인지 판별
    // Firebase - Realtime Database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    // Firebase - Authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseUser user;

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

        System.out.println("---> LoginActivity onCreate()...logout = "+logout);
        initViews();
        initFirebaseAuth();
        initValues();

    }

    private void initViews() {
        System.out.println("--->initViews()");

        mBtnGoogleSignIn = (SignInButton) findViewById(R.id.btn_google_signin);
        mBtnGoogleSignOut = (Button) findViewById(R.id.btn_google_signout);
        mBtnGoogleSignIn.setOnClickListener(this);
        mBtnGoogleSignOut.setOnClickListener(this);

        mTxtProfileInfo = (TextView) findViewById(R.id.txt_profile_info);
        mImgProfile = (ImageView) findViewById(R.id.img_profile);
    }


    private void initFirebaseAuth() {
        System.out.println("--->initFirebaseAuth()");
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
        System.out.println("--->initValues()");
        user = mAuth.getCurrentUser();
        if (user != null) {
        }



    }

    private void updateProfile() {
        System.out.println("--->updateProfile");
        user = mAuth.getCurrentUser();

        if (user == null){
        }else {
            System.out.println("--->updateProfile : user != null");
            userName = user.getDisplayName();
            fcmToken = FirebaseInstanceId.getInstance().getToken();
            userEmail = user.getEmail();
            userEmailID = userEmail.substring(0, userEmail.indexOf('@'));
            userPhotoUrl = user.getPhotoUrl().toString();

            mDatabaseReference = FirebaseDatabase.getInstance().getReference();
            System.out.println("--->updateProfile  userData = new User(...)");
            userData = new User(userName, userPhotoUrl, userEmail, userEmailID, fcmToken);
            mDatabaseReference.child("users").child(userEmailID).setValue(userData);


        }
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        logout=0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("--->onActivityResult()");
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                System.out.println("--->onActivityResult....result.isSuccess() ");
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
                                    System.out.println("--->onActivityResult....onComplete()...task.isSuccessful logout="+logout );
                                    if(logout==0){
                                        Intent intent = new Intent(getApplication(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                    logout=0;
                                }
                            }
                        });
            } else {
                System.out.println("--->onActivityResult....result.isSuccess() is not ");
                updateProfile();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_google_signin:
                if(logout==1) {
                    System.out.println("--->onClick() btn_google_signin...logout=1");
                    signOut();
                    signIn();
                }
                else if(logout==0) {
                    System.out.println("--->onClick() btn_google_signin...logout=0");
                    signIn();
                }
                break;
            case R.id.btn_google_signout:
                signOut();
                break;
        }
    }

    @Override
    public void onBackPressed()
    {

        moveTaskToBack(true);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());


    }
}
