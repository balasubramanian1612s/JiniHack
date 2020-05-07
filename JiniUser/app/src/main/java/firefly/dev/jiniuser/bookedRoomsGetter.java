package firefly.dev.jiniuser;

public class bookedRoomsGetter {


    String BookDate,Hotel,PaymentStatus,RoomType,TimeIn,StayDays,RoomNo;

    public bookedRoomsGetter(){


    }

    public bookedRoomsGetter(String BookDate, String Hotel, String PaymentStatus, String RoomType, String TimeIn, String StayDays, String RoomNo){


        this.BookDate=BookDate;
        this.Hotel=Hotel;
        this.PaymentStatus=PaymentStatus;
        this.RoomType= RoomType;
        this.StayDays=StayDays;
        this.TimeIn=TimeIn;
        this.RoomNo=RoomNo;



    }

    public String getBookDate() {
        return BookDate;
    }

    public void setBookDate(String bookDate) {
        BookDate = bookDate;
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

    public String getHotel() {
        return Hotel;
    }

    public void setHotel(String hotel) {
        Hotel = hotel;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public String getRoomNo() {
        return RoomNo;
    }

    public void setRoomNo(String roomNo) {
        RoomNo = roomNo;
    }
}
