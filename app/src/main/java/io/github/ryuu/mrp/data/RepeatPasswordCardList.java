package io.github.ryuu.mrp.data;

public class RepeatPasswordCardList {

    private String password;

    private String repeatTimes;

    public RepeatPasswordCardList(String password, String repeatTimes) {
        this.password = password;
        this.repeatTimes = repeatTimes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatTimes() {
        return repeatTimes;
    }

//    public void setRepeatTimes(String repeatTimes) {
//        this.repeatTimes = repeatTimes;
//    }
}
