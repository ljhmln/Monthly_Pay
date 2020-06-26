package com.example.monthly_household_account_book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Weather extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weather, container, false);

        //MainActivity의 메소드 사용하여 액션바 타이틀 변경.
        ((MainActivity) getActivity()).setActionBarTitle("저축 현황");

        return view;
    }
}
