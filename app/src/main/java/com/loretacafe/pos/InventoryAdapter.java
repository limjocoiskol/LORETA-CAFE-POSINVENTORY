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

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private List<InventoryItem> inventoryItems;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(InventoryItem item, int position);
        void onDeleteClick(InventoryItem item, int position);
    }

    public InventoryAdapter(List<InventoryItem> items, OnItemClickListener listener) {
        this.inventoryItems = items != null ? items : new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inventory, parent, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        InventoryItem item = inventoryItems.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }

    public void updateItems(List<InventoryItem> newItems) {
        this.inventoryItems = newItems != null ? newItems : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class InventoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvQuantity, tvUnit, tvCost, tvStatus;
        ImageButton btnEdit, btnDelete;

        InventoryViewHolder(View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvUnit = itemView.findViewById(R.id.tvUnit);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        void bind(InventoryItem item, OnItemClickListener listener) {
            tvItemName.setText(item.getName());
            tvQuantity.setText("Product Quantity: " + item.getFormattedQuantity());
            tvUnit.setText("Product Unit: " + item.getUnit());
            tvCost.setText("Product Cost: " + item.getFormattedCost());
            tvStatus.setText("Status: " + item.getStatus().getDisplayName());
            tvStatus.setTextColor(item.getStatusColor());

            btnEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditClick(item, getAdapterPosition());
                }
            });

            btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(item, getAdapterPosition());
                }
            });
        }
    }
}

