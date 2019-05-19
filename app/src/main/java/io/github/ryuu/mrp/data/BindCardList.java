package io.github.ryuu.mrp.data;

import java.util.List;

public class BindCardList {
    private String phone;
    private String mail;
    private String qq;
    private String wechat;
    private String times;
    private List list;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public BindCardList(String phone, String mail, String qq, String wechat, String times) {
        this.phone = phone;
        this.mail = mail;
        this.qq = qq;
        this.wechat = wechat;
        this.times = times;
    }

    public String getTimes() {
        return times;
    }

//    public void setTimes(String times) {
//        this.times = times;
//    }

    public String getPhone() {
        return phone;
    }

//    public void setPhone(String phone) {
//        this.phone = phone;
//    }

    public String getMail() {
        return mail;
    }

//    public void setMail(String mail) {
//        this.mail = mail;
//    }

    public String getQq() {
        return qq;
    }
//
//    public void setQq(String qq) {
//        this.qq = qq;
//    }

    public String getWechat() {
        return wechat;
    }

//    public void setWechat(String wechat) {
//        this.wechat = wechat;
//    }
}
