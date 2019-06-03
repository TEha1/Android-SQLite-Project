package com.abdu.teha.mycompanytesting03.sqlDBclasses;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by TEha on 4/13/2018.
 */
public class DBConnection {

    private DBCreator dBcreator;
    private Context context1;
    private String SQLiteDataBaseQueryHolder;
    private ContentValues contentValues;
    private SQLiteDatabase db;

    /**
     * Instantiates a new Db connection.
     *
     * @param context the context
     */
    public DBConnection(Context context) {
        dBcreator = new DBCreator(context);
        this.context1 = context;
        db = dBcreator.getWritableDatabase();
    }


    //Company Sign Up


    /**
     * Company sign up boolean.
     *
     * The <h1>companySignUp</h1> program implements an application that
     * simply insert <p>Company</p> given data into Database using <p>insert</p> function
     * that take the <p>ContentValues</p>, then check if data is inserted or not using IF ELSE condition
     *
     * @param company the company
     * @param select  the select
     * @return the boolean
     */
    public boolean companySignUp(Company company, int select) {
        long rowID;
        contentValues = new ContentValues();
        contentValues.put(DBCreator.Company_Name, company.getCname().trim());
        contentValues.put(DBCreator.Company_Email, company.getCemail().trim());
        contentValues.put(DBCreator.Company_Password, company.getCpass().trim());
        contentValues.put(DBCreator.Company_Address, company.getCaddress().trim());
        contentValues.put(DBCreator.Company_Phone, company.getCphone().trim());
        contentValues.put(DBCreator.Company_Website, company.getCwebsite().trim());
        contentValues.put(DBCreator.Company_Product_Type_Id, company.getProduct_type_id());
        if (select == 1) {
            rowID = db.insert(DBCreator.CompanyTable, null, contentValues);
        } else if (select == 2) {
            rowID = db.update(DBCreator.CompanyTable, contentValues, DBCreator.Company_ID + "=" + company.getCid(), null);
        } else {
            rowID = -1;
        }
        if (rowID != -1) {
            return true;
        } else {
            return false;
        }
/*
        SQLiteDataBaseQueryHolder = "INSERT INTO '"+ DBCreator.CompanyTable +"' ('" + DBCreator.Company_Name + "', '" +
                DBCreator.Company_Email + "', '" + DBCreator.Company_Password + "', '" + DBCreator.Company_Address +
                "', '" + DBCreator.Company_Phone + "', '" + DBCreator.Company_Website + "', '" + DBCreator.Company_Product_Type_Id +
                "') VALUES('" + company.getCname() + "', '" + company.getCemail() + "', '" + company.getCpass() +
                "', '" + company.getCaddress() + "', '" + company.getCphone() + "', '" + company.getCwebsite() +
                "', '" + company.getProduct_type_id() + "');";

        try {
            db.execSQL(SQLiteDataBaseQueryHolder);
            Toast.makeText(context1,"Data Inserted Successfully", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(context1, "due to " + e, Toast.LENGTH_LONG).show();
        }
*/
    }


    //Company Log In
    /**
     * Company log in boolean.
     *
     * The <h1>CompanyLogIn</h1> program implements an application that
     * simply select <p>Company</p> data from Database according to given data from <p>Company</p> Object
     * the check if data selected or not using If Else condition, if TRUE it will set the <p>Company ID</p>
     * to <p>Company Class</p>, if FALSE it will return false
     *
     * @param company the company
     * @return the boolean
     */
    public boolean CompanyLogIn(Company company) {
        // SELECT * FROM COMPANY WHERE AGE >= 25 AND SALARY >= 65000;

        SQLiteDataBaseQueryHolder = "SELECT * FROM " + DBCreator.CompanyTable + " WHERE " +
                DBCreator.Company_Email + " = '" + company.getCemail() + "' AND " + DBCreator.Company_Password +
                " = '" + company.getCpass() + "' ;";
        Cursor cursor = db.rawQuery(SQLiteDataBaseQueryHolder, null);
        if (cursor.moveToFirst()) {
            company.setCid(cursor.getInt(0));
            //company.setCid(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_ID)));
        } else
            return false;

        cursor.close();
        return true;
    }


