package com.example.monthly_household_account_book;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class YearPicker extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;
    public Calendar cal = Calendar.getInstance();

    private static final int MAX_YEAR = 3000;
    private static final int MIN_YEAR = 2019;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    Button btnConfirm;
    Button btnCancel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.year_picker, null);

        btnConfirm = dialog.findViewById(R.id.confirm_btn);
        btnCancel = dialog.findViewById(R.id.cancel_btn);

        final NumberPicker yearPicker = dialog.findViewById(R.id.picker_year);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YearPicker.this.getDialog().cancel();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDateSet(null, yearPicker.getValue(), 0, 0);
                YearPicker.this.getDialog().cancel();
            }
        });


        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setWrapSelectorWheel(false);
        yearPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        yearPicker.setValue(year);
        builder.setView(dialog);
        builder.setView(dialog);
        return builder.create();
    }


}

