package com.example.e_commerce_part_2;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {

    List<HorizontalProductModel> horizontalProductModelList;

    public GridProductLayoutAdapter(List<HorizontalProductModel> horizontalProductModelList) {
        this.horizontalProductModelList = horizontalProductModelList;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view ;
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            ImageView productImage = view.findViewById(R.id.horizontal_scroll_image);
            TextView productTitle = view.findViewById(R.id.model);
            TextView description = view.findViewById(R.id.description);
            TextView  price = view.findViewById(R.id.price);

            productImage.setImageResource(horizontalProductModelList.get(position).getProductImage());
            productTitle.setText(horizontalProductModelList.get(position).getProduct());
            description.setText(horizontalProductModelList.get(position).getDescription());
            price.setText(horizontalProductModelList.get(position).getPrice());


        }else {

            view = convertView;
        }
        return view;
    }
}
