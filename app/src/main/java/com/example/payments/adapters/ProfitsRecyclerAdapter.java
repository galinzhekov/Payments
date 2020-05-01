package com.example.payments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payments.R;
import com.example.payments.holders.ProfitsExpensesViewHolder;
import com.example.payments.listeners.OnItemListener;
import com.example.payments.models.Profits;

import java.util.ArrayList;

public class ProfitsRecyclerAdapter extends RecyclerView.Adapter<ProfitsExpensesViewHolder> {

    private ArrayList<Profits> mProfits;

    private OnItemListener mOnItemListener;

    public ProfitsRecyclerAdapter(ArrayList<Profits> profits, OnItemListener onItemListener) {
        this.mProfits = profits;
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ProfitsExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.profits_expenses_layout, parent, false);
        return new ProfitsExpensesViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfitsExpensesViewHolder holder, int position) {
        Profits oProfit = mProfits.get(position);

        holder.setTvPrExLSum(oProfit.getStrSum());
        holder.setTvPrExLDescription(oProfit.getStrTitle());
        holder.setTvPrExLDate(oProfit.getStrDate());
    }

    @Override
    public int getItemCount() {
        return mProfits.size();
    }
}
