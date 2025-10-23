package com.loretacafe.pos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {

    private List<MenuItem> favoriteItems;

    public FavoritesAdapter(List<MenuItem> favoriteItems) {
        this.favoriteItems = favoriteItems;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        MenuItem item = favoriteItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return favoriteItems.size();
    }

    public void updateFavorites(List<MenuItem> newFavorites) {
        favoriteItems.clear();
        favoriteItems.addAll(newFavorites);
        notifyDataSetChanged();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView tvFavoriteItemName, tvFavoriteItemPrice;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFavoriteItemName = itemView.findViewById(R.id.tvFavoriteItemName);
            tvFavoriteItemPrice = itemView.findViewById(R.id.tvFavoriteItemPrice);
        }

        public void bind(MenuItem item) {
            tvFavoriteItemName.setText(item.getName());
            tvFavoriteItemPrice.setText(item.getFormattedPrice());
        }
    }
}