package com.loretacafe.pos;

import android.content.Intent;
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
        transaction = (Transaction) getIntent().getSerializableExtra("transaction");

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
            Toast.makeText(this, "Create New Order", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to add transaction screen
        });
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
                // Go back to transactions list
                onBackPressed();
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

    private void displayTransactionDetails() {
        if (transaction == null) {
            Toast.makeText(this, "Error loading transaction", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set order info
        tvOrderNumber.setText("Order No.: #" + transaction.getOrderId());
        tvCustomerName.setText(transaction.getCustomerName());
        tvDateTime.setText("Month " + transaction.getDate() + ", 0000 | " + transaction.getTime());
        tvOrderNumberBottom.setText("Order No.: #" + transaction.getOrderId());
        tvPaymentMethod.setText("Mode of Payment: " + transaction.getPaymentMethod());

        // Add items dynamically
        itemsContainer.removeAllViews();
        
        if (transaction.getItems() == null || transaction.getItems().isEmpty()) {
            // Add sample items for demo
            addSampleItems();
        } else {
            for (Transaction.OrderItem item : transaction.getItems()) {
                View itemView = createItemView(item);
                itemsContainer.addView(itemView);
            }
        }

        // Calculate and display total
        double total = transaction.getTotalAmount();
        if (total == 0 && transaction.getItems() != null && !transaction.getItems().isEmpty()) {
            total = transaction.calculateTotal();
        }
        tvTotal.setText(String.format("₱ %.2f", total));
    }

    /**
     * Add sample items for demonstration
     */
    private void addSampleItems() {
        Transaction.OrderItem item1 = new Transaction.OrderItem("Item Name 1", 2, 92.00);
        Transaction.OrderItem item2 = new Transaction.OrderItem("Iced Latte", 2, 92.00);
        
        itemsContainer.addView(createItemView(item1));
        itemsContainer.addView(createItemView(item2));
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
        tvItemPrice.setText(String.format("₱ %.2f", item.getPricePerUnit()));
        tvItemQuantity.setText(String.format("%d x ₱ %.2f", item.getQuantity(), item.getPricePerUnit()));
        tvItemTotal.setText(String.format("₱ %.2f", item.getTotal()));

        return itemView;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
