package com.cattleguru.shopping;

import android.content.Intent;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cattleguru.shopping.Model.Products;
import com.cattleguru.shopping.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    private EditText nameEditText,phoneEditText,addressEditText,cityEditText;
    private Button confirmOrderBtn;
    private String totalAmount = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price = Rs. "+totalAmount,Toast.LENGTH_SHORT).show();
        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText =(EditText) findViewById(R.id.shippment_name);
        phoneEditText =(EditText) findViewById(R.id.shippment_phone_number);
        addressEditText =(EditText) findViewById(R.id.shippment_address);
        cityEditText =(EditText) findViewById(R.id.shippment_city);
        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });
    }

    private void Check() {
        if(TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Full Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Phone Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Valid Address.",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cityEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your City Name",Toast.LENGTH_SHORT).show();
        }
        else {

            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {
        final String saveCurrentTime,saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd. yyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

//        HashMap<String, Object> ordersMap = new HashMap<>();
//        ordersMap.put("totalAmount",totalAmount);
//        ordersMap.put("name",nameEditText.getText().toString());
//        ordersMap.put("phone",phoneEditText.getText().toString());
//        ordersMap.put("address",addressEditText.getText().toString());
//        ordersMap.put("city",cityEditText.getText().toString());
//        ordersMap.put("date",saveCurrentDate);
//        ordersMap.put("time",saveCurrentTime);
//        ordersMap.put("state", "Not Shipped");
//        ordersMap.put("products",new HashMap<String, Object>());
//        final DatabaseReference ordersRef= FirebaseDatabase.getInstance().getReference().child("Orders");
//        final DatabaseReference childRef = ordersRef.push();
//        childRef.setValue(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(ConfirmFinalOrderActivity.this,"Your final Order has been placed successfully.",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        final DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Cart List")
//                .child("User view").child(Prevalent.currentOnlineUser.getPhone()).child("Products");
//        cartRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                    Toast.makeText(ConfirmFinalOrderActivity.this,"Not successful.",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Log.e("firebase", String.valueOf(task.getResult().getValue()));
//                    Toast.makeText(ConfirmFinalOrderActivity.this,"successful.",Toast.LENGTH_SHORT).show();
//                    Toast.makeText(ConfirmFinalOrderActivity.this, task.getResult().toString(), Toast.LENGTH_SHORT ).show();
//                    //task.getResult().getChildren().iterator();
//                    for (DataSnapshot child : task.getResult().getChildren()) {
////                        Products product = child.getValue(Products.class);
//                        GenericTypeIndicator<Map<String, Object>> to = new
//                                GenericTypeIndicator<Map<String, Object>>() {};
//                        final Map<String, Object> product = child.getValue(to);
//                        Toast.makeText(ConfirmFinalOrderActivity.this, product.get("pname").toString(),Toast.LENGTH_SHORT).show();
//                        Toast.makeText(ConfirmFinalOrderActivity.this, product.get("price").toString(),Toast.LENGTH_SHORT).show();
////                        Log.e("child", product.getDescription());
////                        Log.e("child", product.getPname());
//                        childRef.child("products").setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful())
//                                    Toast.makeText(ConfirmFinalOrderActivity.this, "Products were updated",Toast.LENGTH_SHORT).show();
//                                else
//                                    Log.e("firebase", "Error getting data", task.getException());
//                            }
//                        });
//                    }
//                   // childRef.child("Products").setValue(task.getResult().)
//                }
//            }
//        });







        Intent intent=new Intent(ConfirmFinalOrderActivity.this,CreateOrderActivity.class);
        intent.putExtra("Total Amount", "100");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK );
        finish();
        startActivity(intent);

//        final DatabaseReference ordersRef= FirebaseDatabase.getInstance().getReference()
//                .child("Orders").push()
//                .child(Prevalent.currentOnlineUser.getPhone());
//        HashMap<String, Object> ordersMap = new HashMap<>();
//        ordersMap.put("totalAmount",totalAmount);
//        ordersMap.put("name",nameEditText.getText().toString());
//        ordersMap.put("phone",phoneEditText.getText().toString());
//        ordersMap.put("address",addressEditText.getText().toString());
//        ordersMap.put("city",cityEditText.getText().toString());
//        ordersMap.put("date",saveCurrentDate);
//        ordersMap.put("time",saveCurrentTime);
//        ordersMap.put("state", "Not Shipped");
//        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    FirebaseDatabase.getInstance().getReference()
//                            .child("Cart List")
//                            .child("User view")
//                            .child(Prevalent.currentOnlineUser.getPhone())
//                            .removeValue()
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()){
//                                        Toast.makeText(ConfirmFinalOrderActivity.this,"Your final Order has been placed successfully.",Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                }
//                            });
//                }
//            }
//        });


    }
}
