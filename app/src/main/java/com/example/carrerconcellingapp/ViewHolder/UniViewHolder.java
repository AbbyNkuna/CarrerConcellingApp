package com.example.carrerconcellingapp.ViewHolder;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrerconcellingapp.Interface.ItemClickListener;
import com.example.carrerconcellingapp.R;

public class UniViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ItemClickListener itemClickListener;
    public TextView name,Req;
    public Button Delete;

    public UniViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.Name);
       Req= itemView.findViewById(R.id.Req);
        Delete = itemView.findViewById(R.id.Delete);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
