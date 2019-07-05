package com.sundram.shardhafertilizer;

public class ProductName {

    private String productName, productPhoto, description;
    private String price;
    private String cartPrice;
    private int id;
    private String userid;
    private String rateId;
    private String quantity;
    private String status;
    private String packing;
    private String rateMt;
    private String MRP;
    private String REM;

    public ProductName() {
    }


    //ProductDescription Start
    public ProductName(String rateId,String quantity, String packing, String rateMt, String REM, String MRP, String Status) {
        this.rateId = rateId;
        this.quantity = quantity;
        this.packing = packing;
        this.rateMt = rateMt;
        this.REM = REM;
        this.MRP = MRP;
        this.status = Status;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCartPrice(String cartPrice) {
        this.cartPrice = cartPrice;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getRateMt() {
        return rateMt;
    }

    public void setRateMt(String rateMt) {
        this.rateMt = rateMt;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getREM() {
        return REM;
    }

    public void setREM(String REM) {
        this.REM = REM;
    }

    //Product Description End


    //ProductList Construtor Start
    public ProductName(int id, String product_Name, String Description,String quantity, String packing,String MRP, String Photo) {
        this.id = id;
        this.productName = product_Name;
        this.description = Description;
        this.quantity=quantity;
        this.packing=packing;
        this.MRP = MRP;
        this.productPhoto = Photo;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getCartPrice() {
        return cartPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    //ProductList Constructor End




    //Add To Cart End
}
