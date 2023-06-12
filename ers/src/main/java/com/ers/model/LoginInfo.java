package com.ers.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class LoginInfo {
    @NotEmpty
    @NotBlank

    private String userid;
    @NotEmpty
    @NotBlank
    private String pwd;

    private Boolean rememberid;
    public String getUserid() { return userid; }
    public String getPwd() { return pwd; }
    public Boolean getRememberid() {return rememberid;}

    public void setUserid(String userid) { this.userid = userid; }
    public void setPwd(String pwd) { this.pwd = pwd; }

    public void setRememberid(Boolean rememberid) {
        this.rememberid = rememberid;
    }
}