package com.loretacafe.pos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IconSelectorAdapter extends RecyclerView.Adapter<IconSelectorAdapter.IconViewHolder> {

    private List<String> iconNames;
    private OnIconClickListener clickListener;
    private int selectedPosition = -1;

    public interface OnIconClickListener {
        void onIconClick(String iconName);
    }

    public IconSelectorAdapter(List<String> iconNames, OnIconClickListener listener) {
        this.iconNames = iconNames;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon_selector, parent, false);
        return new IconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
        String iconName = iconNames.get(position);
        holder.bind(iconName, position == selectedPosition);
    }

    @Override
    public int getItemCount() {
        return iconNames.size();
    }

    class IconViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon, ivCheckmark;
        View vSelectedIndicator;

        public IconViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            ivCheckmark = itemView.findViewById(R.id.ivCheckmark);
            vSelectedIndicator = itemView.findViewById(R.id.vSelectedIndicator);
        }

        public void bind(String iconName, boolean isSelected) {
            // Load icon
            int resourceId = itemView.getContext().getResources().getIdentifier(
                iconName, "drawable", itemView.getContext().getPackageName()
            );
            if (resourceId != 0) {
                ivIcon.setImageResource(resourceId);
            } else {
                ivIcon.setImageResource(R.drawable.ic_image_placeholder);
            }

            // Show/hide selection
            if (isSelected) {
                vSelectedIndicator.setVisibility(View.VISIBLE);
                ivCheckmark.setVisibility(View.VISIBLE);
            } else {
                vSelectedIndicator.setVisibility(View.GONE);
                ivCheckmark.setVisibility(View.GONE);
            }

            // Click listener
            itemView.setOnClickListener(v -> {
                int previousPosition = selectedPosition;
                selectedPosition = getAdapterPosition();
                
                notifyItemChanged(previousPosition);
                notifyItemChanged(selectedPosition);

                if (clickListener != null) {
                    clickListener.onIconClick(iconName);
                }
            });
        }
    }
}

