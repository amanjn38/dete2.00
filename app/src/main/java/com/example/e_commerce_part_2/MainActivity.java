package com.example.e_commerce_part_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button btnLoginActivity, btnRegisterActivity, btnAdminActivity;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdminActivity = findViewById(R.id.btnAdminActivity);
        btnLoginActivity = findViewById(R.id.btnLoginActivity);
        btnRegisterActivity = findViewById(R.id.btnRegisterActivity);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        Paper.init(this);

        btnAdminActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AdminLoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        String user_Email = Paper.book().read(Prevalent.user_email);
        String user_Password = Paper.book().read(Prevalent.user_password);

        if(user_Email != "" && user_Password != "")
        {
            if(!TextUtils.isEmpty(user_Email)  &&  !TextUtils.isEmpty(user_Password))
            {
                AllowAccess(user_Email,user_Password);
            }
        }
    }

    private void AllowAccess(String user_email, String user_password) {

        progressDialog.setMessage("Logging in...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Already Logged In",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);

                }
                else if (!task.isSuccessful())
                {
                    try
                    {
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidUserException invalidEmail)
                    {
                        Toast.makeText(MainActivity.this, invalidEmail.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(MainActivity.this,RegisterActivity.class));

                    }
                    catch (FirebaseAuthInvalidCredentialsException wrongPassword)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,wrongPassword.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
