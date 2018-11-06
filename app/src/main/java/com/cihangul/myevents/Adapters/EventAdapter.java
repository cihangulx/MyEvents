package com.cihangul.myevents.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cihangul.myevents.DB.Models.Event;
import com.cihangul.myevents.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private List<Event> events;
    private OnItemClickListener onItemClickListener;

    public EventAdapter(Context context, List<Event> events, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.events = events;
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventViewHolder(LayoutInflater.from(context).inflate(R.layout.event_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.setTitle(events.get(position).getTitle());
        holder.setSubTitle(events.get(position).getSubTitle());
        holder.setTimeRemaining(events.get(position).getTimeRemaining());
    }

    @Override
    public int getItemCount() {
        return events != null ? events.size() : 0;
    }

    public void updateData(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    public Event getItem(int position) {
        return events.get(position);
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView title, subTitle, timeRemaining;
        private ImageButton edit;

        EventViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.sub_title);
            timeRemaining = itemView.findViewById(R.id.time_remaining);

            edit = itemView.findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null)
                        onItemClickListener.OnItemClick(events.get(getAdapterPosition()));
                }
            });
        }

        void setTitle(String title) {
            this.title.setText(title);
        }

        void setSubTitle(String subTitle) {
            this.subTitle.setText(subTitle);
        }

        void setTimeRemaining(String timeRemaining) {
            this.timeRemaining.setText(timeRemaining);
        }


    }

    public interface OnItemClickListener {
        void OnItemClick(Event event);
    }
}

