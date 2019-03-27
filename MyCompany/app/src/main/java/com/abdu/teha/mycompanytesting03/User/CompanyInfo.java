package com.abdu.teha.mycompanytesting03.User;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.abdu.teha.mycompanytesting03.MyDialogFragment;
import com.abdu.teha.mycompanytesting03.R;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Company;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.DBConnection;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Getters;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Product;

import java.util.ArrayList;


public class CompanyInfo extends AppCompatActivity {

    Intent intent;
    int company_id = 0;
    DBConnection dbConnection;
    Company company;
    Product product;
    Getters getter;
    TextView viewComName, viewComEmail, viewComAddress, viewComPhone, viewComWebsite, btnShowProducts;
    String name, email, address, phone, website;
    ArrayList<String> products_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_info);
        setTitle("Company Info");
        viewComName = (TextView) findViewById(R.id.companyName03);
        viewComEmail = (TextView) findViewById(R.id.companyEmail03);
        viewComAddress = (TextView)findViewById(R.id.companyAddress03);
        viewComPhone = (TextView)findViewById(R.id.companyPhone03);
        viewComWebsite = (TextView) findViewById(R.id.companyWebsite03);
        btnShowProducts = (TextView) findViewById(R.id.showProducts01);
        dbConnection = new DBConnection(this);
        products_list = new ArrayList<>();

        intent = getIntent();
        if( intent.getExtras() != null) {
            String myData = intent.getStringExtra("company_id");
            company_id = Integer.parseInt(myData);
            Toast.makeText(this, " > "+ company_id, Toast.LENGTH_SHORT).show();
        }

        if (get_products()) {
            btnShowProducts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getFragmentManager();
                    MyDialogFragment dialog = new MyDialogFragment() {
                        @Override
                        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                            return super.onCreateView(inflater, container, savedInstanceState);
                        }
                    };
                    dialog.listitems = products_list;
                    dialog.show(manager, "dialog");
                }
            });
        }

        fill_data();
    }

    private void fill_data() {
        boolean b;
        b = get_company_info();
        if (b) {
            viewComName.setText(name);
            viewComAddress.setText(address);
            viewComEmail.setText(email);
            viewComPhone.setText(phone);
            viewComWebsite.setText(website);
        }
        else
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
    }

    //Get Company Info
    private boolean get_company_info() {
        company = new Company();
        company.setCid(company_id);
        boolean b = dbConnection.get_Company_Info(company);
        if (b) {
            name = company.getCname();
            email = company.getCemail();
            address = company.getCaddress();
            phone = company.getCpass();
            website = company.getCwebsite();
            return true;
        }
        return false;
    }

    //Get Products Name
    private boolean get_products() {
        company = new Company();
        getter = new Getters();
        company.setCid(company_id);
        boolean b = dbConnection.get_Products(company, getter);
        if (b) {
            products_list = getter.getProducts_name();
            return true;
        }
        return false;
    }

}
