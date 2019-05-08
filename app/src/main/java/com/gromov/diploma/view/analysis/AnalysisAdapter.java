package com.gromov.diploma.view.analysis;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gromov.diploma.R;

import java.util.List;

public class AnalysisAdapter extends RecyclerView.Adapter<AnalysisAdapter.MyViewHolder> {
    private List<StatisticsByCategory> statistics;
    private List<Integer> colors;

    public AnalysisAdapter(List<StatisticsByCategory> statistics, List<Integer> colors) {
        this.statistics = statistics;
        this.colors = colors;
    }

    @NonNull
    @Override
    public AnalysisAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_legend, parent, false);

        return new AnalysisAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnalysisAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return statistics.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       private ImageView icon;
       private TextView name;
       private TextView cost;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.legend_icon);
            name=itemView.findViewById(R.id.legend_item_name);
            cost=itemView.findViewById(R.id.legend_item_cost);
        }

        void bind(final int pos){
            final StatisticsByCategory statistic = statistics.get(pos);
            final Integer color = colors.get(pos);
            icon.setBackgroundColor(color);
            name.setText(statistic.getName());
            cost.setText(String.valueOf(statistic.getCost()));
        }
    }
}
