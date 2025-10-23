package com.loretacafe.pos;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AssignItemsActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText etSearch;
    private ImageView ivCategoryIcon;
    private TextView tvCategoryName, tvCategorySubtitle;
    private RecyclerView rvItems;
    private LinearLayout llEmptyState;
    private Button btnSave;

    private Category category;
    private AssignItemsAdapter adapter;
    private List<MenuItem> allItems;
    private Set<String> selectedItemIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_items);

        // Get category from intent
        category = (Category) getIntent().getSerializableExtra("category");
        if (category == null) {
            finish();
            return;
        }

        initializeViews();
        setupClickListeners();
        loadItems();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        etSearch = findViewById(R.id.etSearch);
        ivCategoryIcon = findViewById(R.id.ivCategoryIcon);
        tvCategoryName = findViewById(R.id.tvCategoryName);
        tvCategorySubtitle = findViewById(R.id.tvCategorySubtitle);
        rvItems = findViewById(R.id.rvItems);
        llEmptyState = findViewById(R.id.llEmptyState);
        btnSave = findViewById(R.id.btnSave);

        // Set category info
        tvCategoryName.setText(category.getName());
        tvCategorySubtitle.setText(category.getName());

        // Load category icon
        String iconName = category.getIconName();
        if (iconName != null && !iconName.isEmpty()) {
            int resourceId = getResources().getIdentifier(iconName, "drawable", getPackageName());
            if (resourceId != 0) {
                ivCategoryIcon.setImageResource(resourceId);
            }
        }

        // Setup RecyclerView
        selectedItemIds = new HashSet<>();
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AssignItemsAdapter(new ArrayList<>(), selectedItemIds);
        rvItems.setAdapter(adapter);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        btnSave.setOnClickListener(v -> {
            // Save selected items to category
            Toast.makeText(this, selectedItemIds.size() + " items assigned to " + category.getName(), Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Search functionality
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
    }

    private void loadItems() {
        // Load sample items (in real app, load from database)
        allItems = new ArrayList<>();
        
        // Iced Coffee items
        allItems.add(new MenuItem("Cappuccino", 78.00, "Iced Coffee", "iced_coffee_cappuccino"));
        allItems.add(new MenuItem("Americano", 68.00, "Iced Coffee", "iced_coffee_americano"));
        allItems.add(new MenuItem("Cafe Latte", 78.00, "Iced Coffee", "iced_coffee_cafe_latte"));
        allItems.add(new MenuItem("Caramel Macchiato", 78.00, "Iced Coffee", "iced_coffee_caramel_macchiato"));

        // Frappe items
        allItems.add(new MenuItem("Choc Chip", 98.00, "Frappe", "frappe_chocchip"));
        allItems.add(new MenuItem("Cookies and Cream", 98.00, "Frappe", "frappe_cookies_and_cream"));

        // Milktea items
        allItems.add(new MenuItem("Wintermelon", 78.00, "Milktea", "milktea_wintermelon"));
        allItems.add(new MenuItem("Taro", 78.00, "Milktea", "milktea_taro"));

        updateItemsView();
    }

    private void filterItems(String query) {
        if (query.isEmpty()) {
            adapter.updateItems(allItems);
        } else {
            List<MenuItem> filteredList = new ArrayList<>();
            String lowerQuery = query.toLowerCase();
            for (MenuItem item : allItems) {
                if (item.getName().toLowerCase().contains(lowerQuery)) {
                    filteredList.add(item);
                }
            }
            adapter.updateItems(filteredList);
        }
        updateEmptyState();
    }

    private void updateItemsView() {
        adapter.updateItems(allItems);
        updateEmptyState();
    }

    private void updateEmptyState() {
        if (allItems.isEmpty()) {
            llEmptyState.setVisibility(View.VISIBLE);
            rvItems.setVisibility(View.GONE);
        } else {
            llEmptyState.setVisibility(View.GONE);
            rvItems.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}

