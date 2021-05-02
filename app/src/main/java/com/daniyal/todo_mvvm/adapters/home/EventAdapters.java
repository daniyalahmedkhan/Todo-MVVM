package com.daniyal.todo_mvvm.adapters.home;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.daniyal.todo_mvvm.R;
import com.daniyal.todo_mvvm.data.enums.Pri;
import com.daniyal.todo_mvvm.data.enums.Priority;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


public class EventAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView txt_header;

        HeaderViewHolder(View itemView) {
            super(itemView);
            txt_header = (TextView) itemView.findViewById(R.id.txt_header);
        }

    }

    private static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title, txt_desc, txt_time , txt_midday;
        ImageView IV_Priority;

        EventViewHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.TV_Title);
            txt_desc = (TextView) itemView.findViewById(R.id.TV_Desc);
            txt_time = (TextView) itemView.findViewById(R.id.TV_Time);
            txt_midday = (TextView) itemView.findViewById(R.id.TV_Midday);
            IV_Priority = (ImageView) itemView.findViewById(R.id.IV_Priority);
        }

    }

    @NonNull
    private List<ListItem> items = Collections.emptyList();

    public EventAdapters(@NonNull List<ListItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ListItem.TYPE_HEADER: {
                View itemView = inflater.inflate(R.layout.view_list_item_header, parent, false);
                return new HeaderViewHolder(itemView);
            }
            case ListItem.TYPE_EVENT: {
                View itemView = inflater.inflate(R.layout.todo_item_layout, parent, false);
                return new EventViewHolder(itemView);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ListItem.TYPE_HEADER: {
                HeaderItem header = (HeaderItem) items.get(position);
                HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
                // your logic here
                //  holder.txt_header.setText(DateUtils.formatDate(header.getDate()));
                break;
            }
            case ListItem.TYPE_EVENT: {
                EventItem event = (EventItem) items.get(position);
                EventViewHolder holder = (EventViewHolder) viewHolder;
                holder.txt_title.setText(event.getEvent());
                holder.txt_desc.setText(event.getDesc());
                holder.txt_time.setText(event.getHour().split(" ")[0]);
                holder.txt_midday.setText(event.getHour().split(" ")[1]);
                holder.IV_Priority.setColorFilter(ContextCompat.getColor(context, R.color.background), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

}