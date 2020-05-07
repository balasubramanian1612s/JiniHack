package firefly.dev.jiniuser;

import java.util.ArrayList;

public class hotelInfoGetter {

    String hotelName,hotelAddress,checkOut,lat,lon,parking,picLinks,pool,rating;

    public hotelInfoGetter(){



    }

    public hotelInfoGetter(String hotelName, String hotelAddress, String checkOut, String lat,String lon,String parking,String picLinks,String pool,String rating){


        this.checkOut=checkOut;
        this.hotelAddress=hotelAddress;
        this.hotelName=hotelName;
        this.lat=lat;
        this.lon=lon;
        this.parking=parking;
        this.picLinks=picLinks;
        this.pool=pool;
                this.rating=rating;

    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getPicLinks() {
        return picLinks;
    }

    public void setPicLinks(String picLinks) {
        this.picLinks = picLinks;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
