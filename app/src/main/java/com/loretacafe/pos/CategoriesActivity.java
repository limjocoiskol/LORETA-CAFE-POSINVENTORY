package com.loretacafe.pos;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoriesActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private RecyclerView rvCategories;
    private LinearLayout llEmptyState;
    private FloatingActionButton fabAddCategory;

    private CategoryAdapter categoryAdapter;
    private List<Category> categories;

    private String selectedIconName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        initializeViews();
        setupClickListeners();
        loadCategories();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        rvCategories = findViewById(R.id.rvCategories);
        llEmptyState = findViewById(R.id.llEmptyState);
        fabAddCategory = findViewById(R.id.fabAddCategory);

        // Setup RecyclerView
        rvCategories.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter = new CategoryAdapter(new ArrayList<>(), (category, position) -> {
            // Navigate to category detail/assign items
            Intent intent = new Intent(CategoriesActivity.this, AssignItemsActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        rvCategories.setAdapter(categoryAdapter);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fabAddCategory.setOnClickListener(v -> showCreateCategoryDialog());
    }

    private void loadCategories() {
        // Load sample categories for demonstration
        categories = new ArrayList<>();
        
        // Create sample category with Iced Coffee
        Category icedCoffee = new Category(UUID.randomUUID().toString(), "Iced Coffee", "iced_coffee_cafe_latte");
        icedCoffee.setItemCount(13);
        categories.add(icedCoffee);

        updateCategoriesView();
    }

    private void updateCategoriesView() {
        if (categories.isEmpty()) {
            llEmptyState.setVisibility(View.VISIBLE);
            rvCategories.setVisibility(View.GONE);
        } else {
            llEmptyState.setVisibility(View.GONE);
            rvCategories.setVisibility(View.VISIBLE);
            categoryAdapter.updateCategories(categories);
        }
    }

    private void showCreateCategoryDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_create_category);
        dialog.getWindow().setBackgroundDrawableResource(android.R.drawable.screen_background_light);

        TextInputEditText etCategoryName = dialog.findViewById(R.id.etCategoryName);
        RecyclerView rvIconGrid = dialog.findViewById(R.id.rvIconGrid);
        Button btnAssignItems = dialog.findViewById(R.id.btnAssignItems);
        Button btnCreateItem = dialog.findViewById(R.id.btnCreateItem);

        // Setup icon grid
        List<String> iconNames = getAvailableIcons();
        rvIconGrid.setLayoutManager(new GridLayoutManager(this, 4));
        IconSelectorAdapter iconAdapter = new IconSelectorAdapter(iconNames, iconName -> {
            selectedIconName = iconName;
        });
        rvIconGrid.setAdapter(iconAdapter);

        btnAssignItems.setOnClickListener(v -> {
            String categoryName = etCategoryName.getText().toString().trim();
            if (categoryName.isEmpty()) {
                Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedIconName.isEmpty()) {
                Toast.makeText(this, "Please select an icon", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create new category
            Category newCategory = new Category(UUID.randomUUID().toString(), categoryName, selectedIconName);
            categories.add(newCategory);
            updateCategoriesView();

            dialog.dismiss();

            // Navigate to assign items
            Intent intent = new Intent(this, AssignItemsActivity.class);
            intent.putExtra("category", newCategory);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        btnCreateItem.setOnClickListener(v -> {
            String categoryName = etCategoryName.getText().toString().trim();
            if (categoryName.isEmpty()) {
                Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedIconName.isEmpty()) {
                Toast.makeText(this, "Please select an icon", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create new category
            Category newCategory = new Category(UUID.randomUUID().toString(), categoryName, selectedIconName);
            categories.add(newCategory);
            updateCategoriesView();

            dialog.dismiss();
            Toast.makeText(this, "Create Item - Coming Soon", Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }

    private List<String> getAvailableIcons() {
        List<String> icons = new ArrayList<>();
        icons.add("iced_coffee_cafe_latte");
        icons.add("iced_coffee_cappuccino");
        icons.add("frappe_chocchip");
        icons.add("frappe_cookies_and_cream");
        icons.add("milktea_wintermelon");
        icons.add("milktea_taro");
        icons.add("cheesecake_oreo_cheesecake");
        icons.add("cheesecake_strawberry_cheesecake");
        icons.add("fruittea_sunrise");
        icons.add("fruitmilk_strawberrymilk");
        icons.add("specials_nutellalatte");
        icons.add("hotcoffee");
        return icons;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}

