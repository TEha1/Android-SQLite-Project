package com.abdu.teha.mycompanytesting03.Company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.abdu.teha.mycompanytesting03.R;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Company;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.DBConnection;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Getters;

import java.util.ArrayList;


public class EditProfile extends AppCompatActivity  {

    DBConnection dbConnection;
    Company company;
    Getters getter;
    EditText editComName, editComEmail,editComPassword, editComAddress, editComPhone, editComWebsite;
    String name, email, password, address, phone, website, spinnerValue = "";
    String name01, email01, password01, address01, phone01, website01;
    Button submitUpdate;
    Spinner productsSpinner;
    ArrayAdapter<String> dataAdapter;
    ArrayList<Integer> id_list;
    ArrayList<String> names_list;
    Intent intent;

    int company_id, selected_id;
    boolean EditTextEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        setTitle("Edit Profile");

        editComName = (EditText)findViewById(R.id.companyName02);
        editComEmail = (EditText)findViewById(R.id.companyEmail02);
        editComPassword = (EditText)findViewById(R.id.companyPassword02);
        editComAddress = (EditText)findViewById(R.id.companyAddress01);
        editComPhone = (EditText)findViewById(R.id.companyPhone01);
        editComWebsite = (EditText)findViewById(R.id.companyWebsite01);
        productsSpinner = (Spinner)findViewById(R.id.productSpinner01);
        submitUpdate = (Button)findViewById(R.id.submitUpdate);
        dbConnection = new DBConnection(this);
        names_list = new ArrayList<>();
        id_list = new ArrayList<>();


        intent = getIntent();
        if( intent.getExtras() != null) {
            String myData = intent.getStringExtra("companyID");
            company_id = Integer.parseInt(myData);
        }
        ///////////////////////////////////////////////////////////////////////////
        boolean getTypes = checkGetTypes();
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
        /////////////////////////////////////////////////////////////////////////////////////
        fill_hint();
        submitUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextStatus();
                UpdataCompany();
            }
        });
    }


    //Fill hints by Previous Data
    private void fill_hint() {
        boolean b = get_company_info();
        if (b) {
            editComName.setText(name01);
            editComEmail.setText(email01);
            editComPassword.setText(password01);
            editComAddress.setText(address01);
            editComPhone.setText(phone01);
            editComWebsite.setText(website01);
        }
        else
            Toast.makeText(this, "can't fill hint" , Toast.LENGTH_SHORT).show();
    }
    private boolean get_company_info() {
        company = new Company();
        company.setCid(company_id);
        boolean b = dbConnection.get_Company_Info(company);
        if (b) {
            name01 = company.getCname();
            email01 = company.getCemail();
            password01 = company.getCpass();
            address01 = company.getCaddress();
            phone01 = company.getCphone();
            website01 = company.getCwebsite();
            return true;
        }
        return false;
    }
    //////////////////////////////////////////////////////////////////

    //Fill Spinner
    public boolean checkGetTypes() {
        getter = new Getters();
        boolean type = dbConnection.get_all_product_type(getter);
        if (type) {
            id_list = getter.getTypes_id();
            names_list = getter.getTypes_name();
            return true;
        }
        return false;
    }
    //////////////////////////////////////////////////////////////////////////

    //Update Data
    private void UpdataCompany() {
        if(EditTextEmpty) {
            company = new Company();
            company.setCid(company_id);
            company.setCname(name);
            company.setCemail(email);
            company.setCpass(password);
            company.setCaddress(address);
            company.setCphone(phone);
            company.setCwebsite(website);
            company.setProduct_type_id(selected_id);
            boolean updateBool = dbConnection.companySignUp(company, 2);
            if (updateBool) {
                ClearEditText();
                intent = new Intent(EditProfile.this, CompanyProfile.class);
                intent.putExtra("companyID", Integer.toString(company_id));
                startActivity(intent);
                Toast.makeText(this, "successfully Updated", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "failed to update", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this,"Please Fill All The Required Fields.", Toast.LENGTH_SHORT).show();
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////

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
    public void ClearEditText(){
        editComName.getText().clear();
        editComEmail.getText().clear();
        editComPassword.getText().clear();
        editComAddress.getText().clear();
        editComPhone.getText().clear();
        editComWebsite.getText().clear();
    }
    /////////////////////////////////////////////////////////////////////////


}
