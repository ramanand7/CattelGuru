package com.cattleguru.shopping;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cattleguru.shopping.Model.Users;
import com.cattleguru.shopping.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button CreateAccountButton;
    private EditText InputName, InputAddress;
    private TextView InputPhoneNumber;
    private ProgressDialog loadingBar;
    String phonenumber ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputAddress = (EditText) findViewById(R.id.register_address_input);
        InputPhoneNumber =  findViewById(R.id.register_phone_number_input);

        loadingBar = new ProgressDialog(this);
        phonenumber=getIntent().getStringExtra("mobile").toString();
        InputPhoneNumber.setText(phonenumber);
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }
    private void CreateAccount(){
        String name = InputName.getText().toString();
        String phone = phonenumber;
        String address = InputAddress.getText().toString();
        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(address))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(name, phone, address);
        }

    }

    private void ValidatephoneNumber(final String name, final String phone,final String address) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("address", address);
                    userdataMap.put("name", name);
                    Users user = new Users(name, phone , null, address);
                    Prevalent.currentOnlineUser= user ;
                    RootRef.child("Users").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }
                else {
                    Toast.makeText(RegisterActivity.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    //Toast.makeText(RegisterActivity.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
