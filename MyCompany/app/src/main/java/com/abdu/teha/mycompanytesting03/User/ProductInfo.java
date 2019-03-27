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
import android.widget.TextView;
import android.widget.Toast;

import com.abdu.teha.mycompanytesting03.MyDialogFragment;
import com.abdu.teha.mycompanytesting03.R;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Company;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.DBConnection;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Getters;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Product;

import java.util.ArrayList;

public class ProductInfo extends AppCompatActivity {

    Intent intent;
    DBConnection dbConnection;
    Company company;
    Product product;
    Getters getter;
    TextView p_name, p_desc, p_price, p_type, p_area, c_name;
    String name, desc, price, type_name, com_name, area_name;
    int type_id = 0, pro_area_id = 0;
    int company_id = 0;
    int product_id = 0;
    ArrayList<Integer> area_id_list;
    ArrayList<String> area_name_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_info);
        setTitle("Product Info");
        p_name = (TextView)findViewById(R.id.product_name02);
        p_desc = (TextView)findViewById(R.id.product_desc02);
        p_price = (TextView)findViewById(R.id.product_price02);
        p_type = (TextView)findViewById(R.id.product_type02);
        p_area = (TextView)findViewById(R.id.product_area02);
        c_name = (TextView)findViewById(R.id.company_name02);
        dbConnection = new DBConnection(this);
        area_id_list = new ArrayList<>();
        area_name_list = new ArrayList<>();

        Intent intent = getIntent();
        if( intent.getExtras() != null) {
            String product_id_ = intent.getStringExtra("product_id");
            String company_id_ = intent.getStringExtra("company_id");
            product_id = Integer.parseInt(product_id_);
            company_id = Integer.parseInt(company_id_);
            //Toast.makeText(this, " company_id = "+ company_id, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, " product_id = "+ product_id_, Toast.LENGTH_SHORT).show();
        }
        fill_data();
        if (get_areas_id()) {
            for (int i = 0; i < area_id_list.size(); i++) {
                area_name = get_specific_area_name(area_id_list.get(i));
                area_name_list.add(area_name);
            }
            p_area.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager manager = getFragmentManager();
                    MyDialogFragment dialog = new MyDialogFragment() {
                        @Override
                        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                            return super.onCreateView(inflater, container, savedInstanceState);
                        }
                    };
                    dialog.listitems = area_name_list;
                    dialog.show(manager, "dialog");
                }
            });
        }



    }

    private void fill_data() {
        boolean b1, b2, b3;
        b1 = get_one_product_info();
        b2 = get_one_product_type();
        b3 = get_company_name();
        if (b1 && b2 && b3) {
            p_name.setText(name);
            p_desc.setText(desc);
            p_price.setText(price);
            p_type.setText(type_name);
            c_name.setText(com_name);
        }
        else
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
    }

    public boolean get_one_product_info() {
        product = new Product();
        product.setPro_ID(product_id);
        boolean b;
        b = dbConnection.get_one_product(product);
        if (b) {
            name = product.getPname();
            desc = product.getPdescription();
            price = product.getPprice();
            type_id = product.getPtypeID();
            return true;
        }
        return false;
    }


    public boolean get_one_product_type() {
        product = new Product();
        product.setPtypeID(type_id);
        boolean b = dbConnection.get_one_product_type(product);
        if (b) {
            type_name = product.getPtypeName();
            return true;
        }
        return false;
    }

    private boolean get_company_name() {
        company = new Company();
        company.setCid(company_id);
        boolean b = dbConnection.get_Company_Info(company);
        if (b) {
            com_name = company.getCname();
            return true;
        }
        return false;
    }

    private boolean get_areas_id() {
        product = new Product();
        getter = new Getters();
        product.setPro_ID(product_id);
        boolean b = dbConnection.get_prod_areas_id(getter, product);
        if (b) {
            area_id_list = getter.getProd_Area_id();
            return true;
        }
        return false;
    }

    private String get_specific_area_name(int id) {
        product = new Product();
        product.setAreaID(id);
        String name = "";
        boolean b = dbConnection.get_specific_area_name(product);
        if (b) {
            name = product.getAreaName();
        }
        return name;
    }



    /*public void showDialog(View view) {

        FragmentManager manager = getFragmentManager();

        MyDialogFragment dialog = new MyDialogFragment() {
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                return super.onCreateView(inflater, container, savedInstanceState);
            }
        };
        String[] produts = {"p1", "p2", "p3", "p4"};
        dialog.listitems = produts;
        dialog.show(manager, "dialog");
    }*/

}
