package com.example.payments.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.payments.models.Expenses;

import java.util.List;

@Dao
public interface ExpensesDao {
    @Insert
    long[] insertItems(Expenses... expenses);

    @Query("select * from expenses")
    LiveData<List<Expenses>> getExpenses();

    @Query("SELECT * FROM expenses ORDER BY cast(sum as unsigned) ASC")
    LiveData<List<Expenses>> sortedFindAsc();

    @Query("SELECT * FROM expenses ORDER BY  cast(sum as unsigned) DESC ")
    LiveData<List<Expenses>> sortedFindDesc();

    @Query("SELECT * FROM expenses WHERE [date] LIKE :sortQuery ")
    LiveData<List<Expenses>> runtimeQuery(String sortQuery);

    @Delete
    int delete(Expenses... expenses);

    @Update
    int update(Expenses... expenses);
}
