package com.skyskisoft.pawex.snacktrendy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ItemViewHolder
    extends RecyclerView.ViewHolder
    implements View.OnClickListener {

    ItemsRecyclerViewAdapter itemRecyclerViewAdapter;
    TextView itemTextView;
    ItemClickListener itemClickListener;

    ItemViewHolder(
        ItemsRecyclerViewAdapter itemRecyclerViewAdapter,
        View view,
        ItemClickListener itemClickListener
    ) {
        super(view);
        this.itemRecyclerViewAdapter = itemRecyclerViewAdapter;
        this.itemClickListener = itemClickListener;
        itemTextView = view.findViewById(R.id.item_txt);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (itemRecyclerViewAdapter.itemClickListener != null)
            itemRecyclerViewAdapter.itemClickListener.onItemClick(view, getAdapterPosition());
    }
}