package com.example.quotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder> {
    private List<String> categories;
    private Context context;
    private QuotesListener listener;

    public QuotesAdapter(List<String> categories, Context context, QuotesListener listener) {
        this.categories = categories;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_quotes_row, parent, false);
        return new QuotesViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuotesViewHolder holder, int position) {
        holder.catName.setText(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class QuotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView catName;
        QuotesListener listener;
        ConstraintLayout parent;
        public QuotesViewHolder(@NonNull View itemView, QuotesListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.catName = itemView.findViewById(R.id.catName);
            this.listener = listener;
            this.parent = itemView.findViewById(R.id.constraintL);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    public interface QuotesListener {
        void onClick(View v, int position);
    }
}
