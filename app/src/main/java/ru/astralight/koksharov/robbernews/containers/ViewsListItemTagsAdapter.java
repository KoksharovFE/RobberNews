package ru.astralight.koksharov.robbernews.containers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.astralight.koksharov.robbernews.R;

/**
 * Created by Koksharov on 13.10.2017. RobberNews
 */

public class ViewsListItemTagsAdapter extends RecyclerView.Adapter<ViewsListItemTagsAdapter.ViewHolder>{
    private List<String> records;

    public ViewsListItemTagsAdapter(List<String> records) {
        this.records = records;
    }
    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public ViewsListItemTagsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spinner_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String record = records.get(i);
        viewHolder.name.setText(record);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.chose_spinner_text);
        }
    }
}
