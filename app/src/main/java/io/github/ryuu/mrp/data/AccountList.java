package io.github.ryuu.mrp.data;

public class AccountList {

    private String psdOrBinds;
    private String nickname;
    private String info;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountList(String psdOrBinds, String nickname, String info, String id) {
        this.psdOrBinds = psdOrBinds;
        this.nickname = nickname;
        this.info = info;
        this.id = id;
    }

    public String getPsdOrBinds() {
        return psdOrBinds;
    }

//    public void setPsdOrBinds(String psdOrBinds) {
//        this.psdOrBinds = psdOrBinds;
//    }

    public String getNickname() {
        return nickname;
    }

//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }

    public String getInfo() {
        return info;
    }

//    public void setInfo(String info) {
//        this.info = info;
//    }
}
