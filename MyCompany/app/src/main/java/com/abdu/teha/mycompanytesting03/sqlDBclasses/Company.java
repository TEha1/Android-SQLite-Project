package com.abdu.teha.mycompanytesting03.sqlDBclasses;

/**
 * Created by TEha on 4/13/2018.
 */

public class Company {


    private int Cid;
    private String Cname;
    private String Cemail;
    private String Cpass;
    private String Caddress;
    private String cphone;
    private String cwebsite;
    private int product_type_id;

    public void setCid(int cid) {
        Cid = cid;
    }

    public int getCid() {
        return Cid;
    }

    public void setCemail(String cemail) {
        Cemail = cemail;
    }

    public void setCpass(String cpass) {
        Cpass = cpass;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public void setCaddress(String caddress) {
        Caddress = caddress;
    }


    public void setProduct_type_id(int product_type_id) {
        this.product_type_id = product_type_id;
    }

    public String getCname() {
        return Cname;
    }

    public String getCemail() {
        return Cemail;
    }

    public String getCpass() {
        return Cpass;
    }

    public String getCaddress() {
        return Caddress;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }

    public String getCphone() {
        return cphone;
    }

    public String getCwebsite() {
        return cwebsite;
    }

    public void setCwebsite(String cwebsite) {
        this.cwebsite = cwebsite;
    }

    public int getProduct_type_id() {
        return product_type_id;
    }
}
