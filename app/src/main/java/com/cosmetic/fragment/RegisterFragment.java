package com.cosmetic.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cosmetic.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yujeen on 2017. 10. 28..
 */

public class RegisterFragment extends Fragment {

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_ALBUM = 2;
    private static final int CROP_FROM_CAMERA = 3;

    private Uri photoUri;
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private static final int MULTIPLE_PERMISSIONS = 101;

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

    @BindView(R.id.cosBrandBtn) Button cosBrandBtn;
    @BindView(R.id.cosMainCategoryBtn) Button cosMainCategoryBtn;
    @BindView(R.id.cosMidCategoryBtn) Button cosMidCategoryBtn;
    @BindView(R.id.categoryResultTxt) TextView categoryResultTxt;
    @BindView(R.id.iscosOpenCheckbox) CheckBox iscosOpenCheckbox;
    @BindView(R.id.opendateBtn) Button opendateBtn;
    @BindView(R.id.expRadioGroup) RadioGroup expRadioGroup;
    @BindView(R.id.expMonthRadioBtn) RadioButton expMonthRadioBtn;
    @BindView(R.id.expDateRadioBtn) RadioButton expDateRadioBtn;
    @BindView(R.id.expNonRadioBtn) RadioButton expNonRadioBtn;
    @BindView(R.id.expMonthLayout) LinearLayout expMonthLayout;
    @BindView(R.id.expMonthEdt) EditText expMonthEdt;
    @BindView(R.id.expDateLayout) LinearLayout expDateLayout;
    @BindView(R.id.expDateTxt) TextView expDateTxt;
    @BindView(R.id.img_cosmetic) ImageButton cosImg;

