package com.cosmetic.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cosmetic.Navigator;
import com.cosmetic.R;
import com.cosmetic.db.DBHelper;
import com.cosmetic.model.UserSave;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yujeen on 2017. 10. 28..
 */

public class RegisterFragment extends Fragment {

    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_PHOTO = 2222;
    private static final int REQUEST_TAKE_ALBUM = 3333;
    private static final int REQUEST_IMAGE_CROP = 4444;
    private final long FINISH_INTERVAL_TIME = 2000;

    private long backPressedTime = 0;
    private Uri imageUri;
    private Uri photoURI, albumURI;
    /*private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};*/

    private String mCurrentPhotoPath;

    private static final String BRAND_DIALOG_TITLE = "브랜드를 선택해주세요.";
    private static final String MAIN_CATEGORY_DIALOG_TITLE = "대분류를 선택해주세요.";
    private static final String MID_CATEGORY_DIALOG_TITLE = "중분류를 선택해주세요.";
    private final String[] cosBrand = {"네이처리퍼블릭", "더샘", "더페이스샵", "마몽드", "맥", "미샤", "베네피트", "비욘드", "시드물", "아리따움", "어퓨", "에뛰드하우스", "이니스프리", "키스미", "토니모리", "페리페라", "홀리카홀리카", "기타"};
    private final String[] cosMainCategory = {"스킨케어", "페이스 메이크업", "아이 메이크업", "립 메이크업", "클렌징"};
    private final String[] cosMidCategorySkin = {"토너, 스킨", "로션, 에멀젼", "크림", "아이 케어", "미스트", "마사지 팩", "선 케어", "기타"};
    private final String[] cosMidCategoryFace = {"베이스", "파운데이션", "컨실러", "파우더", "치크, 하이라이터, 쉐딩", "기타"};
    private final String[] cosMidCategoryEye = {"아이섀도우", "마스카라", "아이라이너", "아이브로우", "기타"};
    private final String[] cosMidCategoryLip = {"틴트, 립 라커", "립스틱", "립글로스", "기타"};
    private final String[] cosMidCategoryCleansing = {"폼 클렌징", "클렌징 오일, 크림, 밤", "리무버", "기타"};
    private String brandStr = "브랜드";
    private String mainCategoryStr = "대분류";
    private String midCategoryStr = "중분류";


    @BindView(R.id.cosBrandBtn)
    Button cosBrandBtn;
    @BindView(R.id.cosMainCategoryBtn)
    Button cosMainCategoryBtn;
    @BindView(R.id.cosMidCategoryBtn)
    Button cosMidCategoryBtn;
    @BindView(R.id.categoryResultTxt)
    TextView categoryResultTxt;
    @BindView(R.id.inputCosmeticName)
    EditText cosName;
    @BindView(R.id.iscosOpenCheckbox)
    CheckBox iscosOpenCheckbox;
    @BindView(R.id.opendateBtn)
    Button opendateBtn;
    @BindView(R.id.opendateTxt)
    TextView opendateTxt;
    @BindView(R.id.expRadioGroup)
    RadioGroup expRadioGroup;
    @BindView(R.id.expMonthRadioBtn)
    RadioButton expMonthRadioBtn;
    @BindView(R.id.expDateRadioBtn)
    RadioButton expDateRadioBtn;
    @BindView(R.id.expNonRadioBtn)
    RadioButton expNonRadioBtn;
    @BindView(R.id.expMonthLayout)
    LinearLayout expMonthLayout;
    @BindView(R.id.expMonthEdt)
    EditText expMonthEdt;
    @BindView(R.id.expDateLayout)
    LinearLayout expDateLayout;
    @BindView(R.id.expDateTxt)
    TextView expDateTxt;
    @BindView(R.id.img_cosmetic)
    ImageButton cosImg;
    @BindView(R.id.ic_check)
    Button check;

