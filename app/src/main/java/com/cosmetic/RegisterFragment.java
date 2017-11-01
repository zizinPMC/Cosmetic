package com.cosmetic;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cosmetic.board.UserInfo;
import com.cosmetic.db.CosmeticWriteDB;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.cosmetic.board.UserInfo.profileUrl;

/**
 * Created by yujeen on 2017. 10. 28..
 */

public class RegisterFragment extends Fragment {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private CosmeticWriteDB dbManager;

    private Uri mImageCaptureUri;

    @BindView(R.id.img_cosmetic)
    ImageButton imgbtn_Cos; //화장품 이미지

    @BindView(R.id.btn_cos_Brand)
    Button btn_CosBrand; //화장품 브랜드

    @BindView(R.id.btn_cos_MainCate)
    Button btn_CosMainCategory;    //화장품 대분류 선택

    @BindView(R.id.btn_cos_MidCate)
    Button btn_CosMidCategory;  //화장품 중분류 선택

    @BindView(R.id.edt_cosName)
    EditText edt_cosName;   //화장품 이름입력

    @BindView(R.id.checkbox_cosIsOpen)
    CheckBox checkbox_cosIsOpen;    //화장품 개봉여부

    @BindView(R.id.btn_opendate)
    Button btn_opendate;    //화장품 개봉일자

    @BindView(R.id.radioGroup_exp)
    RadioGroup radioGroup_exp;

    @BindView(R.id.radio_exp_date)
    RadioButton radiobtn_exp_date; //화장품 유통기한(날짜)

    @BindView(R.id.txt_exp_date)
    TextView txt_exp_date;  //화장품 유통기한(날짜)

    @BindView(R.id.radio_exp_month)
    RadioButton radiobtn_exp_month;  //화장품 유통기한(월)

    @BindView(R.id.edt_exp_month)
    EditText edt_exp_month;//화장품 유통기한 (월)
    @BindView(R.id.txt_exp_month)
    TextView txt_exp_month;//화장품 유통기한 (월)

    @BindView(R.id.radio_exp_none)
    RadioButton radiobtn_exp_none;

    @BindView(R.id.btn_cancel)
    Button btn_cancel;

    @BindView(R.id.btn_register)
    Button btn_register;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbManager = new CosmeticWriteDB();

        context = getContext();
        imgbtn_Cos.setOnClickListener(listener);
        checkbox_cosIsOpen.setOnClickListener(listener_chechbox_OpenDate);
        btn_opendate.setOnClickListener(listener_btn_openDateClick);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final String[] cos_brand = {"네이처리퍼블릭", "더샘", "더페이스샵", "마몽드", "맥", "미샤", "베네피트", "비욘드", "시드물", "아리따움", "어퓨", "에뛰드하우스", "이니스프리", "키스미", "토니모리", "페리페라", "홀리카홀리카", "기타"};
        final String[] cos_maincate = {"스킨 케어", "페이스 메이크업", "아이 메이크업", "립 메이크업", "클렌징"};
        final String[] cos_midcate_skincare = {"토너, 스킨", "로션, 에멀젼", "크림", "아이 케어", "미스트", "마사지 팩", "선 케어", "기타"};
        final String[] cos_midcate_facemakeup = {"베이스", "파운데이션", "컨실러", "파우더", "치크, 하이라이터, 쉐딩", "기타"};
        final String[] cos_midcate_eyemakeup = {"아이섀도우", "마스카라", "아이라이너", "아이브로우", "기타"};
        final String[] cos_midcate_lipmakeup = {"틴트, 립 라커", "립스틱", "립글로스", "기타"};
        final String[] cos_midcate_cleansing = {"폼 클렌징", "클렌징 오일, 크림, 밤", "리무버", "기타"};


