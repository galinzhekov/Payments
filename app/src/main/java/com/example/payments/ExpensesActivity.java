package com.example.payments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.payments.adapters.ExpensesRecyclerAdapter;
import com.example.payments.listeners.OnItemListener;
import com.example.payments.listeners.OnSelectMonth;
import com.example.payments.models.Expenses;
import com.example.payments.persistence.PaymentsRepository;
import com.example.payments.util.VerticalSpacingItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExpensesActivity extends AppCompatActivity implements
        OnItemListener,
        View.OnClickListener, OnSelectMonth {

    private static final String TAG = "ExpensesActivity";
    //Ui components
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;

    //vars
    private ArrayList<Expenses> mExpenses = new ArrayList<>();
    private ExpensesRecyclerAdapter mExpensesRecyclerAdapter;
    private PaymentsRepository mPaymentsRepository;
    private String mStrDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        mRecyclerView = findViewById(R.id.expensesView);
        fab = findViewById(R.id.fab1);
        fab.setOnClickListener(this);

        mPaymentsRepository = new PaymentsRepository(this);

        initRecyclerView();
        retrievePayments();

        setSupportActionBar(findViewById(R.id.toolbar1));
        setTitle("Expenses");
    }

    private void retrievePayments(){
        mPaymentsRepository.retrieveExpensesTask().observe(this, expenses -> {
            if(mExpenses.size() > 0){
                mExpenses.clear();
            }
            if(expenses != null){
                mExpenses.addAll(expenses);
            }
            mExpensesRecyclerAdapter.notifyDataSetChanged();
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.separator_line));
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        Log.i(TAG, "onItemClick: " + "main");
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(5);
        mRecyclerView.addItemDecoration(itemDecorator);
        mExpensesRecyclerAdapter = new ExpensesRecyclerAdapter(mExpenses, this);
        mRecyclerView.setAdapter(mExpensesRecyclerAdapter);
    }

    @Override
    public void onItemClick(int iPosition) {
        Log.i(TAG, "onItemClick: " + iPosition);
        Intent intent = new Intent(this, ExpensesEditActivity.class);
        intent.putExtra("expense_selected_item", mExpenses.get(iPosition));
        startActivity(intent);


    }

    void lowestExpenses(){
        mPaymentsRepository.populateListOfExpensesByAsc().observe(this, expenses -> {
            if(mExpenses.size() > 0){
                mExpenses.clear();
            }
            if(expenses != null){
                mExpenses.addAll(expenses);
                Log.d(TAG, "lowestExpenses: "+ mExpenses.toString());
            }
            mExpensesRecyclerAdapter.notifyDataSetChanged();
        });
    }

    void highestExpenses(){
        mPaymentsRepository.populateListOfExpensesByDesc().observe(this, expenses -> {
            if(mExpenses.size() > 0){
                mExpenses.clear();
            }
            if(expenses != null){
                mExpenses.addAll(expenses);
            }
            mExpensesRecyclerAdapter.notifyDataSetChanged();
        });
    }

    void showMonthYearExpenses(){
        mPaymentsRepository.getExpensesMonthyear("%"+ mStrDate + "%").observe(this, expenses -> {
            if(mExpenses.size() > 0){
                mExpenses.clear();
            }
            if(expenses != null){
                mExpenses.addAll(expenses);
                Log.d(TAG, "lowestExpenses: "+ mExpenses.toString());
            }
            mExpensesRecyclerAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ExpensesEditActivity.class);
        startActivity(intent);
    }

    private void deleteNote(Expenses oExpense){
        mExpenses.remove(oExpense);
        mExpensesRecyclerAdapter.notifyDataSetChanged();

        mPaymentsRepository.deleteExpenses(oExpense);
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mExpenses.get(viewHolder.getAdapterPosition()));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    public void clear() {
        int size = mExpenses.size();
        mExpenses.clear();
        mExpensesRecyclerAdapter.notifyItemRangeRemoved(0,size);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.byMonth:{
                clear();
                FragmentManager fm = getSupportFragmentManager();
                DateFragment addContactFragment = DateFragment.newInstance();
                addContactFragment.show(fm, "expense_by_month");
                return true;
            }
            case R.id.lowestSums:{
                clear();
                lowestExpenses();
                return true;
            }
            case R.id.highestSums:{
                clear();
                highestExpenses();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMonthSelected(String strDate) {
        this.mStrDate = strDate;
        showMonthYearExpenses();
    }
}
