package com.abdu.teha.mycompanytesting03.sqlDBclasses;

import java.util.ArrayList;

/**
 * Created by TEha on 4/13/2018.
 */

public class Getters {

    private ArrayList<Integer> Types_id = new ArrayList<>();
    private ArrayList<String> Types_name = new ArrayList<>();
    private ArrayList<Integer> Area_id = new ArrayList<>();
    private ArrayList<String> Area_names = new ArrayList<>();
    private ArrayList<Integer> Prod_Area_id = new ArrayList<>();
    private ArrayList<Integer> Prod_id = new ArrayList<>();
    private ArrayList<String> Products_name = new ArrayList<>();
    private ArrayList<String> Products_desc = new ArrayList<>();
    private ArrayList<String> Products_price = new ArrayList<>();
    private ArrayList<Integer> Products_type_id = new ArrayList<>();
    private ArrayList<Integer> Company_Product_id = new ArrayList<>();

    public void setTypes_name(ArrayList<String> types_name) {
        Types_name = types_name;
    }

    public void setTypes_id(ArrayList<Integer> types_id) {
        Types_id = types_id;
    }

    public ArrayList<String> getTypes_name() {
        return Types_name;
    }

    public ArrayList<Integer> getTypes_id() {
        return Types_id;
    }
    public void setProducts_name(ArrayList<String> products_name) {
        Products_name = products_name;
    }

    public ArrayList<String> getProducts_name() {
        return Products_name;
    }

    public void setArea_id(ArrayList<Integer> area_id) {
        Area_id = area_id;
    }

    public void setArea_names(ArrayList<String> area_names) {
        Area_names = area_names;
    }

    public ArrayList<Integer> getArea_id() {
        return Area_id;
    }

    public ArrayList<String> getArea_names() {
        return Area_names;
    }

    public void setProd_Area_id(ArrayList<Integer> prod_Area_id) {
        Prod_Area_id = prod_Area_id;
    }

    public void setProd_id(ArrayList<Integer> prod_id) {
        Prod_id = prod_id;
    }

    public ArrayList<Integer> getProd_Area_id() {
        return Prod_Area_id;
    }

    public ArrayList<Integer> getProd_id() {
        return Prod_id;
    }


    public void setProducts_desc(ArrayList<String> products_desc) {
        Products_desc = products_desc;
    }

    public void setProducts_price(ArrayList<String> products_price) {
        Products_price = products_price;
    }

    public void setProducts_type_id(ArrayList<Integer> products_type_id) {
        Products_type_id = products_type_id;
    }

    public void setCompany_Product_id(ArrayList<Integer> company_Product_id) {
        Company_Product_id = company_Product_id;
    }

    public ArrayList<String> getProducts_desc() {
        return Products_desc;
    }

    public ArrayList<String> getProducts_price() {
        return Products_price;
    }

    public ArrayList<Integer> getProducts_type_id() {
        return Products_type_id;
    }

    public ArrayList<Integer> getCompany_Product_id() {
        return Company_Product_id;
    }
}

