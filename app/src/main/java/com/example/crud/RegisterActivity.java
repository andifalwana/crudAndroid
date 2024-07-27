package com.example.crud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etEmail, etPassword, etKonpassword;
    private Button btnMasuk, btnTologin;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        etUsername = findViewById(R.id.inputUsernameRegister);
        etEmail = findViewById(R.id.inputEmailRegister);
        etPassword = findViewById(R.id.inputPasswordRegister);
        etKonpassword = findViewById(R.id.inputKonPasswordRegister);
        btnMasuk = findViewById(R.id.btnMasuk);
        btnTologin = findViewById(R.id.btnToLogin);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

        btnTologin.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        });
        btnMasuk.setOnClickListener(v -> {
            if(etUsername.getText().length()>0 && etEmail.getText().length()>0 && etPassword.getText().length()>0 && etKonpassword.getText().length()>0){
                if(etPassword.getText().toString().equals(etKonpassword.getText().toString())){
                    register(etUsername.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(), "password dan konfirmasi password harus sama", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void register(String username, String email, String password){
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful() && task.getResult()!=null){
                   FirebaseUser firebaseUser = task.getResult().getUser();
                   if (firebaseUser!=null) {
                       UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                               .setDisplayName(username).build();
                       firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                            reload();
                           }
                       });
                   }else {
                       Toast.makeText(getApplicationContext(), "Register Gagal", Toast.LENGTH_SHORT).show();
                   }
               }else {
                   Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
    private  void reload(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    @Override
    public  void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser !=null){
            reload();
        }
    }
}