    long open = -1, exp, count;
    String notice = "사용자가 사용기한을 입력하지 않을시에는 화장품의 대분류 별로 기본값이 들어가게 됩니다"
            + "\n" + "스킨케어: 12개월" + "\n" + "페이스 메이크업: 12개월" + "\n" + "아이 메이크업: 18개월"
            + "\n" + "립 메이크업: 12개월" + "\n" + "클렌징: 12개월";
    String user_ID, cos_Name;
    String cos_Brand = "", cos_MainCate = "", cos_MidCate = "", cos_PicUrl = "";
    Date cos_open_date, cos_exp_date;
    int cos_exp_month, cos_IsOpen = 0, cos_ExpDday = 0;
    private Context context;

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    private void showDialog(String title, String[] items, DialogInterface.OnClickListener clickListener) {
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setItems(items, clickListener)
                .show();
    }


    private void updateCategoryResultTxt() {
        final StringBuilder builder = new StringBuilder("[").append(brandStr).append("], [")
                .append(mainCategoryStr).append("], [")
                .append(midCategoryStr).append("]");
        categoryResultTxt.setText(builder);
    }

    private String[] getMidCategory() {
        if (!mainCategoryStr.equals("대분류")) {
            switch (mainCategoryStr) {
                case "스킨케어":
                    return cosMidCategorySkin;
                case "페이스 메이크업":
                    return cosMidCategoryFace;
                case "아이 메이크업":
                    return cosMidCategoryEye;
                case "립 메이크업":
                    return cosMidCategoryLip;
                case "클렌징":
                    return cosMidCategoryCleansing;
            }
        }
        return null;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final DBHelper dbHelper = new DBHelper(getContext(), "Cosmetics.db", null, 2);
        cosBrandBtn.setOnClickListener(v -> showDialog(BRAND_DIALOG_TITLE, cosBrand, (dialog, which) -> {
            brandStr = cosBrand[which];
            cosBrandBtn.setSelected(true);
            updateCategoryResultTxt();
            cos_Brand = cosBrandBtn.getText().toString();
        }));
        cosMainCategoryBtn.setOnClickListener(v -> showDialog(MAIN_CATEGORY_DIALOG_TITLE, cosMainCategory, (dialog, which) -> {
            mainCategoryStr = cosMainCategory[which];
            cosMainCategoryBtn.setSelected(true);
            updateCategoryResultTxt();
            cos_MainCate = cosMainCategoryBtn.getText().toString();
        }));
        cosMidCategoryBtn.setOnClickListener(v -> {
            final String[] midCategory = getMidCategory();
            if (midCategory != null) {
                cosMidCategoryBtn.setSelected(true);
                showDialog(MID_CATEGORY_DIALOG_TITLE, midCategory, (dialog, which) -> {
                    midCategoryStr = midCategory[which];
                    updateCategoryResultTxt();
                    cos_MidCate = cosMidCategoryBtn.getText().toString();
                });
            } else {
                Toast.makeText(getContext(), "화장품 대분류를 선택해주세요.", Toast.LENGTH_LONG).show();
            }
        });

        cos_Name = cosName.getText().toString();
        expRadioGroup.setOnCheckedChangeListener((radioGroup, checkedID) -> {
            switch (checkedID) {
                case R.id.expDateRadioBtn: {
                    expMonthLayout.setVisibility(View.INVISIBLE);
                    expDateLayout.setVisibility(View.VISIBLE);
                    expDateRadioBtn.setOnClickListener(listener_Radiobtn_ExpDateClick);

                    break;
                }
                case R.id.expMonthRadioBtn: {
                    expDateLayout.setVisibility(View.INVISIBLE);
                    expMonthLayout.setVisibility(View.VISIBLE);
                    if (!expMonthEdt.getText().toString().equals("")) {
                        cos_exp_month = Integer.parseInt(expMonthEdt.getText().toString());
                    }
                    break;
                }
                case R.id.expNonRadioBtn:
                    expMonthLayout.setVisibility(View.INVISIBLE);
                    expDateLayout.setVisibility(View.INVISIBLE);

                    new AlertDialog.Builder(getContext())
                            .setTitle("< 사용기한에 대한 안내 >")
                            .setMessage(notice)
                            .setPositiveButton("알겠습니다", null)
                            .show();
                default:
                    break;
            }
        });
        cosImg.setOnClickListener(listener);

        iscosOpenCheckbox.setOnClickListener(listener_chechbox_OpenDate);
        opendateBtn.setOnClickListener(listener_btn_openDateClick);
        check.setOnClickListener(view1 -> {
            if (brandStr.equals("브랜드") == false | mainCategoryStr.equals("대분류") == false | midCategoryStr.equals("중분류") == false | cos_Name.equals("") == false) {
                dbHelper.insert(cos_PicUrl, cosName.getText().toString(), brandStr, mainCategoryStr, midCategoryStr, countdday(), UserSave.getUserEmailID());

                //Toast.makeText(getContext(), dbHelper.getResult(), Toast.LENGTH_LONG).show();
                Navigator.goMain(getContext());
            } else if (brandStr.equals("브랜드") | mainCategoryStr.equals("대분류") | midCategoryStr.equals("중분류") | cos_Name.equals("")) {
                Toast.makeText(getContext(), "값을 모두 입력했는지 확인해주세요 ", Toast.LENGTH_LONG).show();
            }

        });
    }


