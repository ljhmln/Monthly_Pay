package com.example.monthly_household_account_book;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;


import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.ViewPortHandler;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Chart extends Fragment {


    private View view;
    BarChart barChart;
    ArrayList<IBarDataSet> dataSets = new ArrayList<>();
    float defaultBarWidth = -1;
    List<String> xAxisValues = new ArrayList<>(Arrays.asList("1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"));



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chart, container, false);
        barChart = (BarChart) view.findViewById(R.id.graph);
//        graph();
//        chart();
        setChart();
        return view;
    }


    public void setChart(){

        List<BarEntry> incomeEntries = getIncomeEntries();
        List<BarEntry> expenseEntries = getExpenseEntries();
        dataSets = new ArrayList<>();
        BarDataSet set1, set2;

        set1 = new BarDataSet(incomeEntries, "수입");
        set1.setColor(Color.rgb(74,168,216));
        set1.setValueTextColor(Color.rgb(55,70,73));
        set1.setValueTextSize(10f);

        set2 = new BarDataSet(expenseEntries, "지출");
        set2.setColor(Color.rgb(241,107,72));
        set2.setValueTextColor(Color.rgb(55,70,73));
        set2.setValueTextSize(10f);

        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);
        barChart.setData(data);
        barChart.getAxisLeft().setAxisMinimum(0);

        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setAxisMinimum(0);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(12);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        Legend l = barChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setTextSize(15);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.CIRCLE);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setLabelCount(xAxisValues.size()+1, true);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(getIncomeEntries().size());

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.removeAllLimitLines();
        yAxis.setTypeface(Typeface.DEFAULT);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);

        setBarWidth(data);
        barChart.invalidate();

    }

    private void setBarWidth(BarData barData){
        if(dataSets.size() > 1){
            float barSpace = 0.02f;
            float groupSpace = 0.3f;
            defaultBarWidth = (1 - groupSpace) / dataSets.size() - barSpace;
            if(defaultBarWidth >= 0){
                barData.setBarWidth(defaultBarWidth);
            }else {
//                Toast.makeText(getContext(), "Defalut Barwidth" + defaultBarWidth, Toast.LENGTH_SHORT).show();
                System.out.println("문제 발생");
            }
            int groupCount = getIncomeEntries().size();
            if (groupCount != -1){
                barChart.getXAxis().setAxisMinimum(0);
                barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
                barChart.getXAxis().setCenterAxisLabels(true);
            }else{
                System.out.println("문제 발생");
//                Toast.makeText(getContext(), "no of bar groups is " + groupCount, Toast.LENGTH_SHORT).show();
            }
            barChart.groupBars(0,groupSpace,barSpace);
            barChart.invalidate();
        }
    }

    private List<BarEntry> getIncomeEntries(){
        ArrayList<BarEntry> incomeEntries = new ArrayList<>();

        incomeEntries.add(new BarEntry(1, 1900000));
        incomeEntries.add(new BarEntry(2, 2000000));
        incomeEntries.add(new BarEntry(3, 2100000));
        incomeEntries.add(new BarEntry(4, 1980000));
        incomeEntries.add(new BarEntry(5, 1700000));
        incomeEntries.add(new BarEntry(6, 1500000));
        incomeEntries.add(new BarEntry(7, 1000000));
        incomeEntries.add(new BarEntry(8, 1000000));
        incomeEntries.add(new BarEntry(9, 1200000));
        incomeEntries.add(new BarEntry(10, 1800000));
        incomeEntries.add(new BarEntry(11, 1870000));
        incomeEntries.add(new BarEntry(12, 1250000));
        return incomeEntries.subList(0,12);
    }

    private List<BarEntry> getExpenseEntries(){
        ArrayList<BarEntry> expenseEntries = new ArrayList<>();

        expenseEntries.add(new BarEntry(1, 1400000));
        expenseEntries.add(new BarEntry(2, 1520000));
        expenseEntries.add(new BarEntry(3, 1250000));
        expenseEntries.add(new BarEntry(4, 1120000));
        expenseEntries.add(new BarEntry(5, 1180000));
        expenseEntries.add(new BarEntry(6, 1000000));
        expenseEntries.add(new BarEntry(7, 650000));
        expenseEntries.add(new BarEntry(8, 800000));
        expenseEntries.add(new BarEntry(9, 950000));
        expenseEntries.add(new BarEntry(10, 1000000));
        expenseEntries.add(new BarEntry(11, 840000));
        expenseEntries.add(new BarEntry(12, 950000));
        return expenseEntries.subList(0,12);
    }









