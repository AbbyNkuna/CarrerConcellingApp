package com.example.carrerconcellingapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrerconcellingapp.Interface.ItemClickListener;
import com.example.carrerconcellingapp.R;

public class JobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ItemClickListener itemClickListener;
    public TextView Fac,Info;

    public JobViewHolder(@NonNull View itemView) {
        super(itemView);
        Fac = itemView.findViewById(R.id.Fac);
       Info= itemView.findViewById(R.id.Info);
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
