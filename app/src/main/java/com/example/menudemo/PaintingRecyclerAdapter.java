package com.example.menudemo;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PaintingRecyclerAdapter extends ContextMenuRecyclerView.Adapter<PaintingRecyclerAdapter.PaintingViewHolder> {
    private final List<Painting> list;
    private final RecyclerViewLongClickListener recyclerViewLongClickListener;
    private final RecyclerViewClickListener<Painting> recyclerViewClickListener;

    public PaintingRecyclerAdapter(List<Painting> list, RecyclerViewClickListener<Painting> recyclerViewClickListener, RecyclerViewLongClickListener recyclerViewLongClickListener) {
        this.list = list;
        this.recyclerViewClickListener = recyclerViewClickListener;
        this.recyclerViewLongClickListener = recyclerViewLongClickListener;
    }

    @NonNull
    @Override
    public PaintingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.painting_item, parent, false);
        return new PaintingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaintingViewHolder holder, int position) {
        int index = holder.getAdapterPosition();
        holder.getImage().setImageResource(list.get(index).getDrawableId());
        holder.getImage().setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.getName().setText(list.get(index).getName());
        String artistAndDateText = list.get(index).getArtist() + " - " + list.get(index).getDateOfPublish();
        holder.getArtistAndDate().setText(artistAndDateText);
        holder.bindOnClickListener(list.get(position), recyclerViewClickListener);
        holder.bindOnLongClickListener(position, recyclerViewLongClickListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeAt(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    public static class PaintingViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private final ImageView image;
        private final TextView name;
        private final TextView artistAndDate;

        public PaintingViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.item_image);
            this.name = itemView.findViewById(R.id.item_name);
            this.artistAndDate = itemView.findViewById(R.id.item_artist_and_date);
            itemView.setOnCreateContextMenuListener(this);
        }

        public ImageView getImage() {
            return image;
        }

        public TextView getName() {
            return name;
        }

        public TextView getArtistAndDate() {
            return artistAndDate;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        }

        public void bindOnClickListener(final Painting item, final RecyclerViewClickListener<Painting> listener) {
            itemView.setOnClickListener(view -> listener.onRecyclerViewItemClick(item));
        }

        public void bindOnLongClickListener(final int position, final RecyclerViewLongClickListener listener) {
            itemView.setOnLongClickListener(view -> listener.onRecyclerViewItemLongClick(position));
        }
    }
}
