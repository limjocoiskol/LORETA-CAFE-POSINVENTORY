package com.loretacafe.pos;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EditItemActivity extends AppCompatActivity {

    private ImageButton btnBack, btnRemoveSize, btnAddSize;
    private ImageView ivItemPhoto, ivSizeIcon;
    private Button btnChoosePhoto, btnTakePhoto, btnSave, btnAddIngredient;
    private TextInputEditText etItemName, etSizeName, etCost, etPrice, etStockKeepingUnit;
    private Spinner spinnerCategory;
    private TextView tvCurrentSize;
    private LinearLayout llIngredientsList;

    private List<Size> sizes;
    private int currentSizeIndex = 0;
    private Uri selectedImageUri;

    private static final int REQUEST_IMAGE_PICK = 1001;
    private static final int REQUEST_IMAGE_CAPTURE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        initializeViews();
        setupCategorySpinner();
        initializeSizes();
        setupClickListeners();
        loadCurrentSize();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        btnRemoveSize = findViewById(R.id.btnRemoveSize);
        btnAddSize = findViewById(R.id.btnAddSize);
        ivItemPhoto = findViewById(R.id.ivItemPhoto);
        ivSizeIcon = findViewById(R.id.ivSizeIcon);
        btnChoosePhoto = findViewById(R.id.btnChoosePhoto);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnSave = findViewById(R.id.btnSave);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        etItemName = findViewById(R.id.etItemName);
        etSizeName = findViewById(R.id.etSizeName);
        etCost = findViewById(R.id.etCost);
        etPrice = findViewById(R.id.etPrice);
        etStockKeepingUnit = findViewById(R.id.etStockKeepingUnit);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        tvCurrentSize = findViewById(R.id.tvCurrentSize);
        llIngredientsList = findViewById(R.id.llIngredientsList);
    }

    private void setupCategorySpinner() {
        List<String> categories = new ArrayList<>();
        categories.add("Choose Category*");
        categories.add("Iced Coffee");
        categories.add("Frappe");
        categories.add("Coffee Frappe");
        categories.add("Milktea Classic");
        categories.add("Loreta's Specials");
        categories.add("Cheesecake");
        categories.add("Fruit Tea and Lemonade");
        categories.add("Fruit Milk");
        categories.add("Fruit Soda");
        categories.add("Hot Coffee");
        categories.add("Add ons");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void initializeSizes() {
        sizes = new ArrayList<>();
        sizes.add(new Size(UUID.randomUUID().toString(), "Small"));
        sizes.add(new Size(UUID.randomUUID().toString(), "Medium"));
        sizes.add(new Size(UUID.randomUUID().toString(), "Large"));
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        btnChoosePhoto.setOnClickListener(v -> choosePhotoFromGallery());

        btnTakePhoto.setOnClickListener(v -> takePhotoWithCamera());

        btnAddSize.setOnClickListener(v -> {
            if (currentSizeIndex < sizes.size() - 1) {
                saveCurrentSizeData();
                currentSizeIndex++;
                loadCurrentSize();
            } else {
                Toast.makeText(this, "Maximum 3 sizes allowed", Toast.LENGTH_SHORT).show();
            }
        });

        btnRemoveSize.setOnClickListener(v -> {
            if (currentSizeIndex > 0) {
                saveCurrentSizeData();
                currentSizeIndex--;
                loadCurrentSize();
            } else {
                Toast.makeText(this, "Cannot remove the first size", Toast.LENGTH_SHORT).show();
            }
        });

        btnAddIngredient.setOnClickListener(v -> showAddIngredientDialog());

        btnSave.setOnClickListener(v -> saveItem());
    }

    private void loadCurrentSize() {
        Size currentSize = sizes.get(currentSizeIndex);
        
        tvCurrentSize.setText("Size: " + currentSize.getName());
        etSizeName.setText(currentSize.getName());
        etCost.setText(currentSize.getCost() > 0 ? String.format("₱%.2f", currentSize.getCost()) : "");
        etPrice.setText(currentSize.getPrice() > 0 ? String.format("%.2f", currentSize.getPrice()) : "");
        etStockKeepingUnit.setText(currentSize.getStockKeepingUnit());

        // Load ingredients for this size
        loadIngredientsForCurrentSize();
    }

    private void saveCurrentSizeData() {
        Size currentSize = sizes.get(currentSizeIndex);
        
        String sizeName = etSizeName.getText().toString().trim();
        String priceStr = etPrice.getText().toString().replace("₱", "").trim();
        String sku = etStockKeepingUnit.getText().toString().trim();

        if (!sizeName.isEmpty()) {
            currentSize.setName(sizeName);
        }

        if (!priceStr.isEmpty()) {
            try {
                currentSize.setPrice(Double.parseDouble(priceStr));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        currentSize.setStockKeepingUnit(sku);
    }

    private void loadIngredientsForCurrentSize() {
        llIngredientsList.removeAllViews();
        
        Size currentSize = sizes.get(currentSizeIndex);
        List<String> ingredients = currentSize.getIngredients();

        if (ingredients != null) {
            for (String ingredient : ingredients) {
                addIngredientView(ingredient);
            }
        }
    }

    private void addIngredientView(String ingredientName) {
        View ingredientView = LayoutInflater.from(this).inflate(R.layout.item_ingredient, llIngredientsList, false);
        
        TextView tvIngredientName = ingredientView.findViewById(R.id.tvIngredientName);
        ImageButton btnRemove = ingredientView.findViewById(R.id.btnRemoveIngredient);

        tvIngredientName.setText(ingredientName);
        
        btnRemove.setOnClickListener(v -> {
            llIngredientsList.removeView(ingredientView);
            Size currentSize = sizes.get(currentSizeIndex);
            currentSize.removeIngredient(ingredientName);
        });

        llIngredientsList.addView(ingredientView);
    }

    private void showAddIngredientDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_ingredient);
        dialog.getWindow().setBackgroundDrawableResource(android.R.drawable.screen_background_light);

        EditText etIngredientName = dialog.findViewById(R.id.etIngredientName);
        Button btnAdd = dialog.findViewById(R.id.btnAddIngredientConfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancelIngredient);

        btnAdd.setOnClickListener(v -> {
            String ingredientName = etIngredientName.getText().toString().trim();
            if (!ingredientName.isEmpty()) {
                Size currentSize = sizes.get(currentSizeIndex);
                currentSize.addIngredient(ingredientName);
                addIngredientView(ingredientName);
                dialog.dismiss();
                Toast.makeText(this, "Ingredient added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter ingredient name", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void choosePhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    private void takePhotoWithCamera() {
        Toast.makeText(this, "Camera feature - Coming Soon", Toast.LENGTH_SHORT).show();
        // TODO: Implement camera functionality
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                selectedImageUri = data.getData();
                ivItemPhoto.setImageURI(selectedImageUri);
            }
        }
    }

    private void saveItem() {
        String itemName = etItemName.getText().toString().trim();
        int selectedCategoryIndex = spinnerCategory.getSelectedItemPosition();

        if (itemName.isEmpty()) {
            Toast.makeText(this, "Please enter item name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedCategoryIndex == 0) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save current size data
        saveCurrentSizeData();

        // Show success message
        Toast.makeText(this, "Item saved successfully! ✅", Toast.LENGTH_SHORT).show();
        
        // Go back to previous screen
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}

