package com.loretacafe.pos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Activity to display detailed transaction/order information
 */
public class TransactionDetailActivity extends AppCompatActivity {

    private TextView tvOrderNumber;
    private TextView tvCustomerName;
    private TextView tvDateTime;
    private LinearLayout itemsContainer;
    private TextView tvTotal;
    private TextView tvOrderNumberBottom;
    private TextView tvPaymentMethod;
    private ImageView btnBack;
    private BottomNavigationView bottomNavigation;
    private FloatingActionButton fabAdd;

    private Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        // Get transaction from intent
        transaction = (Transaction) getIntent().getSerializableExtra("TRANSACTION");

        initializeViews();
        setupListeners();
        setupBottomNavigation();
        displayTransactionDetails();
    }

    private void initializeViews() {
        tvOrderNumber = findViewById(R.id.tvOrderNumber);
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvDateTime = findViewById(R.id.tvDateTime);
        itemsContainer = findViewById(R.id.itemsContainer);
        tvTotal = findViewById(R.id.tvTotal);
        tvOrderNumberBottom = findViewById(R.id.tvOrderNumberBottom);
        tvPaymentMethod = findViewById(R.id.tvPaymentMethod);
        btnBack = findViewById(R.id.btnBack);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        fabAdd = findViewById(R.id.fabAdd);
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
                // Go back to transactions list
                onBackPressed();
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

    private void displayTransactionDetails() {
        if (transaction == null) {
            Toast.makeText(this, "Error loading transaction", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set order info
        tvOrderNumber.setText("Order No.: #" + transaction.getOrderId());
        tvCustomerName.setText(transaction.getCustomerName());
        tvDateTime.setText(transaction.getDate() + ", " + transaction.getTime());
        tvOrderNumberBottom.setText("Order No.: #" + transaction.getOrderId());
        tvPaymentMethod.setText("Mode of Payment: " + transaction.getPaymentMethod());

        // Add items dynamically
        itemsContainer.removeAllViews();
        for (Transaction.OrderItem item : transaction.getItems()) {
            View itemView = createItemView(item);
            itemsContainer.addView(itemView);
        }

        // Calculate and display total
        double total = transaction.calculateTotal();
        tvTotal.setText(String.format("₱ %.2f", total));
    }

    /**
     * Create a view for each order item
     */
    private View createItemView(Transaction.OrderItem item) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View itemView = inflater.inflate(R.layout.item_order_detail, itemsContainer, false);

        TextView tvItemName = itemView.findViewById(R.id.tvItemName);
        TextView tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
        TextView tvItemQuantity = itemView.findViewById(R.id.tvItemQuantity);
        TextView tvItemTotal = itemView.findViewById(R.id.tvItemTotal);

        tvItemName.setText(item.getItemName());
        tvItemPrice.setText(item.getFormattedPrice());
        tvItemQuantity.setText(item.getQuantityDisplay());
        tvItemTotal.setText(item.getFormattedTotal());

        return itemView;
    }
}