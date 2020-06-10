package com.example.monthly_household_account_book;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.monthly_household_account_book.main_adapter.Items;
import com.example.monthly_household_account_book.main_adapter.ListviewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Money moneyPage;
    private Chart chartPage;
    private Saving savingPage;
    private Calendar calendarPage;
    private ArrayList<Items> itemsArr = new ArrayList<Items>();

    private int income = 0;
    private int outgoing = 0;
    private int belence = 0;
    public int fixed_money = 2000000;
    static public DateRunnable dateRun = new DateRunnable();

    /*수입금액 설정 테스트 코드 시작*/
    private TextView fixed_money_edit;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            fixed_money_edit = (TextView) findViewById(R.id.fixed_money_edit);
            fixed_money_edit.setText(data.getStringExtra("result"));
            DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
            helper.setFixedMoney(fixed_money_edit.getText().toString(),dateRun.getNowYear_Month());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.setting) {

            final Intent intent = new Intent(MainActivity.this, TestFixedActivity.class);
            startActivityForResult(intent, 0);
        }

        return super.onOptionsItemSelected(item);
    }

    /*수입 금액 설정 테스트 코드 끝*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("달마다 가계부");


        bottomNavigationView = findViewById(R.id.bottomNavigationView4);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.money:
                        setFrag(0);
                        break;
                    case R.id.chart:
                        setFrag(1);
                        break;
                    case R.id.saving:
                        setFrag(2);
                        break;
                    case R.id.calendar:
                        setFrag(3);
                        break;
                }

                return true;
            }
        });

        moneyPage = new Money();
        chartPage = new Chart();
        savingPage = new Saving();
        calendarPage = new Calendar();
        setFrag(0);


    }

    // Fragmant
    private void setFrag(int n) {

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (n) {
            case 0:
                fragmentTransaction.replace(R.id.main_frame, moneyPage);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.main_frame, chartPage);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.main_frame, savingPage);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentTransaction.replace(R.id.main_frame, calendarPage);
                fragmentTransaction.commit();
                break;
        }
    }

    //실제 뷰로 inflate 함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar, menu);
        return true;
    }

    // 각 탭에서 액션바 타이틀 세팅하기 위한 메소드
    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    //버튼을 눌렀을때 반응
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//      switch (item.getItemId()){
//          case R.id.setting:
//
//      }
//    }

    //item Array test
//    public void setItemsArr(Items item) {
//        this.itemsArr.add(item);
//        adapter.addItem(itemsArr);
//        adapter.notifyDataSetChanged();
//
//    }

    public ArrayList<Items> getItemsArr() {
        return this.itemsArr;

    }

    public int getBeleance() {
        income = 0;
        outgoing = 0;
        belence = 0;
        System.out.println("items 사이즈 : " + itemsArr.size());
        for (int i = 0; i < itemsArr.size(); i++) {
            if (itemsArr.get(i).getKind().equals("수입")) {
                income += Integer.parseInt(itemsArr.get(i).getMoney());
                System.out.println("지출금 : " + itemsArr.get(i).getMoney());
            } else if (itemsArr.get(i).getKind().equals("지출")) {
                System.out.println("지출금 : " + itemsArr.get(i).getMoney());
                outgoing += Integer.parseInt(itemsArr.get(i).getMoney());
            }

        }

        belence = fixed_money - outgoing + income;
        System.out.println("밸런스 : " + belence);
        return belence;
    }

    public int getOutgoing() {
        getBeleance();
        return outgoing;
    }

    public int getIncome() {
        getBeleance();
        return income;
    }
    //item Array test end


    @Override
    protected void onStart() {
        super.onStart();

        Thread dateThread = new Thread(dateRun);
        dateThread.start();

    }
}
