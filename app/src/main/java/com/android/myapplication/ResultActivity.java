package com.android.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {
    TextView txtThucLai,txtTongLaiVaGoc;
    Button btnBack,btnTake;
    ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //

        addControl();
        addEvent();

        processData();

    }
    void addControl()
    {
        txtThucLai = (TextView) findViewById(R.id.textViewLaiNhan);
        txtTongLaiVaGoc = (TextView) findViewById(R.id.textViewLaiVaGoc);
        btnTake = (Button) findViewById(R.id.buttonTake);
        btnBack = (Button) findViewById(R.id.buttonBack);
        imgView = (ImageView) findViewById(R.id.imageView);
    }
    void addEvent()
    {
        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityCamera.launch(intent);
            }
        });
        //Đưa dữ liệu lại MainActivity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Bundle bundle = intent.getBundleExtra("dulieubundle");
                int tienGui = bundle.getInt("tienGui",0);
                int laiSuat = bundle.getInt("laiSuat",0);
                int kyHan = bundle.getInt("kyHan",0);


                Intent intentback = new Intent();
                intentback.putExtra("tienGui",tienGui);
                intentback.putExtra("laiVaGoc",laiSuat);
                intentback.putExtra("kyHan",kyHan);
                setResult(RESULT_OK,intentback);
                finish();
            }
        });
    }

    void processData()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dulieubundle");
        if(bundle!=null)
        {
            DecimalFormat formatnumber = new DecimalFormat("#,###,###,###");
            int laiThucTe = bundle.getInt("tienlai",0);
            int laiVaGoc = bundle.getInt("tongtiennhan",0);
            //formatnumber.format(laiThucTe);
            txtThucLai.setText(formatnumber.format(laiThucTe) +" đ");
            txtTongLaiVaGoc.setText(formatnumber.format(laiVaGoc) +" đ");

            //txtBundle.setText(txtBundle.getText().toString() +ten + "\n" + so +"\n"+mang[0]+"\n"+hocsinh.getTen());
        }
    }
    ActivityResultLauncher<Intent> activityCamera  = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    if(intent!=null)
                    {
                        Bitmap bitmap = (Bitmap) intent.getExtras().get("data");//"data" là mặc định
                        imgView.setImageBitmap(bitmap);
                        Intent intentX = new Intent(ResultActivity.this, MainActivity.class);
                        startActivity(intentX);
                    }
                }
            });

}