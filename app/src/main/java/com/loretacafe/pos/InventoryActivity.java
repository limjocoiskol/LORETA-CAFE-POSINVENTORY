package com.loretacafe.pos;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class InventoryActivity extends AppCompatActivity {

    private ImageButton btnBack, btnSort;
    private EditText etSearch;
    private RecyclerView rvInventory;
    private LinearLayout emptyState;
    private FloatingActionButton fabAdd;
    private BottomNavigationView bottomNavigation;

    private InventoryAdapter adapter;
    private List<InventoryItem> allInventoryItems;
    private List<InventoryItem> filteredItems;
    private String currentSort = "Newest Added";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        initializeViews();
        loadInventoryItems();
        setupRecyclerView();
        setupListeners();
        setupBottomNavigation();
        updateEmptyState();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        btnSort = findViewById(R.id.btnSort);
        etSearch = findViewById(R.id.etSearch);
        rvInventory = findViewById(R.id.rvInventory);
        emptyState = findViewById(R.id.emptyState);
        fabAdd = findViewById(R.id.fabAdd);
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void loadInventoryItems() {
        allInventoryItems = new ArrayList<>();
        filteredItems = new ArrayList<>();

        // Sample inventory items
        allInventoryItems.add(new InventoryItem("Oat Milk 1L", 1000, "mL", 100.0));
        allInventoryItems.add(new InventoryItem("Espresso Beans", 500, "g", 250.0));
        allInventoryItems.add(new InventoryItem("Sugar", 2000, "g", 50.0));
        allInventoryItems.add(new InventoryItem("Milk", 500, "mL", 80.0));
        allInventoryItems.add(new InventoryItem("Matcha Powder", 200, "g", 300.0));
        allInventoryItems.add(new InventoryItem("Vanilla Syrup", 500, "mL", 150.0));
        allInventoryItems.add(new InventoryItem("Caramel Sauce", 300, "mL", 200.0));
        allInventoryItems.add(new InventoryItem("Whipped Cream", 1000, "mL", 120.0));
        allInventoryItems.add(new InventoryItem("Ice Cubes", 5000, "pcs", 10.0));
        allInventoryItems.add(new InventoryItem("Paper Cups", 200, "pcs", 5.0));

        filteredItems.addAll(allInventoryItems);
    }

    private void setupRecyclerView() {
        rvInventory.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InventoryAdapter(filteredItems, new InventoryAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(InventoryItem item, int position) {
                showEditItemDialog(item, position);
            }

            @Override
            public void onDeleteClick(InventoryItem item, int position) {
                showDeleteConfirmationDialog(item, position);
            }
        });
        rvInventory.setAdapter(adapter);
    }

    private void setupListeners() {
        // Back button
        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Sort button
        btnSort.setOnClickListener(v -> showSortDialog());

        // Search
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterItems(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // FAB - Add new item
        fabAdd.setOnClickListener(v -> showAddItemDialog());
    }

    private void filterItems(String query) {
        filteredItems.clear();
        
        if (query.isEmpty()) {
            filteredItems.addAll(allInventoryItems);
        } else {
            String lowerQuery = query.toLowerCase();
            for (InventoryItem item : allInventoryItems) {
                if (item.getName().toLowerCase().contains(lowerQuery)) {
                    filteredItems.add(item);
                }
            }
        }
        
        applySort();
        adapter.updateItems(filteredItems);
        updateEmptyState();
    }

    private void showSortDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by");
        builder.setItems(new String[]{"Low Stock Priority", "Newest Added"}, (dialog, which) -> {
            if (which == 0) {
                currentSort = "Low Stock Priority";
            } else {
                currentSort = "Newest Added";
            }
            applySort();
            adapter.updateItems(filteredItems);
        });
        builder.show();
    }

    private void applySort() {
        if (currentSort.equals("Low Stock Priority")) {
            Collections.sort(filteredItems, (a, b) -> {
                // Sort by status priority: Low Stock > Running Low > In Stock
                int priorityA = getStatusPriority(a.getStatus());
                int priorityB = getStatusPriority(b.getStatus());
                if (priorityA != priorityB) {
                    return Integer.compare(priorityA, priorityB);
                }
                // If same priority, sort by quantity (lower first)
                return Double.compare(a.getQuantity(), b.getQuantity());
            });
        } else {
            // Newest Added - keep original order (reverse of add order)
            Collections.reverse(filteredItems);
        }
    }

    private int getStatusPriority(InventoryItem.StockStatus status) {
        switch (status) {
            case LOW_STOCK:
                return 0;
            case RUNNING_LOW:
                return 1;
            case IN_STOCK:
                return 2;
            default:
                return 3;
        }
    }

    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_inventory_item, null);
        builder.setView(dialogView);

        TextInputEditText etItemName = dialogView.findViewById(R.id.etItemName);
        TextInputEditText etQuantity = dialogView.findViewById(R.id.etQuantity);
        TextInputEditText etUnit = dialogView.findViewById(R.id.etUnit);
        TextInputEditText etCost = dialogView.findViewById(R.id.etCost);
        TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
        ImageButton btnClose = dialogView.findViewById(R.id.btnClose);
        android.widget.Button btnSave = dialogView.findViewById(R.id.btnSave);

        tvTitle.setText("New Item");

        AlertDialog dialog = builder.create();

        btnClose.setOnClickListener(v -> dialog.dismiss());
        btnSave.setOnClickListener(v -> {
            String name = etItemName.getText().toString().trim();
            String quantityStr = etQuantity.getText().toString().trim();
            String unit = etUnit.getText().toString().trim();
            String costStr = etCost.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter item name", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double quantity = quantityStr.isEmpty() ? 0 : Double.parseDouble(quantityStr);
                double cost = costStr.isEmpty() ? 0 : Double.parseDouble(costStr);

                InventoryItem newItem = new InventoryItem(name, quantity, unit.isEmpty() ? "pcs" : unit, cost);
                allInventoryItems.add(newItem);
                filterItems(etSearch.getText().toString());
                dialog.dismiss();
                Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void showEditItemDialog(InventoryItem item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_inventory_item, null);
        builder.setView(dialogView);

        TextInputEditText etItemName = dialogView.findViewById(R.id.etItemName);
        TextInputEditText etQuantity = dialogView.findViewById(R.id.etQuantity);
        TextInputEditText etUnit = dialogView.findViewById(R.id.etUnit);
        TextInputEditText etCost = dialogView.findViewById(R.id.etCost);
        TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
        ImageButton btnClose = dialogView.findViewById(R.id.btnClose);
        android.widget.Button btnSave = dialogView.findViewById(R.id.btnSave);

        tvTitle.setText("Edit Item");
        etItemName.setText(item.getName());
        etQuantity.setText(String.format(Locale.getDefault(), "%.0f", item.getQuantity()));
        etUnit.setText(item.getUnit());
        etCost.setText(String.format(Locale.getDefault(), "%.2f", item.getCost()));

        AlertDialog dialog = builder.create();

        btnClose.setOnClickListener(v -> dialog.dismiss());
        btnSave.setOnClickListener(v -> {
            String name = etItemName.getText().toString().trim();
            String quantityStr = etQuantity.getText().toString().trim();
            String unit = etUnit.getText().toString().trim();
            String costStr = etCost.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter item name", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double quantity = quantityStr.isEmpty() ? 0 : Double.parseDouble(quantityStr);
                double cost = costStr.isEmpty() ? 0 : Double.parseDouble(costStr);

                item.setName(name);
                item.setQuantity(quantity);
                item.setUnit(unit.isEmpty() ? "pcs" : unit);
                item.setCost(cost);

                filterItems(etSearch.getText().toString());
                dialog.dismiss();
                Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void showDeleteConfirmationDialog(InventoryItem item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete this item");
        builder.setMessage("Are you sure you want to delete \"" + item.getName() + "\" from your inventory?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            allInventoryItems.remove(item);
            filterItems(etSearch.getText().toString());
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void setupBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.nav_reports); // Inventory is in reports section

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                return true;
            } else if (itemId == R.id.nav_history) {
                Intent intent = new Intent(this, RecentTransactionsActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if (itemId == R.id.nav_add) {
                Intent intent = new Intent(this, CreateOrderActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if (itemId == R.id.nav_menu) {
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if (itemId == R.id.nav_reports) {
                // Already here
                return true;
            }

            return false;
        });
    }

    private void updateEmptyState() {
        if (filteredItems.isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
            rvInventory.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            rvInventory.setVisibility(View.VISIBLE);
        }
    }
}

