package com.example.e_commerce_part_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText etFullName, etEmailLogin, etPhoneLogin, etPasswordLogin, etConfirmPassword;
    TextView tvAlreadySignedUp;
    private String user_email, fullName, phone, user_password, user_confirm_password;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullName = findViewById(R.id.etFullName);
        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPhoneLogin = findViewById(R.id.etPhoneLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnRegister = findViewById(R.id.btnRegister);

        tvAlreadySignedUp = findViewById(R.id.tvAlreadySignedUp);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Creating Account...");
                progressDialog.show();
                user_email = etEmailLogin.getText().toString().trim();
                user_password = etPasswordLogin.getText().toString().trim();
                user_confirm_password = etConfirmPassword.getText().toString().trim();
                phone = etPhoneLogin.getText().toString().trim();
                fullName = etFullName.getText().toString().trim();

                if(validate()) {
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                    sendUserData();
                                                    Toast.makeText(RegisterActivity.this, "Registered successfully, Please verify your email.", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

                            });
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this,"Please check the message",Toast.LENGTH_LONG).show();
                }
            }

        });
        tvAlreadySignedUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    private void sendUserData() {

        User user = new User(fullName, user_email, phone, user_password);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

    }
    private boolean validate() {
        boolean result = false;

        String name = etFullName.getText().toString().trim();
        String email = etEmailLogin.getText().toString().trim();
        String password = etPasswordLogin.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String phone = etPhoneLogin.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please enter full name", Toast.LENGTH_SHORT).show();
        } else if (phone.isEmpty() || phone.length() != 10) {
            Toast.makeText(RegisterActivity.this, "Enter correct phone number", Toast.LENGTH_LONG).show();
        } else if (email.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please enter the email", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please enter the password", Toast.LENGTH_SHORT).show();
        } else if (confirmPassword.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please re enter the password", Toast.LENGTH_SHORT).show();
        } else if (!(password.equals(confirmPassword))){
            Toast.makeText(RegisterActivity.this,"Passwords do not match",Toast.LENGTH_LONG).show();
        }
        else
        {
            result = true;
        }
        return result;
    }
}
