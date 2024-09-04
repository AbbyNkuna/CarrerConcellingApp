package com.example.carrerconcellingapp.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrerconcellingapp.Interface.ItemClickListener;
import com.example.carrerconcellingapp.R;

public class BookingViewholder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    private ItemClickListener itemClickListener;
 public TextView code,name,link,date,approval;
    public Button Submit ;
    public BookingViewholder(@NonNull View itemView) {
        super(itemView);
        code= itemView.findViewById(R.id.code);
        name = itemView.findViewById(R.id.Name);
        link = itemView.findViewById(R.id.Link);
        date = itemView.findViewById(R.id.Date);
        approval = itemView.findViewById(R.id.Approval);
        Submit = itemView.findViewById(R.id.Submit);
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
