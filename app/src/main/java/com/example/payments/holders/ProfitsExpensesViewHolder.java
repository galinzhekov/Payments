package com.example.payments.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payments.R;
import com.example.payments.listeners.OnItemListener;

public class ProfitsExpensesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tvPrExLSum;
    TextView tvPrExLDescription;
    TextView tvPrExLDate;

    OnItemListener onItemListener;

    public ProfitsExpensesViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
        super(itemView);
        this.tvPrExLSum = itemView.findViewById(R.id.tvPrExLSum);
        this.tvPrExLDescription = itemView.findViewById(R.id.tvPrExLDescription);
        this.tvPrExLDate = itemView.findViewById(R.id.tvPrExLData);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    public void setTvPrExLSum(String tvPrExLSum) {
        this.tvPrExLSum.setText(tvPrExLSum);
    }

    public void setTvPrExLDescription(String tvPrExLDescription) {
        this.tvPrExLDescription.setText(tvPrExLDescription);
    }

    public void setTvPrExLDate(String tvPrExLDate) {
        this.tvPrExLDate.setText(tvPrExLDate);
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition());
    }
}
