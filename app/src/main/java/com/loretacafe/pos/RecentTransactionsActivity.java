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
            Toast.makeText(this, "Add new transaction", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to add transaction screen
        });
    }

    private void setupBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.nav_history);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Navigate to Dashboard
                finish();
                return true;
            } else if (itemId == R.id.nav_history) {
                // Already here
                return true;
            } else if (itemId == R.id.nav_menu) {
                Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_reports) {
                Toast.makeText(this, "Reports", Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        });
    }

    /**
     * Load transactions - This will be connected to database by your backend buddy
     */
    private void loadTransactions() {
        // TODO: Replace this with actual database call
        List<Transaction> transactions = getDummyTransactions();

        if (transactions.isEmpty()) {
            // Show empty state
            rvTransactions.setVisibility(View.GONE);
            emptyState.setVisibility(View.VISIBLE);
        } else {
            // Show transactions
            emptyState.setVisibility(View.GONE);
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
        intent.putExtra("TRANSACTION", transaction);
        startActivity(intent);
    }

    /**
     * PLACEHOLDER DATA - Your backend buddy will replace this with database calls
     */
    private List<Transaction> getDummyTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        // Today
        Transaction t1 = new Transaction(
                "000001",
                "Customer Name",
                "Oct 19",
                "02:35 PM",
                0.00,
                "Card",
                "Today"
        );
        t1.addItem(new Transaction.OrderItem("Iced Latte", 2, 92.00));
        transactions.add(t1);

        // Yesterday
        Transaction t2 = new Transaction(
                "000002",
                "Customer Name",
                "Oct 17",
                "02:35 PM",
                92.00,
                "Cash",
                "Yesterday"
        );
        t2.addItem(new Transaction.OrderItem("Iced Latte", 1, 92.00));
        transactions.add(t2);

        // 00 Month
        Transaction t3 = new Transaction(
                "000003",
                "Customer Name",
                "Oct 01",
                "00:00 PM",
                0.00,
                "Cash",
                "00 Month"
        );
        transactions.add(t3);

        Transaction t4 = new Transaction(
                "000004",
                "Customer Name",
                "Oct 01",
                "00:00 PM",
                0.00,
                "Cash",
                "00 Month"
        );
        transactions.add(t4);

        Transaction t5 = new Transaction(
                "000005",
                "Customer Name",
                "Oct 01",
                "00:00 PM",
                0.00,
                "Cash",
                "00 Month"
        );
        transactions.add(t5);

        return transactions;
    }
}