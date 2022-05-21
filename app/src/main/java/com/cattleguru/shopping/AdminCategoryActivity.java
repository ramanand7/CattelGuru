package com.cattleguru.shopping;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class AdminCategoryActivity extends AppCompatActivity {
    private Button LogoutBtn, CheckNewOrdersBtn, CheckShippedOrdersBtn , CheckCancelledOrdersBtn, addNewProductBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        CheckNewOrdersBtn = (Button) findViewById(R.id.check_orders_btn);


        CheckNewOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryActivity.this,AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });

        CheckShippedOrdersBtn = (Button) findViewById(R.id.check_shipped_orders_btn);


        CheckShippedOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryActivity.this,AdminShippedOrdersActivity.class);
                startActivity(intent);
            }
        });

        CheckCancelledOrdersBtn = (Button) findViewById(R.id.check_cancelled_orders_btn);


        CheckCancelledOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryActivity.this,AdminCancelledOrdersActivity.class);
                startActivity(intent);
            }
        });



        addNewProductBtn = (Button)findViewById(R.id.add_new_product);
        addNewProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, com.cattleguru.shopping.AdminAddNewProductActivity.class);
                intent.putExtra("category", "Some Material");
                startActivity(intent);
            }
        });
    }
}
