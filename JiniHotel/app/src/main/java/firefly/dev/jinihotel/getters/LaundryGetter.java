package firefly.dev.jinihotel.getters;

public class LaundryGetter {
    String roomNo,time,authID;

    public LaundryGetter(String roomNo, String time,String authID) {
        this.roomNo = roomNo;
        this.time = time;
        this.authID=authID;
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
