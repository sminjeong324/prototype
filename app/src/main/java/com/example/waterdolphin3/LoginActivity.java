package com.example.waterdolphin3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
EditText edtEmail, edtPassword;
CheckBox cbAutoLogin;
Button btnLogin;
TextView tvLostPw, tvJoin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        cbAutoLogin = findViewById(R.id.cbAutoLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvLostPw = findViewById(R.id.tvLostPw);
        tvJoin = findViewById(R.id.tvJoin);

        //키패드 칠때 엔터 누를 때 : [edtEmail] ->  [edtPassword] -> [로그인] 기능 구현
        edtEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if(id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL)
                    return true;

                return false;
            }
        });

        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if(id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {

                    return true;
                }
                return false;
            }
        });

        //로그인 버튼 눌렀을 때 이벤트 처리
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int colorPrimary = getResources().getColor(R.color.colorPrimary);
                btnLogin.setBackgroundColor(colorPrimary);

                //서버 연동하면 수정 부탁
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        //자동로그인 구현(미완)
        if(cbAutoLogin.isChecked()) {

        }

        //비번 찾기 및 회원가입 하이퍼링크 구현
        String textIdPw = "비밀번호를 잊으셨나요?";
        String join = "회원가입하기";

        tvLostPw.setText(textIdPw);
        tvJoin.setText(join);

        Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return "";
            }
        };

        Pattern pattern1 = Pattern.compile("비밀번호를 잊으셨나요?");
        Pattern pattern2 = Pattern.compile("회원가입하기");

        Linkify.addLinks(tvLostPw, pattern1, "http://www.naver.com/", null, mTransform);
        Linkify.addLinks(tvJoin, pattern2, "http://www.naver.com/", null, mTransform);
    }
}