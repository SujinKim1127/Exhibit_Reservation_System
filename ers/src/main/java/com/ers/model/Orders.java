package com.ers.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

public class Orders {
    @PositiveOrZero
    private int order_id;
    @NotNull
    @PositiveOrZero
    private int user_id;
    @NotNull
    @PositiveOrZero
    private int exhibit_id;
    @PositiveOrZero
    private int price;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String purchase_date;
    private String address;
    @NotEmpty
    private String name;
    @NotEmpty
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}")
    private String tel;
    @NotNull
    @Positive
    private int amount;
  
    public Orders(@JsonProperty("order_id") int order_id, @JsonProperty("user_id") int user_id,
                    @JsonProperty("exhibit_id") int exhibit_id, @JsonProperty("price") int price,
                    @JsonProperty("purchase_date") String purchase_date, @JsonProperty("address") String address,
                    @JsonProperty("name") String name,@JsonProperty("tel") String tel,@JsonProperty("amount") int amount) {
          this.order_id = order_id;
          this.user_id = user_id;
          this.exhibit_id = exhibit_id;
          this.price = price;
          this.purchase_date = purchase_date;
          this.address = address;
          this.name = name;
          this.tel = tel;
          this.amount = amount;
      }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getExhibit_id() {
        return exhibit_id;
    }

    public void setExhibit_id(int exhibit_id) {
        this.exhibit_id = exhibit_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
      this.purchase_date = purchase_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