    //Adding New Product

    /**
     * Add new product boolean.
     *
     * The <h1>add_new_product</h1> program implements an application that
     * simply insert <p>Product</p> given data into Database using <p>insert</p> function
     * that take the <p>ContentValues</p>, then check if data is inserted or not using IF ELSE condition
     *
     * @param product the product
     * @return the boolean
     */
    public boolean add_new_product(Product product) {

        contentValues = new ContentValues();
        contentValues.put(DBCreator.Product_Name, product.getPname().trim());
        contentValues.put(DBCreator.Product_Description, product.getPdescription().trim());
        contentValues.put(DBCreator.Product_Price, product.getPprice().trim());
        contentValues.put(DBCreator.Product_Type_ID, product.getPtypeID());
        contentValues.put(DBCreator.Com_ID, product.getCompanyID());
        long rowID = db.insert(DBCreator.ProductInfoTable, null, contentValues);
        if (rowID != -1)
            return true;
        else
            return false;

        /*
        SQLiteDataBaseQueryHolder = "INSERT INTO "+ DBCreator.ProductInfoTable +" (" + DBCreator.Product_Name + ", " +
                DBCreator.Product_Description + ", " + DBCreator.Product_Price + ", " + DBCreator.Product_Type_ID +
                ", " + DBCreator.Product_A_ID + ", " + DBCreator.Com_ID + ") VALUES('"  +
                product.getPname()+ "', '" + product.getPdescription() + "', '" + product.getPprice() +
                "', '" + product.getPtypeID()+ "', '" + product.getPAreaID() + "', '" + product.getCompanyID() +  "');";

        try {
            db.execSQL(SQLiteDataBaseQueryHolder);
            //Toast.makeText(context1,"Data Inserted Successfully", Toast.LENGTH_SHORT).show();
            return true;
        } catch (SQLException e) {
            Toast.makeText(context1, "due to " + e, Toast.LENGTH_LONG).show();
            return false;
        }*/
    }



    /**
     * Gets products.
     *
     * The <h1>get_Products</h1> program implements an application that simply select <p>Company Products</p> data
     * from Database according to <p>Company ID</p> then check if data selected or not using If Else condition,
     * if TRUE it will set the <p>Product Info</p>
     * to <p>Getters Object</p>, if FALSE it will return false
     *
     * @param company the company
     * @param getters the getters
     * @return the products
     */
    public boolean get_Products(Company company, Getters getters) {

        ArrayList<String> Products_names = new ArrayList<>();
        ArrayList<Integer> Products_id = new ArrayList<>();
        SQLiteDataBaseQueryHolder = "SELECT * FROM " + DBCreator.ProductInfoTable + " WHERE " +
                DBCreator.Com_ID + " = '" + company.getCid() + "' ;";
        Cursor cursor = db.rawQuery(SQLiteDataBaseQueryHolder, null);
        if (cursor.moveToFirst()) {
            do {
                Products_id.add(cursor.getInt(0));
                Products_names.add(cursor.getString(1));
            } while (cursor.moveToNext());
            getters.setProd_id(Products_id);
            getters.setProducts_name(Products_names);
        } else
            return false;

        cursor.close();
        return true;
    }

    //Get All Products

