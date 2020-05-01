package com.example.payments.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "profits")
public class Profits implements Parcelable {

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

    protected Profits(Parcel in) {
        id = in.readInt();
        strSum = in.readString();
        strDescription = in.readString();
        strDate = in.readString();
        strTitle = in.readString();
    }

    public static final Creator<Profits> CREATOR = new Creator<Profits>() {
        @Override
        public Profits createFromParcel(Parcel in) {
            return new Profits(in);
        }

        @Override
        public Profits[] newArray(int size) {
            return new Profits[size];
        }
    };

    public int getId() { return id;}

    public void setId(int iProfitsId) { this.id = iProfitsId; }

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
    public Profits(){}

    public Profits(String strSum, String strDescription, String strDate, String strTitle) {
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
        return "Profits{" +
                "id=" + id +
                ", strSum='" + strSum + '\'' +
                ", strDescription='" + strDescription + '\'' +
                ", strDate='" + strDate + '\'' +
                ", strTitle='" + strTitle + '\'' +
                '}';
    }
}
