package com.example.payments.async;

import android.os.AsyncTask;

import com.example.payments.models.Expenses;
import com.example.payments.persistence.ExpensesDao;

public class ExpensesInsertAsyncTask extends AsyncTask<Expenses, Void, Void> {

    private ExpensesDao mExpensesDao;

    public ExpensesInsertAsyncTask(ExpensesDao dao){
        this.mExpensesDao = dao;
    }
    @Override
    protected Void doInBackground(Expenses... expenses) {
        mExpensesDao.insertItems(expenses);
        return null;
    }
}