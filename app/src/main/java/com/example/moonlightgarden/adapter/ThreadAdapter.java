package com.example.moonlightgarden.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moonlightgarden.R;
import com.example.moonlightgarden.models.ThreadItem;

import java.util.List;

public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.ThreadViewHolder> {

    public interface OnThreadClickListener {
        void onThreadClick(ThreadItem thread);
    }

    private List<ThreadItem> threadList;
    private OnThreadClickListener listener;

    public ThreadAdapter(List<ThreadItem> threadList, OnThreadClickListener listener) {
        this.threadList = threadList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ThreadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forum_thread, parent, false);
        return new ThreadViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadViewHolder holder, int position) {
        ThreadItem thread = threadList.get(position);
        holder.tvTitle.setText(thread.getTitle());
        holder.tvAuthor.setText("By: " + thread.getAuthor());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onThreadClick(thread);
        });
    }

    @Override
    public int getItemCount() {
        return threadList != null ? threadList.size() : 0;
    }

    // Método para actualizar la lista desde Firebase
    public void setThreads(List<ThreadItem> threads) {
        this.threadList = threads;
        notifyDataSetChanged();
    }

    static class ThreadViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAuthor;

        public ThreadViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvThreadTitle);
            tvAuthor = itemView.findViewById(R.id.tvThreadAuthor);
        }
    }
}
