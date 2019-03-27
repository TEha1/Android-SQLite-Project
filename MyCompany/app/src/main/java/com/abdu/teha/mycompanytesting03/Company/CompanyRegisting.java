package com.abdu.teha.mycompanytesting03.Company;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.abdu.teha.mycompanytesting03.R;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.*;

import java.util.ArrayList;


public class CompanyRegisting extends Activity {

    DBConnection dbConnection;
    Company company;
    Getters getters;
    EditText editComName, editComEmail,editComPassword, editComAddress, editComPhone, editComWebsite;
    String name, email, password, address, phone, website, spinnerValue = "";
    Spinner productsSpinner;
    Button btnRegist;
    Intent intent;
    ArrayList<Integer> id_list;
    ArrayList<String> names_list;
    ArrayAdapter<String> dataAdapter;
    boolean EditTextEmpty, signupBool, getTypes;
    int selected_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registing_company);
        dbConnection = new DBConnection(this);
        getters = new Getters();
        names_list = new ArrayList<>();
        id_list = new ArrayList<>();

        editComName = (EditText)findViewById(R.id.companyName);
        editComEmail = (EditText)findViewById(R.id.companyEmail01);
        editComPassword = (EditText)findViewById(R.id.companyPassword01);
        editComAddress = (EditText)findViewById(R.id.companyAddress);
        editComPhone = (EditText)findViewById(R.id.companyPhone);
        editComWebsite = (EditText)findViewById(R.id.companyWebsite);
        productsSpinner = (Spinner)findViewById(R.id.productSpinner);
        btnRegist = (Button)findViewById(R.id.regist);
        /////////////////////////////////////////////////////////////////////////////////
        getTypes = checkGetTypes();
        if (getTypes) {
            dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names_list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            productsSpinner.setAdapter(dataAdapter);
            productsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    spinnerValue = productsSpinner.getSelectedItem().toString();
                    for (int i=0; i<names_list.size(); i++) {
                        if(spinnerValue.equals(names_list.get(i)) ) {
                            selected_id = id_list.get(i);
                        }
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
            });
        }

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextStatus();
                SignUp();
            }
        });

        ClearEditText();
    }


    public void CheckEditTextStatus(){
        name = editComName.getText().toString();
        email = editComEmail.getText().toString();
        password = editComPassword.getText().toString();
        address = editComAddress.getText().toString();
        phone = editComPhone.getText().toString();
        website = editComWebsite.getText().toString();
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(address) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(website) ){
            EditTextEmpty = false;
        }
        else {
            EditTextEmpty = true;
        }
    }
    private void SignUp() {
        if(EditTextEmpty) {
            company = new Company();
            company.setCname(name);
            company.setCemail(email);
            company.setCpass(password);
            company.setCaddress(address);
            company.setCphone(phone);
            company.setCwebsite(website);
            company.setProduct_type_id(selected_id);
            signupBool = dbConnection.companySignUp(company, 1);
            if (signupBool) {
                ClearEditText();
                intent = new Intent(CompanyRegisting.this, CompanyLogin.class);
                startActivity(intent);
                Toast.makeText(this, "successfully sign up", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "failed to sign up", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this,"Please Fill All The Required Fields.", Toast.LENGTH_SHORT).show();
        }
    }

    public void ClearEditText(){
        editComName.getText().clear();
        editComEmail.getText().clear();
        editComPassword.getText().clear();
        editComAddress.getText().clear();
        editComPhone.getText().clear();
        editComWebsite.getText().clear();
    }

    //Fill Spinner
    public boolean checkGetTypes() {
        getters = new Getters();
        boolean type = dbConnection.get_all_product_type(getters);
        if (type) {
            id_list = getters.getTypes_id();
            names_list = getters.getTypes_name();
            return true;
        }
        return false;
    }
    ////////////////////////////////////////////////////////////////////////////////


    public void profile01 (View view) {
        Intent intent = new Intent(CompanyRegisting.this, CompanyProfile.class);
        startActivity(intent);
    }
}