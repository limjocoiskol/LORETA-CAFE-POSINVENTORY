package com.loretacafe.pos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<MenuItem> menuItems;
    private List<MenuItem> menuItemsFull; // For search functionality
    private OnFavoriteClickListener favoriteClickListener;

    public interface OnFavoriteClickListener {
        void onFavoriteClick(MenuItem item, int position);
    }

    public MenuAdapter(List<MenuItem> menuItems, OnFavoriteClickListener listener) {
        this.menuItems = menuItems;
        this.menuItemsFull = new ArrayList<>(menuItems);
        this.favoriteClickListener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem item = menuItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public void filter(String query) {
        menuItems.clear();
        if (query.isEmpty()) {
            menuItems.addAll(menuItemsFull);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (MenuItem item : menuItemsFull) {
                if (item.getName().toLowerCase().contains(lowerCaseQuery)) {
                    menuItems.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filterByCategory(String category) {
        menuItems.clear();
        if (category.equals("All")) {
            menuItems.addAll(menuItemsFull);
        } else {
            for (MenuItem item : menuItemsFull) {
                if (item.category().equals(category)) {
                    menuItems.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateFullList(List<MenuItem> newList) {
        menuItemsFull.clear();
        menuItemsFull.addAll(newList);
        menuItems.clear();
        menuItems.addAll(newList);
        notifyDataSetChanged();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemPrice, tvNewBadge;
        ImageButton btnFavorite;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            tvNewBadge = itemView.findViewById(R.id.tvNewBadge);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }

        public void bind(MenuItem item) {
            tvItemName.setText(item.getName());
            tvItemPrice.setText(item.getFormattedPrice());

            // Show/hide NEW badge
            if (item.isNew()) {
                tvNewBadge.setVisibility(View.VISIBLE);
            } else {
                tvNewBadge.setVisibility(View.GONE);
            }

            // Set favorite icon
            if (item.isFavorite()) {
                btnFavorite.setImageResource(R.drawable.ic_heart_filled);
            } else {
                btnFavorite.setImageResource(R.drawable.ic_heart_outline);
            }

            // Favorite button click
            btnFavorite.setOnClickListener(v -> {
                if (favoriteClickListener != null) {
                    favoriteClickListener.onFavoriteClick(item, getAdapterPosition());
                }
            });
        }
    }
}