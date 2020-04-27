package com.example.payments.async;

import android.os.AsyncTask;

import com.example.payments.models.Profits;
import com.example.payments.persistence.ProfitsDao;

public class InsertAsyncTask extends AsyncTask<Profits, Void, Void> {

    private ProfitsDao mProfitsDao;

    public InsertAsyncTask(ProfitsDao dao){
        this.mProfitsDao = dao;
    }
    @Override
    protected Void doInBackground(Profits... profits) {
        mProfitsDao.insertItems(profits);
        return null;
    }
}
