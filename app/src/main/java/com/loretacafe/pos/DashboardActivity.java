package com.loretacafe.pos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DashboardActivity extends AppCompatActivity {

    private ImageButton btnMenu;
    private TextView tvSeeAll;
    private RecyclerView rvTransactions;
    private FloatingActionButton fabAdd;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize views
        initializeViews();

        // Setup listeners
        setupClickListeners();

        // Setup bottom navigation
        setupBottomNavigation();
    }

    private void initializeViews() {
        btnMenu = findViewById(R.id.btnMenu);
        tvSeeAll = findViewById(R.id.tvSeeAll);
        rvTransactions = findViewById(R.id.rvTransactions);
        fabAdd = findViewById(R.id.fabAdd);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        // Setup RecyclerView
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        // TODO: Set adapter when you have transaction data
        // rvTransactions.setAdapter(new TransactionAdapter(transactionList));
    }

    private void setupClickListeners() {
        // Menu button click
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Open navigation drawer or menu
                Toast.makeText(DashboardActivity.this, "Menu clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // See all transactions click - NAVIGATE TO RECENT TRANSACTIONS
        tvSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, RecentTransactionsActivity.class);
                startActivity(intent);
            }
        });

        // Floating action button click
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Navigate to add transaction/order screen
                Toast.makeText(DashboardActivity.this, "Add new transaction", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupBottomNavigation() {
        // Set Home/Dashboard as selected
        bottomNavigation.setSelectedItemId(R.id.nav_home);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Already on dashboard/home
                return true;
            } else if (itemId == R.id.nav_history) {
                // Navigate to Recent Transactions
                Intent intent = new Intent(DashboardActivity.this, RecentTransactionsActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_menu) {
                // TODO: Open menu/navigation drawer
                Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_reports) {
                // TODO: Navigate to reports screen
                Toast.makeText(this, "Reports", Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        });
    }
}