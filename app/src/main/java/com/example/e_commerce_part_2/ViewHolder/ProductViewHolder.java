package com.example.e_commerce_part_2.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce_part_2.Interface.ItemClickListener;
import com.example.e_commerce_part_2.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvProductName, tvProductDescription, tvProductPrice;
    public ImageView imageView;
    public  ItemClickListener listener;

    public ProductViewHolder(View itemView)
    {
        super(itemView);

        imageView = (ImageView)itemView.findViewById(R.id.productImage);
        tvProductDescription = (TextView) itemView.findViewById(R.id.productDescription);
        tvProductName = (TextView) itemView.findViewById(R.id.productName);
        tvProductPrice = (TextView) itemView.findViewById(R.id.productPrice);
    }

    public void setItemCliclListener(ItemClickListener listener)
    {
        this.listener = listener;
    }
    @Override
    public void onClick (View view)
    {
        listener.onClick(view, getAdapterPosition(),false);
    }

}
