package com.example.payments.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.payments.models.Categories;
import com.example.payments.models.Profits;

@Database(entities = {Profits.class, Categories.class}, version = 1)
public abstract class PaymentsDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "payments_db";

    public static PaymentsDatabase instance;

    static PaymentsDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext()
                    , PaymentsDatabase.class
                    , DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract ProfitsDao getProfitsDao();
}