    /*
    btn_register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String userprofileurl = profileUrl;
            String usernickname = UserInfo.userName;

            userID = usernickname;
            if (edt_cosName.getText().toString() != null) {
                //화장품 이름
                cos_Name = edt_cosName.getText().toString();

                dbManager.cosmeticDBManager(cos_No, userID, cos_Name, cos_Brand, cos_MainCate,
                        cos_MidCate, cos_IsOpen, cos_open_date, cos_ExpDday, cos_PicUrl);
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("ProfileUrl", userprofileurl);
                intent.putExtra("userName", usernickname);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "화장품 이름을 입력해주세요.", Toast.LENGTH_LONG).show();
            }
        }
    });


}
*/


    public int countdday() {
        try {
            if (expNonRadioBtn.isChecked()) {
                if (mainCategoryStr.equals("아이 메이크업")) {
                    return 18 * 30;
                } else {
                    return 12 * 30;
                }
            } else if (expMonthRadioBtn.isChecked()) {
                return Integer.parseInt(expMonthEdt.getText().toString()) * 30;
            } else {
                count = exp - open;
                cos_ExpDday = (int) count;
                return (int) count;
            }

        } catch (
                Exception e)

        {
            e.printStackTrace();
            return -1;
        }

    }

    //이미지 관련
    View.OnClickListener listener = view -> {
        DialogInterface.OnClickListener cameraListener = (dialog, which) -> captureCamera();

        DialogInterface.OnClickListener albumListener = (dialog, which) -> {
            getAlbum();
            checkPermission();
        };

        DialogInterface.OnClickListener cancelListener = (dialog, which) -> dialog.dismiss();

        new AlertDialog.Builder(getContext())
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("앨범선택", albumListener)
                .setNegativeButton("취소", cancelListener)
                .show();
    };

