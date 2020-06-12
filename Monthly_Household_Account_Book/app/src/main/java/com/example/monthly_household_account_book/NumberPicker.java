package com.example.monthly_household_account_book;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NumberPicker extends DialogFragment {
    private android.widget.NumberPicker.OnValueChangeListener valueChangeListener;

        String titile;       //dialog 제목
        String subtitle;     //dialog 부제목
        int minValue;        //입력 가능 최소값
        int maxValue;       // 입력 가능 최대값
        int step;           // 선택 가능 값들의 간격
        int defValue;       // dialog 시작 숫자 (현재값)

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final android.widget.NumberPicker numberPicker = new android.widget.NumberPicker(getActivity());

        //Dialog 시작 시 bundle로 전달된 값을 받아온다.

        titile = getArguments().getString("title");
        subtitle = getArguments().getString("subtitle");
        minValue = getArguments().getInt("minValue");
        maxValue = getArguments().getInt("maxValue");
        step = getArguments().getInt("step");
        defValue = getArguments().getInt("defValue");

        //최소값과 최대값 사이의 값들 중에서 일정한 step사이즈에 맞는 값들을 배열로 만든다.

        String[] myValues =getArrayWithSteps(minValue, maxValue, step);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue((maxValue - minValue) / step);
        numberPicker.setDisplayedValues(myValues);

        //현재값 설정
        numberPicker.setValue((defValue - minValue) / step);

        //키보드 입력을 방지
        numberPicker.setDescendantFocusability(android.widget.NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //제목 설정
        builder.setTitle(titile);
        //부제목 설정
        builder.setMessage(subtitle);

        //확인 button 눌렀을때 동작 설정
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //dialog 종료하면서 값이 변했다는 함수는 onValueChange 함수를 실행시킨다.
                //실제 구현에서는 이 클래스의 함수를 재정의해서 동작을 수행시킨다.

                valueChangeListener.onValueChange(numberPicker,numberPicker.getValue(),numberPicker.getValue());

            }
        });

        //취소 버튼 눌렀을때 동작 설정
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setView(numberPicker);
        //numberPicker 실행

        return builder.create();
    }

        //Listner의 getter

    public android.widget.NumberPicker.OnValueChangeListener getValueChangeListener(){
        return  valueChangeListener;
    }

        //listner의 setter
    public void setValueChangeListener(android.widget.NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public String[] getArrayWithSteps(int min, int max, int step){
        int numberArray = (max - min) / step +1;

        String[] result = new String[numberArray];

        for(int i = 0; i<numberArray; i++){
            result[i] = String.valueOf(min + step * i);
        }
        return  result;
    }
}
