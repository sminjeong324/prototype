package com.example.waterdolphin3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
                //btnLogin.setBackgroundColor(colorPrimary);


                String loginid = edtEmail.getText().toString();
                String loginpwd = edtPassword.getText().toString();
                try {
                    String result  = new CustomTask().execute(loginid,loginpwd,"login").get();
                    if(result.equals("true")) {
                        Toast.makeText(LoginActivity.this,"로그인",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if(result.equals("false")) {
                        Toast.makeText(LoginActivity.this,"아이디 or 비밀번호가 다름",Toast.LENGTH_SHORT).show();
                        edtEmail.setText("");
                        edtPassword.setText("");
                    } else if(result.equals("noId")) {
                        Toast.makeText(LoginActivity.this,"존재하지 않는 아이디",Toast.LENGTH_SHORT).show();
                        edtEmail.setText("");
                        edtPassword.setText("");
                    }
                }catch (Exception e) {}
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
    }//OnCreate
    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.0.100:8080/water/data.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id="+strings[0]+"&pwd="+strings[1]+"&type="+strings[2];
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }
}