    private void captureCamera() {
        String state = Environment.getExternalStorageState();
        // 외장 메모리 검사
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.e("captureCamera Error", ex.toString());
                }
                if (photoFile != null) {
                    // getUriForFile의 두 번째 인자는 Manifest provier의 authorites와 일치해야 함

                    Uri providerURI = FileProvider.getUriForFile(getContext(), getActivity().getPackageName(), photoFile);
                    imageUri = providerURI;

                    // 인텐트에 전달할 때는 FileProvier의 Return값인 content://로만!!, providerURI의 값에 카메라 데이터를 넣어 보냄
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);

                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        } else {
            Toast.makeText(getContext(), "저장공간이 접근 불가능한 기기입니다", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "PMC");

        if (!storageDir.exists()) {
            Log.i("mCurrentPhotoPath1", storageDir.toString());
            storageDir.mkdirs();
        }

        imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }


    private void getAlbum() {
        Log.i("getAlbum", "Call");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    private void galleryAddPic() {
        Log.i("galleryAddPic", "Call");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        cos_PicUrl = contentUri.toString();
        getActivity().sendBroadcast(mediaScanIntent);
        //Toast.makeText(getContext(), "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    // 카메라 전용 크랍
    public void cropImage() {
        Log.i("cropImage", "Call");
        Log.i("cropImage", "photoURI : " + photoURI + " / albumURI : " + albumURI);

        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        // 50x50픽셀미만은 편집할 수 없다는 문구 처리 + 갤러리, 포토 둘다 호환하는 방법
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI, "image/*");
        //cropIntent.putExtra("outputX", 200); // crop한 이미지의 x축 크기, 결과물의 크기
        //cropIntent.putExtra("outputY", 200); // crop한 이미지의 y축 크기
        cropIntent.putExtra("aspectX", 1); // crop 박스의 x축 비율, 1&1이면 정사각형
        cropIntent.putExtra("aspectY", 1); // crop 박스의 y축 비율
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("output", albumURI); // 크랍된 이미지를 해당 경로에 저장
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Log.i("REQUEST_TAKE_PHOTO", "OK");
                        galleryAddPic();

                        cosImg.setImageURI(imageUri);
                    } catch (Exception e) {
                        Log.e("REQUEST_TAKE_PHOTO", e.toString());
                    }
                } else {
                    Toast.makeText(getContext(), "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_TAKE_ALBUM:
                if (resultCode == Activity.RESULT_OK) {

                    if (data.getData() != null) {
                        try {
                            File albumFile = null;
                            albumFile = createImageFile();
                            photoURI = data.getData();
                            albumURI = Uri.fromFile(albumFile);
                            cropImage();
                        } catch (Exception e) {
                            Log.e("TAKE_ALBUM_SINGLE ERROR", e.toString());
                        }
                    }
                }
                break;

            case REQUEST_IMAGE_CROP:
                if (resultCode == Activity.RESULT_OK) {

                    galleryAddPic();
                    cosImg.setImageURI(albumURI);
                }
                break;
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
                    (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA))) {
                new AlertDialog.Builder(getContext())
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getActivity().finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CAMERA:
                for (int i = 0; i < grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        Toast.makeText(getActivity(), "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서..

                break;
        }
    }

    View.OnClickListener listener_chechbox_OpenDate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (iscosOpenCheckbox.isChecked()) {
                opendateTxt.setVisibility(View.VISIBLE);
                opendateBtn.setVisibility(View.VISIBLE);
            } else {
                opendateBtn.setVisibility(View.INVISIBLE);
                cos_PicUrl = "";
                cos_ExpDday = 0;
                cos_IsOpen = 1;
                cos_open_date = null;
            }
        }
    };
    //개봉일 - 날짜
    View.OnClickListener listener_btn_openDateClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // API 24 이상일 경우 시스템 기본 테마 사용
                context = getContext();
            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, dateSetListener, year, month, day);
            datePickerDialog.show();

        }
    };
    private DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth)
            -> {

        String showOpenDate = year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일";
        opendateBtn.setText(showOpenDate);


        DateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
        String date = String.valueOf(year) + String.valueOf(monthOfYear + 1) + String.valueOf(dayOfMonth);
        //Toast.makeText(getContext(), date, Toast.LENGTH_SHORT).show();
        try {
            Date openDate = transFormat.parse(date);
            Toast.makeText(getContext(), transFormat.format(openDate), Toast.LENGTH_SHORT).show();
            cos_open_date = openDate;
            RegisterFragment obj = new RegisterFragment();
            Calendar calendar = obj.dateToCalendar(openDate);
            open = calendar.getTimeInMillis() / 86400000;//->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
            System.out.println("time : ----->" + open);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    private Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    //사용기한 - 날짜
    View.OnClickListener listener_Radiobtn_ExpDateClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // API 24 이상일 경우 시스템 기본 테마 사용
                context = getContext();
            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, dateSetListenerExp, year, month, day);
            datePickerDialog.show();

        }
    };
    private DatePickerDialog.OnDateSetListener dateSetListenerExp = (view, year, monthOfYear, dayOfMonth)
            -> {

        String showExpDate = year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일";
        expDateTxt.setText(showExpDate);


        DateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
        String date = String.valueOf(year) + String.valueOf(monthOfYear + 1) + String.valueOf(dayOfMonth);
        //Toast.makeText(getContext(), date, Toast.LENGTH_SHORT).show();
        try {
            Date expDate = transFormat.parse(date);
            Toast.makeText(getContext(), transFormat.format(expDate), Toast.LENGTH_SHORT).show();
            cos_exp_date = expDate;
            RegisterFragment obj = new RegisterFragment();
            Calendar calendar = obj.dateToCalendar(expDate);
            exp = calendar.getTimeInMillis() / 86400000;//->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
            System.out.println("time : ----->" + exp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
}
