package com.example.monthly_household_account_book;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;


public class Chart extends Fragment {


    private View view;
    BarChart barChart;
    Description desc;
    Legend l;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chart, container, false);
        barChart = (BarChart) view.findViewById(R.id.graph);
        graph();
        return view;
    }


    public void  graph(){
        l = barChart.getLegend();
        desc = barChart.getDescription();
        desc.setText("");
        l.setEnabled(false);

        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rigthAxis = barChart.getAxisRight();
        XAxis xAxis = barChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        leftAxis.setTextSize(10f);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(false);

        rigthAxis.setDrawAxisLine(false);
        rigthAxis.setDrawGridLines(false);
        rigthAxis.setDrawLabels(false);

        BarData data = new BarData(setData());

        data.setBarWidth(1f);
        barChart.setData(data);

        barChart.setFitBars(true);
        barChart.invalidate();
        barChart.setScaleEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setBackgroundColor(Color.rgb(255,255,255));
        barChart.animateXY(2000,2000);
        barChart.setDrawBorders(false);
        barChart.setDescription(desc);
        barChart.setDrawValueAboveBar(true);
    }

    private BarDataSet setData(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        entries.add(new BarEntry(4f, 70f));
        entries.add(new BarEntry(5f, 60f));

        BarDataSet set = new BarDataSet(entries, "");
//        set.setDrawValues(false);
        set.setColor(Color.rgb(155,155,155));
        set.setValueTextColor(Color.rgb(155,155,155));
        return set;
    }




//    public class BarChartCustomRenderer extends BarChartRenderer{
//
//        public BarChartCustomRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
//            super(chart, animator, viewPortHandler);
//        }
//
//        @Override
//        public void drawValue(Canvas c, String valueText, float x, float y, int color) {
//            mValuePaint.setColor(color);
//            c.save();
//            c.rotate(90f, x, y);
//            c.drawText(valueText.toString(),x,y,mValuePaint);
//            c.restore();
//        }
//
//
//
//    }




//    ArrayList<BarEntry> entries = generateBarEntries();
//    BarDataSet set = new BarDataSet(entries, "BarDataSet");
//    BarData data = new BarData(set);
//    barChart.setData(data);
//
//
//
//    private ArrayList<BarEntry> generateBarEntries(){
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        for(int i = 0; i<timeValues.size(); i++){
//            float x = timeValues.get(i);
//            float y = areaValue.get(i);
//            entries.add(new BarEntry(x, y));
//        }
//        return entries;
//    }



//    public class graphView extends AppCompatActivity implements View.OnClickListener{
//        Context context;
//        ArrayList<Integer> jsonList = new ArrayList<>();
//        ArrayList<String> labelList = new ArrayList<>();
//
//        @Override
//        protected void onCreate(@Nullable Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.chart);
//            context = this.getBaseContext();
//
//        }
//
//        public void initView(){
//
//        }
//
//        @Override
//        public void onClick(View view) {
//
//        }
//    }




}
