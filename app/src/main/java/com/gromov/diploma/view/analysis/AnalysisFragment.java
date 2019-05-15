package com.gromov.diploma.view.analysis;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gromov.diploma.R;
import com.gromov.diploma.data.async.AddMinDateAsyncTask;
import com.gromov.diploma.data.async.GetCategoryAsyncTask;
import com.gromov.diploma.data.async.GetPurchaseAsyncTask;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Category;
import com.gromov.diploma.data.database.entities.Product;
import com.gromov.diploma.data.database.entities.Purchase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class AnalysisFragment extends Fragment {

    private PieChart chart;
    private DatabasePurchase databasePurchase;
    private List<StatisticsByCategory> statistics = new ArrayList<>();
    private List<Category> categories;
    private View view;
    private Calendar calendarBegin = Calendar.getInstance();
    private Calendar calendarEnd = Calendar.getInstance();
    private Date dateBegin;
    private Date dateEnd = Calendar.getInstance().getTime();
    private Button dateBeginText;
    private Button dateEndText;
    private AnalysisAdapter adapter;
    private PieDataSet dataSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_analysis, container, false);
        createDb();
        dateBeginText = view.findViewById(R.id.time_begin);
        setStartBeginDate();

        dateBeginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateBegin(v);
            }
        });

        dateEndText = view.findViewById(R.id.time_end);
        setStartEndDate();
        dateEndText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateEnd(v);
            }
        });
        chart = view.findViewById(R.id.piechart);
        chart.setUsePercentValues(true);

        chart.getDescription().setEnabled(false);
        chart.setDrawHoleEnabled(true);
        categories = getCategory();

        setValues();
        RecyclerView recyclerView = view.findViewById(R.id.legend);
        adapter = new AnalysisAdapter(statistics, dataSet.getColors());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void setStartBeginDate() {
        dateBegin = new Date();
        try {
            dateBegin = new AddMinDateAsyncTask(databasePurchase).execute().get();
            if (dateBegin == null) {
                dateBegin = dateEnd;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dateBeginText.setText(DateUtils.formatDateTime(view.getContext(),
                dateBegin.getTime(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    private void setStartEndDate() {
        dateEndText.setText(DateUtils.formatDateTime(view.getContext(),
                dateEnd.getTime(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

    }


    private void setColors(PieDataSet dataSet) {
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
    }


    private void setValues() {
        ArrayList<PieEntry> values = addValues(getPurchase());
        dataSet = new PieDataSet(values, "");
        if (values.size() == 0) chart.setCenterText(getString(R.string.center_text));
        setParameters(dataSet);
        chart.invalidate();
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
            StatisticsByCategory statisticsByCategory = new StatisticsByCategory(category.getName());
            statistics.add(statisticsByCategory);
        }

    }

    private List<Purchase> getPurchase() {

        try {
            return new GetPurchaseAsyncTask(databasePurchase, dateBegin, dateEnd).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<PieEntry> addValues(List<Purchase> purchases) {
        statistics.clear();
        setStatisticsCategory();
        for (Purchase purchase : purchases) {
            List<Product> products = purchase.getItems();
            for (Product product : products) {
                for (StatisticsByCategory stat : statistics) {
                    if (product.getCategory().getName().equals(stat.getName()))
                        stat.addCost(product.getSum() / 100);
                }
            }
        }

        ArrayList<PieEntry> values = new ArrayList<>();

        List<StatisticsByCategory> newStat = new ArrayList<>();
        for (StatisticsByCategory stat : statistics) {
            if (stat.getCost() != 0) {
                values.add(new PieEntry(stat.getCost(), stat.getName()));
            } else newStat.add(stat);
        }

        statistics.removeAll(newStat);
        return values;

    }


    private void setParameters(PieDataSet dataSet) {
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        chart.setData(data);

        chart.setExtraTopOffset(2f);
        setColors(dataSet);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        chart.setTransparentCircleColor(Color.WHITE);

        chart.setTransparentCircleAlpha(110);//прозрачная область в центре
        chart.setTransparentCircleRadius(61f);//величина прозрачной области = это значение - радиус "дырки"
        chart.setHoleRadius(54f);
        chart.getLegend().setEnabled(false);
    }

    // отображаем диалоговое окно для выбора даты
    public void setDateBegin(View v) {
        new DatePickerDialog(v.getContext(), begin,
                calendarBegin.get(Calendar.YEAR),
                calendarBegin.get(Calendar.MONTH),
                calendarBegin.get(Calendar.DAY_OF_MONTH))
                .show();

    }

    public void setDateEnd(View v) {
        new DatePickerDialog(v.getContext(), end,
                calendarEnd.get(Calendar.YEAR),
                calendarEnd.get(Calendar.MONTH),
                calendarEnd.get(Calendar.DAY_OF_MONTH))
                .show();

    }

    private void setInitialDateBegin() {

        dateBeginText.setText(DateUtils.formatDateTime(view.getContext(),
                calendarBegin.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        dateBegin = calendarBegin.getTime();
        setValues();
        adapter.notifyDataSetChanged();

    }

    private void setInitialDateEnd() {

        dateEndText.setText(DateUtils.formatDateTime(view.getContext(),
                calendarEnd.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        dateEnd = calendarEnd.getTime();
        setValues();
        adapter.notifyDataSetChanged();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener begin = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendarBegin.set(Calendar.YEAR, year);
            calendarBegin.set(Calendar.MONTH, monthOfYear);
            calendarBegin.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendarBegin.set(Calendar.HOUR_OF_DAY, 0);
            calendarBegin.set(Calendar.MINUTE, 0);
            setInitialDateBegin();
        }
    };

    DatePickerDialog.OnDateSetListener end = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendarEnd.set(Calendar.YEAR, year);
            calendarEnd.set(Calendar.MONTH, monthOfYear);
            calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
            calendarEnd.set(Calendar.MINUTE, 59);
            setInitialDateEnd();
        }
    };

    public void createDb() {
        Context context = getContext();
        databasePurchase = DatabasePurchase.getInstanse(context);
    }

}
