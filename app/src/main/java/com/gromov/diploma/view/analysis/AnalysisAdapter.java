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

public class AnalysisAdapter extends RecyclerView.Adapter<AnalysisAdapter.ViewHolder> {
    private List<StatisticsByCategory> statistics;
    private List<Integer> colors;
    private View v;

    public AnalysisAdapter(List<StatisticsByCategory> statistics, List<Integer> colors) {
        this.statistics = statistics;
        this.colors = colors;
    }

    @NonNull
    @Override
    public AnalysisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_legend, parent, false);

        return new AnalysisAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnalysisAdapter.ViewHolder myViewHolder, int i) {
        myViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return statistics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView name;
        private TextView cost;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.legend_icon);
            name = itemView.findViewById(R.id.legend_item_name);
            cost = itemView.findViewById(R.id.legend_item_cost);
        }

        void bind(final int pos) {
            final StatisticsByCategory statistic = statistics.get(pos);
            final Integer color = colors.get(pos);
            icon.setBackgroundColor(color);
            name.setText(statistic.getName());
            cost.setText(String.valueOf((int)statistic.getCost()) + " " + v.getContext().getString(R.string.currency_unit_rus));
        }
    }
}
