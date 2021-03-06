package com.example.e_commerce_part_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.UUID;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private CheckBox cbRemember;
    private ImageView ivFacebook, ivGoogle, ivTwitter;
    private final static int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleSignInClient;
    private static final String TAG = "LoginActivity";
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRemember = findViewById(R.id.cbRemember);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        ivFacebook = findViewById(R.id.ivFacebook);
        ivGoogle = findViewById(R.id.ivGoogle);
        ivTwitter = findViewById(R.id.ivTwitter);
        callbackManager = CallbackManager.Factory.create();

        FacebookSdk.sdkInitialize(getApplicationContext());

        ivFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivFacebook.setEnabled(false);

                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //Toast.makeText(MainActivity.this,"Login successful",Toast.LENGTH_LONG).show();
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "On Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(LoginActivity.this, "On Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(LoginActivity.this, "Connection to google sign in failed", Toast.LENGTH_SHORT).show();

            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        ivGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        Paper.init(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(etEmail.getText().toString().trim(), etPassword.getText().toString().trim());
            }
        });

        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");


    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }*/

    private void handleFacebookAccessToken(AccessToken token) {
        //Toast.makeText(MainActivity.this,"Handle facebook handle token" + token,Toast.LENGTH_LONG).show();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            ivFacebook.setEnabled(true);
                            // updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            ivFacebook.setEnabled(true);
                            //updateUI();
                        }

                    }
                });
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            progressDialog.setTitle("Google Sign In");
            progressDialog.setMessage("Logging in using your google account");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                Toast.makeText(LoginActivity.this, "Please wait, while we are getting your auth results", Toast.LENGTH_LONG).show();
            } else {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Can't get Auth results", Toast.LENGTH_SHORT).show();
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Signed In successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            progressDialog.dismiss();

                        } else {

                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            String message = task.getException().toString();
                            SendUserToLoginActivity();
                            Toast.makeText(LoginActivity.this, "Not authenticated " + message, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private void SendUserToLoginActivity() {
        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void validate(String username, String userPassword) {
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        if (cbRemember.isChecked()) {
            Paper.book().write(Prevalent.user_email, username);
            Paper.book().write(Prevalent.user_password, userPassword);
        }
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(username, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (firebaseAuth.getCurrentUser().isEmailVerified()) {

                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                        retrieveData();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Please verify your email", Toast.LENGTH_LONG).show();
                    }

                } else if (!task.isSuccessful()) {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException invalidEmail) {
                        Toast.makeText(LoginActivity.this, invalidEmail.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

                    } catch (FirebaseAuthInvalidCredentialsException wrongPassword) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, wrongPassword.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void retrieveData() {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String UID =firebaseAuth.getCurrentUser().getUid();
                User user = dataSnapshot.child("Users").child(UID).getValue(User.class);
                Prevalent.currentOnlineUser = user;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}