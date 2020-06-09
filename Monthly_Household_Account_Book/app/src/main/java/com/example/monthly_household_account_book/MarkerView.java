package com.example.monthly_household_account_book;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

public class MarkerView extends com.github.mikephil.charting.components.MarkerView {

    private TextView textView;

    public MarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if(e instanceof CandleEntry){
            CandleEntry candleEntry = (CandleEntry) e;
            textView.setText("" + Utils.formatNumber(candleEntry.getHigh(), 0, true));

        }else{
            textView.setText("" + Utils.formatNumber(e.getY(), 0, true));

        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {

        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