    long open = -1, exp, count;
    int cos_No = 0;
    String userID, cos_Name;
    String cos_Brand = "", cos_MainCate = "", cos_MidCate = "", cos_PicUrl = "";
    Date cos_open_date, cos_exp_date;
    int cos_exp_month, cos_IsOpen = 0, cos_ExpDday = 0;
    private Context context;
    private int mYear, mMonth, mDay;

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_registerfragment_ok, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cosBrandBtn.setOnClickListener(v -> showDialog(BRAND_DIALOG_TITLE, cosBrand, (dialog, which) -> {
            brandStr = cosBrand[which];
            cosBrandBtn.setSelected(true);
            updateCategoryResultTxt();
        }));
        cosMainCategoryBtn.setOnClickListener(v -> showDialog(MAIN_CATEGORY_DIALOG_TITLE, cosMainCategory, (dialog, which) -> {
            mainCategoryStr = cosMainCategory[which];
            cosMainCategoryBtn.setSelected(true);
            updateCategoryResultTxt();
        }));
        cosMidCategoryBtn.setOnClickListener(v -> {
            final String[] midCategory = getMidCategory();
            if (midCategory != null) {
                cosMidCategoryBtn.setSelected(true);
                showDialog(MID_CATEGORY_DIALOG_TITLE, midCategory, (dialog, which) -> {
                    midCategoryStr = midCategory[which];
                    updateCategoryResultTxt();
                });
            } else {
                Toast.makeText(getContext(), "화장품 대분류를 선택해주세요.", Toast.LENGTH_LONG).show();
            }
        });
        expRadioGroup.setOnCheckedChangeListener((radioGroup, checkedID) -> {
            switch (checkedID) {
                case R.id.expDateRadioBtn: {
                    expMonthLayout.setVisibility(View.INVISIBLE);
                    expDateLayout.setVisibility(View.VISIBLE);

                    final Calendar c_txt_exp_date = Calendar.getInstance();
                    mYear = c_txt_exp_date.get(Calendar.YEAR);
                    mMonth = c_txt_exp_date.get(Calendar.MONTH);
                    mDay = c_txt_exp_date.get(Calendar.DAY_OF_MONTH);
                    if (open != -1) {
                        exp = c_txt_exp_date.getTimeInMillis() / 86400000;
                    }
                    DatePickerDialog datePickerDialog
                            = new DatePickerDialog(getContext(), (datePicker, year, monthOfYear, dayOfMonth) -> {
                        expDateTxt.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                        //                                Toast.makeText(getContext(), (txt_exp_date.getText().toString()), Toast.LENGTH_SHORT).show();
                        if (open != -1) {
                            // Toast.makeText(getContext(), Long.toString(open) + "," + Long.toString(exp) + "," + String.valueOf(countdday()), Toast.LENGTH_SHORT).show();
                        }
                        try {
                            //cos_exp_date = dateFormat.parse(txt_exp_date.getText().toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();

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

                default:
                    break;
            }
        });
        cosImg.setOnClickListener(listener);

        iscosOpenCheckbox.setOnClickListener(listener_chechbox_OpenDate);
        opendateBtn.setOnClickListener(listener_btn_openDateClick);
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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            count = exp - open;
            cos_ExpDday = (int) count;
            return (int) count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    //이미지 관련
    View.OnClickListener listener = view -> {
        DialogInterface.OnClickListener cameraListener = (dialog, which) -> takePhoto();

        DialogInterface.OnClickListener albumListener = (dialog, which) -> goToAlbum();

        DialogInterface.OnClickListener cancelListener = (dialog, which) -> dialog.dismiss();

        new AlertDialog.Builder(getContext())
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("사진촬영", cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", cancelListener)
                .show();
    };

    /**
     * 카메라에서 이미지 가져오기
     */
   private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            e.printStackTrace();
        }
        if (photoFile != null) {
            photoUri = FileProvider.getUriForFile(getActivity(),
                    "dongster.cameranostest.provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }/**
     * 앨범에서 이미지 가져오기
     */   private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "nostest_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/NOSTest/");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(this.permissions[0])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        } else if (permissions[i].equals(this.permissions[1])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        } else if (permissions[i].equals(this.permissions[2])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        }
                    }
                } else {
                    showNoPermissionToastAndFinish();
                }
                return;
            }
        }
    }

    private void showNoPermissionToastAndFinish() {
        Toast.makeText(getContext(), "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            Toast.makeText(getContext(), "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == PICK_FROM_ALBUM) {
            if (data == null) {
                return;
            }
            photoUri = data.getData();
            cropImage();
        } else if (requestCode == PICK_FROM_CAMERA) {
            cropImage();
            // 갤러리에 나타나게
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{photoUri.getPath()}, null,
                    (path, uri) -> {
                    });
        } else if (requestCode == CROP_FROM_CAMERA) {
            cosImg.setImageURI(null);
            cosImg.setImageURI(photoUri);
        }
    }

    //Android N crop image
    public void cropImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getActivity().grantUriPermission("com.android.camera", photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");

        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getActivity().grantUriPermission(list.get(0).activityInfo.packageName, photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        int size = list.size();
        if (size == 0) {
            Toast.makeText(getActivity(), "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(getActivity(), "용량이 큰 사진의 경우 시간이 오래 걸릴 수 있습니다.", Toast.LENGTH_SHORT).show();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            File croppedFileName = null;
            try {
                croppedFileName = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            File folder = new File(Environment.getExternalStorageDirectory() + "/NOSTest/");
            File tempFile = new File(folder.toString(), croppedFileName.getName());

            photoUri = FileProvider.getUriForFile(getActivity(),
                    "zizin.cosImg.provider", tempFile);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                getActivity().grantUriPermission(res.activityInfo.packageName, photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, CROP_FROM_CAMERA);
        }
    }

    View.OnClickListener listener_chechbox_OpenDate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (iscosOpenCheckbox.isChecked()) {
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
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            open = c.getTimeInMillis() / 86400000; //->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
            DatePickerDialog datePickerDialog
                    = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                    opendateBtn.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");

                }
            }, mYear, mMonth, mDay);

            datePickerDialog.show();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
           /* try {
                cos_open_date = dateFormat.parse(btn_opendate.getText().toString());
                Toast.makeText(getContext(), dateFormat.parse(btn_opendate.getText().toString()).toString(), Toast.LENGTH_LONG).show();
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
        }
    };
}
