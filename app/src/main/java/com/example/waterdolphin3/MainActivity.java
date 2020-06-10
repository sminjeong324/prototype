package com.example.waterdolphin3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
private BottomNavigationView bottomNavigationView;
private FragmentManager fragmentManager;
private FragmentTransaction fragmentTransaction;
private MainFragment mainFragment;
private RewardFragment rewardFragment;
private HistoryFragment historyFragment;
private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //아이콘 클릭에 따라 프래그먼트 바뀌는 것 구현
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemMain:
                        setFrag(0);
                        break;
                    case R.id.itemReward:
                        setFrag(1);
                        break;
                    case R.id.itemHistory:
                        setFrag(2);
                        break;
                    case R.id.itemSetting:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });

        mainFragment = new MainFragment();
        rewardFragment = new RewardFragment();
        historyFragment = new HistoryFragment();
        settingFragment = new SettingFragment();
        setFrag(0); //첫 프래그먼트 화면 지정
    } //onCreate()

    //프래그먼트 교제 메서드
    private void setFrag(int n) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (n) {
            case 0:
                fragmentTransaction.replace(R.id.mainFrame, mainFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.mainFrame, rewardFragment);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.mainFrame, historyFragment);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentTransaction.replace(R.id.mainFrame, settingFragment);
                fragmentTransaction.commit();
                break;
        } //switch
    } //setFrag()
} //main()