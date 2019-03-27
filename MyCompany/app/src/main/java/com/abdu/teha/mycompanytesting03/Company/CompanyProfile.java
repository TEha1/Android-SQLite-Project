package com.abdu.teha.mycompanytesting03.Company;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.abdu.teha.mycompanytesting03.MyDialogFragment;
import com.abdu.teha.mycompanytesting03.R;
import com.abdu.teha.mycompanytesting03.User.RecentProduccts;
import com.abdu.teha.mycompanytesting03.Welcome;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.*;

import java.util.ArrayList;


public class CompanyProfile extends AppCompatActivity {

    DBConnection dbConnection;
    Company company;
    Getters getter;
    Intent intent;
    Button btnShowProducts, btnDelete;
    TextView companyName;
    ArrayList<String> products_list;
    String company_name;
    int company_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        setTitle("My Profile");
        companyName = (TextView)findViewById(R.id.companyName01);
        btnShowProducts = (Button)findViewById(R.id.showProducts);
        btnDelete = (Button)findViewById(R.id.delete_product02);
        products_list = new ArrayList<>();
        company_name = "";

        dbConnection = new DBConnection(this);

        intent = getIntent();
        if( intent.getExtras() != null) {
            String myData = intent.getStringExtra("companyID");
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CompanyProfile.this, DeleteProduct.class);
                intent.putExtra("companyID", Integer.toString(company_id));
                startActivity(intent);
            }
        });

        if(get_company_name())
            companyName.setText(company_name);

    }

    // Go to Edit Profile Page
    public void editProfile(View view) {
        intent = new Intent(CompanyProfile.this, EditProfile.class);
        intent.putExtra("companyID", Integer.toString(company_id));
        startActivity(intent);
    }

    // Go to Add Product Page
    public void addProduct(View view) {
        intent = new Intent(CompanyProfile.this, AddProduct.class);
        intent.putExtra("companyID", Integer.toString(company_id));
        startActivity(intent);
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

    private boolean get_company_name() {
        company = new Company();
        company.setCid(company_id);
        boolean b = dbConnection.get_Company_Info(company);
        if (b) {
            company_name = company.getCname();
            return true;
        }
        return false;
    }



    //Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Browse:
                intent = new Intent(CompanyProfile.this, RecentProduccts.class);
                startActivity(intent);
                break;
            case R.id.logout:
                intent = new Intent(CompanyProfile.this, Welcome.class);
                startActivity(intent);
                break;
        }
        return true;
    }


}
