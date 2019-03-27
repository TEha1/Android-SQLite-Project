package com.abdu.teha.mycompanytesting03.Company;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.abdu.teha.mycompanytesting03.R;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Company;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.DBConnection;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Getters;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddProduct extends AppCompatActivity {

    AlertDialog.Builder alertdialogbuilder;
    TextView textview;
    EditText prodName, prodDesc, prodPrice;
    String Name, Desc, Price;
    Button btnSubmit;
    Intent intent;
    ArrayList<String> types_names_list, areas_names_list;
    ArrayList<Integer> types_id_list, areas_id_list;
    ArrayAdapter<String> dataAdapter;
    Spinner productsSpinner;
    String spinnerValue = "";
    boolean getTypes, EditTextEmpty, isAdded;
    int selected_type_id, company_id, proID;
    List<String> ItemsIntoList;
    boolean[] Selectedtruefalse;
    DBConnection dbConnection;
    Getters getters;
    Company company;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        setTitle("Add Product");
        dbConnection = new DBConnection(this);
        textview = (TextView)findViewById(R.id.textView000);
        prodName = (EditText)findViewById(R.id.product_name);
        prodDesc = (EditText)findViewById(R.id.product_desc);
        prodPrice = (EditText)findViewById(R.id.product_price);
        btnSubmit = (Button) findViewById(R.id.submitProduct);
        productsSpinner = (Spinner)findViewById(R.id.productSpinner);
        types_id_list = new ArrayList<>();
        types_names_list = new ArrayList<>();
        areas_id_list = new ArrayList<>();
        areas_names_list = new ArrayList<>();
        /////////////////////////////////////////////////////////////////////////////////
        intent = getIntent();
        if( intent.getExtras() != null) {
            String myData = intent.getStringExtra("companyID");
            company_id = Integer.parseInt(myData);
            Toast.makeText(this, " > "+ company_id, Toast.LENGTH_SHORT).show();
        }
        /////////////////////////////////////////////////////////////////////////////////
        // Spinner
        getTypes = checkGetTypes();
        if (getTypes) {
            dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types_names_list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            productsSpinner.setAdapter(dataAdapter);
            productsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    spinnerValue = productsSpinner.getSelectedItem().toString();
                    for (int i=0; i<types_names_list.size(); i++) {
                        if(spinnerValue.equals(types_names_list.get(i)) ) {
                            selected_type_id = types_id_list.get(i);
                        }
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
            });
        }
        //////////////////////////////////////////////////////////////////////////////////
        /// Dialog
        Selectedtruefalse = new boolean[area_items().length];
        Arrays.fill(Selectedtruefalse, Boolean.FALSE);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextStatus();
                isAdded = Add_Product();
                if (isAdded) {
                    ClearEditText();
                    textview.setText("");
                    alertdialogbuilder = new AlertDialog.Builder(AddProduct.this);
                    ItemsIntoList = Arrays.asList(area_items());
                    alertdialogbuilder.setMultiChoiceItems(area_items(), Selectedtruefalse, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        }
                    });
                    alertdialogbuilder.setCancelable(false);
                    alertdialogbuilder.setTitle("Select Product Areas");
                    alertdialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int a = 0;
                            String s2 = "";
                            while(a < Selectedtruefalse.length)
                            {
                                boolean value = Selectedtruefalse[a];
                                if(value){
                                    textview.setText(textview.getText() + ItemsIntoList.get(a) + "\n");
                                }
                                a++;
                            }
                            s2 = textview.getText().toString();
                            if (!TextUtils.isEmpty(s2) ) {
                                int[] selected_area_id = getData(s2);
                                get_product_id();
                                for (int i=0; i<selected_area_id.length; i++) {
                                    int area_id = selected_area_id[i];
                                    Add_Prod_Area(area_id);
                                }
                                Toast.makeText(getBaseContext(), "Successfully Added The Product", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getBaseContext(), "You can select Areas later", Toast.LENGTH_LONG).show();
                            }
                            intent = new Intent(AddProduct.this, CompanyProfile.class);
                            intent.putExtra("companyID", Integer.toString(company_id));
                            startActivity(intent);

                        }
                    });

                    AlertDialog dialog = alertdialogbuilder.create();
                    dialog.show();
                }

            }
        });

        ////////////////////////////////////////////////////////////////////////////////////
    }

    public void CheckEditTextStatus(){
        Name = prodName.getText().toString() ;
        Desc = prodDesc.getText().toString();
        Price = prodPrice.getText().toString();
        if(TextUtils.isEmpty(Name) || TextUtils.isEmpty(Desc) || TextUtils.isEmpty(Price)){
            EditTextEmpty = false;
        }
        else {
            EditTextEmpty = true;
        }
    }
    private boolean Add_Product() {
        if(EditTextEmpty) {
            product = new Product();
            product.setPname(Name);
            product.setPdescription(Desc);
            product.setPprice(Price);
            product.setPtypeID(selected_type_id);;
            product.setCompanyID(company_id);
            boolean addBool = dbConnection.add_new_product(product);
            if (addBool) {
                Toast.makeText(this, "Added the product", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "failed to add the product", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
        else {
            Toast.makeText(this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private void Add_Prod_Area(int area_id) {
        product = new Product();
        product.setProA_ID(area_id);
        product.setPro_ID(proID);
        boolean b = dbConnection.add_prod_area(product);
        if (b) {
            Toast.makeText(this, "successfully added the Areas" , Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "failed to add the Areas", Toast.LENGTH_SHORT).show();
        }
    }

    public void get_product_id() {
        product = new Product();
        company = new Company();
        product.setPname(Name);
        company.setCid(company_id);
        boolean b = dbConnection.get_product_id(product, company);
        if (b) {
            proID = product.getPro_ID();
            //Toast.makeText(this, "proID == " + Integer.toString(proID), Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(this, "failed to get the Products", Toast.LENGTH_SHORT).show();
        }

    }

    //Fill Spinner
    public boolean checkGetTypes() {
        getters = new Getters();
        boolean type = dbConnection.get_all_product_type(getters);
        if (type) {
            types_id_list = getters.getTypes_id();
            types_names_list = getters.getTypes_name();
            return true;
        }
        return false;
    }
    ////////////////////////////////////////////////////////////////////////////////

    //Fill Area
    public boolean checkGetArea() {
        getters = new Getters();
        boolean type = dbConnection.get_areas(getters);
        if (type) {
            areas_id_list = getters.getArea_id();
            areas_names_list = getters.getArea_names();
            return true;
        }
        return false;
    }
    public String[] area_items() {
        String[] areaNames = {""};
        if (checkGetArea()){
            areaNames = new String[areas_names_list.size()];
            areaNames = areas_names_list.toArray(areaNames);
            return areaNames;
        }
        return areaNames;
    }

    public int[] getData(String data) {
        String[] s2 = data.split("\n");
        ArrayList <String> Area_Name_List = areas_names_list;
        ArrayList <Integer> Area_Id_List = areas_id_list;
        int[] ids = new int[s2.length];
        for (int i=0; i<s2.length; i++) {
            for (int j=0; j<Area_Name_List.size(); j++) {
                if (s2[i].equals(Area_Name_List.get(j))) {
                    ids[i] = Area_Id_List.get(i);
                }
            }
        }
        //Toast.makeText(this, "> " + ids[2], Toast.LENGTH_SHORT).show();
        return ids;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////

    public void ClearEditText(){
        prodName.getText().clear();
        prodDesc.getText().clear();
        prodPrice.getText().clear();
    }

}
