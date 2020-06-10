package com.example.monthly_household_account_book;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.MPPointD;

import java.util.ArrayList;
import java.util.List;

public class Chart_Fragment1 extends Fragment {

    BarChart barChart1;
    BarChart barChart2;
    BarChart barChart3;

    ArrayList<IBarDataSet> dataSets1 = new ArrayList<>();
    ArrayList<IBarDataSet> dataSets2 = new ArrayList<>();
    ArrayList<IBarDataSet> dataSets3 = new ArrayList<>();
    float defaultBarWidth = -1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chart_fragment1, container, false);
        barChart1 = (BarChart) view.findViewById(R.id.graph1);
        barChart2 = (BarChart) view.findViewById(R.id.graph2);
        barChart3 = (BarChart) view.findViewById(R.id.graph3);

        setChart1();
        setChart2();
        setChart3();
        return view;
    }






    public void setChart1() {
        List<BarEntry> expenseEntries = getExpensesEntries();
        List<BarEntry> residueEntries = getResidueEntries();

        dataSets1 = new ArrayList<>();
        BarDataSet set1, set2;

        set1 = new BarDataSet(expenseEntries,  "지출 예산");
        set1.setColor(Color.rgb(241, 107, 72));
        set1.setValueTextColor(Color.rgb(55, 70, 73));
        set1.setValueTextSize(10f);

        set2 = new BarDataSet(residueEntries, "잔여 예산");
        set2.setColor(Color.rgb(74, 168, 216));
        set2.setValueTextColor(Color.rgb(55, 70, 73));
        set2.setValueTextSize(10f);

        dataSets1.add(set1);
        dataSets1.add(set2);

        BarData data = new BarData(dataSets1);
        barChart1.setData(data);
        barChart1.getAxisLeft().setAxisMinimum(0);

        barChart1.getDescription().setEnabled(false);
        barChart1.getAxisRight().setAxisMinimum(0);
        barChart1.setDrawBarShadow(false);
        barChart1.setDrawValueAboveBar(true);
        barChart1.setMaxVisibleValueCount(1);
        barChart1.setPinchZoom(false);
        barChart1.setDrawGridBackground(false);
        barChart1.setScaleEnabled(false);


        Legend l = barChart1.getLegend();
        l.setWordWrapEnabled(false);
//        l.setTextSize(15);
//        l.setFormSize(15);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.LINE);

        XAxis xAxis = barChart1.getXAxis();
//        xAxis.setLabelCount(xAxisValues.size() + 1, true);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
//        xAxis.setTextSize(12);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(getExpensesEntries().size());

//        barChart1.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        YAxis yAxis = barChart1.getAxisLeft();

        yAxis.setDrawGridLines(false);
        yAxis.setDrawLabels(false);
        yAxis.setDrawAxisLine(false);
        barChart1.getAxisRight().setEnabled(false);
//
        setBarWidth(data);
        barChart1.invalidate();


        barChart1.setOnChartGestureListener(new OnChartGestureListener() {
            @Override  //터치 제스처가 차트에서 시작되면 콜백
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override  //터치 제스처가 차트에서 끝났을때의 콜백
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override // 차트를 길게 누르면 콜백
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override //차트를 더블클릭 했을때 콜백
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override  //차트를 한번 누르면 콜백
            public void onChartSingleTapped(MotionEvent me) {

                float tappedX = me.getX();
                float tappedY = me.getY();
                MPPointD pointD = barChart1.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(tappedX, tappedY);
                System.out.println("tapped at : " + pointD.x + ", " + pointD.y);

            }

            @Override  //콜백은 플링 제스처로 차트에 표시됩니다.
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override //핀치 확대 / 축소 제스처를 통해 차트가 확대 / 축소 될 때의 콜백
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override //드래그 동작을 통해 차트를 이동 / 번역 할 때의 콜백
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });


    }
    public void setChart2() {
        List<BarEntry> expenseEntries = getExpensesEntries();
        List<BarEntry> residueEntries = getResidueEntries();

        dataSets2 = new ArrayList<>();
        BarDataSet set1, set2;

        set1 = new BarDataSet(expenseEntries,  "지출 예산");
        set1.setColor(Color.rgb(241, 107, 72));
        set1.setValueTextColor(Color.rgb(55, 70, 73));
        set1.setValueTextSize(10f);

        set2 = new BarDataSet(residueEntries, "잔여 예산");
        set2.setColor(Color.rgb(74, 168, 216));
        set2.setValueTextColor(Color.rgb(55, 70, 73));
        set2.setValueTextSize(10f);

        dataSets2.add(set1);
        dataSets2.add(set2);

        BarData data = new BarData(dataSets2);
        barChart2.setData(data);
        barChart2.getAxisLeft().setAxisMinimum(0);

        barChart2.getDescription().setEnabled(false);
        barChart2.getAxisRight().setAxisMinimum(0);
        barChart2.setDrawBarShadow(false);
        barChart2.setDrawValueAboveBar(true);
        barChart2.setMaxVisibleValueCount(1);
        barChart2.setPinchZoom(false);
        barChart2.setDrawGridBackground(false);
        barChart2.setScaleEnabled(false);


        Legend l = barChart2.getLegend();
        l.setWordWrapEnabled(false);
//        l.setTextSize(15);
//        l.setFormSize(15);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.LINE);

        XAxis xAxis = barChart2.getXAxis();
//        xAxis.setLabelCount(xAxisValues.size() + 1, true);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
//        xAxis.setTextSize(12);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(getExpensesEntries().size());

//        barChart1.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        YAxis yAxis = barChart2.getAxisLeft();

        yAxis.setDrawGridLines(false);
        yAxis.setDrawLabels(false);
        yAxis.setDrawAxisLine(false);
        barChart2.getAxisRight().setEnabled(false);
//
        setBarWidth(data);
        barChart2.invalidate();


        barChart2.setOnChartGestureListener(new OnChartGestureListener() {
            @Override  //터치 제스처가 차트에서 시작되면 콜백
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override  //터치 제스처가 차트에서 끝났을때의 콜백
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override // 차트를 길게 누르면 콜백
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override //차트를 더블클릭 했을때 콜백
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override  //차트를 한번 누르면 콜백
            public void onChartSingleTapped(MotionEvent me) {

                float tappedX = me.getX();
                float tappedY = me.getY();
                MPPointD pointD = barChart2.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(tappedX, tappedY);
                System.out.println("tapped at : " + pointD.x + ", " + pointD.y);

            }

            @Override  //콜백은 플링 제스처로 차트에 표시됩니다.
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override //핀치 확대 / 축소 제스처를 통해 차트가 확대 / 축소 될 때의 콜백
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override //드래그 동작을 통해 차트를 이동 / 번역 할 때의 콜백
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });


    }
    public void setChart3() {
        List<BarEntry> expenseEntries = getExpensesEntries();
        List<BarEntry> residueEntries = getResidueEntries();

        dataSets3 = new ArrayList<>();
        BarDataSet set1, set2;

        set1 = new BarDataSet(expenseEntries,  "지출 예산");
        set1.setColor(Color.rgb(241, 107, 72));
        set1.setValueTextColor(Color.rgb(55, 70, 73));
        set1.setValueTextSize(10f);

        set2 = new BarDataSet(residueEntries, "잔여 예산");
        set2.setColor(Color.rgb(74, 168, 216));
        set2.setValueTextColor(Color.rgb(55, 70, 73));
        set2.setValueTextSize(10f);

        dataSets3.add(set1);
        dataSets3.add(set2);

        BarData data = new BarData(dataSets3);
        barChart3.setData(data);
        barChart3.getAxisLeft().setAxisMinimum(0);

        barChart3.getDescription().setEnabled(false);
        barChart3.getAxisRight().setAxisMinimum(0);
        barChart3.setDrawBarShadow(false);
        barChart3.setDrawValueAboveBar(true);
        barChart3.setMaxVisibleValueCount(1);
        barChart3.setPinchZoom(false);
        barChart3.setDrawGridBackground(false);
        barChart3.setScaleEnabled(false);


        Legend l = barChart3.getLegend();
        l.setWordWrapEnabled(false);
//        l.setTextSize(15);
//        l.setFormSize(15);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.LINE);

        XAxis xAxis = barChart3.getXAxis();
//        xAxis.setLabelCount(xAxisValues.size() + 1, true);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
//        xAxis.setTextSize(12);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(getExpensesEntries().size());

//        barChart1.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        YAxis yAxis = barChart3.getAxisLeft();

        yAxis.setDrawGridLines(false);
        yAxis.setDrawLabels(false);
        yAxis.setDrawAxisLine(false);
        barChart3.getAxisRight().setEnabled(false);
//
        setBarWidth(data);
        barChart3.invalidate();


        barChart3.setOnChartGestureListener(new OnChartGestureListener() {
            @Override  //터치 제스처가 차트에서 시작되면 콜백
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override  //터치 제스처가 차트에서 끝났을때의 콜백
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override // 차트를 길게 누르면 콜백
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override //차트를 더블클릭 했을때 콜백
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override  //차트를 한번 누르면 콜백
            public void onChartSingleTapped(MotionEvent me) {

                float tappedX = me.getX();
                float tappedY = me.getY();
                MPPointD pointD = barChart3.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(tappedX, tappedY);
                System.out.println("tapped at : " + pointD.x + ", " + pointD.y);

            }

            @Override  //콜백은 플링 제스처로 차트에 표시됩니다.
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override //핀치 확대 / 축소 제스처를 통해 차트가 확대 / 축소 될 때의 콜백
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override //드래그 동작을 통해 차트를 이동 / 번역 할 때의 콜백
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });


    }

    private void setBarWidth(BarData barData) {
        if (dataSets1.size() > 1) {
            float barSpace = 0.02f;
            float groupSpace = 0.3f;
            defaultBarWidth = (1 - groupSpace) / dataSets1.size() - barSpace;
            if (defaultBarWidth >= 0) {
                barData.setBarWidth(defaultBarWidth);
            } else {
//                Toast.makeText(getContext(), "Defalut Barwidth" + defaultBarWidth, Toast.LENGTH_SHORT).show();
                System.out.println("문제 발생");
            }
            int groupCount = getExpensesEntries().size();
            if (groupCount != -1) {
                barChart1.getXAxis().setAxisMinimum(0);
                barChart1.getXAxis().setAxisMaximum(0 + barChart1.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
                barChart1.getXAxis().setCenterAxisLabels(true);
            } else {
                System.out.println("문제 발생");
//                Toast.makeText(getContext(), "no of bar groups is " + groupCount, Toast.LENGTH_SHORT).show();
            }
            barChart1.groupBars(0, groupSpace, barSpace);
            barChart1.invalidate();
        }
        if (dataSets2.size() > 1) {
            float barSpace = 0.02f;
            float groupSpace = 0.3f;
            defaultBarWidth = (1 - groupSpace) / dataSets2.size() - barSpace;
            if (defaultBarWidth >= 0) {
                barData.setBarWidth(defaultBarWidth);
            } else {
//                Toast.makeText(getContext(), "Defalut Barwidth" + defaultBarWidth, Toast.LENGTH_SHORT).show();
                System.out.println("문제 발생");
            }
            int groupCount = getExpensesEntries().size();
            if (groupCount != -1) {
                barChart2.getXAxis().setAxisMinimum(0);
                barChart2.getXAxis().setAxisMaximum(0 + barChart2.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
                barChart2.getXAxis().setCenterAxisLabels(true);
            } else {
                System.out.println("문제 발생");
//                Toast.makeText(getContext(), "no of bar groups is " + groupCount, Toast.LENGTH_SHORT).show();
            }
            barChart2.groupBars(0, groupSpace, barSpace);
            barChart2.invalidate();
        }
        if (dataSets3.size() > 1) {
            float barSpace = 0.02f;
            float groupSpace = 0.3f;
            defaultBarWidth = (1 - groupSpace) / dataSets3.size() - barSpace;
            if (defaultBarWidth >= 0) {
                barData.setBarWidth(defaultBarWidth);
            } else {
//                Toast.makeText(getContext(), "Defalut Barwidth" + defaultBarWidth, Toast.LENGTH_SHORT).show();
                System.out.println("문제 발생");
            }
            int groupCount = getExpensesEntries().size();
            if (groupCount != -1) {
                barChart3.getXAxis().setAxisMinimum(0);
                barChart3.getXAxis().setAxisMaximum(0 + barChart3.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
                barChart3.getXAxis().setCenterAxisLabels(true);
            } else {
                System.out.println("문제 발생");
//                Toast.makeText(getContext(), "no of bar groups is " + groupCount, Toast.LENGTH_SHORT).show();
            }
            barChart3.groupBars(0, groupSpace, barSpace);
            barChart3.invalidate();
        }

    }

    private List<BarEntry> getExpensesEntries() { //지출 예산

        ArrayList<BarEntry> expensesEntries = new ArrayList<>();

        expensesEntries.add(new BarEntry(0, 2500000));


        return expensesEntries.subList(0, expensesEntries.size());
    }

    private List<BarEntry> getResidueEntries() { //잔여 예산
        int count;
        ArrayList<BarEntry> residueEntries = new ArrayList<>();

        residueEntries.add(new BarEntry(0, 900000));



        return residueEntries.subList(0, residueEntries.size());
    }


}
