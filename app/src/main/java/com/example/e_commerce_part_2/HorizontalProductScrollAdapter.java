package com.example.e_commerce_part_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {

    private List<HorizontalProductModel> horizontalProductModelList;

    public HorizontalProductScrollAdapter(List<HorizontalProductModel> horizontalProductModelList) {
        this.horizontalProductModelList = horizontalProductModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resource = horizontalProductModelList.get(position).getProductImage();
        String title = horizontalProductModelList.get(position).getProduct();
        String description = horizontalProductModelList.get(position).getDescription();
        String price = horizontalProductModelList.get(position).getPrice();

        holder.setProductImage(resource);
        holder.setProductTitle(title);
        holder.setDescription(description);
        holder.setPrice(price);
    }

    @Override
    public int getItemCount() {
        return horizontalProductModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle, description, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.horizontal_scroll_image);
            productTitle = itemView.findViewById(R.id.model);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
        }

        private void setProductImage(int resource){
            productImage.setImageResource(resource);
        }
        private void setProductTitle(String title) {
            productTitle.setText(title);
        }
        private void setDescription(String description1){
            description.setText(description1);
        }
        private void setPrice(String price1) {
            price.setText(price1);
        }
    }
}
