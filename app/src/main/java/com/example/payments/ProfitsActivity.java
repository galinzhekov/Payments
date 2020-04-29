package com.example.payments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payments.adapters.ProfitsRecyclerAdapter;
import com.example.payments.listeners.OnItemListener;
import com.example.payments.listeners.OnSelectMonth;
import com.example.payments.models.Profits;
import com.example.payments.persistence.PaymentsRepository;
import com.example.payments.util.VerticalSpacingItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProfitsActivity extends AppCompatActivity implements
        OnItemListener,
        View.OnClickListener, OnSelectMonth {

    private static final String TAG = "ProfitsActivity";
    //Ui components
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;

    //vars
    private ArrayList<Profits> mProfits = new ArrayList<>();
    private ProfitsRecyclerAdapter mProfitsRecyclerAdapter;
    private PaymentsRepository mPaymentsRepository;
    private String mStrDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profits);
        mRecyclerView = findViewById(R.id.profitsView);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        mPaymentsRepository = new PaymentsRepository(this);

        initRecyclerView();
        retrievePayments();

        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle("Profits");
    }

    private void retrievePayments(){
        mPaymentsRepository.retrieveProfitsTask().observe(this, profits -> {
            if(mProfits.size() > 0){
                mProfits.clear();
            }
            if(profits != null){
                mProfits.addAll(profits);
            }
            mProfitsRecyclerAdapter.notifyDataSetChanged();
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
        mProfitsRecyclerAdapter = new ProfitsRecyclerAdapter(mProfits, this);
        mRecyclerView.setAdapter(mProfitsRecyclerAdapter);
    }

    @Override
    public void onItemClick(int iPosition) {
        Log.i(TAG, "onItemClick: " + iPosition);
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("selected_item", mProfits.get(iPosition));
        startActivity(intent);


    }

    void lowestProfits(){
        mPaymentsRepository.populateListOfProfitsByAsc().observe(this, profits -> {
            if(mProfits.size() > 0){
                mProfits.clear();
            }
            if(profits != null){
                mProfits.addAll(profits);
                Log.d(TAG, "lowestProfits: "+ mProfits.toString());
            }
            mProfitsRecyclerAdapter.notifyDataSetChanged();
        });
    }

    void highestProfits(){
        mPaymentsRepository.populateListOfProfitsByDesc().observe(this, profits -> {
            if(mProfits.size() > 0){
                mProfits.clear();
            }
            if(profits != null){
                mProfits.addAll(profits);
            }
            mProfitsRecyclerAdapter.notifyDataSetChanged();
        });
    }

    void showMonthYearProfits(){
        mPaymentsRepository.getMonthyear("%"+ mStrDate + "%").observe(this, profits -> {
            if(mProfits.size() > 0){
                mProfits.clear();
            }
            if(profits != null){
                mProfits.addAll(profits);
                Log.d(TAG, "lowestProfits: "+ mProfits.toString());
            }
            mProfitsRecyclerAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    private void deleteNote(Profits oProfit){
        mProfits.remove(oProfit);
        mProfitsRecyclerAdapter.notifyDataSetChanged();

        mPaymentsRepository.deleteProfits(oProfit);
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mProfits.get(viewHolder.getAdapterPosition()));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    public void clear() {
        int size = mProfits.size();
        mProfits.clear();
        mProfitsRecyclerAdapter.notifyItemRangeRemoved(0,size);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.byMonth:{
                clear();
                FragmentManager fm = getSupportFragmentManager();
                DateFragment addContactFragment = DateFragment.newInstance();
                addContactFragment.show(fm, "profits_by_month");
                return true;
            }
            case R.id.lowestProfits:{
                clear();
                lowestProfits();
                return true;
            }
            case R.id.highestProfits:{
                clear();
                highestProfits();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMonthSelected(String strDate) {
        this.mStrDate = strDate;
        showMonthYearProfits();
    }
}
