package com.example.e_commerce_part_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView tshirts, sportsTshirts, femaleDresses, sweathers;
    private ImageView glasses, hats, wallets, shoes;
    private ImageView headPhones, watches, laptops, mobilephones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        tshirts = (ImageView)findViewById(R.id.t_shirts);
        sportsTshirts = (ImageView)findViewById(R.id.sports_t_shirts);
        femaleDresses = (ImageView)findViewById(R.id.female_dresses);
        sweathers = (ImageView)findViewById(R.id.sweather);
        glasses = (ImageView)findViewById(R.id.glasses);
        hats =(ImageView) findViewById(R.id.hats);
        wallets = (ImageView)findViewById(R.id.purses_bags_wallets);
        shoes = (ImageView)findViewById(R.id.shoes);
        headPhones = (ImageView)findViewById(R.id.headphones);
        watches =(ImageView) findViewById(R.id.watches);
        laptops =(ImageView) findViewById(R.id.laptops_pc);
        mobilephones = (ImageView)findViewById(R.id.mobiles);

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this, Admin_add_new_category.class);
                intent.putExtra("category","T shirts");
                startActivity(intent);
            }
        });

        sportsTshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this, Admin_add_new_category.class);
                intent.putExtra("category","Sports T shirt");
                startActivity(intent);
            }
        });

        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, Admin_add_new_category.class);
                intent.putExtra("category","Female Dresses");
                startActivity(intent);
            }
        });

        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, Admin_add_new_category.class);
                intent.putExtra("category","Sweathers");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, Admin_add_new_category.class);
                intent.putExtra("category","Glasses");
                startActivity(intent);
            }
        });

        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, Admin_add_new_category.class);
                intent.putExtra("category","Hats Caps");
                startActivity(intent);
            }
        });

        wallets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, Admin_add_new_category.class);
                intent.putExtra("category","Wallets");
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, Admin_add_new_category.class);
                intent.putExtra("category","Shoes");
                startActivity(intent);
            }
        });

        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, Admin_add_new_category.class);
                intent.putExtra("category","Laptops");
                startActivity(intent);
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, Admin_add_new_category.class);
                intent.putExtra("category","Watches");
                startActivity(intent);
            }
        });

        mobilephones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, Admin_add_new_category.class);
                intent.putExtra("category","Mobile phones");
                startActivity(intent);
            }
        });

        headPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, Admin_add_new_category.class);
                intent.putExtra("category","Headphones");
                startActivity(intent);
            }
        });
    }
}
