package com.abdu.teha.mycompanytesting03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abdu.teha.mycompanytesting03.sqlDBclasses.DBConnection;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Product;

public class AdminControl extends AppCompatActivity {

    DBConnection dbConnection;
    Product product;
    EditText editType, editArea;
    Button btnType, btnArea, submit_admin;
    String type, area;
    boolean TypeEmpty, AreaEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_control);
        setTitle("Admin Control");
        editType = (EditText) findViewById(R.id.type);
        editArea = (EditText) findViewById(R.id.area);
        btnType = (Button) findViewById(R.id.get_type);
        btnArea = (Button) findViewById(R.id.get_area);
        submit_admin = (Button)findViewById(R.id.submit_admin);
        dbConnection = new DBConnection(this);

        btnType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextStatus();
                if (TypeEmpty) {
                    add_product_type();
                    ClearEditText();
                } else {
                    Toast.makeText(getBaseContext(), "please fill to add", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextStatus();
                if (AreaEmpty) {
                    add_product_area();
                    ClearEditText();
                } else {
                    Toast.makeText(getBaseContext(), "please fill to add", Toast.LENGTH_LONG).show();
                }
            }
        });
        submit_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControl.this, Welcome.class);
                startActivity(intent);
            }
        });

    }

    public void add_product_type() {
        product = new Product();
        product.setTypeName(type);
        boolean b = dbConnection.add_product_type(product);
        if (b) {
            Toast.makeText(this, "successfully added the PType" , Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "failed to add the PType", Toast.LENGTH_SHORT).show();
        }
    }

    public void add_product_area() {
        product = new Product();
        product.setAreaName(area);
        boolean b = dbConnection.add_area(product);
        if (b) {
            Toast.makeText(this, "successfully added the PA" , Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "failed to add the PA", Toast.LENGTH_SHORT).show();
        }
    }

    public void CheckEditTextStatus() {
        type = editType.getText().toString();
        area = editArea.getText().toString();
        if (TextUtils.isEmpty(type)) {
            TypeEmpty = false;
        } else if (!TextUtils.isEmpty(type)) {
            TypeEmpty = true;
        }

        if (TextUtils.isEmpty(area)) {
            AreaEmpty = false;
        } else if (!TextUtils.isEmpty(area)) {
            AreaEmpty = true;
        }
    }

    public void ClearEditText(){
        editType.getText().clear();
        editArea.getText().clear();
    }
}