    /**
     * Gets all products.
     *
     * The <h1>get_all_products</h1> program implements an application that simply select all <p>Product</p> table rows data
     * from Database then check if data selected or not using If Else condition, if TRUE it will set the <p>Product Info</p>
     *  to <p>Getters Object</p>, if FALSE it will return false
     *
     * @param getters the getters
     * @return the all products
     */
    public boolean get_all_products(Getters getters) {

        ArrayList<Integer> Products_id_ = new ArrayList<>();
        ArrayList<String> Products_names_ = new ArrayList<>();
        ArrayList<String> Products_desc_ = new ArrayList<>();
        ArrayList<String> Products_price_ = new ArrayList<>();
        ArrayList<Integer> Products_type_id_ = new ArrayList<>();
        ArrayList<Integer> Company_id_ = new ArrayList<>();
        SQLiteDataBaseQueryHolder = "SELECT * FROM " + DBCreator.ProductInfoTable +  ";";
        Cursor cursor = db.rawQuery(SQLiteDataBaseQueryHolder, null);
        if (cursor.moveToFirst()) {
            do {
                Products_id_.add(cursor.getInt(0));
                Products_names_.add(cursor.getString(1));
                Products_desc_.add(cursor.getString(2));
                Products_price_.add(cursor.getString(3));
                Products_type_id_.add(cursor.getInt(4));
                Company_id_.add(cursor.getInt(5));

            } while (cursor.moveToNext());
            getters.setProd_id(Products_id_);
            getters.setProducts_name(Products_names_);
            getters.setProducts_desc(Products_desc_);
            getters.setProducts_price(Products_price_);
            getters.setProducts_type_id(Products_type_id_);
            getters.setCompany_Product_id(Company_id_);
        } else
            return false;

        cursor.close();
        return true;
    }

    //Get Specific Product

