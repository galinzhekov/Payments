package com.example.payments.persistence;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.payments.async.DeleteAsyncTask;
import com.example.payments.async.InsertAsyncTask;
import com.example.payments.async.UpdateAsyncTask;
import com.example.payments.models.Profits;

import java.util.List;

public class PaymentsRepository {
    private PaymentsDatabase mPaymentsDatabase;

    public PaymentsRepository(Context context) {
        mPaymentsDatabase = PaymentsDatabase.getInstance(context);
    }

    public void insertProfitTask(Profits profits){
        new InsertAsyncTask(mPaymentsDatabase.getProfitsDao()).execute(profits);
    }

    public void updateProfit(Profits profits){
        new UpdateAsyncTask(mPaymentsDatabase.getProfitsDao()).execute(profits);
    }

    public LiveData<List<Profits>> retrieveProfitsTask(){
        return mPaymentsDatabase.getProfitsDao().getProfits();
    }

    public void deleteProfits(Profits profits){
        new DeleteAsyncTask(mPaymentsDatabase.getProfitsDao()).execute(profits);
    }

    public LiveData<List<Profits>> populateListOfProfitsByAsc(){
        return mPaymentsDatabase.getProfitsDao().sortedFindAsc();
    }

    public LiveData<List<Profits>> populateListOfProfitsByDesc(){
        return mPaymentsDatabase.getProfitsDao().sortedFindDesc();
    }

    public LiveData<List<Profits>>  getMonthyear(String strDate){
        String query ="SELECT * FROM User ORDER BY " + strDate;
        LiveData<List<Profits>> users = mPaymentsDatabase.getProfitsDao().runtimeQuery(strDate);
        return users;
    }

}
