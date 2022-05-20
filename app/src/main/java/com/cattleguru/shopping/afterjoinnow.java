package com.cattleguru.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class afterjoinnow extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText t1;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterjoinnow);
        t1=(EditText)findViewById(R.id.t1);
        ccp=(CountryCodePicker)findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(t1);
        b1=(Button)findViewById(R.id.b1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }
    private void CreateAccount(){
       // String name = InputName.getText().toString();
        String phone = t1.getText().toString();
        //String password = InputPassword.getText().toString();
//        if (TextUtils.isEmpty(name))
//        {
//            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
//        }
        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
//        else if (TextUtils.isEmpty(password))
//        {
//            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
//        }
        else
        {
            ValidatephoneNumber(phone);
        }

    }

    private void ValidatephoneNumber( final String phone) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("Users").child(phone).exists())){

                    Toast.makeText(afterjoinnow.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(afterjoinnow.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(afterjoinnow.this, com.cattleguru.shopping.MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent= new Intent(afterjoinnow.this,manageotp.class);
                    intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}