package com.example.payments.async;

import android.os.AsyncTask;

import com.example.payments.models.Expenses;
import com.example.payments.persistence.ExpensesDao;

public class ExpensesUpdateAsyncTask extends AsyncTask<Expenses, Void, Void> {

    private ExpensesDao mExpensesDao;

    public ExpensesUpdateAsyncTask(ExpensesDao dao){
        this.mExpensesDao = dao;
    }
    @Override
    protected Void doInBackground(Expenses... expenses) {
        mExpensesDao.update(expenses);
        return null;
    }
}