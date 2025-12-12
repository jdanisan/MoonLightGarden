package com.example.moonlightgarden.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moonlightgarden.R;
import com.example.moonlightgarden.models.Topic;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    public interface OnTopicClickListener {
        void onTopicClick(Topic topic);
    }

    List<Topic> topicList;
    private OnTopicClickListener listener;

    public TopicAdapter(List<Topic> topicList, OnTopicClickListener listener) {
        this.topicList = topicList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forum_topic, parent, false);
        return new TopicViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = topicList.get(position);
        holder.tvTitle.setText(topic.getTitle());
        holder.tvDescription.setText(topic.getDescription());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onTopicClick(topic);
        });
    }

    @Override
    public int getItemCount() {
        return topicList != null ? topicList.size() : 0;
    }

    // Método para actualizar la lista desde Firebase
    public void setTopics(List<Topic> topics) {
        this.topicList = topics;
        notifyDataSetChanged();
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.topicTitle);
            tvDescription = itemView.findViewById(R.id.topicDescription);
        }
    }
}
