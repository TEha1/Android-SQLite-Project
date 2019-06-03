package com.abdu.teha.mycompanytesting03.Company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.abdu.teha.mycompanytesting03.R;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Company;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.DBConnection;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Getters;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Product;

import java.util.ArrayList;

public class DeleteProduct extends AppCompatActivity {

    ListView listView;
    Button confirmDelete;
    DeleteProductAdapter adapter;
    ArrayList<String> products_list;
    ArrayList<Integer> products_list_id;
    DBConnection dbConnection;
    Product product;
    Company company;
    Getters getter;
    Intent intent;
    int company_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_product);
        setTitle("Delete Product");
        listView = (ListView)findViewById(R.id.companyProducts);
        confirmDelete = (Button)findViewById(R.id.confirmDelete);
        dbConnection = new DBConnection(this);
        products_list = new ArrayList<>();
        products_list_id = new ArrayList<>();
        /////////////////////////////////////////////////////////////////////////
        intent = getIntent();
        if( intent.getExtras() != null) {
            String myData = intent.getStringExtra("companyID");
            company_id = Integer.parseInt(myData);
            //Toast.makeText(this, " > "+ company_id, Toast.LENGTH_SHORT).show();
        }
        //////////////////////////////////////////////////////////////////////////
        if (get_products()) {
            String[] product_names = new String[products_list.size()];
            int[] product_id = new int[products_list_id.size()];
            product_names = products_list.toArray(product_names);
            for (int i=0; i<products_list_id.size(); i++) {
                product_id[i] = products_list_id.get(i);
            }
            //Toast.makeText(this, " > "+ product_names[0], Toast.LENGTH_SHORT).show();
            adapter = new DeleteProductAdapter(getApplicationContext(), R.layout.custom_delete_product_layout);
            listView.setAdapter(adapter);
            for (int i = 0; i < products_list.size(); i++) {
                product = new Product();
                product.setPname(product_names[i]);
                product.setPro_ID(product_id[i]);
                adapter.add(product);
            }
            confirmDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(DeleteProduct.this, CompanyProfile.class);
                    intent.putExtra("companyID", Integer.toString(company_id));
                    startActivity(intent);
                    Toast.makeText(getBaseContext(), " id = " + company_id, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, " Empty ", Toast.LENGTH_SHORT).show();
        }

    }

    //Get Products Name
    private boolean get_products() {
        company = new Company();
        getter = new Getters();
        company.setCid(company_id);
        boolean b = dbConnection.get_Products(company, getter);
        if (b) {
            products_list = getter.getProducts_name();
            products_list_id = getter.getProd_id();
            return true;
        }
        return false;
    }


}
