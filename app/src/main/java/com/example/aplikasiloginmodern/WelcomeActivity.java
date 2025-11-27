package com.example.aplikasiloginmodern;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class WelcomeActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin, btnRegister;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        edtUsername = findViewById(R.id.edt_welcome_username);
        edtPassword = findViewById(R.id.edt_welcome_password);
        btnLogin = findViewById(R.id.btn_welcome_login);
        btnRegister = findViewById(R.id.btn_welcome_register);

        // AUTO LOGIN → harus masuk ke HomeActivity / Dashboard
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedUser = prefs.getString("username", null);

        if (savedUser != null) {
            startActivity(new Intent(this, HomeFragment.class));
            finish();
            return;
        }

        // LOGIN
        btnLogin.setOnClickListener(v -> {
            String user = edtUsername.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Isi semua field", Toast.LENGTH_SHORT).show();
                return;
            }

            authViewModel.login(user, pass);
        });

        // Observasi hasil login
        authViewModel.getLoginStatus().observe(this, success -> {
            if (success) {

                // Simpan username
                SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
                editor.putString("username", edtUsername.getText().toString());
                editor.apply();

                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(this, HomeFragment.class));
                finish();


            } else {
                Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show();
            }
        });

        // REGISTER
        btnRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }
}
