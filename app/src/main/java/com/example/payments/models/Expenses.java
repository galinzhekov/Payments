package com.example.payments.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses")
public class Expenses implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "sum")
    private String strSum;

    @ColumnInfo(name = "description")
    private String strDescription;

    @ColumnInfo(name = "date")
    private String strDate;

    @ColumnInfo(name = "title")
    private String strTitle;

    protected Expenses(Parcel in) {
        id = in.readInt();
        strSum = in.readString();
        strDescription = in.readString();
        strDate = in.readString();
        strTitle = in.readString();
    }

    public static final Creator<Expenses> CREATOR = new Creator<Expenses>() {
        @Override
        public Expenses createFromParcel(Parcel in) {
            return new Expenses(in);
        }

        @Override
        public Expenses[] newArray(int size) {
            return new Expenses[size];
        }
    };

    public int getId() { return id;}

    public void setId(int iExpensesId) { this.id = iExpensesId; }

    public String getStrSum() {
        return strSum;
    }

    public void setStrSum(String strSum) {
        this.strSum = strSum;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrTitle() { return strTitle; }

    public void setStrTitle(String strTitle) { this.strTitle = strTitle; }

    @Ignore
    public Expenses(){}

    public Expenses(String strSum, String strDescription, String strDate, String strTitle) {
        this.strSum = strSum;
        this.strDescription = strDescription;
        this.strDate = strDate;
        this.strTitle = strTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(strSum);
        dest.writeString(strDescription);
        dest.writeString(strDate);
        dest.writeString(strTitle);
    }

    @Override
    public String toString() {
        return "Expenses{" +
                "id=" + id +
                ", strSum='" + strSum + '\'' +
                ", strDescription='" + strDescription + '\'' +
                ", strDate='" + strDate + '\'' +
                ", strTitle='" + strTitle + '\'' +
                '}';
    }
}
