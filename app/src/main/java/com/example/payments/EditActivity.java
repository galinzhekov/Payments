package com.example.payments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.payments.models.Profits;
import com.example.payments.persistence.PaymentsRepository;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements
        View.OnClickListener, TextWatcher {

    private static final String TAG = "EditActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    private TextView tvDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private LinedEditText mLinedEditText;
    private EditText mEditTitle, etSum;
    private TextView mViewTitle;
    private boolean mIsNewItem;
    private Profits mInitialProfit;
    private int iMode;
    private RelativeLayout mCheckContainer, mBackArrowContainer;
    private ImageButton mCheck, mBackArrow;
    private PaymentsRepository mPaymentsRepository;
    private Profits mFinalProfit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tvDate = findViewById(R.id.tvEditDate);
        mLinedEditText = findViewById(R.id.etDescription);
        mEditTitle = findViewById(R.id.item_edit_title);
        mViewTitle = findViewById(R.id.item_text_title);
        etSum = findViewById(R.id.etSum);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);
        mCheck = findViewById(R.id.toolbar_check);
        mBackArrow = findViewById(R.id.toolbar_back_arrow);

        mPaymentsRepository = new PaymentsRepository(this);

        if(getIncomingIntent()){
            setNewProfitProperties();
            enableEditMode();
        }
        else{
            setProfitProperties();
            disableContentInteraction();
        }

        mDateSetListener = (view, year, month, dayOfMonth) -> {
            String date = year + " " + monthNumberToName(month) + " " + dayOfMonth;
            tvDate.setText(date);
        };

        setListeners();
    }

    private void saveChanges(){
        if(mIsNewItem){
            saveNewItem();
        }
        else {
            updateItem();
        }
    }

    private void updateItem(){
        mPaymentsRepository.updateProfit(mFinalProfit);
    }

    private void saveNewItem(){
        mPaymentsRepository.insertProfitTask(mFinalProfit);
    }

    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selected_item")){
            mInitialProfit = getIntent().getParcelableExtra("selected_item");

            mFinalProfit = new Profits();
            mFinalProfit.setStrTitle(mInitialProfit.getStrTitle());
            mFinalProfit.setStrDate(mInitialProfit.getStrDate());
            mFinalProfit.setStrSum(mInitialProfit.getStrSum());
            mFinalProfit.setStrDescription(mInitialProfit.getStrDescription());
            mFinalProfit.setId(mInitialProfit.getId());
            mFinalProfit.setiCategory(mInitialProfit.getICategory());

            mIsNewItem = false;
            iMode = EDIT_MODE_DISABLED;
            return false;
        }
        iMode = EDIT_MODE_ENABLED;
        mIsNewItem = true;
        return true;
    }

    private void setProfitProperties(){
        mViewTitle.setText(mInitialProfit.getStrTitle());
        mEditTitle.setText(mInitialProfit.getStrTitle());
        mLinedEditText.setText(mInitialProfit.getStrDescription());
        tvDate.setText(mInitialProfit.getStrDate());
        etSum.setText(mInitialProfit.getStrSum());
    }

    private void setNewProfitProperties(){
        mViewTitle.setText("Title Text");
        mEditTitle.setText("Title Text");

        mInitialProfit = new Profits();
        mFinalProfit = new Profits();

        mInitialProfit.setStrTitle("Title Text");
        mFinalProfit.setStrTitle("Title Text");


        mLinedEditText.requestFocus();
    }

    private void enableEditMode(){
        mBackArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        mViewTitle.setVisibility(View.GONE);
        mEditTitle.setVisibility(View.VISIBLE);

        iMode = EDIT_MODE_ENABLED;
        enableContentInteraction();
    }

    private void disableEditMode(){
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mViewTitle.setVisibility(View.VISIBLE);
        mEditTitle.setVisibility(View.GONE);

        iMode = EDIT_MODE_DISABLED;
        String temp = mLinedEditText.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");
        if(temp.length() > 0){
            mFinalProfit.setStrTitle(mEditTitle.getText().toString());
            mFinalProfit.setStrDescription(mLinedEditText.getText().toString());
            mFinalProfit.setStrSum(etSum.getText().toString());
            mFinalProfit.setStrDate(tvDate.getText().toString());
            mFinalProfit.setiCategory(1);

            if(!mFinalProfit.getStrDescription().equals(mInitialProfit.getStrDescription())
            || !mFinalProfit.getStrSum().equals(mInitialProfit.getStrSum())
                    || !mFinalProfit.getStrDate().equals(mInitialProfit.getStrDate())
                    || !mFinalProfit.getStrTitle().equals(mInitialProfit.getStrTitle())){
                saveChanges();
            }
        }
    }

    public void setListeners(){
        mViewTitle.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mBackArrow.setOnClickListener(this);
        mEditTitle.addTextChangedListener(this);
        mLinedEditText.setOnClickListener(this);
        etSum.setOnClickListener(this);
        tvDate.setOnClickListener(this);
    }

    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void showSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditTitle, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_check:{
                hideSoftKeyboard();
                disableEditMode();
                break;
            }
            case R.id.tvEditDate:{
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(this,
                        android.R.style.Theme_DeviceDefault_Dialog, mDateSetListener, year,month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                enableEditMode();
                break;
            }
            case R.id.etSum:{
                enableEditMode();
                etSum.requestFocus();
                etSum.setSelection(etSum.length());
                showSoftKeyboard();
                break;
            }
            case R.id.etDescription:{
                enableEditMode();
                mLinedEditText.requestFocus();
                mLinedEditText.setSelection(mLinedEditText.length());
                showSoftKeyboard();
                break;
            }
            case R.id.item_text_title:{
                enableEditMode();
                mEditTitle.requestFocus();
                mEditTitle.setSelection(mEditTitle.length());
                showSoftKeyboard();
                break;
            }

            case R.id.toolbar_back_arrow:{
                finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(iMode == EDIT_MODE_ENABLED){
            hideSoftKeyboard();
            disableEditMode();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", iMode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        iMode = savedInstanceState.getInt("mode");
        if(iMode == EDIT_MODE_ENABLED){
            enableEditMode();
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mViewTitle.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void disableContentInteraction(){
        mLinedEditText.setKeyListener(null);
        mLinedEditText.setFocusable(false);
        mLinedEditText.setFocusableInTouchMode(false);
        mLinedEditText.setCursorVisible(false);
        mLinedEditText.clearFocus();
    }

    private void enableContentInteraction(){
        mLinedEditText.setKeyListener(new EditText(this).getKeyListener());
        mLinedEditText.setFocusable(true);
        mLinedEditText.setFocusableInTouchMode(true);
        mLinedEditText.setCursorVisible(true);
        mLinedEditText.requestFocus();
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
