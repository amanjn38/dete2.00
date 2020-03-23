package com.example.e_commerce_part_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Admin_add_new_category extends AppCompatActivity {

    private String categoryName, description, price, name, saveCurrentDate, saveCurrentTimE, productRandomKey, downloadImageUrl;
    private Button add_new_product;
    private EditText productName, productPrice, productDescription;
    private ImageView selectProductImage;
    private static final int GalleryPick = 1;
    private Uri imageURI;
    private StorageReference productImageRef;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_category);

        categoryName = getIntent().getExtras().get("category").toString();

        Toast.makeText(Admin_add_new_category.this,categoryName,Toast.LENGTH_LONG).show();

        progressDialog = new ProgressDialog(this);
        add_new_product = (Button)findViewById(R.id.add_new_product);
        productPrice = (EditText)findViewById(R.id.product_price);
        productDescription = (EditText)findViewById(R.id.product_description);
        productName = (EditText)findViewById(R.id.product_name);
        selectProductImage = (ImageView)findViewById(R.id.select_product_image);
        productImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("products");
        selectProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        add_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateProductData();
            }
        });
    }

    private void validateProductData() {
        description = productDescription.getText().toString().trim();
        price = productPrice.getText().toString().trim();
        name = productName.getText().toString().trim();

        if(imageURI == null)
        {
            Toast.makeText(Admin_add_new_category.this,"Product Image is mandatory..", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(description))
        {
            Toast.makeText(Admin_add_new_category.this,"Please write product description..",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(price))
        {
            Toast.makeText(Admin_add_new_category.this,"Please write product price..",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(name))
        {
            Toast.makeText(Admin_add_new_category.this,"Please write product name..",Toast.LENGTH_LONG).show();
        }
        else
        {
            StoreImageInformation();
        }

    }

    private void StoreImageInformation() {

        progressDialog.setTitle("Adding new Product");
        progressDialog.setMessage("Please wait while we are adding the new product");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTimE = currentTime.format(calendar.getTime());

        //CREATING A UNIQUE OF EACH OF THE PRODUCT THAT WILL BE SOLD IN OUR APP USING CURRENTDATE AND CURRENTTIME

        productRandomKey = saveCurrentDate + saveCurrentTimE;

        final StorageReference filepath = productImageRef.child(imageURI.getLastPathSegment() + productRandomKey);

        final UploadTask uploadTask = filepath.putFile(imageURI);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(Admin_add_new_category.this,message,Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Admin_add_new_category.this,"Product Image uploaded successfully",Toast.LENGTH_LONG).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(Admin_add_new_category.this,"Stored product url successfully",Toast.LENGTH_LONG).show();
                            SaveProductInfoToDatabass();
                        }
                    }
                });

            }
        });
    }

    private void SaveProductInfoToDatabass() {

        final HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTimE);
        productMap.put("description",description);
        productMap.put("image",downloadImageUrl);
        productMap.put("category",categoryName);
        productMap.put("price",price);
        productMap.put("name",name);

        databaseReference.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {

                    Intent intent = new Intent(Admin_add_new_category.this, AdminCategoryActivity.class);
                    startActivity(intent);
                    progressDialog.dismiss();
                    Toast.makeText(Admin_add_new_category.this,"Product is added successfully",Toast.LENGTH_LONG).show();
                }
                else
                {
                    progressDialog.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(Admin_add_new_category.this,message,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void OpenGallery()
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryPick && resultCode == RESULT_OK && data!= null)
        {
            imageURI = data.getData();
            selectProductImage.setImageURI(imageURI);
        }
    }
}
