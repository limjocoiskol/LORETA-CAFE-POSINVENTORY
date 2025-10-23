package com.loretacafe.pos;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMenu;
    private TextView tvSeeAll;
    private TextView tvEstimatedProfit;
    private TextView tvStocksCount;
    private RecyclerView rvTransactions;
    private LinearLayout llEmptyState;
    private FloatingActionButton fabAdd;
    private BottomNavigationView bottomNavigation;
    
    private TransactionAdapter transactionAdapter;
    private List<Transaction> recentTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize views
        initializeViews();

        // Setup navigation drawer
        setupNavigationDrawer();

        // Setup listeners
        setupClickListeners();

        // Setup bottom navigation
        setupBottomNavigation();

        // Load sample data
        loadSampleData();
    }

    private void initializeViews() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        btnMenu = findViewById(R.id.btnMenu);
        tvSeeAll = findViewById(R.id.tvSeeAll);
        tvEstimatedProfit = findViewById(R.id.tvEstimatedProfit);
        tvStocksCount = findViewById(R.id.tvStocksCount);
        rvTransactions = findViewById(R.id.rvTransactions);
        llEmptyState = findViewById(R.id.llEmptyState);
        fabAdd = findViewById(R.id.fabAdd);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        // Setup RecyclerView
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        rvTransactions.setNestedScrollingEnabled(false);
        
        transactionAdapter = new TransactionAdapter(transaction -> {
            // Navigate to transaction detail
            Intent intent = new Intent(DashboardActivity.this, TransactionDetailActivity.class);
            intent.putExtra("transaction", transaction);
            startActivity(intent);
        });
        
        rvTransactions.setAdapter(transactionAdapter);
    }

    private void setupNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();
            
            if (itemId == R.id.nav_dashboard) {
                // Already on dashboard
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.nav_recent_transactions) {
                Intent intent = new Intent(this, RecentTransactionsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.nav_create_order) {
                Toast.makeText(this, "Create Order - Coming Soon", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.nav_add_item) {
                Intent intent = new Intent(this, EditItemActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.nav_add_category) {
                Intent intent = new Intent(this, CategoriesActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.nav_inventory) {
                Toast.makeText(this, "Inventory - Coming Soon", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.nav_sales_report) {
                Toast.makeText(this, "Sales Report - Coming Soon", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            
            return false;
        });
    }

    private void setupClickListeners() {
        // Menu button click - Open navigation drawer
        btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // See all transactions click
        tvSeeAll.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, RecentTransactionsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Floating action button click
        fabAdd.setOnClickListener(v -> {
            Toast.makeText(this, "Create New Order", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to create order screen
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
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            } else if (itemId == R.id.nav_menu) {
                // Navigate to Menu Activity
                Intent intent = new Intent(DashboardActivity.this, MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            } else if (itemId == R.id.nav_reports) {
                Toast.makeText(this, "Reports - Coming Soon", Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        });
    }

    private void loadSampleData() {
        // Create sample transactions
        recentTransactions = createSampleTransactions();

        if (recentTransactions.isEmpty()) {
            // Show empty state
            llEmptyState.setVisibility(View.VISIBLE);
            rvTransactions.setVisibility(View.GONE);
            tvSeeAll.setVisibility(View.GONE);
            tvEstimatedProfit.setText("₱ 0.00");
            tvStocksCount.setText("0");
        } else {
            // Show transactions (limit to 3 for dashboard)
            llEmptyState.setVisibility(View.GONE);
            rvTransactions.setVisibility(View.VISIBLE);
            tvSeeAll.setVisibility(View.VISIBLE);
            
            List<Transaction> limitedTransactions = recentTransactions.size() > 3 
                ? recentTransactions.subList(0, 3) 
                : recentTransactions;
            
            transactionAdapter.setTransactions(limitedTransactions);
            
            // Calculate total profit
            double totalProfit = 0;
            for (Transaction transaction : recentTransactions) {
                totalProfit += transaction.getTotalAmount();
            }
            tvEstimatedProfit.setText(String.format(Locale.getDefault(), "₱ %.2f", totalProfit));
            
            // Mock stocks count
            tvStocksCount.setText("127");
        }
    }

    private List<Transaction> createSampleTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        
        // Sample transaction 1
        Transaction t1 = new Transaction();
        t1.setOrderId("ORD001");
        t1.setCustomerName("Quan item");
        t1.setDate("Mon 00");
        t1.setTime("00:00 AM");
        t1.setTotalAmount(100.00);
        t1.setPaymentMethod("Cash");
        t1.setSection("Today");
        
        // Sample transaction 2
        Transaction t2 = new Transaction();
        t2.setOrderId("ORD002");
        t2.setCustomerName("Mae");
        t2.setDate("Sep 12");
        t2.setTime("03:23 PM");
        t2.setTotalAmount(312.00);
        t2.setPaymentMethod("1 Cafe Latte, +3 items");
        t2.setSection("Today");
        
        // Sample transaction 3
        Transaction t3 = new Transaction();
        t3.setOrderId("ORD003");
        t3.setCustomerName("John");
        t3.setDate("Sep 12");
        t3.setTime("02:17 PM");
        t3.setTotalAmount(118.00);
        t3.setPaymentMethod("1 Oreo Cheesecake");
        t3.setSection("Today");
        
        // Sample transaction 4
        Transaction t4 = new Transaction();
        t4.setOrderId("ORD004");
        t4.setCustomerName("Dine");
        t4.setDate("Sep 12");
        t4.setTime("09:26 AM");
        t4.setTotalAmount(374.00);
        t4.setPaymentMethod("1 Wintermelon, +2 items");
        t4.setSection("Today");
        
        transactions.add(t1);
        transactions.add(t2);
        transactions.add(t3);
        transactions.add(t4);
        
        return transactions;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
