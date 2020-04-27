package com.example.payments.async;

import android.os.AsyncTask;

import com.example.payments.models.Profits;
import com.example.payments.persistence.ProfitsDao;

public class UpdateAsyncTask extends AsyncTask<Profits, Void, Void> {

    private ProfitsDao mProfitsDao;

    public UpdateAsyncTask(ProfitsDao dao){
        this.mProfitsDao = dao;
    }
    @Override
    protected Void doInBackground(Profits... profits) {
        mProfitsDao.update(profits);
        return null;
    }
}
