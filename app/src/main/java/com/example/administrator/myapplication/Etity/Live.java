package com.example.administrator.myapplication.Etity;

public class Live {
    private int code;
    private String msg;
    private String poster;
    private String src;
    private int vHeight;
    private int vWidth;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPoster() {
        return poster;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrc() {
        return src;
    }

    public void setVHeight(int vHeight) {
        this.vHeight = vHeight;
    }

    public int getVHeight() {
        return vHeight;
    }

    public void setVWidth(int vWidth) {
        this.vWidth = vWidth;
    }

    public int getVWidth() {
        return vWidth;
    }

    @Override
    public String toString() {
        return "Live{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", poster='" + poster + '\'' +
                ", src='" + src + '\'' +
                ", vHeight=" + vHeight +
                ", vWidth=" + vWidth +
                '}';
    }
}
