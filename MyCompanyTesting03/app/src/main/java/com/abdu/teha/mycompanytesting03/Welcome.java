package com.abdu.teha.mycompanytesting03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.abdu.teha.mycompanytesting03.User.*;
import com.abdu.teha.mycompanytesting03.Company.*;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.*;

import java.util.ArrayList;

public class Welcome extends AppCompatActivity {
    DBConnection dbConnection;
    Company company;
    Product product;
    Getters getters, getters2;

    int proID =1;
    String Name = "G1080";
    int company_id = 1;

    boolean login, signup, addProduct, addPA, addType, getTypes, getAreas;
    ArrayList<String> types_names_list, areas_names_list;
    ArrayList<Integer> types_id_list, areas_id_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        dbConnection = new DBConnection(this);
        getters2 = new Getters();
        //company_sign_up();
        //company_log_in();
        /*if (add_product()) {
            Toast.makeText(this, "Added" , Toast.LENGTH_SHORT).show();
            get_product_id();
            Toast.makeText(this, "" + Integer.toString(proID), Toast.LENGTH_SHORT).show();
        }*/
        //Add_Prod_Area(15);
        //add_product_type();
        //get_types();
        //add_product_area();
        //get_product_areas();
        //String[] s1 = area_items();
        //Toast.makeText(this, s1[s1.length-1], Toast.LENGTH_LONG).show();
        //delete_product();
    }

    public void delete_product() {
        product = new Product();
        product.setPro_ID(4);
        boolean b = dbConnection.DeleteProduct(product);
        if (b) {
            Toast.makeText(this, "Deleted" , Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "failed to delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void Add_Prod_Area(int area_id) {
        product = new Product();
        product.setProA_ID(area_id);
        product.setPro_ID(proID);
        boolean b = dbConnection.add_prod_area(product);
        if (b) {
            Toast.makeText(this, "successfully added the PType" , Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "failed to add the PType", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean add_product() {
        product = new Product();
        product.setPname(Name);
        product.setPdescription("A new modern laptop");
        product.setPprice("5200");
        product.setPtypeID(2);
        product.setCompanyID(2);
        addType = dbConnection.add_new_product(product);
        return addType;
    }
    public void get_product_id() {
        product = new Product();
        company = new Company();
        product.setPname(Name);
        company.setCid(company_id);
        boolean b = dbConnection.get_product_id(product, company);
        if (b) {
            proID = product.getPro_ID();
            //Toast.makeText(this, "" + Integer.toString(proID), Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(this, "failed to get the Products", Toast.LENGTH_SHORT).show();
        }

    }


    public void add_product_type() {
        product = new Product();
        product.setTypeName("Cloths");
        addType = dbConnection.add_product_type(product);
        if (addType) {
            Toast.makeText(this, "successfully added the PType" , Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "failed to add the PType", Toast.LENGTH_SHORT).show();
        }
    }
    public void company_sign_up() {
        company = new Company();
        company.setCname("lenovo");
        company.setCemail("lenovo@g.com");
        company.setCpass("aaaaaaaaaaaa");
        company.setCaddress("6 Qctober, Cairo");
        company.setCphone("01141336724");
        company.setCwebsite("http://toshiba.com");
        company.setProduct_type_id(9);
        signup = dbConnection.companySignUp(company, 1);
        if (signup) {
            Toast.makeText(this, "successfully signed up, " , Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "failed to log in ", Toast.LENGTH_SHORT).show();
        }
    }

    public void company_log_in() {
        company = new Company();
        company.setCemail("lenovo@g.com");
        company.setCpass("aaaaaaaaaaaa");
        login = dbConnection.CompanyLogIn(company);
        if (login) {
            Toast.makeText(this, "successfully logged in" , Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "failed to log in", Toast.LENGTH_SHORT).show();
        }
    }

    public void add_product_area() {
        product = new Product();
        product.setAreaName("Cairo");
        addPA = dbConnection.add_area(product);
        if (addPA) {
            Toast.makeText(this, "successfully added the PA" , Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "failed to add the PA", Toast.LENGTH_SHORT).show();
        }
    }
    public void AsCompany(View view) {
        Intent intent = new Intent(Welcome.this, CompanyLogin.class);
        startActivity(intent);
    }
    public void AsUser(View view) {
        Intent intent = new Intent(Welcome.this, RecentProduccts.class);
        startActivity(intent);
    }
    public void AsAdmin(View view) {
        Intent intent = new Intent(Welcome.this, CompanyLogin.class);
        startActivity(intent);
    }



}
