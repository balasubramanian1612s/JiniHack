package firefly.dev.jinihotel.getters;

import firefly.dev.jinihotel.R;

public class bookedRoomsGetter {


    String BookDate,CurrentPhoto,IDProof,RoomType,TimeIn,StayDays,UserId,Username;

    public bookedRoomsGetter(){


    }

    public bookedRoomsGetter(String BookDate, String CurrentPhoto, String IDProof, String RoomType, String TimeIn, String StayDays,String UserId, String Username){


        this.BookDate=BookDate;
        this.CurrentPhoto=CurrentPhoto;
        this.IDProof=IDProof;
        this.RoomType= RoomType;
        this.StayDays=StayDays;
        this.TimeIn=TimeIn;
        this.UserId=UserId;
        this.Username=Username;



    }

    public String getBookDate() {
        return BookDate;
    }

    public void setBookDate(String bookDate) {
        BookDate = bookDate;
    }

    public String getCurrentPhoto() {
        return CurrentPhoto;
    }

    public void setCurrentPhoto(String currentPhoto) {
        CurrentPhoto = currentPhoto;
    }

    public String getIDProof() {
        return IDProof;
    }

    public void setIDProof(String IDProof) {
        this.IDProof = IDProof;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setRoomType(String roomType) {
        RoomType = roomType;
    }

    public String getTimeIn() {
        return TimeIn;
    }

    public void setTimeIn(String timeIn) {
        TimeIn = timeIn;
    }

    public String getStayDays() {
        return StayDays;
    }

    public void setStayDays(String stayDays) {
        StayDays = stayDays;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = Username;
    }
}
