package com.cattleguru.shopping;


import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class AdminHomeActivity extends AppCompatActivity {
    private Button LogoutBtn, CheckNewOrdersBtn, CheckShippedOrdersBtn , CheckCancelledOrdersBtn, addNewProductBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent= new Intent(AdminHomeActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        CheckNewOrdersBtn = (Button) findViewById(R.id.check_orders_btn);


        CheckNewOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHomeActivity.this,AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });

        CheckShippedOrdersBtn = (Button) findViewById(R.id.check_shipped_orders_btn);


        CheckShippedOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHomeActivity.this,AdminShippedOrdersActivity.class);
                startActivity(intent);
            }
        });

        CheckCancelledOrdersBtn = (Button) findViewById(R.id.check_cancelled_orders_btn);


        CheckCancelledOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHomeActivity.this,AdminCancelledOrdersActivity.class);
                startActivity(intent);
            }
        });



        addNewProductBtn = (Button)findViewById(R.id.add_new_product);
        addNewProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminHomeActivity.this, com.cattleguru.shopping.AdminAddNewProductActivity.class);
                intent.putExtra("category", "Some Material");
                startActivity(intent);
            }
        });
    }
}
