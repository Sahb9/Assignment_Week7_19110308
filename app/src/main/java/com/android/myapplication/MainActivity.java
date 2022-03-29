package com.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //TextView txtDong,txtPhanTram,txtThang;
    EditText txtTienGui, txtLaiSuat, txtKyHan;
    Button btnClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControll();
        addEvent();
    }

    void addControll() {
        txtTienGui = (EditText) findViewById(R.id.editTextSoTienGui);
        txtLaiSuat = (EditText) findViewById(R.id.editTextLaiSuatGui);
        txtKyHan = (EditText) findViewById(R.id.editTextKyHanGui);
        btnClick = (Button) findViewById(R.id.buttonClick);

    }

    void addEvent() {
        txtTienGui.addTextChangedListener(onTextChangedListener());
       // txtTienGui.getText().toString().replaceAll(",", "");
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //showing formatted text and original text of EditText to TextView
//                textView.setText(String.format("Formatted number value: %s\nOriginal input: %s",
//                        editText.getText().toString(),
//                        editText.getText().toString().replaceAll(",", "")));
//            }
//        });
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);

                //int tienGui = Integer.parseInt(txtTienGui.getText().toString());
                int tienGui = Integer.parseInt(txtTienGui.getText().toString().replaceAll(",", ""));
                int laiSuat = Integer.parseInt(txtLaiSuat.getText().toString());
                int kyHan = Integer.parseInt(txtKyHan.getText().toString());
                int result = (tienGui * laiSuat * kyHan / 12) / 100;
                Bundle bundle = new Bundle();
                bundle.putInt("tienGui", tienGui);
                bundle.putInt("laiSuat", laiSuat);
                bundle.putInt("kyHan", kyHan);
                bundle.putInt("tienlai", result);
                bundle.putInt("tongtiennhan", result + tienGui);
                intent.putExtra("dulieubundle", bundle);
                activityLauncher.launch(intent);
               // startActivity(intent);


            }
        });
    }
    //Lấy dữ liệu lại từ bên result
    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            int tienGui = intent.getIntExtra("tienGui",0);
                            int laiVaGoc = intent.getIntExtra("laiVaGoc",0);
                            int kyHan = intent.getIntExtra("kyHan",0);
                           // Toast.makeText(this,tienGui + "-"+);
                            txtTienGui.setText(tienGui+"");
                            txtLaiSuat.setText(laiVaGoc+"");
                            txtKyHan.setText(kyHan+"");

                        }

                    }
                }
            });


    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtTienGui.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    txtTienGui.setText(formattedString);
                    txtTienGui.setSelection(txtTienGui.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                txtTienGui.addTextChangedListener(this);
            }
        };
    }
}