// -------------------------------------------------
//    public void chart(){
//        barChart.setDrawBarShadow(false);
//        barChart.setDrawValueAboveBar(false);
//        barChart.getDescription().setEnabled(false);
//        barChart.setDrawGridBackground(false);
//
//        BarChartCustomRenderer barChartCustomRenderer = new BarChartCustomRenderer(barChart, barChart.getAnimator(),
//                barChart.getViewPortHandler());
//        barChart.setRenderer(barChartCustomRenderer);
//
//
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setDrawGridLines(false);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(0.5f);
//        xAxis.setGranularityEnabled(true);
//        xAxis.setDrawLabels(true);
//        xAxis.setDrawAxisLine(true);
//
//        YAxis yAxisLeft = barChart.getAxisLeft();
//
//        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
//        yAxisLeft.setDrawGridLines(false);
//        yAxisLeft.setDrawAxisLine(false);
//        yAxisLeft.setEnabled(false);
//
//        barChart.getAxisRight().setEnabled(false);
//
//        Legend legend = barChart.getLegend();
//        legend.setEnabled(false);
//
//        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
////        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
//
//        ArrayList<String> yLabels = new ArrayList<>();
//
//        for(int i = 1; i<=8; i++){
//            BarEntry entry = new BarEntry(i,(i+1)*2);
//            valueSet1.add(entry);
////            valueSet2.add(entry);
//
//            yLabels.add(" " + i);
//        }
//
//        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//        BarDataSet barDataSet1 = new BarDataSet(valueSet1, " ");
////        BarDataSet barDataSet2 = new BarDataSet(valueSet2, " ");
//        barDataSet1.setColor(Color.LTGRAY);
////        barDataSet2.setColor(Color.RED);
//        barDataSet1.setDrawValues(true);
//
////        barDataSet2.setDrawValues(true);
//        dataSets.add(barDataSet1);
////        dataSets.add(barDataSet2);
//
//        BarData data = new BarData(dataSets);
//
//        data.setBarWidth(0.4f);
//        data.setValueTextSize(10f);
//        data.setValueTextColor(Color.GRAY);
//        barChart.setData(data);
//        barChart.setFitBars(true);
//        barChart.invalidate();
//
//    }
//
//
//    public class BarChartCustomRenderer extends BarChartRenderer{
//        public BarChartCustomRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
//            super(chart, animator, viewPortHandler);
//        }
//
//        public void drawValue(Canvas c, IValueFormatter formatter, float value, Entry entry, int dataSetIndex, float x, float y, int color){
//            mValuePaint.setColor(color);
//            c.save();
//            c.rotate(90f, x, y);
//            Log.d("here", formatter.getFormattedValue(value, entry,dataSetIndex, mViewPortHandler));
//            c.drawText(formatter.getFormattedValue(value, entry, dataSetIndex, mViewPortHandler), x,y,mValuePaint);
//            c.restore();
//        }
//    }




 //----------------------------------
//    public void  graph(){
//        l = barChart.getLegend();
//        desc = barChart.getDescription();
//        desc.setText("");
//        l.setEnabled(false);
//
//        YAxis leftAxis = barChart.getAxisLeft();
//        YAxis rigthAxis = barChart.getAxisRight();
//        XAxis xAxis = barChart.getXAxis();
//
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextSize(10f);
//        xAxis.setDrawAxisLine(true);
//        xAxis.setDrawGridLines(false);
//
//        leftAxis.setTextSize(10f);
//        leftAxis.setDrawLabels(false);
//        leftAxis.setDrawAxisLine(true);
//        leftAxis.setDrawGridLines(false);
//
//        rigthAxis.setDrawAxisLine(false);
//        rigthAxis.setDrawGridLines(false);
//        rigthAxis.setDrawLabels(false);
//
//        BarData data = new BarData(setData());
//
//        data.setBarWidth(1f);
//        barChart.setData(data);
//
//        barChart.setFitBars(true);
//        barChart.invalidate();
//        barChart.setScaleEnabled(false);
//        barChart.setDoubleTapToZoomEnabled(false);
//        barChart.setBackgroundColor(Color.rgb(255,255,255));
//        barChart.animateXY(2000,2000);
//        barChart.setDrawBorders(false);
//        barChart.setDescription(desc);
//        barChart.setDrawValueAboveBar(true);
//    }
//
//    private BarDataSet setData(){
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(0f, 30f));
//        entries.add(new BarEntry(1f, 80f));
//        entries.add(new BarEntry(2f, 60f));
//        entries.add(new BarEntry(3f, 50f));
//        entries.add(new BarEntry(4f, 70f));
//        entries.add(new BarEntry(5f, 10f));
//        entries.add(new BarEntry(6f, 20f));
//        entries.add(new BarEntry(7f, 60f));
//        entries.add(new BarEntry(8f, 90f));
//        entries.add(new BarEntry(9f, 100f));
//
//        BarDataSet set = new BarDataSet(entries, "");
////        set.setDrawValues(false);
//        set.setColor(Color.rgb(100,100,200));
//        set.setValueTextColor(Color.rgb(155,155,155));
//        return set;
//    }




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
