package firefly.dev.jinihotel.getters;

public class ValetGetter {
    String username,regNo,tokenNo,time,authId;

    public ValetGetter(String username, String regNo, String tokenNo, String time,String authId) {
        this.username = username;
        this.regNo = regNo;
        this.tokenNo = tokenNo;
        this.time = time;
        this.authId=authId;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(String tokenNo) {
        this.tokenNo = tokenNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
