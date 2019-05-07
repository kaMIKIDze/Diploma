package com.gromov.diploma.view.analysis;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gromov.diploma.R;
import com.gromov.diploma.data.async.GetCategoryAsyncTask;
import com.gromov.diploma.data.async.GetPurchaseAsyncTask;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Category;
import com.gromov.diploma.data.database.entities.Product;
import com.gromov.diploma.data.database.entities.Purchase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class AnalysisFragment extends Fragment {

    private PieChart chart;
    private DatabasePurchase databasePurchase;
    private List<StatisticsByCategotY> statistics;
    private List<Category> categories;
    private View v;
    private Calendar dateAndTime = Calendar.getInstance();
    private Button dateBeginText;
    private Button dateEndText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_analysis, container, false);
        dateBeginText = v.findViewById(R.id.time_begin);
        dateEndText = v.findViewById(R.id.time_end);
        dateBeginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateBegin(v);
            }
        });

        dateEndText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateEnd(v);
            }
        });
        chart = v.findViewById(R.id.piechart);
        chart.setUsePercentValues(true);

        createDb();
        categories = getCategory();

        statistics = new ArrayList<>();


        ArrayList<PieEntry> values = addValues(getPurchase());
        PieDataSet dataSet = new PieDataSet(values, "Election Results");
        setParameters(dataSet);

        return v;
    }

    public class StatisticsByCategotY {
        private String name;
        private float cost;

        public StatisticsByCategotY(String name) {
            this.name = name;
            cost = 0;
        }

        public void addCost(float cost) {
            this.cost += cost;
        }

        public float getCost() {
            return cost;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCost(float cost) {
            this.cost = cost;
        }


    }

    private List<Category> getCategory() {

        try {
            return new GetCategoryAsyncTask(databasePurchase).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void setStatisticsCategory() {

        for (Category category : categories) {
            StatisticsByCategotY statisticsByCategotY = new StatisticsByCategotY(category.getName());
            statistics.add(statisticsByCategotY);
        }

    }

    private List<Purchase> getPurchase() {//добавить даты

        try {
            return new GetPurchaseAsyncTask(databasePurchase).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<PieEntry> addValues(List<Purchase> purchases) {
        setStatisticsCategory();
        for (Purchase purchase : purchases) {
            List<Product> products = purchase.getItems();
            for (Product product : products) {
                for (StatisticsByCategotY stat : statistics) {
                    if (product.getCategory().getName().equals(stat.getName()))
                        stat.addCost(product.getSum() / 100);
                }
            }
        }


        ArrayList<PieEntry> values = new ArrayList<>();
        for (StatisticsByCategotY stat : statistics) {
            values.add(new PieEntry(stat.getCost(), stat.getName()));
        }
        return values;

    }


    private void setParameters(PieDataSet dataSet) {
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        chart.setData(data);

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);

        chart.getDescription().setEnabled(false);
        chart.setDrawHoleEnabled(true);

        chart.setTransparentCircleColor(Color.WHITE);

        chart.setTransparentCircleAlpha(110);//прозрачная область в центре
        chart.setTransparentCircleRadius(61f);//величина прозрачной области = это значение - радиус "дырки"
        chart.setHoleRadius(58f);
    }

    private void setLegends() {
        Legend l = chart.getLegend();

        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);

        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    // отображаем диалоговое окно для выбора даты
    public void setDateBegin(View v) {
        new DatePickerDialog(v.getContext(), begin,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setDateEnd(View v) {
        new DatePickerDialog(v.getContext(), end,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }


    // установка  даты и времени
    private void setInitialDateBegin() {

        dateBeginText.setText(DateUtils.formatDateTime(v.getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    private void setInitialDateEnd() {

        dateEndText.setText(DateUtils.formatDateTime(v.getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }


    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener begin = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateBegin();
        }
    };

    DatePickerDialog.OnDateSetListener end = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateEnd();
        }
    };


    public void createDb() {
        Context context = getContext();
        databasePurchase = DatabasePurchase.getInstanse(context);
    }

}
