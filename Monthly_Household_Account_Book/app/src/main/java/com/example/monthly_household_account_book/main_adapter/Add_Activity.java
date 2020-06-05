package com.example.monthly_household_account_book.main_adapter;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.monthly_household_account_book.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Add_Activity extends BottomSheetDialogFragment {
    DrawerLayout add_drawer;
    TextView cancle, confirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add, container, false);

        cancle = (TextView)view.findViewById(R.id.cancle);
        confirm = (TextView)view.findViewById(R.id.confirm);

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
                Toast.makeText(getContext(), "확인 눌렀습니다.",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return view;
    }

    //    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add);
//        add_drawer = (DrawerLayout)findViewById(R.id.add_drawer);
//        cancle = (TextView) findViewById(R.id.cancle);
//
//
//
//    }
//
//    @Override
//    protected void onResume(){
//        super.onResume();
//
//        try{
//            if(!add_drawer.isDrawerOpen(Gravity.RIGHT))
//            add_drawer.openDrawer(Gravity.RIGHT);
//        }catch (IllegalArgumentException e){
//            Toast.makeText(getApplicationContext(),"00",Toast.LENGTH_SHORT).show();
//        }
//
//        cancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               try{
//                add_drawer.closeDrawer(Gravity.RIGHT);
//            }catch (IllegalArgumentException e){
//                Toast.makeText(getApplicationContext(),"00",Toast.LENGTH_SHORT).show();
//            }
//            }
//        });
//
//
//
//
//    }
}
