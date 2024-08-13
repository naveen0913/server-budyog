package com.buyogo.demo.Response;

public class CustomResponse {

    private int code;
    private String loggedInWith;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLoggedInWith() {
        return loggedInWith;
    }

    public void setLoggedInWith(String loggedInWith) {
        this.loggedInWith = loggedInWith;
    }

    public CustomResponse(int code, String loggedInWith) {
        this.code = code;
        this.loggedInWith = loggedInWith;
    }
}
