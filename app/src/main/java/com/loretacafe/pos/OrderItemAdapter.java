package com.loretacafe.pos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {

    private List<CartItem> cartItems;

    public OrderItemAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems != null ? cartItems : new ArrayList<>();
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_summary, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.bind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateItems(List<CartItem> newItems) {
        this.cartItems = newItems != null ? newItems : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemDetails, tvItemTotal;

        OrderItemViewHolder(View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemDetails = itemView.findViewById(R.id.tvItemDetails);
            tvItemTotal = itemView.findViewById(R.id.tvItemTotal);
        }

        void bind(CartItem cartItem) {
            tvItemName.setText(cartItem.getMenuItem().getName());
            
            // Build details string
            StringBuilder details = new StringBuilder();
            if (cartItem.getSelectedSize() != null && !cartItem.getSelectedSize().isEmpty()) {
                details.append(cartItem.getSelectedSize());
            } else {
                // Try to parse from menu item size
                String sizeStr = cartItem.getMenuItem().getSize();
                if (sizeStr != null && !sizeStr.isEmpty()) {
                    details.append("Regular"); // Default
                }
            }
            
            if (details.length() > 0) {
                details.append(" • ");
            }
            details.append(cartItem.getQuantity()).append(" x ").append(cartItem.getFormattedUnitPrice());
            
            tvItemDetails.setText(details.toString());
            tvItemTotal.setText(cartItem.getFormattedTotalPrice());
        }
    }
}

