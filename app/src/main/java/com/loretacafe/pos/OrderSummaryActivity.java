package com.loretacafe.pos;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderSummaryActivity extends AppCompatActivity {

    private TextInputEditText etCustomerName, etCashReceived;
    private TextInputLayout tilCashReceived;
    private RecyclerView rvOrderItems;
    private TextView tvTotal, tvTotalAmountDue, tvChange;
    private RadioGroup rgPaymentMethod;
    private RadioButton rbCash, rbCard;
    private Button btnCharge, btnNewSale;
    private LinearLayout changeBar;

    private OrderItemAdapter adapter;
    private List<CartItem> cartItems;
    private Order currentOrder;
    private boolean paymentProcessed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        // Get cart items from intent
        Serializable serializable = getIntent().getSerializableExtra("cartItems");
        if (serializable instanceof ArrayList) {
            cartItems = (ArrayList<CartItem>) serializable;
        } else {
            cartItems = new ArrayList<>();
        }

        initializeViews();
        setupRecyclerView();
        setupListeners();
        calculateAndDisplayTotal();
    }

    private void initializeViews() {
        etCustomerName = findViewById(R.id.etCustomerName);
        etCashReceived = findViewById(R.id.etCashReceived);
        tilCashReceived = findViewById(R.id.tilCashReceived);
        rvOrderItems = findViewById(R.id.rvOrderItems);
        tvTotal = findViewById(R.id.tvTotal);
        tvTotalAmountDue = findViewById(R.id.tvTotalAmountDue);
        tvChange = findViewById(R.id.tvChange);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);
        rbCash = findViewById(R.id.rbCash);
        rbCard = findViewById(R.id.rbCard);
        btnCharge = findViewById(R.id.btnCharge);
        btnNewSale = findViewById(R.id.btnNewSale);
        changeBar = findViewById(R.id.changeBar);

        // Set default payment method to Cash
        rbCash.setChecked(true);
        updatePaymentUI();
    }

    private void setupRecyclerView() {
        rvOrderItems.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderItemAdapter(cartItems);
        rvOrderItems.setAdapter(adapter);
    }

    private void setupListeners() {
        // Back button
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            if (paymentProcessed) {
                // Navigate to dashboard
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            } else {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // Payment method change
        rgPaymentMethod.setOnCheckedChangeListener((group, checkedId) -> {
            updatePaymentUI();
            if (checkedId == R.id.rbCard) {
                // For card, set cash received to total amount
                double total = calculateTotal();
                etCashReceived.setText(String.format(Locale.getDefault(), "%.2f", total));
            }
        });

        // Cash received input change
        etCashReceived.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (rbCash.isChecked() && !paymentProcessed) {
                    calculateChange();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Charge button
        btnCharge.setOnClickListener(v -> {
            if (paymentProcessed) {
                // Already processed, do nothing
                return;
            }
            processPayment();
        });

        // New Sale button
        btnNewSale.setOnClickListener(v -> {
            // Navigate to Create Order
            Intent intent = new Intent(this, CreateOrderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void updatePaymentUI() {
        if (rbCash.isChecked()) {
            tilCashReceived.setVisibility(View.VISIBLE);
            tilCashReceived.setHint("Cash received");
        } else {
            tilCashReceived.setVisibility(View.GONE);
        }
    }

    private double calculateTotal() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        return total;
    }

    private void calculateAndDisplayTotal() {
        double total = calculateTotal();
        tvTotal.setText(String.format(Locale.getDefault(), "₱ %.2f", total));
        tvTotalAmountDue.setText(String.format(Locale.getDefault(), "₱ %.2f", total));
        
        // Set initial cash received for cash payment
        if (rbCash.isChecked()) {
            etCashReceived.setText(String.format(Locale.getDefault(), "%.2f", total));
            calculateChange();
        }
    }

    private void calculateChange() {
        if (!rbCash.isChecked() || paymentProcessed) {
            return;
        }

        try {
            double cashReceived = Double.parseDouble(etCashReceived.getText().toString());
            double total = calculateTotal();
            double change = cashReceived - total;

            if (change >= 0) {
                tvChange.setText(String.format(Locale.getDefault(), "₱ %.2f", change));
            } else {
                tvChange.setText(String.format(Locale.getDefault(), "₱ %.2f", 0.0));
                Toast.makeText(this, "Insufficient cash received", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            tvChange.setText("₱ 0.00");
        }
    }

    private void processPayment() {
        String customerName = etCustomerName.getText().toString().trim();
        if (customerName.isEmpty()) {
            customerName = "Walk-in Customer";
        }

        String paymentMethod = rbCash.isChecked() ? "Cash" : "Card";
        
        // Validate cash received for cash payment
        if (rbCash.isChecked()) {
            try {
                double cashReceived = Double.parseDouble(etCashReceived.getText().toString());
                double total = calculateTotal();
                
                if (cashReceived < total) {
                    Toast.makeText(this, "Cash received is less than total amount", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid cash amount", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Create order
        currentOrder = new Order(customerName, cartItems, paymentMethod);
        
        if (rbCash.isChecked()) {
            try {
                double cashReceived = Double.parseDouble(etCashReceived.getText().toString());
                currentOrder.setCashReceived(cashReceived);
                currentOrder.calculateChange();
            } catch (NumberFormatException e) {
                // Should not happen due to validation above
            }
        } else {
            // For card, cash received equals total
            currentOrder.setCashReceived(calculateTotal());
        }

        // Show success state
        showPaymentSuccess();
        
        Toast.makeText(this, "Payment processed successfully!", Toast.LENGTH_SHORT).show();
    }

    private void showPaymentSuccess() {
        paymentProcessed = true;
        
        // Hide charge button, show new sale button
        btnCharge.setVisibility(View.GONE);
        btnNewSale.setVisibility(View.VISIBLE);
        
        // Show change bar if cash payment
        if (rbCash.isChecked() && currentOrder != null) {
            changeBar.setVisibility(View.VISIBLE);
            tvChange.setText(currentOrder.getFormattedChange());
        }
        
        // Disable inputs
        etCustomerName.setEnabled(false);
        etCashReceived.setEnabled(false);
        rbCash.setEnabled(false);
        rbCard.setEnabled(false);
    }
}

