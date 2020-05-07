package firefly.dev.jinihotel.getters;

public class FoodDeliveryGetter {
    String roomNo,authId;

    public FoodDeliveryGetter(String roomNo, String authId) {
        this.roomNo = roomNo;
        this.authId = authId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }
}
