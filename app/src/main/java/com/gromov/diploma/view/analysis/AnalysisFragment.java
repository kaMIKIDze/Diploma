package com.gromov.diploma.view.analysis;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gromov.diploma.R;

import java.util.ArrayList;


public class AnalysisFragment extends Fragment {

    private PieChart chart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_analysis, container, false);

        chart = v.findViewById(R.id.piechart);
        chart.setUsePercentValues(true);
        ArrayList<PieEntry> values = new ArrayList<>();
        values.add(new PieEntry(8f, 0));
        values.add(new PieEntry(15f, 1));
        values.add(new PieEntry(12f, 2));
        values.add(new PieEntry(25f, 3));
        values.add(new PieEntry(23f, 4));
        values.add(new PieEntry(17f, 5));

        PieDataSet dataSet = new PieDataSet(values, "Election Results");


        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("January");
        xVals.add("February");
        xVals.add("March");
        xVals.add("April");
        xVals.add("May");
        xVals.add("June");

        PieData data = new PieData(dataSet);


        data.setValueFormatter(new PercentFormatter());
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        chart.setData(data);
        //chart.setDescription("This is Pie Chart");
        chart.setDrawHoleEnabled(true);
        chart.setTransparentCircleRadius(58f);

        chart.setHoleRadius(58f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);

        //chart.setOnChartValueSelectedListener(this);

        return v;
    }




}
