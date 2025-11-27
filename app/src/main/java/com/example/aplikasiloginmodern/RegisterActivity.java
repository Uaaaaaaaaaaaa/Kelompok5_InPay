package com.example.aplikasiloginmodern;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword, edtConfirmPassword;
    private Button btnRegister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // sesuaikan dengan nama file XML kamu

        mAuth = FirebaseAuth.getInstance();

        // Hubungkan View ke ID XML
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        // Validasi input
        if (TextUtils.isEmpty(username)) {
            edtUsername.setError("Username tidak boleh kosong");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Password tidak boleh kosong");
            return;
        }
        if (password.length() < 6) {
            edtPassword.setError("Minimal 6 karakter");
            return;
        }
        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("Password tidak sama");
            return;
        }

        // Karena Firebase Auth butuh email, kita buat email otomatis
        String generatedEmail = username + "@app.com";

        // Daftarkan user ke Firebase Auth
        mAuth.createUserWithEmailAndPassword(generatedEmail, password)
                .addOnCompleteListener(this::handleRegisterResult);
    }

    private void handleRegisterResult(Task<AuthResult> task) {
        if (task.isSuccessful()) {

            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // Simpan data user ke Realtime Database
            FirebaseDatabase.getInstance().getReference("users")
                    .child(uid)
                    .setValue(new UserModel(uid,
                            edtUsername.getText().toString()))
                    .addOnCompleteListener(t -> {
                        if (t.isSuccessful()) {
                            Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, WelcomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            Toast.makeText(this, "Registrasi gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Model user untuk disimpan ke Firebase
    public static class UserModel {
        public String uid;
        public String username;

        public UserModel() {
        }

        public UserModel(String uid, String username) {
            this.uid = uid;
            this.username = username;
        }
    }
}