    /**
     * Gets one product.
     *
     * The <h1>get_one_product</h1> program implements an application that simply select one <p>Product</p>
     * from Database according to <p>Product ID</p> then check if data selected or not using If Else condition,
     * if TRUE it will set the <p>Product Info</p>
     * to <p>Product Object</p>, if FALSE it will return false
     *
     * @param product the product
     * @return the one product
     */
    public boolean get_one_product(Product product) {
        String name, desc, price;
        int p_type_id, com_id;
        SQLiteDataBaseQueryHolder = "SELECT * FROM " + DBCreator.ProductInfoTable + " WHERE " +
                DBCreator.Product_ID + " = '" + product.getPro_ID() + "' ;";
        Cursor cursor = db.rawQuery(SQLiteDataBaseQueryHolder, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(1);
                desc = cursor.getString(2);
                price = cursor.getString(3);
                p_type_id = cursor.getInt(4);
                com_id = cursor.getInt(5);
            } while (cursor.moveToNext());
            product.setPname(name);
            product.setPdescription(desc);
            product.setPprice(price);
            product.setPtypeID(p_type_id);
            product.setCompanyID(com_id);
            return true;
        }
        cursor.close();
        return false;
    }

    //Get Specific Product ID

    /**
     * Gets product id.
     *
     * The <h1>get_product_id</h1> program implements an application that simply select <p>Product ID</p>
     * from Database according to <p>Product Name</p> and <p>Company ID</p> then check if data selected
     * or not using If Else condition, if TRUE it will set the <p>Product ID</p> to <p>Product Object</p>,
     * if FALSE it will return false
     *
     * @param product the product
     * @param company the company
     * @return the product id
     */
    public boolean get_product_id(Product product, Company company) {
        int prod_ID = 0;
        SQLiteDataBaseQueryHolder = "SELECT * FROM " + DBCreator.ProductInfoTable + " WHERE " +
                DBCreator.Product_Name + " = '" + product.getPname() + "' AND " + DBCreator.Com_ID +
                " = '" + company.getCid() + "' ;";

        Cursor cursor = db.rawQuery(SQLiteDataBaseQueryHolder, null);
        if (cursor.moveToFirst()) {
            do {
                prod_ID = cursor.getInt(0);
            } while (cursor.moveToNext());
            product.setPro_ID(prod_ID);
        } else
            return false;

        cursor.close();
        return true;
    }


    //Delete Product

    /**
     * Delete product boolean.
     *
     * The <h1>DeleteProduct</h1> program implements an application that simply Delete one <p>Product</p>
     * from Database according to <p>Product ID</p> then check if Row deleted or not using If Else condition,
     * if TRUE it will return TRUE, if FALSE it will return false
     *
     * @param product the product
     * @return the boolean
     */
    public boolean DeleteProduct(Product product) {
        String where = DBCreator.Product_ID + " = ?";
        int id = product.getPro_ID();
        long rowID = db.delete(DBCreator.ProductInfoTable, where, new String[]{Integer.toString(id)});
        if (rowID != -1)
            return true;
        else
            return false;

    }

    //Get Company Info/

    /**
     * Gets company info.
     *
     * The <h1>get_Company_Info</h1> program implements an application that simply select one <p>Company Info</p>
     * from Database according to <p>Company ID</p> then check if data selected or not using If Else condition,
     * if TRUE it will set the <p>Company Info</p> to <p>Product Object</p>, if FALSE it will return false
     *
     * @param company the company
     * @return the company info
     */
    public boolean get_Company_Info(Company company) {

        String name;
        String email;
        String password;
        String address;
        String phone;
        String website;
        int product_type_id;
        SQLiteDataBaseQueryHolder = "SELECT * FROM " + DBCreator.CompanyTable + " WHERE " +
                DBCreator.Company_ID + " = '" + company.getCid() + "' ;";
        Cursor cursor = db.rawQuery(SQLiteDataBaseQueryHolder, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(1);
                email = cursor.getString(2);
                password = cursor.getString(3);
                address = cursor.getString(4);
                phone = cursor.getString(5);
                website = cursor.getString(6);
                product_type_id = cursor.getInt(7);
            } while (cursor.moveToNext());

            company.setCname(name);
            company.setCemail(email);
            company.setCpass(password);
            company.setCaddress(address);
            company.setCphone(phone);
            company.setCwebsite(website);
            company.setProduct_type_id(product_type_id);


        } else
            return false;

        cursor.close();
        return true;
    }

    /**
     * Add product type boolean.
     *
     * The <h1>add_product_type</h1> program implements an application that
     * simply insert <p>Type of Product</p> from given data into Database using <p>insert</p> function
     * that take the <p>ContentValues</p>, then check if data is inserted or not using IF ELSE condition
     *
     * @param product the product
     * @return the boolean
     */
    public boolean add_product_type(Product product) {
        contentValues = new ContentValues();
        contentValues.put(DBCreator.Type_Name, product.getTypeName().trim());
        long rowID = db.insert(DBCreator.ProductTypeTable, null, contentValues);
        if (rowID != -1)
            return true;
        else
            return false;
    }

    /**
     * Gets all product type.
     *
     * The <h1>get_all_product_type</h1> program implements an application that simply select all <p>Type Of Products</p>
     * table rows data from Database then check if data selected or not using If Else condition,
     * if TRUE it will set the <p>Product Info</p>
     * to <p>Getters Object</p>, if FALSE it will return false
     *
     * @param getters the getters
     * @return the all product type
     */
    public boolean get_all_product_type(Getters getters) {
        ArrayList<Integer> types_id = new ArrayList<>();
        ArrayList<String> types_name = new ArrayList<>();
        SQLiteDataBaseQueryHolder = "SELECT * FROM " + DBCreator.ProductTypeTable + ";";
        Cursor cursor = db.rawQuery(SQLiteDataBaseQueryHolder, null);
        if (cursor.moveToFirst()) {
            do {
                types_id.add(cursor.getInt(0));
                types_name.add(cursor.getString(1));
            } while (cursor.moveToNext());
            getters.setTypes_id(types_id);
            getters.setTypes_name(types_name);
        } else
            return false;

        cursor.close();
        return true;
    }

    //Get Specific Product Type

    /**
     * Gets one product type.
     *
     * The <h1>get_one_product_type</h1> program implements an application that simply select one <p>Type Of Product</p>
     * from Database according to <p>Type ID</p> then check if data selected or not using If Else condition,
     * if TRUE it will set the <p>Type Name</p>
     * to <p>Product Object</p>, if FALSE it will return false
     *
     * @param product the product
     * @return the one product type
     */
    public boolean get_one_product_type(Product product) {
        String name;
        SQLiteDataBaseQueryHolder = "SELECT * FROM " + DBCreator.ProductTypeTable +" WHERE " +
                DBCreator.Type_ID + " = '" + product.getPtypeID() + "' ;";
        Cursor cursor = db.rawQuery(SQLiteDataBaseQueryHolder, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(1);
            } while (cursor.moveToNext());
            product.setPtypeName(name);
        } else
            return false;

        cursor.close();
        return true;
    }


    /**
     * Add area boolean.
     *
     * The <h1>add_area</h1> program implements an application that
     * simply insert <p>Area</p> given data into Database using <p>insert</p> function
     * that take the <p>ContentValues</p>, then check if data is inserted or not using IF ELSE condition
     *
     * @param product the product
     * @return the boolean
     */
    public boolean add_area(Product product) {
        contentValues = new ContentValues();
        contentValues.put(DBCreator.Area_Name, product.getAreaName().trim());
        long rowID = db.insert(DBCreator.AreaTable, null, contentValues);
        if (rowID != -1)
            return true;
        else
            return false;

    }

    //Get All Areas

    /**
     * Gets areas.
     *
     * The <h1>get_areas</h1> program implements an application that simply select all <p>Area Info</p>
     * then check if data selected or not using If Else condition,
     * if TRUE it will set the <p>Area Name</p> and <p>Area ID</p>
     * to <p>Getter Object</p>, if FALSE it will return false
     *
     * @param getters the getters
     * @return the areas
     */
    public boolean get_areas(Getters getters) {
        ArrayList<Integer> Areas_id = new ArrayList<>();
        ArrayList<String> Areas_name = new ArrayList<>();

        SQLiteDataBaseQueryHolder = "SELECT * FROM " + DBCreator.AreaTable + ";";

        Cursor cursor = db.rawQuery(SQLiteDataBaseQueryHolder, null);
        if (cursor.moveToFirst()) {
            do {
                Areas_id.add(cursor.getInt(0));
                Areas_name.add(cursor.getString(1));
            } while (cursor.moveToNext());
            getters.setArea_id(Areas_id);
            getters.setArea_names(Areas_name);
        } else
            return false;

        cursor.close();
        return true;
    }

    // Get Specific Area Name

    /**
     * Gets specific area name.

     * The <h1>get_specific_area_name</h1> program implements an application that simply select one <p>Area Info</p>
     * from Database according to <p>Area ID</p> then check if data selected or not using If Else condition,
     * if TRUE it will set the <p>Area Name</p>
     * to <p>Product Object</p>, if FALSE it will return false
     *
     * @param product the product
     * @return the specific area name
     */
    public boolean get_specific_area_name(Product product) {
        String name = "";
        SQLiteDataBaseQueryHolder = "SELECT * FROM " + DBCreator.AreaTable + " WHERE " +
                DBCreator.Area_ID + " = '" + product.getAreaID() + "' ;";

        Cursor cursor = db.rawQuery(SQLiteDataBaseQueryHolder, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(1);
            } while (cursor.moveToNext());
            product.setAreaName(name);
        } else
            return false;

        cursor.close();
        return true;

    }


    /**
     * Add prod area boolean.
     *
     * The <h1>add_prod_area</h1> program implements an application that
     * simply insert <p>Area ID</p> and <p>Product ID</p> from given data into Database using <p>insert</p> function
     * that take the <p>ContentValues</p>, then check if data is inserted or not using IF ELSE condition
     *
     * @param product the product
     * @return the boolean
     */
    public boolean add_prod_area(Product product) {
        contentValues = new ContentValues();
        contentValues.put(DBCreator.Prod_Area_ID, product.getProA_ID());
        contentValues.put(DBCreator.Prod___ID, product.getPro_ID());
        long rowID = db.insert(DBCreator.ProductAreaTable, null, contentValues);
        if (rowID != -1)
            return true;
        else
            return false;

    }


    /**
     * Gets prod areas id.
     *
     * The <h1>get_prod_areas_id</h1> program implements an application that simply select  <p>Product Area Info</p>
     * then check if data selected or not using If Else condition,
     * if TRUE it will set the <p>Area ID</p>
     * to <p>Getter Object</p>, if FALSE it will return false
     *
     * @param getters the getters
     * @param product the product
     * @return the prod areas id
     */
    public boolean get_prod_areas_id(Getters getters, Product product) {
        ArrayList<Integer> Areas_id = new ArrayList<>();
        SQLiteDataBaseQueryHolder = "SELECT * FROM " + DBCreator.ProductAreaTable + " WHERE " +
                DBCreator.Prod___ID + " = '" + product.getPro_ID() + "' ;";
        Cursor cursor = db.rawQuery(SQLiteDataBaseQueryHolder, null);
        if (cursor.moveToFirst()) {
            do {
                Areas_id.add(cursor.getInt(0));
            } while (cursor.moveToNext());
            getters.setProd_Area_id(Areas_id);
        } else
            return false;

        cursor.close();
        return true;
    }






    //Inner Class

    private static class DBCreator extends SQLiteOpenHelper { // 3mlna L Class da Static 3l4an my2dr4 y4of L 7aga elli fi L Parent [ aaaaaa ]
        private Context context;

        /**
         * Instantiates a new Db creator.
         *
         * @param context the context
         */
        DBCreator(Context context) {
            super(context, dataBase_Name, null, dataBase_Version);
            this.context = context;
        }


        private static final int dataBase_Version = 2;
        private static final String dataBase_Name = "dss_project";

        //Product Type Table
        private static final String ProductTypeTable = "product_type";
        private static final String Type_ID = "type_id";
        private static final String Type_Name = "type_name";
        private static final String CREATE_PRODUCT_TYPE_TABLE = "CREATE TABLE " + ProductTypeTable + "( " + Type_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + Type_Name + " VARCHAR(255)" + ");";
        private static final String DROP_PRODUCT_TYPE_TABLE = "DROP TABLE IF EXISTS " + ProductTypeTable;

        //Company Table
        private static final String CompanyTable = "company";
        private static final String Company_ID = "com_id";
        private static final String Company_Name = "Com_name";
        private static final String Company_Email = "com_email";
        private static final String Company_Password = "com_password";
        private static final String Company_Address = "com_address";
        private static final String Company_Phone = "com_phone";
        private static final String Company_Website = "com_website";
        private static final String Company_Product_Type_Id = "com_product_type_id";
        private static final String CREATE_COMPANY_TABLE = "CREATE TABLE " + CompanyTable + "( " + Company_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + Company_Name + " VARCHAR(255)," + Company_Email
                + " VARCHAR(255)," + Company_Password + " VARCHAR(255)," + Company_Address + " VARCHAR(255),"
                + Company_Phone + " VARCHAR(255)," + Company_Website + " VARCHAR(255)," +
                Company_Product_Type_Id + " INTEGER NOT NULL ," + "FOREIGN KEY(" + Company_Product_Type_Id +
                ") REFERENCES " + ProductTypeTable + "(" + Type_ID + ") " + ");";
        private static final String DROP_COMPANY_TABLE = "DROP TABLE IF EXISTS " + CompanyTable;


        //Area Table
        private static final String AreaTable = "area";
        private static final String Area_ID = "area_id";
        private static final String Area_Name = "area_name";
        private static final String CREATE_AREA_TABLE = "CREATE TABLE " + AreaTable + " (" + Area_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + Area_Name + " VARCHAR(30) " + ");";
        private static final String DROP_AREA_TABLE = "DROP TABLE IF EXISTS " + AreaTable;


        //Product Information Table
        private static final String ProductInfoTable = "product_info";
        private static final String Product_ID = "p_id";
        private static final String Product_Name = "p_name";
        private static final String Product_Description = "p_desc";
        private static final String Product_Price = "p_price";
        private static final String Product_Type_ID = "p_type_id";
        //private static final String Product_A_ID = "product_A_id";
        private static final String Com_ID = "company_id";
        private static final String CREATE_PRODUCT_INFO_TABLE = "CREATE TABLE " + ProductInfoTable + " ( " + Product_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + Product_Name + " VARCHAR(255), " + Product_Description + " VARCHAR(255), " +
                Product_Price + " VARCHAR(255), " + Product_Type_ID + " INTEGER NOT NULL," +
                Com_ID + " INTEGER NOT NULL, " + "FOREIGN KEY(" + Product_Type_ID + ") REFERENCES " + ProductTypeTable + "(" +
                Type_ID + "), " + "FOREIGN KEY(" + Com_ID + ") REFERENCES " + CompanyTable + "(" +
                Company_ID + ") " + ");";
        private static final String DROP_PRODUCT_INFO_TABLE = "DROP TABLE IF EXISTS " + ProductInfoTable;


        //Product Area Table
        private static final String ProductAreaTable = "product_area";
        private static final String Prod_Area_ID = "pa_id";
        private static final String Prod___ID = "p_id";
        private static final String CREATE_PRODUCT_AREA_TABLE = "CREATE TABLE " + ProductAreaTable + " (" + Prod_Area_ID +
                " INTEGER NOT NULL," + Prod___ID + " INTEGER NOT NULL," + "FOREIGN KEY(" + Prod_Area_ID +
                ") REFERENCES " + AreaTable + "(" + Area_ID + "), " + "FOREIGN KEY(" + Prod___ID +
                ") REFERENCES " + ProductInfoTable + "(" + Product_ID + ") " + ");";
        private static final String DROP_PRODUCT_AREA_TABLE = "DROP TABLE IF EXISTS " + ProductAreaTable;

        //User Table
        private static final String UserTable = "user";
        private static final String User_ID = "u_id";
        private static final String User_Name = "u_name";
        private static final String User_Password = "u_password";
        private static final String CREATE_USER_TABLE = "CREATE TABLE " + UserTable + "( " + User_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                User_Name + " VARCHAR(255), " + User_Password + " VARCHAR(255) " + ");";
        private static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + UserTable;

        //Favourite Product Table
        private static final String FavouriteProductTable = "favourite_product";
        private static final String U_ID = "user_id";
        private static final String Prod_ID = "product_id";
        private static final String CREATE_FAVOURITE_PRODUCT_TABLE = "CREATE TABLE " + FavouriteProductTable + "( " +
                U_ID + " INTEGER NOT NULL, " + Prod_ID + " INTEGER NOT NULL, " + " FOREIGN KEY(" + U_ID + ") REFERENCES " +
                UserTable + "(" + User_ID + ") " + ");";
        private static final String DROP_FAVOURITE_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + FavouriteProductTable;


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_PRODUCT_TYPE_TABLE);
                sqLiteDatabase.execSQL(CREATE_COMPANY_TABLE);
                sqLiteDatabase.execSQL(CREATE_AREA_TABLE);
                sqLiteDatabase.execSQL(CREATE_PRODUCT_INFO_TABLE);
                sqLiteDatabase.execSQL(CREATE_PRODUCT_AREA_TABLE);
                sqLiteDatabase.execSQL(CREATE_USER_TABLE);
                sqLiteDatabase.execSQL(CREATE_FAVOURITE_PRODUCT_TABLE);
                Toast.makeText(context, "onCreate", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(context, " onCreate failed due to " + e, Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL(DROP_PRODUCT_TYPE_TABLE);
                sqLiteDatabase.execSQL(DROP_COMPANY_TABLE);
                sqLiteDatabase.execSQL(DROP_AREA_TABLE);
                sqLiteDatabase.execSQL(DROP_PRODUCT_INFO_TABLE);
                sqLiteDatabase.execSQL(DROP_PRODUCT_AREA_TABLE);
                sqLiteDatabase.execSQL(DROP_USER_TABLE);
                sqLiteDatabase.execSQL(DROP_FAVOURITE_PRODUCT_TABLE);
                onCreate(sqLiteDatabase);
                Toast.makeText(context, "onUpgrade", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(context, "due to " + e, Toast.LENGTH_LONG).show();
            }
        }


    }
}



