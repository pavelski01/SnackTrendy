package com.skyskisoft.pawex.snacktrendy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ItemsRecyclerViewAdapter 
    extends RecyclerView.Adapter<ItemViewHolder> {
    
    private List<String> data;
    private LayoutInflater layoutInflater;

    public ItemClickListener itemClickListener;

    ItemsRecyclerViewAdapter(Context context, List<String> data) {
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(this, view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        String item = data.get(position);
        holder.itemTextView.setText(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    String getItem(int id) {
        return data.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}