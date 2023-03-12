package com.example.menudemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartItemViewHolder> {
    private final List<PaintingEntity> list;

    public CartRecyclerAdapter(List<PaintingEntity> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_item, parent, false);
        return new CartItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        int index = holder.getAdapterPosition();
        PaintingEntity painting = list.get(index);
        holder.getImageView().setImageResource(painting.getDrawableId());
        holder.getImageView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.getNameView().setText(painting.getName());
        String artistAndDateText = painting.getArtist() + " - " + painting.getDateOfPublish();
        holder.getArtistAndDateView().setText(artistAndDateText);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class CartItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView nameView;
        private final TextView artistAndDateView;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cart_item_image);
            nameView = itemView.findViewById(R.id.cart_item_name);
            artistAndDateView = itemView.findViewById(R.id.cart_item_artist_and_date);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getNameView() {
            return nameView;
        }

        public TextView getArtistAndDateView() {
            return artistAndDateView;
        }
    }
}
