package com.sundram.shardhafertilizer;

public class addToCartCunstructor {

    private String productName, description;
    private String price;

    private String userid;
    private String quantity;
    private String status;

    //Add To Cart Contructor Start

    public addToCartCunstructor(String productName,String productContent,String quantity,String price,String userid,String status){
        this.productName=productName;
        this.description=productContent;
        this.quantity=quantity;
        this.price=price;
        this.userid=userid;
        this.status=status;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
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
}
