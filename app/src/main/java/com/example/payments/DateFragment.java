package com.example.payments;

import androidx.fragment.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.payments.listeners.OnSelectMonth;

public class DateFragment extends DialogFragment implements View.OnClickListener {

    private EditText etMonth, etYear;
    private Button btnShow;
    OnSelectMonth listener;

    public DateFragment() {
    }

    public static DateFragment newInstance() {
        return new DateFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_date, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etMonth = view.findViewById(R.id.etMonth);
        etYear = view.findViewById(R.id.etYear);
        btnShow = view.findViewById(R.id.btnShow);
        btnShow.setOnClickListener(this);

        etMonth.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnSelectMonth)context;
    }

    @Override
    public void onClick(View v) {
        String strYear = etYear.getText().toString();
        String strMonth = monthNumberToName(Integer.valueOf(etMonth.getText().toString()));
        String strDate = strYear + " " + strMonth;

        listener.onMonthSelected(strDate);

        dismiss();
    }

    public String monthNumberToName(int iMonth){
        String strMonth;
        switch(iMonth){
            case 1:{
                strMonth = "Jan";
                break;
            }
            case 2:{
                strMonth = "Feb";
                break;
            }
            case 3:{
                strMonth = "Mar";
                break;
            }
            case 4:{
                strMonth = "Apr";
                break;
            }
            case 5:{
                strMonth = "May";
                break;
            }
            case 6:{
                strMonth = "Jun";
                break;
            }
            case 7:{
                strMonth = "Jul";
                break;
            }
            case 8:{
                strMonth = "Aug";
                break;
            }
            case 9:{
                strMonth = "Sep";
                break;
            }
            case 10:{
                strMonth = "Oct";
                break;
            }
            case 11:{
                strMonth = "Nov";
                break;
            }
            default:{
                strMonth = "Dec";
                break;
            }
        }
        return strMonth;
    }

}