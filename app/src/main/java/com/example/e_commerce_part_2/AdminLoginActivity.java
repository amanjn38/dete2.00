package com.example.e_commerce_part_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText etEmailAdmin, etPasswordAdmin;
    private TextView tvForgotPasswordAdmin, notAnAdmin;
    private Button btnLoginAdmin;
    private ProgressDialog progressDialog;
    private String parentDbName= "Admins";
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etEmailAdmin = findViewById(R.id.etEmailAdmin);
        etPasswordAdmin = findViewById(R.id.etPasswordAdmin);

        progressDialog = new ProgressDialog(this);

        btnLoginAdmin = findViewById(R.id.btnLoginAdmin);
        notAnAdmin = findViewById(R.id.noAnAdmin);

        tvForgotPasswordAdmin = findViewById(R.id.tvForgotPasswordAdmin);
        firebaseAuth = FirebaseAuth.getInstance();

        notAnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminLoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tvForgotPasswordAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminLoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        btnLoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(etEmailAdmin.getText().toString().trim(), etPasswordAdmin.getText().toString().trim());
            }
        });
    }
    private void validate(String username, String userPassword)
    {


        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(username, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if(firebaseAuth.getCurrentUser().isEmailVerified())
                    {
                        progressDialog.dismiss();
                        Toast.makeText(AdminLoginActivity.this,"Login successful",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AdminLoginActivity.this,AdminCategoryActivity.class));
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(AdminLoginActivity.this,"Please verify your email",Toast.LENGTH_LONG).show();
                    }

                }
                else if (!task.isSuccessful())
                {
                    try
                    {
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidUserException invalidEmail)
                    {
                        Toast.makeText(AdminLoginActivity.this, invalidEmail.getMessage(),Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(AdminLoginActivity.this,RegisterActivity.class));

                    }
                    catch (FirebaseAuthInvalidCredentialsException wrongPassword)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(AdminLoginActivity.this,wrongPassword.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(AdminLoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    /*private void LoginUser()
    {

        if (TextUtils.isEmpty(etEmailAdmin.getText().toString()))
        {
            Toast.makeText(this, "Please write your phone number or email address", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(etPasswordAdmin.getText().toString()))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressDialog.setTitle("Login Account");
            progressDialog.setMessage("Please wait, while we are checking the credentials.");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            AllowAccessToAccount(etEmailAdmin.getText().toString().trim(), etPasswordAdmin.getText().toString().trim());
        }
    }



    private void AllowAccessToAccount(final String phone, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("Admins").child(phone).exists())
                {
                    User usersData = dataSnapshot.child(parentDbName).child(phone).getValue(User.class);

                    if (usersData.getPhone().equals(phone) || usersData.getEmail().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            if (parentDbName.equals("Admins"))
                            {
                                Toast.makeText(AdminLoginActivity.this, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                Intent intent = new Intent(AdminLoginActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminLoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(AdminLoginActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
}
