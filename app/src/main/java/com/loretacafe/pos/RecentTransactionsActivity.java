package com.loretacafe.pos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity to display list of recent transactions
 */
public class RecentTransactionsActivity extends AppCompatActivity
        implements TransactionAdapter.OnTransactionClickListener {

    private RecyclerView rvTransactions;
    private LinearLayout emptyState;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TransactionAdapter adapter;
    private ImageView btnBack;
    private BottomNavigationView bottomNavigation;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_transactions);

        initializeViews();
        setupRecyclerView();
        setupListeners();
        setupBottomNavigation();
        loadTransactions();
    }

    private void initializeViews() {
        rvTransactions = findViewById(R.id.rvTransactions);
        emptyState = findViewById(R.id.emptyState);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        btnBack = findViewById(R.id.btnBack);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        fabAdd = findViewById(R.id.fabAdd);
    }

    private void setupRecyclerView() {
        adapter = new TransactionAdapter(this);
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        rvTransactions.setAdapter(adapter);
    }

    private void setupListeners() {
        // Back button
        btnBack.setOnClickListener(v -> onBackPressed());

        // FAB
        fabAdd.setOnClickListener(v -> {
            Toast.makeText(this, "Create New Order", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to add transaction screen
        });

        // Pull to refresh
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(() -> {
                loadTransactions();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(this, "Transactions refreshed", Toast.LENGTH_SHORT).show();
            });

            swipeRefreshLayout.setColorSchemeResources(R.color.cafe_brown_primary);
        }
    }

    private void setupBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.nav_history);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Navigate to Dashboard
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            } else if (itemId == R.id.nav_history) {
                // Already here
                return true;
            } else if (itemId == R.id.nav_menu) {
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_reports) {
                Toast.makeText(this, "Reports - Coming Soon", Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        });
    }

    /**
     * Load transactions
     */
    private void loadTransactions() {
        // TODO: Replace with actual database/API call
        List<Transaction> transactions = generateSampleTransactions();

        if (transactions.isEmpty()) {
            // Show empty state
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setVisibility(View.GONE);
            }
            rvTransactions.setVisibility(View.GONE);
            emptyState.setVisibility(View.VISIBLE);
        } else {
            // Show transactions
            emptyState.setVisibility(View.GONE);
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setVisibility(View.VISIBLE);
            }
            rvTransactions.setVisibility(View.VISIBLE);
            adapter.setTransactions(transactions);
        }
    }

    /**
     * Handle transaction item click - Navigate to detail screen
     */
    @Override
    public void onTransactionClick(Transaction transaction) {
        Intent intent = new Intent(this, TransactionDetailActivity.class);
        intent.putExtra("transaction", transaction);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Generate sample transactions matching the screenshots
     */
    private List<Transaction> generateSampleTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        // TODAY SECTION
        Transaction t1 = new Transaction();
        t1.setOrderId("000000");
        t1.setCustomerName("Customer Name");
        t1.setDate("Oct 19");
        t1.setTime("00:00 PM");
        t1.setTotalAmount(0.00);
        t1.setPaymentMethod("Card");
        t1.setSection("Today");
        transactions.add(t1);

        // YESTERDAY SECTION
        Transaction t2 = new Transaction();
        t2.setOrderId("000001");
        t2.setCustomerName("Customer Name");
        t2.setDate("Oct 17");
        t2.setTime("02:35 PM");
        t2.setTotalAmount(92.00);
        t2.setPaymentMethod("Cash");
        t2.setSection("Yesterday");
        transactions.add(t2);

        // 00 MONTH SECTION
        Transaction t3 = new Transaction();
        t3.setOrderId("000002");
        t3.setCustomerName("Customer Name");
        t3.setDate("MMM DD");
        t3.setTime("00:00 PM");
        t3.setTotalAmount(0.00);
        t3.setPaymentMethod("Cash");
        t3.setSection("00 Month");
        transactions.add(t3);

        Transaction t4 = new Transaction();
        t4.setOrderId("000003");
        t4.setCustomerName("Customer Name");
        t4.setDate("MMM DD");
        t4.setTime("00:00 PM");
        t4.setTotalAmount(0.00);
        t4.setPaymentMethod("Cash");
        t4.setSection("00 Month");
        transactions.add(t4);

        Transaction t5 = new Transaction();
        t5.setOrderId("000004");
        t5.setCustomerName("Customer Name");
        t5.setDate("MMM DD");
        t5.setTime("00:00 PM");
        t5.setTotalAmount(0.00);
        t5.setPaymentMethod("Cash");
        t5.setSection("00 Month");
        transactions.add(t5);

        return transactions;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
