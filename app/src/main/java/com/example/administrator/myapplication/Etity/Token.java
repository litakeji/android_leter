package com.example.administrator.myapplication.Etity;

public class Token {
    private String token;
    private String code;
    private String msg;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {

        return token;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {

        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
