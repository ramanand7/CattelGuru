package com.cattleguru.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cattleguru.shopping.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class afterjoinnow extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText t1;
    Button b1;
    private TextView AdminLink, NotAdminLink;
    private String parentDatabaseName = "Users";
    private CheckBox chkBoxRememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.after_join_now);

        Paper.init(this);
        AdminLink = (TextView) findViewById(R.id.admin_panel_link);
        NotAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);

        final DatabaseReference RootRef;
        String phone = Paper.book().read(Prevalent.UserPhoneKey);
        parentDatabaseName = Paper.book().read("ParentDatabaseName");
        if(parentDatabaseName== null)
            parentDatabaseName= "Users";
        t1=(EditText)findViewById(R.id.t1);
        t1.setText(phone);
        b1=(Button)findViewById(R.id.b1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(afterjoinnow.this, manageotp.class);
                intent.putExtra("mobile",t1.getText().toString());
                intent.putExtra("parentDatabaseName",parentDatabaseName);
                startActivity(intent);
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                b1.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDatabaseName = "Admins";
            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                b1.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDatabaseName = "Users";
            }
        });


        //RootRef = FirebaseDatabase.getInstance().getReference();
//        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.child(parentDatabaseName).child(phone).exists()){
//
//                    Users usersData = dataSnapshot.child(parentDatabaseName).child(phone).getValue(Users.class);
//                    if (usersData.getPhone().equals(phone))
//                    {
//                        if (usersData.getPassword().equals(password))
//                        {
//                            if(parentDatabaseName.equals("Admins"))
//                            {
//                                Toast.makeText(MainActivity.this, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
//                                loadingBar.dismiss();
//
//                                Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
//                                startActivity(intent);
//                            }
//                            else if (parentDatabaseName.equals("Users")){
//                                Toast.makeText(MainActivity.this, "Please wait, you are already logged in...", Toast.LENGTH_SHORT).show();
//                                loadingBar.dismiss();
//
//                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                                Prevalent.currentOnlineUser = usersData;
//                                startActivity(intent);
//                            }
//
//                        }
//                        else {
//                            loadingBar.dismiss();
//                            Toast.makeText(MainActivity.this,"Password is incorrect",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//                else {
//                    Toast.makeText(MainActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
//                    loadingBar.dismiss();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

//        Paper.init(this);
//        try
//        {
//            this.getSupportActionBar().hide();
//        }
//        catch (NullPointerException e){}
//
//        t1=(EditText)findViewById(R.id.t1);
//        t1.setText(phone);
//        ccp=(CountryCodePicker)findViewById(R.id.ccp);
//        ccp.registerCarrierNumberEditText(t1);
//        b1=(Button)findViewById(R.id.b1);
//
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CreateAccount();
//            }
//        });
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