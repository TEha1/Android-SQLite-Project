package com.abdu.teha.mycompanytesting03.sqlDBclasses;

/**
 * Created by TEha on 4/13/2018.
 */

public class Product {
    private String Pname;
    private String Pdescription;
    private String Pprice;
    private String AreaName;
    private String TypeName;
    private String PtypeName;
    private int PtypeID;
    private int AreaID;
    private int CompanyID;
    private int ProA_ID;
    private int Pro_ID;

    public void setPtypeName(String ptypeName) {
        PtypeName = ptypeName;
    }

    public String getPtypeName() {
        return PtypeName;
    }

    public void setProA_ID(int proA_ID) {
        ProA_ID = proA_ID;
    }

    public void setPro_ID(int pro_ID) {
        Pro_ID = pro_ID;
    }

    public int getProA_ID() {
        return ProA_ID;
    }

    public int getPro_ID() {
        return Pro_ID;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public void setPdescription(String pdescription) {
        Pdescription = pdescription;
    }

    public void setPprice(String pprice) {
        Pprice = pprice;
    }

    public void setPtypeID(int ptypeID) {
        PtypeID = ptypeID;
    }


    public void setCompanyID(int companyID) {
        CompanyID = companyID;
    }


    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getPname() {
        return Pname;
    }

    public String getPdescription() {
        return Pdescription;
    }

    public String getPprice() {
        return Pprice;
    }

    public int getPtypeID() {
        return PtypeID;
    }

    public int getCompanyID() {
        return CompanyID;
    }


    public String getTypeName() {
        return TypeName;
    }

    public void setAreaID(int areaID) {
        AreaID = areaID;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public String getAreaName() {
        return AreaName;
    }

    public int getAreaID() {
        return AreaID;
    }

}
