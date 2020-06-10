package com.example.monthly_household_account_book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestFixedActivity extends AppCompatActivity {

    Button btn;
    EditText edit_money;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_fixed_money);

        btn = (Button)findViewById(R.id.btn);
        edit_money = (EditText)findViewById(R.id.edit_money);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result",edit_money.getText().toString());
                setResult(0,intent);
                finish();
            }
        });



    }
}
