package com.abdu.teha.mycompanytesting03.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

import com.abdu.teha.mycompanytesting03.R;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Company;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.DBConnection;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Getters;

import java.util.ArrayList;

public class RecentProduccts extends AppCompatActivity {

    RecentProductData recentProductData;
    RecentProductAdapter adapter;
    ListView recentListView;
    DBConnection dbConnection;
    Company company;
    Getters getter;
    ArrayList<Integer> Products_id;
    ArrayList<String> Products_names ;
    ArrayList<String> Products_desc ;
    ArrayList<String> Products_price ;
    ArrayList<Integer> Products_type_id ;
    ArrayList<Integer> Company_product_id;
    ArrayList<String> company_names ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_produccts);
        setTitle("Recent Products");
        dbConnection = new DBConnection(this);
        recentListView = (ListView)findViewById(R.id.recentProduct);
        Products_id = new ArrayList<>();
        Products_names = new ArrayList<>();
        Products_desc = new ArrayList<>();
        Products_price = new ArrayList<>();
        Products_type_id = new ArrayList<>();
        Company_product_id = new ArrayList<>();
        company_names = new ArrayList<>();


        if (get_all_products()) {
            int[] product_id = new int[Products_id.size()];
            String[] product_names = new String[Products_names.size()];
            int[] company_product_id = new int[Company_product_id.size()];
            product_names = Products_names.toArray(product_names);
            for (int i = 0; i < Products_id.size(); i++) {
                product_id[i] = Products_id.get(i);
                company_product_id[i] = Company_product_id.get(i);
                get_company_name(company_product_id[i]);
            }
            String[] companyName = new String[company_names.size()];
            companyName = company_names.toArray(companyName);
            adapter = new RecentProductAdapter(getApplicationContext(), R.layout.custom_recent_product_layout);
            recentListView.setAdapter(adapter);
            for (int i = 0; i < Products_id.size(); i++) {
                recentProductData = new RecentProductData();
                recentProductData.setRecentPname(product_names[i]);
                recentProductData.setRecentCname(companyName[i]);
                recentProductData.setRecentProd_id(product_id[i]);
                recentProductData.setRecentCom_id(company_product_id[i]);
                adapter.add(recentProductData);
            }
        }
    }

    //Get All Company Name
    private boolean get_company_name(int id) {
        company = new Company();
        company.setCid(id);
        boolean b = dbConnection.get_Company_Info(company);
        if (b) {
            company_names.add(company.getCname());
            return true;
        }
        return false;
    }

    //Get Products Name
    private boolean get_all_products() {
        getter = new Getters();
        boolean b = dbConnection.get_all_products(getter);
        if (b) {
            Products_id = getter.getProd_id();
            Products_names = getter.getProducts_name();
            Products_desc= getter.getProducts_desc();
            Products_price= getter.getProducts_price();
            Products_type_id= getter.getProducts_type_id();
            Company_product_id = getter.getCompany_Product_id();
            return true;
        }
        return false;
    }



}
