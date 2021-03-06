package com.example.payments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.payments.models.Expenses;
import com.example.payments.persistence.PaymentsRepository;

import java.util.Calendar;

public class ExpensesEditActivity extends AppCompatActivity implements
        View.OnClickListener, TextWatcher, View.OnFocusChangeListener {

    private static final String TAG = "ExpensesEditActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    //Ui components
    private TextView tvDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private LinedEditText mLinedEditText;
    private EditText mEditTitle, etSum;
    private TextView mViewTitle;
    private ImageButton mCheck, mBackArrow;
    private RelativeLayout mCheckContainer, mBackArrowContainer;

    //vars
    private PaymentsRepository mPaymentsRepository;
    private boolean mIsNewItem;
    private Expenses mInitialExpense;
    private int iMode;
    private Expenses mFinalExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_edit);

        tvDate = findViewById(R.id.tvEditDate1);
        mLinedEditText = findViewById(R.id.etDescription1);
        mEditTitle = findViewById(R.id.item_edit_title);
        mViewTitle = findViewById(R.id.item_text_title);
        etSum = findViewById(R.id.etSum1);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);
        mCheck = findViewById(R.id.toolbar_check);
        mBackArrow = findViewById(R.id.toolbar_back_arrow);

        mPaymentsRepository = new PaymentsRepository(this);

        if(getIncomingIntent()){
            setNewExpenseProperties();
            enableEditMode();
        }
        else{
            setExpenseProperties();
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
        mPaymentsRepository.updateExpenses(mFinalExpense);
    }

    private void saveNewItem(){
        mPaymentsRepository.insertExpensesTask(mFinalExpense);
    }

    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("expense_selected_item")){
            mInitialExpense = getIntent().getParcelableExtra("expense_selected_item");

            mFinalExpense = new Expenses();
            mFinalExpense.setStrTitle(mInitialExpense.getStrTitle());
            mFinalExpense.setStrDate(mInitialExpense.getStrDate());
            mFinalExpense.setStrSum(mInitialExpense.getStrSum());
            mFinalExpense.setStrDescription(mInitialExpense.getStrDescription());
            mFinalExpense.setId(mInitialExpense.getId());

            mIsNewItem = false;
            iMode = EDIT_MODE_DISABLED;
            return false;
        }
        iMode = EDIT_MODE_ENABLED;
        mIsNewItem = true;
        return true;
    }

    private void setExpenseProperties(){
        mViewTitle.setText(mInitialExpense.getStrTitle());
        mEditTitle.setText(mInitialExpense.getStrTitle());
        mLinedEditText.setText(mInitialExpense.getStrDescription());
        tvDate.setText(mInitialExpense.getStrDate());
        etSum.setText(mInitialExpense.getStrSum());
    }

    private void setNewExpenseProperties(){
        mViewTitle.setText("Title Text");
        mEditTitle.setText("Title Text");

        mInitialExpense = new Expenses();
        mFinalExpense = new Expenses();

        mInitialExpense.setStrTitle("Title Text");
        mFinalExpense.setStrTitle("Title Text");


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
        String temp1 = etSum.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");
        if(temp.length() > 0 || temp1.length() > 0){
            mFinalExpense.setStrTitle(mEditTitle.getText().toString());
            mFinalExpense.setStrDescription(mLinedEditText.getText().toString());
            mFinalExpense.setStrSum(etSum.getText().toString());
            mFinalExpense.setStrDate(tvDate.getText().toString());

            if(!mFinalExpense.getStrDescription().equals(mInitialExpense.getStrDescription())
                    || !mFinalExpense.getStrSum().equals(mInitialExpense.getStrSum())
                    || !mFinalExpense.getStrDate().equals(mInitialExpense.getStrDate())
                    || !mFinalExpense.getStrTitle().equals(mInitialExpense.getStrTitle())){
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
        etSum.setOnFocusChangeListener(this);
        mLinedEditText.setOnFocusChangeListener(this);
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
            case R.id.tvEditDate1:{
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
            case R.id.etSum1:{
                enableEditMode();
                etSum.requestFocus();
                etSum.setSelection(etSum.length());
                showSoftKeyboard();
                break;
            }
            case R.id.etDescription1:{
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
            case 0:{
                strMonth = "Jan";
                break;
            }
            case 1:{
                strMonth = "Feb";
                break;
            }
            case 2:{
                strMonth = "Mar";
                break;
            }
            case 3:{
                strMonth = "Apr";
                break;
            }
            case 4:{
                strMonth = "May";
                break;
            }
            case 5:{
                strMonth = "Jun";
                break;
            }
            case 6:{
                strMonth = "Jul";
                break;
            }
            case 7:{
                strMonth = "Aug";
                break;
            }
            case 8:{
                strMonth = "Sep";
                break;
            }
            case 9:{
                strMonth = "Oct";
                break;
            }
            case 10:{
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            mBackArrowContainer.setVisibility(View.GONE);
            mCheckContainer.setVisibility(View.VISIBLE);

            mViewTitle.setVisibility(View.GONE);
            mEditTitle.setVisibility(View.VISIBLE);
        }
    }
}
