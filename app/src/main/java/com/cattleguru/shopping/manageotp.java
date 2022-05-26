package com.cattleguru.shopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cattleguru.shopping.Model.Users;
import com.cattleguru.shopping.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;

import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class manageotp extends AppCompatActivity
{
   EditText t2;
   View b2;
   String phonenumber, parentDatabaseName;
   String otpid;
   FirebaseAuth mAuth;
    private String parentDbName = "Users";
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_otp);

        phonenumber=getIntent().getStringExtra("mobile").toString();
        parentDatabaseName=getIntent().getStringExtra("parentDatabaseName").toString();
        phonenumber = "+91"+phonenumber;
        t2=(EditText)findViewById(R.id.t2);
        b2= findViewById(R.id.b2);
        mAuth=FirebaseAuth.getInstance();

        //Paper.init(this);
        loadingBar = new ProgressDialog(this);
        FirebaseApp.initializeApp(this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(SafetyNetAppCheckProviderFactory.getInstance());
        initiateotp();
        Paper.init(this);
        parentDbName = "Users";
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(t2.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),"Blank Field can not be processed",Toast.LENGTH_LONG).show();
                else if(t2.getText().toString().length()!=6)
                    Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_LONG).show();
                else
                {
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid,t2.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });
    }

    private void initiateotp()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
                    {
                        otpid=s;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
                    {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });        // OnVerificationStateChangedCallbacks

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            loadingBar.setTitle("OTP Captured");
                            loadingBar.setMessage("Please wait...");
                            loadingBar.setCanceledOnTouchOutside(false);
                            loadingBar.show();
                            String phone = phonenumber.substring(3);
                            Paper.book().write("mobile",phone);
                            Paper.book().write("ParentDatabaseName",parentDatabaseName);
                            ValidatephoneNumber();

                        } else {
                            Toast.makeText(getApplicationContext(),"Signin Code Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void ValidatephoneNumber() {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child(parentDatabaseName).child(phonenumber).exists())){
                    Users usersData = dataSnapshot.child(parentDatabaseName).child(phonenumber).getValue(Users.class);
                    Paper.book().write("currentOnlineUser",usersData);
                    Prevalent.currentOnlineUser = usersData;
                    Toast.makeText(manageotp.this, "This " + phonenumber + " already exists.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(manageotp.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();
                    if(parentDatabaseName.equals("Users")) {
                        Intent intent = new Intent(manageotp.this, com.cattleguru.shopping.HomeActivity.class);
                        Paper.book().write(Prevalent.UserPhoneKey,phonenumber);
                        Paper.book().write("ParentDatabaseName" ,parentDbName);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Intent intent = new Intent(manageotp.this, AdminHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    Intent intent= new Intent(manageotp.this,RegisterActivity.class);
                    intent.putExtra("mobile",phonenumber);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void AllowAccess(final String phone)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists()){

                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if (usersData.getPhone().equals(phone))
                    {

                        Toast.makeText(manageotp.this, "Please wait, you are already logged in...", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(manageotp.this, HomeActivity.class);
                        Prevalent.currentOnlineUser = usersData;
                        startActivity(intent);

                    }
                }
                else {
                    Toast.makeText(manageotp.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}