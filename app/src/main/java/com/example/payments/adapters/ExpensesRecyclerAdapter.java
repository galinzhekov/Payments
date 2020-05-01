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
import com.example.payments.models.Expenses;

import java.util.ArrayList;

public class ExpensesRecyclerAdapter extends RecyclerView.Adapter<ProfitsExpensesViewHolder> {

    private ArrayList<Expenses> mExpenses;

    private OnItemListener mOnItemListener;

    public ExpensesRecyclerAdapter(ArrayList<Expenses> expenses, OnItemListener onItemListener) {
        this.mExpenses = expenses;
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
        Expenses oExpense = mExpenses.get(position);

        holder.setTvPrExLSum(oExpense.getStrSum());
        holder.setTvPrExLDescription(oExpense.getStrTitle());
        holder.setTvPrExLDate(oExpense.getStrDate());
    }

    @Override
    public int getItemCount() {
        return mExpenses.size();
    }
}
