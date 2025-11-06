package com.loretacafe.pos;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreateOrderActivity extends AppCompatActivity {

    private ImageButton btnBack, btnCart;
    private EditText etSearch;
    private RecyclerView rvFavorites, rvMenuItems;
    private ChipGroup chipGroupCategories;
    private TextView tvCategoryLabel, tvFavoritesLabel, tvTotalOrder;
    private LinearLayout totalOrderBar;
    private BottomNavigationView bottomNavigation;

    private MenuAdapter menuAdapter;
    private FavoritesAdapter favoritesAdapter;
    private List<MenuItem> allMenuItems;
    private List<MenuItem> favoriteItems;
    private List<CartItem> cartItems;
    private String currentCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        cartItems = new ArrayList<>();
        
        initializeViews();
        initializeData();
        setupAdapters();
        setupCategories();
        setupListeners();
        setupBottomNavigation();
        updateTotalOrder();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        btnCart = findViewById(R.id.btnCart);
        etSearch = findViewById(R.id.etSearch);
        rvFavorites = findViewById(R.id.rvFavorites);
        rvMenuItems = findViewById(R.id.rvMenuItems);
        chipGroupCategories = findViewById(R.id.chipGroupCategories);
        tvCategoryLabel = findViewById(R.id.tvCategoryLabel);
        tvFavoritesLabel = findViewById(R.id.tvFavoritesLabel);
        tvTotalOrder = findViewById(R.id.tvTotalOrder);
        totalOrderBar = findViewById(R.id.totalOrderBar);
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void initializeData() {
        allMenuItems = new ArrayList<>();
        favoriteItems = new ArrayList<>();

        // Load menu items (same as MenuActivity)
        loadMenuItems();
    }

    private void loadMenuItems() {
        // Iced Coffee
        allMenuItems.add(new MenuItem("Cappuccino", 78.00, "Iced Coffee", "iced_coffee_cappuccino"));
        allMenuItems.add(new MenuItem("Americano", 68.00, "Iced Coffee", "iced_coffee_americano"));
        allMenuItems.add(new MenuItem("Cafe Latte", 72.00, "Iced Coffee", "iced_coffee_cafe_latte"));
        allMenuItems.add(new MenuItem("Caramel Macchiato", 78.00, "Iced Coffee", "iced_coffee_caramel_macchiato"));
        allMenuItems.add(new MenuItem("Cafe Mocha", 78.00, "Iced Coffee", "iced_coffee_cafe_mocha"));
        allMenuItems.add(new MenuItem("French Vanilla", 78.00, "Iced Coffee", "iced_coffee_french_vanilla"));
        allMenuItems.add(new MenuItem("Hazelnut Latte", 78.00, "Iced Coffee", true, "iced_coffee_hazelnut_latte"));
        allMenuItems.add(new MenuItem("Salted Caramel Latte", 78.00, "Iced Coffee", true, "iced_coffee_salted_caramel_latte"));
        allMenuItems.add(new MenuItem("Matcha Latte", 98.00, "Iced Coffee", "iced_coffee_matcha_latte"));
        allMenuItems.add(new MenuItem("Triple Chocolate Mocha", 78.00, "Iced Coffee", "iced_coffee_triple_chocolate_mocha"));
        allMenuItems.add(new MenuItem("Dirty Matcha", 138.00, "Iced Coffee", "iced_coffee_dirty_matcha"));
        allMenuItems.add(new MenuItem("Tiramisu Latte", 78.00, "Iced Coffee", true, "iced_coffee_tiramisu_latte"));
        allMenuItems.add(new MenuItem("Spanish Latte", 78.00, "Iced Coffee", "iced_coffee_spanish_latte"));

        // Frappe
        allMenuItems.add(new MenuItem("Choc Chip", 98.00, "Frappe", "frappe_chocchip"));
        allMenuItems.add(new MenuItem("Cookies and Cream", 98.00, "Frappe", "frappe_cookies_and_cream"));
        allMenuItems.add(new MenuItem("Black Forest", 98.00, "Frappe", "frappe_black_forest"));
        allMenuItems.add(new MenuItem("Double Dutch", 98.00, "Frappe", "frappe_doubledutch"));
        allMenuItems.add(new MenuItem("Dark Chocolate", 98.00, "Frappe", "frappe_dark_chocolate"));
        allMenuItems.add(new MenuItem("Vanilla", 98.00, "Frappe", "frappe_vanilla"));
        allMenuItems.add(new MenuItem("Matcha", 98.00, "Frappe", "frappe_matcha"));
        allMenuItems.add(new MenuItem("Caramel", 98.00, "Frappe", "frappe_caramel"));
        allMenuItems.add(new MenuItem("Salted Caramel", 98.00, "Frappe", true, "frappe_saltedcaramel"));
        allMenuItems.add(new MenuItem("Strawberry", 98.00, "Frappe", "frappe_strawberry"));
        allMenuItems.add(new MenuItem("Mango Graham", 98.00, "Frappe", "frappe_mangograham"));

        // Coffee Frappe
        allMenuItems.add(new MenuItem("Cappuccino", 98.00, "Coffee Frappe", "frappecoffee_cappuccino"));
        allMenuItems.add(new MenuItem("Cafe Latte", 98.00, "Coffee Frappe", "frappecoffee_cafe_latte"));
        allMenuItems.add(new MenuItem("Mocha", 98.00, "Coffee Frappe", "frappecoffee_mocha"));

        // Milktea Classic
        allMenuItems.add(new MenuItem("Wintermelon", 78.00, "Milktea Classic", "milktea_wintermelon"));
        allMenuItems.add(new MenuItem("Taro", 78.00, "Milktea Classic", "milktea_taro"));
        allMenuItems.add(new MenuItem("Okinawa", 78.00, "Milktea Classic", "milktea_okinawa"));
        allMenuItems.add(new MenuItem("Cookies and Cream", 78.00, "Milktea Classic", "milktea_cookiesandcream"));
        allMenuItems.add(new MenuItem("Salted Caramel", 78.00, "Milktea Classic", "milktea_saltedcaramel"));
        allMenuItems.add(new MenuItem("Hazelnut", 78.00, "Milktea Classic", "milktea_hazelnut"));
        allMenuItems.add(new MenuItem("Chocolate", 78.00, "Milktea Classic", "milktea_chocolate"));
        allMenuItems.add(new MenuItem("Dark Chocolate", 78.00, "Milktea Classic", "milktea_dark_chocolate"));
        allMenuItems.add(new MenuItem("Matcha", 78.00, "Milktea Classic", "milktea_matcha"));
        allMenuItems.add(new MenuItem("Ube", 78.00, "Milktea Classic", "milktea_ube"));
        allMenuItems.add(new MenuItem("Mocha", 78.00, "Milktea Classic", "milktea_mocha"));

        // Loreta's Specials
        allMenuItems.add(new MenuItem("Tiger Boba Milk", 138.00, "Loreta's Specials", "specials_tiger_or"));
        allMenuItems.add(new MenuItem("Tiger Boba Milktea", 108.00, "Loreta's Specials", "specials_tiger_oreomilktea"));
        allMenuItems.add(new MenuItem("Tiger Oreo Cheesecake", 128.00, "Loreta's Specials", "specials_tiger_oreocheesecake"));
        allMenuItems.add(new MenuItem("Nutellatte", 118.00, "Loreta's Specials", "specials_nutellalatte"));

        // Cheesecake
        allMenuItems.add(new MenuItem("Wintermelon Cheesecake", 118.00, "Cheesecake", "cheesecake_wintermelon_cheesecake"));
        allMenuItems.add(new MenuItem("Strawberry Cheesecake", 118.00, "Cheesecake", "cheesecake_strawberry_cheesecake"));
        allMenuItems.add(new MenuItem("Oreo Cheesecake", 118.00, "Cheesecake", "cheesecake_oreo_cheesecake"));
        allMenuItems.add(new MenuItem("Ube Cheesecake", 118.00, "Cheesecake", "cheesecake_ube_uheesecake"));
        allMenuItems.add(new MenuItem("Matcha Cheesecake", 118.00, "Cheesecake", "cheesecake_matcha_cheesecake"));
        allMenuItems.add(new MenuItem("Red Velvet Cheesecake", 118.00, "Cheesecake", "cheesecake_red_velvet_cheesecake"));

        // Fruit Tea and Lemonade
        allMenuItems.add(new MenuItem("Sunrise", 68.00, "Fruit Tea and Lemonade", "fruittea_sunrise"));
        allMenuItems.add(new MenuItem("Paradise", 68.00, "Fruit Tea and Lemonade", "fruittea_paradise"));
        allMenuItems.add(new MenuItem("Lychee", 68.00, "Fruit Tea and Lemonade", "fruittea_lychee"));
        allMenuItems.add(new MenuItem("Berry Blossom", 68.00, "Fruit Tea and Lemonade", "fruittea_berry_blossom"));
        allMenuItems.add(new MenuItem("Blue Lemonade", 68.00, "Fruit Tea and Lemonade", "fruittea_blue_lemonade"));
        allMenuItems.add(new MenuItem("Strawberry Lemonade", 68.00, "Fruit Tea and Lemonade", "fruittea_strawberry_lemonade"));
        allMenuItems.add(new MenuItem("Green Apple Lemonade", 68.00, "Fruit Tea and Lemonade", "fruittea_green_apple_lemonade"));

        // Fruit Milk
        allMenuItems.add(new MenuItem("Blueberry Milk", 98.00, "Fruit Milk", "fruitmilk_blueberrymilk"));
        allMenuItems.add(new MenuItem("Strawberry Milk", 98.00, "Fruit Milk", "fruitmilk_strawberrymilk"));
        allMenuItems.add(new MenuItem("Mango Milk", 98.00, "Fruit Milk", "fruitmilk_mangomilk"));

        // Fruit Soda
        allMenuItems.add(new MenuItem("Green Apple", 68.00, "Fruit Soda", "ic_image_placeholder"));
        allMenuItems.add(new MenuItem("Strawberry", 68.00, "Fruit Soda", "ic_image_placeholder"));
        allMenuItems.add(new MenuItem("Lychee", 68.00, "Fruit Soda", "ic_image_placeholder"));
        allMenuItems.add(new MenuItem("Blueberry", 68.00, "Fruit Soda", "ic_image_placeholder"));
        allMenuItems.add(new MenuItem("Pink Soda", 68.00, "Fruit Soda", "ic_image_placeholder"));

        // Hot Coffee (with sizes)
        allMenuItems.add(new MenuItem("Black", "₱ 68.00 | 78.00", "Hot Coffee"));
        allMenuItems.add(new MenuItem("Cafe Latte", "₱ 78.00 | 88.00", "Hot Coffee"));
        allMenuItems.add(new MenuItem("Cafe Mocha", "₱ 88.00 | 98.00", "Hot Coffee"));
        allMenuItems.add(new MenuItem("Caramel Macchiato", "₱ 88.00 | 98.00", "Hot Coffee"));
        allMenuItems.add(new MenuItem("Spanish Latte", "₱ 88.00 | 98.00", "Hot Coffee"));
        allMenuItems.add(new MenuItem("Matcha Latte", "₱ 98.00 | 108.00", "Hot Coffee"));

        // Add ons
        allMenuItems.add(new MenuItem("Pearls", 15.00, "Add ons", "ic_image_placeholder"));
        allMenuItems.add(new MenuItem("Crushed Oreo", 15.00, "Add ons", "ic_image_placeholder"));
        allMenuItems.add(new MenuItem("Nata de Coco", 15.00, "Add ons", "ic_image_placeholder"));
        allMenuItems.add(new MenuItem("Rainbow Jelly", 15.00, "Add ons", "ic_image_placeholder"));
        allMenuItems.add(new MenuItem("Chia Seeds", 15.00, "Add ons", "ic_image_placeholder"));
        allMenuItems.add(new MenuItem("Crushed Graham", 15.00, "Add ons", "ic_image_placeholder"));
        allMenuItems.add(new MenuItem("Brown Sugar", 15.00, "Add ons", "ic_image_placeholder"));
        allMenuItems.add(new MenuItem("Cream Cheese", 20.00, "Add ons", "ic_image_placeholder"));
        allMenuItems.add(new MenuItem("Espresso", 20.00, "Add ons", "ic_image_placeholder"));

        // Get favorites
        for (MenuItem item : allMenuItems) {
            if (item.isFavorite()) {
                favoriteItems.add(item);
            }
        }
    }

    private void setupAdapters() {
        // Setup Menu Items RecyclerView
        rvMenuItems.setLayoutManager(new GridLayoutManager(this, 2));
        menuAdapter = new MenuAdapter(allMenuItems, (item, position) -> {
            // Toggle favorite status
            item.setFavorite(!item.isFavorite());

            if (item.isFavorite()) {
                favoriteItems.add(item);
            } else {
                favoriteItems.remove(item);
            }

            menuAdapter.notifyItemChanged(position);
            favoritesAdapter.updateFavorites(favoriteItems);
            updateFavoritesVisibility();
        });

        // Set add to cart listener
        menuAdapter.setOnAddToCartClickListener((item, position) -> {
            addToCart(item);
        });

        rvMenuItems.setAdapter(menuAdapter);

        // Setup Favorites RecyclerView
        rvFavorites.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        favoritesAdapter = new FavoritesAdapter(favoriteItems);
        rvFavorites.setAdapter(favoritesAdapter);

        updateFavoritesVisibility();
    }

    private void addToCart(MenuItem menuItem) {
        // For items with sizes, show size selection dialog
        if (menuItem.getSize() != null && !menuItem.getSize().isEmpty()) {
            showSizeSelectionDialog(menuItem);
        } else {
            // Add directly to cart with default quantity 1
            CartItem cartItem = new CartItem(menuItem, "", 1, menuItem.getPrice());
            cartItems.add(cartItem);
            updateTotalOrder();
            Toast.makeText(this, menuItem.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSizeSelectionDialog(MenuItem menuItem) {
        // Parse sizes from string like "₱ 68.00 | 78.00"
        String sizeStr = menuItem.getSize();
        String[] prices = sizeStr.replace("₱", "").replace(" ", "").split("\\|");
        
        if (prices.length >= 2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Size");
            builder.setItems(new String[]{"Regular: ₱ " + prices[0].trim(), "Large: ₱ " + prices[1].trim()}, (dialog, which) -> {
                double price = Double.parseDouble(prices[which].trim());
                String size = which == 0 ? "Regular" : "Large";
                CartItem cartItem = new CartItem(menuItem, size, 1, price);
                cartItems.add(cartItem);
                updateTotalOrder();
                Toast.makeText(this, menuItem.getName() + " (" + size + ") added to cart!", Toast.LENGTH_SHORT).show();
            });
            builder.show();
        } else {
            // Fallback: add with default price
            CartItem cartItem = new CartItem(menuItem, "", 1, menuItem.getPrice());
            cartItems.add(cartItem);
            updateTotalOrder();
            Toast.makeText(this, menuItem.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
        }
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
            
            chip.setChipBackgroundColorResource(R.drawable.chip_background_selector);
            chip.setTextColor(getResources().getColorStateList(R.color.chip_text_color));
            chip.setElevation(0);
            chip.setChipStrokeWidth(0);
            chip.setTextSize(14);
            chip.setPadding(16, 8, 16, 8);

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
        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Cart button - Navigate to order summary
        btnCart.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            navigateToOrderSummary();
        });

        // Total order bar click - also navigate to order summary
        totalOrderBar.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            navigateToOrderSummary();
        });

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
    }

    private void navigateToOrderSummary() {
        Intent intent = new Intent(this, OrderSummaryActivity.class);
        intent.putExtra("cartItems", new ArrayList<>(cartItems));
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void setupBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.nav_add);

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
                // Already here
                return true;
            } else if (itemId == R.id.nav_menu) {
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if (itemId == R.id.nav_reports) {
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

    private void updateTotalOrder() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        tvTotalOrder.setText(String.format(Locale.getDefault(), "₱ %.2f", total));
        
        // Show/hide total order bar
        if (total > 0) {
            totalOrderBar.setVisibility(View.VISIBLE);
        } else {
            totalOrderBar.setVisibility(View.GONE);
        }
    }
}

