package com.example.aplikasiloginmodern;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseHelper {

    private final DatabaseReference usersRef;
    private final DatabaseReference pantiRef;

    public DatabaseHelper() {
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        pantiRef = FirebaseDatabase.getInstance().getReference("panti");
    }

    /* ======================== USER LOGIN / REGISTER ======================== */

    public interface LoginCallback {
        void onSuccess(boolean success);
        void onFailure(Exception e);
    }

    public interface RegisterCallback {
        void onComplete(boolean success);
    }

    public void registerUser(String username, String password, RegisterCallback callback) {
        String uid = FirebaseAuth.getInstance().getUid();

        if (uid == null) {
            callback.onComplete(false);
            return;
        }

        User user = new User(uid, username, password);

        FirebaseDatabase.getInstance().getReference("users")
                .child(uid)
                .setValue(user)
                .addOnCompleteListener(task -> callback.onComplete(task.isSuccessful()));
    }


    public void loginUser(String username, String password, LoginCallback callback) {
        usersRef.orderByChild("username").equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            callback.onSuccess(false);
                            return;
                        }

                        for (DataSnapshot data : snapshot.getChildren()) {
                            User user = data.getValue(User.class);
                            if (user != null && user.getPassword().equals(password)) {
                                callback.onSuccess(true);
                                return;
                            }
                        }

                        callback.onSuccess(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onFailure(error.toException());
                    }
                });
    }

    /* ======================== PANTI CALLBACK ======================== */

    public interface PantiCallback {
        void onSuccess(Panti panti);
        void onFailure(DatabaseError error);
    }

    public interface PantiListCallback {
        void onSuccess(ArrayList<Panti> list);
        void onFailure(Exception e);
    }


    /* ======================== GET 1 PANTI ======================== */

    public void getPantiById(String id, PantiCallback callback) {
        pantiRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    callback.onSuccess(null);
                    return;
                }

                Panti p = snapshot.getValue(Panti.class);
                callback.onSuccess(p);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error);
            }
        });
    }

    /* ======================== GET SEMUA PANTI ======================== */

    public void getAllPanti(PantiListCallback callback) {
        pantiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Panti> list = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Panti p = data.getValue(Panti.class);
                    if (p != null) list.add(p);
                }
                callback.onSuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(new Exception(error.getMessage()));
            }
        });
    }


}
