package com.example.payments.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.payments.models.Profits;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Dao
public interface ProfitsDao {

    @Insert
    long[] insertItems(Profits... profits);

    @Query("select * from profits")
    LiveData<List<Profits>> getProfits();

    @Query("SELECT * FROM profits ORDER BY cast(sum as unsigned) ASC")
    LiveData<List<Profits>> sortedFindAsc();

    @Query("SELECT * FROM profits ORDER BY  cast(sum as unsigned) DESC ")
    LiveData<List<Profits>> sortedFindDesc();

    @Query("SELECT * FROM profits WHERE [date] LIKE :sortQuery ")
    LiveData<List<Profits>> runtimeQuery(String sortQuery);

    @Delete
    int delete(Profits... profits);

    @Update
    int update(Profits... profits);
}
