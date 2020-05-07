package firefly.dev.jiniuser;

public class AmenitiesGetter
{


    String Complementry;
    String FreeLaundry;
    String FreeWifi;
    String Pool;
    String bedType;
    String roomSize;

    public AmenitiesGetter(){

    }

    public AmenitiesGetter(String Complementry , String FreeLaundry, String FreeWifi, String Pool, String bedType, String roomSize){

this.Complementry=Complementry;
this.FreeLaundry=FreeLaundry;
this.FreeWifi=FreeWifi;
this.Pool=Pool;
this.bedType=bedType;
this.roomSize=roomSize;


    }

    public String getComplementry() {
        return Complementry;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    public void setComplementry(String complementry) {
        Complementry = complementry;
    }

    public String getFreeLaundry() {
        return FreeLaundry;
    }

    public void setFreeLaundry(String freeLaundry) {
        FreeLaundry = freeLaundry;
    }

    public String getFreeWifi() {
        return FreeWifi;
    }

    public void setFreeWifi(String freeWifi) {
        FreeWifi = freeWifi;
    }

    public String getPool() {
        return Pool;
    }

    public void setPool(String pool) {
        Pool = pool;
    }
}
