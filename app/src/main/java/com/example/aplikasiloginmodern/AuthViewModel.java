package com.example.aplikasiloginmodern;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AuthViewModel extends AndroidViewModel {

    private final DatabaseHelper databaseHelper;
    private final MutableLiveData<Boolean> loginStatus = new MutableLiveData<>();
    private final MutableLiveData<Boolean> registrationStatus = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application) {
        super(application);
        databaseHelper = new DatabaseHelper();
    }

    public LiveData<Boolean> getLoginStatus() { return loginStatus; }
    public LiveData<Boolean> getRegistrationStatus() { return registrationStatus; }

    /* ============================= LOGIN ============================= */
    public void login(String username, String password) {
        databaseHelper.loginUser(username, password, new DatabaseHelper.LoginCallback() {
            @Override
            public void onSuccess(boolean success) {
                loginStatus.postValue(success);
            }

            @Override
            public void onFailure(Exception e) {
                loginStatus.postValue(false);
            }
        });
    }

    /* ============================= REGISTER ============================= */
    public void register(String username, String password) {
        databaseHelper.registerUser(username, password, new DatabaseHelper.RegisterCallback() {
            @Override
            public void onComplete(boolean success) {
                registrationStatus.postValue(success);
            }
        });
    }
}
