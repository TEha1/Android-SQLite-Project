package com.abdu.teha.mycompanytesting03.Company;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abdu.teha.mycompanytesting03.AdminControl;
import com.abdu.teha.mycompanytesting03.R;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.*;

/**
 * Created by TEha on 4/16/2018.
 */

public class CompanyLogin extends Activity {

    Company company;
    DBConnection dbConnection;
    EditText editEmail, editPassword;
    String Email, Password;
    Button btnLogin, btnRegister;
    Intent intent;
    int companyID;
    boolean EditTextEmpty, loginBool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_company);
        dbConnection = new DBConnection(this);
        editEmail = (EditText)findViewById(R.id.companyEmail);
        editPassword = (EditText)findViewById(R.id.companyPassword);
        btnLogin = (Button)findViewById(R.id.loginCom);
        btnRegister = (Button)findViewById(R.id.registCom);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextStatus();
                Login();
            }
        });

        ClearEditText();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CompanyLogin.this, CompanyRegisting.class);
                startActivity(intent);
            }
        });


    }

    public void CheckEditTextStatus(){
        Email = editEmail.getText().toString() ;
        Password = editPassword.getText().toString();
        if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)){
            EditTextEmpty = false;
        }
        else {
            EditTextEmpty = true;
        }
    }
    public boolean CheckAdmin(){
        Email = editEmail.getText().toString() ;
        Password = editPassword.getText().toString();
        if(Email.equals("admin") && Password.equals("admin")){
            return true;
        }
        else {
            return false;
        }
    }

    private void Login() {
        if(EditTextEmpty) {
            boolean b = CheckAdmin();
            if (b) {
                intent = new Intent(CompanyLogin.this, AdminControl.class);
                startActivity(intent);
                ClearEditText();
            }
            else {
                company = new Company();
                company.setCemail(Email);
                company.setCpass(Password);
                loginBool = dbConnection.CompanyLogIn(company);
                companyID = company.getCid();
                if (loginBool) {
                    intent = new Intent(CompanyLogin.this, CompanyProfile.class);
                    intent.putExtra("companyID", Integer.toString(companyID));
                    startActivity(intent);
                    ClearEditText();
                }
                else {
                    Toast.makeText(this, "failed to log in", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else {
            Toast.makeText(this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();
        }
    }


    public void ClearEditText(){
        editEmail.getText().clear();
        editPassword.getText().clear();
    }

}
