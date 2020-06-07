package com.example.monthly_household_account_book.main_adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.monthly_household_account_book.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DecimalFormat;

public class Add_Activity extends BottomSheetDialogFragment {
    DrawerLayout add_drawer;
    TextView cancle, confirm;
    EditText edit_money;
    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    String money_result="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add, container, false);

        cancle = (TextView)view.findViewById(R.id.btn_cancle);
        confirm = (TextView)view.findViewById(R.id.btn_confirm);
        edit_money = (EditText)view.findViewById(R.id.edit_money);

        setCancelable(false);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "취소를 눌렀습니다.",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String withoutcomma = money_result.replace(",","");
                Toast.makeText(getContext(), withoutcomma,Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        edit_money.addTextChangedListener(watcher);

        return view;
    }

//    EditText 글자 변경 감지
   TextWatcher watcher = new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {

       }

       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!TextUtils.isEmpty(s.toString()) && !s.toString().equals(money_result)){
               money_result =
                       decimalFormat.format(Double.parseDouble(s.toString().replace(",","")));
               edit_money.setText(money_result);
               edit_money.setSelection(money_result.length());
            }
       }

       @Override
       public void afterTextChanged(Editable s) {

       }
   };
}
