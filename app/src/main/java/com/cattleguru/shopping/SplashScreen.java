package com.cattleguru.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.cattleguru.shopping.Model.Users;
import com.cattleguru.shopping.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SplashScreen extends AppCompatActivity {
    int f = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        Paper.init(this);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);
        if (UserPhoneKey != "" && UserPasswordKey != "")
        {
            if (!TextUtils.isEmpty(UserPhoneKey)  &&  !TextUtils.isEmpty(UserPasswordKey))
            {
                //AllowAccess(UserPhoneKey, UserPasswordKey);
               f=2;
            }
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(f==1) {
                    sendToLogin();
                }
            }
        },2500);
    }
    private void sendToLogin()
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
//    private void AllowAccess(final String phone, final String password)
//    {
//        final DatabaseReference RootRef;
//        RootRef = FirebaseDatabase.getInstance().getReference();
//        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.child("Users").child(phone).exists()){
//
//                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);
//                    if (usersData.getPhone().equals(phone))
//                    {
//                        if (usersData.getPassword().equals(password))
//                        {
//                            Toast.makeText(SplashScreen.this, "Please wait, you are already logged in...", Toast.LENGTH_SHORT).show();
//
//
//                            Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
//                            Prevalent.currentOnlineUser = usersData;
//                            startActivity(intent);
//
//                        }
//                        else {
//
//                            Toast.makeText(SplashScreen.this,"Password is incorrect",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//                else {
//                    Toast.makeText(SplashScreen.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
}