package com.loretacafe.pos;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private ImageButton btnBack, btnMenuOptions;
    private EditText etSearch;
    private RecyclerView rvFavorites, rvMenuItems;
    private ChipGroup chipGroupCategories;
    private TextView tvCategoryLabel, tvFavoritesLabel;
    private FloatingActionButton fabAddItem;
    private BottomNavigationView bottomNavigation;

    private MenuAdapter menuAdapter;
    private FavoritesAdapter favoritesAdapter;
    private List<MenuItem> allMenuItems;
    private List<MenuItem> favoriteItems;
    private String currentCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initializeViews();
        initializeData();
        setupAdapters();
        setupCategories();
        setupListeners();
        setupBottomNavigation();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        btnMenuOptions = findViewById(R.id.btnMenuOptions);
        etSearch = findViewById(R.id.etSearch);
        rvFavorites = findViewById(R.id.rvFavorites);
        rvMenuItems = findViewById(R.id.rvMenuItems);
        chipGroupCategories = findViewById(R.id.chipGroupCategories);
        tvCategoryLabel = findViewById(R.id.tvCategoryLabel);
        tvFavoritesLabel = findViewById(R.id.tvFavoritesLabel);
        fabAddItem = findViewById(R.id.fabAddItem);
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void initializeData() {
        allMenuItems = new ArrayList<>();
        favoriteItems = new ArrayList<>();

        // Iced Coffee
        allMenuItems.add(new MenuItem("Cappuccino", 78.00, "Iced Coffee"));
        allMenuItems.add(new MenuItem("Americano", 68.00, "Iced Coffee"));
        allMenuItems.add(new MenuItem("Cafe Latte", 78.00, "Iced Coffee"));
        allMenuItems.add(new MenuItem("Caramel Macchiato", 78.00, "Iced Coffee"));
        allMenuItems.add(new MenuItem("Cafe Mocha", 78.00, "Iced Coffee"));
        allMenuItems.add(new MenuItem("French Vanilla", 78.00, "Iced Coffee"));
        allMenuItems.add(new MenuItem("Hazelnut Latte", 78.00, "Iced Coffee", true));
        allMenuItems.add(new MenuItem("Salted Caramel Latte", 78.00, "Iced Coffee", true));
        allMenuItems.add(new MenuItem("Matcha Latte", 98.00, "Iced Coffee"));
        allMenuItems.add(new MenuItem("Triple Chocolate Mocha", 78.00, "Iced Coffee"));
        allMenuItems.add(new MenuItem("Dirty Matcha", 138.00, "Iced Coffee"));
        allMenuItems.add(new MenuItem("Tiramisu Latte", 78.00, "Iced Coffee", true));
        allMenuItems.add(new MenuItem("Spanish Latte", 78.00, "Iced Coffee"));

        // Frappe
        allMenuItems.add(new MenuItem("Choc Chip", 98.00, "Frappe"));
        allMenuItems.add(new MenuItem("Cookies and Cream", 98.00, "Frappe"));
        allMenuItems.add(new MenuItem("Black Forest", 98.00, "Frappe"));
        allMenuItems.add(new MenuItem("Double Dutch", 98.00, "Frappe"));
        allMenuItems.add(new MenuItem("Dark Chocolate", 98.00, "Frappe"));
        allMenuItems.add(new MenuItem("Vanilla", 98.00, "Frappe"));
        allMenuItems.add(new MenuItem("Matcha", 98.00, "Frappe"));
        allMenuItems.add(new MenuItem("Caramel", 98.00, "Frappe"));
        allMenuItems.add(new MenuItem("Salted Caramel", 98.00, "Frappe", true));
        allMenuItems.add(new MenuItem("Strawberry", 98.00, "Frappe"));
        allMenuItems.add(new MenuItem("Mango Graham", 98.00, "Frappe"));

        // Coffee Frappe
        allMenuItems.add(new MenuItem("Cappuccino", 98.00, "Coffee Frappe"));
        allMenuItems.add(new MenuItem("Cafe Latte", 98.00, "Coffee Frappe"));
        allMenuItems.add(new MenuItem("Mocha", 98.00, "Coffee Frappe"));

        // Milktea Classic
        allMenuItems.add(new MenuItem("Wintermelon", 78.00, "Milktea Classic"));
        allMenuItems.add(new MenuItem("Taro", 78.00, "Milktea Classic"));
        allMenuItems.add(new MenuItem("Okinawa", 78.00, "Milktea Classic"));
        allMenuItems.add(new MenuItem("Cookies and Cream", 78.00, "Milktea Classic"));
        allMenuItems.add(new MenuItem("Salted Caramel", 78.00, "Milktea Classic"));
        allMenuItems.add(new MenuItem("Hazelnut", 78.00, "Milktea Classic"));
        allMenuItems.add(new MenuItem("Chocolate", 78.00, "Milktea Classic"));
        allMenuItems.add(new MenuItem("Dark Chocolate", 78.00, "Milktea Classic"));
        allMenuItems.add(new MenuItem("Matcha", 78.00, "Milktea Classic"));
        allMenuItems.add(new MenuItem("Ube", 78.00, "Milktea Classic"));
        allMenuItems.add(new MenuItem("Mocha", 78.00, "Milktea Classic"));

        // Loreta's Specials
        allMenuItems.add(new MenuItem("Tiger Boba Milk", 138.00, "Loreta's Specials"));
        allMenuItems.add(new MenuItem("Tiger Boba Milktea", 108.00, "Loreta's Specials"));
        allMenuItems.add(new MenuItem("Tiger Oreo Cheesecake", 128.00, "Loreta's Specials"));
        allMenuItems.add(new MenuItem("Nutellatte", 118.00, "Loreta's Specials"));

        // Cheesecake
        allMenuItems.add(new MenuItem("Wintermelon Cheesecake", 118.00, "Cheesecake"));
        allMenuItems.add(new MenuItem("Strawberry Cheesecake", 118.00, "Cheesecake"));
        allMenuItems.add(new MenuItem("Oreo Cheesecake", 118.00, "Cheesecake"));
        allMenuItems.add(new MenuItem("Ube Cheesecake", 118.00, "Cheesecake"));
        allMenuItems.add(new MenuItem("Matcha Cheesecake", 118.00, "Cheesecake"));
        allMenuItems.add(new MenuItem("Red Velvet Cheesecake", 118.00, "Cheesecake"));

        // Fruit Tea and Lemonade
        allMenuItems.add(new MenuItem("Sunrise", 68.00, "Fruit Tea and Lemonade"));
        allMenuItems.add(new MenuItem("Paradise", 68.00, "Fruit Tea and Lemonade"));
        allMenuItems.add(new MenuItem("Lychee", 68.00, "Fruit Tea and Lemonade"));
        allMenuItems.add(new MenuItem("Berry Blossom", 68.00, "Fruit Tea and Lemonade"));
        allMenuItems.add(new MenuItem("Blue Lemonade", 68.00, "Fruit Tea and Lemonade"));
        allMenuItems.add(new MenuItem("Strawberry Lemonade", 68.00, "Fruit Tea and Lemonade"));
        allMenuItems.add(new MenuItem("Green Apple Lemonade", 68.00, "Fruit Tea and Lemonade"));

        // Fruit Milk
        allMenuItems.add(new MenuItem("Blueberry Milk", 98.00, "Fruit Milk"));
        allMenuItems.add(new MenuItem("Strawberry Milk", 98.00, "Fruit Milk"));
        allMenuItems.add(new MenuItem("Mango Milk", 98.00, "Fruit Milk"));

        // Fruit Soda
        allMenuItems.add(new MenuItem("Green Apple", 68.00, "Fruit Soda"));
        allMenuItems.add(new MenuItem("Strawberry", 68.00, "Fruit Soda"));
        allMenuItems.add(new MenuItem("Lychee", 68.00, "Fruit Soda"));
        allMenuItems.add(new MenuItem("Blueberry", 68.00, "Fruit Soda"));
        allMenuItems.add(new MenuItem("Pink Soda", 68.00, "Fruit Soda"));

        // Hot Coffee (with sizes)
        allMenuItems.add(new MenuItem("Black", "₱ 68.00 | 78.00", "Hot Coffee"));
        allMenuItems.add(new MenuItem("Cafe Latte", "₱ 78.00 | 88.00", "Hot Coffee"));
        allMenuItems.add(new MenuItem("Cafe Mocha", "₱ 88.00 | 98.00", "Hot Coffee"));
        allMenuItems.add(new MenuItem("Caramel Macchiato", "₱ 88.00 | 98.00", "Hot Coffee"));
        allMenuItems.add(new MenuItem("Spanish Latte", "₱ 88.00 | 98.00", "Hot Coffee"));
        allMenuItems.add(new MenuItem("Matcha Latte", "₱ 98.00 | 108.00", "Hot Coffee"));

        // Add ons
        allMenuItems.add(new MenuItem("Pearls", 15.00, "Add ons"));
        allMenuItems.add(new MenuItem("Crushed Oreo", 15.00, "Add ons"));
        allMenuItems.add(new MenuItem("Nata de Coco", 15.00, "Add ons"));
        allMenuItems.add(new MenuItem("Rainbow Jelly", 15.00, "Add ons"));
        allMenuItems.add(new MenuItem("Chia Seeds", 15.00, "Add ons"));
        allMenuItems.add(new MenuItem("Crushed Graham", 15.00, "Add ons"));
        allMenuItems.add(new MenuItem("Brown Sugar", 15.00, "Add ons"));
        allMenuItems.add(new MenuItem("Cream Cheese", 20.00, "Add ons"));
        allMenuItems.add(new MenuItem("Espresso", 20.00, "Add ons"));
    }

    private void setupAdapters() {
        // Setup Menu Items RecyclerView
        rvMenuItems.setLayoutManager(new GridLayoutManager(this, 2));
        menuAdapter = new MenuAdapter(allMenuItems, (item, position) -> {
            // Toggle favorite status
            item.setFavorite(!item.isFavorite());

            if (item.isFavorite()) {
                favoriteItems.add(item);
                Toast.makeText(this, item.getName() + " added to favorites", Toast.LENGTH_SHORT).show();
            } else {
                favoriteItems.remove(item);
                Toast.makeText(this, item.getName() + " removed from favorites", Toast.LENGTH_SHORT).show();
            }

            menuAdapter.notifyItemChanged(position);
            favoritesAdapter.updateFavorites(favoriteItems);
            updateFavoritesVisibility();
        });
        rvMenuItems.setAdapter(menuAdapter);

        // Setup Favorites RecyclerView
        rvFavorites.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        favoritesAdapter = new FavoritesAdapter(favoriteItems);
        rvFavorites.setAdapter(favoritesAdapter);

        updateFavoritesVisibility();
    }

    private void setupCategories() {
        String[] categories = {
                "All", "Iced Coffee", "Frappe", "Coffee Frappe", "Milktea Classic",
                "Loreta's Specials", "Cheesecake", "Fruit Tea and Lemonade",
                "Fruit Milk", "Fruit Soda", "Hot Coffee", "Add ons"
        };

        for (String category : categories) {
            Chip chip = new Chip(this);
            chip.setText(category);
            chip.setCheckable(true);
            chip.setChipBackgroundColorResource(R.color.chip_background);
            chip.setTextColor(getResources().getColor(R.color.chip_text));

            chip.setOnClickListener(v -> {
                currentCategory = category;
                tvCategoryLabel.setText(category);
                menuAdapter.filterByCategory(category);
            });

            chipGroupCategories.addView(chip);

            if (category.equals("All")) {
                chip.setChecked(true);
            }
        }
    }

    private void setupListeners() {
        // Back button
        btnBack.setOnClickListener(v -> finish());

        // Menu options
        btnMenuOptions.setOnClickListener(v ->
                Toast.makeText(this, "Menu options", Toast.LENGTH_SHORT).show());

        // Search functionality
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                menuAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Floating action button
        fabAddItem.setOnClickListener(v ->
                Toast.makeText(this, "Add new item", Toast.LENGTH_SHORT).show());
    }

    private void setupBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.nav_menu);

        // Prevent re-selection crash
        bottomNavigation.setOnItemReselectedListener(item -> {
            // Do nothing when the same item is clicked again
        });

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Navigate to Dashboard
                Intent intent = new Intent(MenuActivity.this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                return true;

            } else if (itemId == R.id.nav_history) {
                // Navigate to History
                Intent intent = new Intent(MenuActivity.this, RecentTransactionsActivity.class);
                startActivity(intent);
                finish();
                return true;

            } else if (itemId == R.id.nav_menu) {
                // Already in Menu, do nothing
                return true;

            } else if (itemId == R.id.nav_reports) {
                // TODO: Create ReportsActivity later
                Toast.makeText(this, "Reports coming soon", Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        });
    }

    private void updateFavoritesVisibility() {
        if (favoriteItems.isEmpty()) {
            rvFavorites.setVisibility(View.GONE);
            tvFavoritesLabel.setVisibility(View.GONE);
        } else {
            rvFavorites.setVisibility(View.VISIBLE);
            tvFavoritesLabel.setVisibility(View.VISIBLE);
        }
    }
}