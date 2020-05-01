package com.example.payments.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.payments.async.DeleteAsyncTask;
import com.example.payments.async.ExpensesDeleteAsyncTask;
import com.example.payments.async.ExpensesInsertAsyncTask;
import com.example.payments.async.ExpensesUpdateAsyncTask;
import com.example.payments.async.InsertAsyncTask;
import com.example.payments.async.UpdateAsyncTask;
import com.example.payments.models.Expenses;
import com.example.payments.models.Profits;

import java.util.List;

public class PaymentsRepository {
    private PaymentsDatabase mPaymentsDatabase;

    public PaymentsRepository(Context context) {
        mPaymentsDatabase = PaymentsDatabase.getInstance(context);
    }

////Profits
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
        LiveData<List<Profits>> profits = mPaymentsDatabase.getProfitsDao().runtimeQuery(strDate);
        return profits;
    }

////Expenses
    public void insertExpensesTask(Expenses expenses){
        new ExpensesInsertAsyncTask(mPaymentsDatabase.getExpensesDao()).execute(expenses);
    }

    public void updateExpenses(Expenses expenses){
        new ExpensesUpdateAsyncTask(mPaymentsDatabase.getExpensesDao()).execute(expenses);
    }

    public LiveData<List<Expenses>> retrieveExpensesTask(){
        return mPaymentsDatabase.getExpensesDao().getExpenses();
    }

    public void deleteExpenses(Expenses expenses){
        new ExpensesDeleteAsyncTask(mPaymentsDatabase.getExpensesDao()).execute(expenses);
    }

    public LiveData<List<Expenses>> populateListOfExpensesByAsc(){
        return mPaymentsDatabase.getExpensesDao().sortedFindAsc();
    }

    public LiveData<List<Expenses>> populateListOfExpensesByDesc(){
        return mPaymentsDatabase.getExpensesDao().sortedFindDesc();
    }

    public LiveData<List<Expenses>>  getExpensesMonthyear(String strDate){
        LiveData<List<Expenses>> expenses = mPaymentsDatabase.getExpensesDao().runtimeQuery(strDate);
        return expenses;
    }

}