        btn_CosBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("화장품 브랜드를 선택해주세요")
                        .setItems(cos_brand, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                btn_CosBrand.setText(cos_brand[i]);

                                //for input database
                                cos_Brand = btn_CosBrand.getText().toString();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        //화장품 대분류 선택
        btn_CosMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //알림창의 속성 설정
                builder.setTitle("화장품 대분류를 선택해주세요")
                        .setItems(cos_maincate, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int index) {
                                btn_CosMainCategory.setText(cos_maincate[index]);
                                Toast.makeText(getContext(), btn_CosMainCategory.getText().toString(), Toast.LENGTH_LONG).show();
                                //for input DB
                                cos_MainCate = btn_CosMainCategory.getText().toString();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });
        btn_CosMidCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_CosMainCategory.getText().toString().equals("스킨케어")) {
                    //알림창의 속성 설정
                    builder.setTitle("화장품 중분류 선택하기")
                            .setItems(cos_midcate_skincare, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int index) {
                                    btn_CosMidCategory.setText(cos_midcate_skincare[index]);
                                    cos_MidCate = cos_midcate_facemakeup[index];
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else if (btn_CosMainCategory.getText().toString().equals("페이스 메이크업")) {
                    //알림창의 속성 설정
                    builder.setTitle("화장품 중분류 선택하기")
                            .setItems(cos_midcate_facemakeup, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int index) {
                                    btn_CosMidCategory.setText(cos_midcate_facemakeup[index]);
                                    cos_MidCate = cos_midcate_facemakeup[index];
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else if (btn_CosMainCategory.getText().toString().equals("아이 메이크업")) {
                    //알림창의 속성 설정
                    builder.setTitle("화장품 중분류 선택하기")
                            .setItems(cos_midcate_eyemakeup, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int index) {
                                    btn_CosMidCategory.setText(cos_midcate_eyemakeup[index]);
                                    cos_MidCate = cos_midcate_facemakeup[index];
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else if (btn_CosMainCategory.getText().toString().equals("립 메이크업")) {
                    //알림창의 속성 설정
                    builder.setTitle("화장품 중분류 선택하기")
                            .setItems(cos_midcate_lipmakeup, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int index) {
                                    btn_CosMidCategory.setText(cos_midcate_lipmakeup[index]);
                                    cos_MidCate = cos_midcate_facemakeup[index];
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else if (btn_CosMainCategory.getText().toString().equals("클렌징")) {
                    //알림창의 속성 설정
                    builder.setTitle("화장품 중분류 선택하기")
                            .setItems(cos_midcate_cleansing, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int index) {
                                    btn_CosMidCategory.setText(cos_midcate_cleansing[index]);
                                    //for input DB
                                    cos_MidCate = cos_midcate_facemakeup[index];
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });
        radioGroup_exp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedID) {
                switch (checkedID) {
                    case R.id.radio_exp_date: {
                        edt_exp_month.setVisibility(View.INVISIBLE);
                        txt_exp_month.setVisibility(View.INVISIBLE);

                        txt_exp_date.setVisibility(View.VISIBLE);
                        final Calendar c_txt_exp_date = Calendar.getInstance();
                        mYear = c_txt_exp_date.get(Calendar.YEAR);
                        mMonth = c_txt_exp_date.get(Calendar.MONTH);
                        mDay = c_txt_exp_date.get(Calendar.DAY_OF_MONTH);
                        if (open != -1) {
                            exp = c_txt_exp_date.getTimeInMillis() / 86400000;
                        }
                        DatePickerDialog datePickerDialog
                                = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                txt_exp_date.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
//                                Toast.makeText(getContext(), (txt_exp_date.getText().toString()), Toast.LENGTH_SHORT).show();
                                if (open != -1) {
                                    // Toast.makeText(getContext(), Long.toString(open) + "," + Long.toString(exp) + "," + String.valueOf(countdday()), Toast.LENGTH_SHORT).show();
                                }
                                try {
                                    cos_exp_date = dateFormat.parse(txt_exp_date.getText().toString());

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, mYear, mMonth, mDay);
                        datePickerDialog.show();

                        break;
                    }
                    case R.id.radio_exp_month: {
                        txt_exp_date.setVisibility(View.INVISIBLE);
                        edt_exp_month.setVisibility(View.VISIBLE);
                        txt_exp_month.setVisibility(View.VISIBLE);
                        if (!edt_exp_month.getText().toString().equals("")) {
                            cos_exp_month = Integer.parseInt(edt_exp_month.getText().toString());
                        }

                        break;
                    }
                    case R.id.radio_exp_none:
                        txt_exp_date.setVisibility(View.INVISIBLE);
                        edt_exp_month.setVisibility(View.INVISIBLE);
                        txt_exp_month.setVisibility(View.INVISIBLE);

                    default:
                        break;
                }
            }
        });

    }

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
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakePhotoAction();
                }
            };

            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeAlbumAction();
                }
            };

            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            new AlertDialog.Builder(context)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNeutralButton("앨범선택", albumListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();
        }

    };

    /**
     * 카메라에서 이미지 가져오기
     */
    private void doTakePhotoAction() {
    /*
     * 참고 해볼곳
     * http://2009.hfoss.org/Tutorial:Camera_and_Gallery_Demo
     * http://stackoverflow.com/questions/1050297/how-to-get-the-url-of-the-captured-image
     * http://www.damonkohler.com/2009/02/android-recipes.html
     * http://www.firstclown.us/tag/android/
     */

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        // 특정기기에서 사진을 저장못하는 문제가 있어 다음을 주석처리 합니다.
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    /**
     * 앨범에서 이미지 가져오기
     */
    private void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case CROP_FROM_CAMERA: {
// 크롭이 된 이후의 이미지를 넘겨 받습니다.
// 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
// 임시 파일을 삭제합니다.
                final Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    imgbtn_Cos.setImageBitmap(photo);
                }

                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }

                break;
            }

            case PICK_FROM_ALBUM: {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.

                mImageCaptureUri = data.getData();
                cos_PicUrl = mImageCaptureUri.toString();
            }

            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.

                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image");

                intent.putExtra("outputX", 90);
                intent.putExtra("outputY", 90);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);
                imgbtn_Cos.setAdjustViewBounds(true);

                break;
            }
        }
    }

    View.OnClickListener listener_chechbox_OpenDate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (checkbox_cosIsOpen.isChecked()) {
                btn_opendate.setVisibility(View.VISIBLE);
            } else {
                btn_opendate.setVisibility(View.INVISIBLE);
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
                    btn_opendate.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");

                }
            }, mYear, mMonth, mDay);

            datePickerDialog.show();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
            try {
                cos_open_date = dateFormat.parse(btn_opendate.getText().toString());
                Toast.makeText(getContext(), dateFormat.parse(btn_opendate.getText().toString()).toString(), Toast.LENGTH_LONG).show();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };
}