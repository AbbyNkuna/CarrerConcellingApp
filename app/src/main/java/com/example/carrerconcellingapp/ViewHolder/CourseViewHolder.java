package com.example.carrerconcellingapp.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrerconcellingapp.Interface.ItemClickListener;
import com.example.carrerconcellingapp.R;

public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ItemClickListener itemClickListener;
    public TextView code,name,faculty,aps;
    public Button Delete;

    public CourseViewHolder(@NonNull View itemView) {
        super(itemView);
        code = itemView.findViewById(R.id.code);
        name = itemView.findViewById(R.id.Name);
        faculty = itemView.findViewById(R.id.faculty);
        aps= itemView.findViewById(R.id.APS);
        Delete =itemView.findViewById(R.id.Delete);
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
