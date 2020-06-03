package com.example.monthly_household_account_book;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;


import java.util.ArrayList;



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
//        graph();
//        chart();
        return view;
    }

    public void twoChart() {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(false);
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);

        String[] labels = {"", "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월", ""};
        IAxisValueFormatter xAxisFormatter = new LabelFormatter(barChart, labels);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(12);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAxisMinimum(1f);
        xAxis.setValueFormatter((ValueFormatter) xAxisFormatter);


        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.BLUE);
        yAxisLeft.setTextSize(12);
        yAxisLeft.setAxisLineColor(Color.WHITE);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setGranularity(2);
        yAxisLeft.setLabelCount(8, true);
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        float[] valOne = {30, 40};
        float[] valTwo = {80, 20};

        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            barOne.add(new BarEntry(i, valOne[i]));
            barTwo.add(new BarEntry(i, valTwo[i]));
        }

        BarDataSet set1 = new BarDataSet(barOne, "barOne");
        set1.setColor(Color.BLUE);
        BarDataSet set2 = new BarDataSet(barTwo, "barOne");
        set1.setColor(Color.RED);

        set1.setHighlightEnabled(false);
        set1.setDrawValues(false);
        set2.setHighlightEnabled(false);
        set2.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);
        float groupSpace = 0.2f;
        float barSpace = 0f;
        float barWidth = 0.16f;

        data.setBarWidth(barWidth);

        xAxis.setAxisMaximum(labels.length - 1.1f);
        barChart.setData(data);
        barChart.setScaleEnabled(false);
        barChart.setVisibleXRangeMaximum(2f);
        barChart.groupBars(1f, groupSpace, barSpace);
        barChart.invalidate();

    }


    private class LabelFormatter implements IAxisValueFormatter {
        String[] labels;
        BarLineChartBase<?> chart;
        LabelFormatter(BarLineChartBase<?> chart, String[] labels){
            this.chart = chart;
            this.labels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return labels[(int) value];
        }
    }

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
