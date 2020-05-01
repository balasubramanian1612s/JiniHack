package firefly.dev.jinihotel.getters;

public class AmenitiesGetter
{


    String Complementry;
    String FreeLaundry;
    String FreeWifi;
    String Pool;

    public AmenitiesGetter(){

    }

    public AmenitiesGetter(String Complementry , String FreeLaundry, String FreeWifi, String Pool){

this.Complementry=Complementry;
this.FreeLaundry=FreeLaundry;
this.FreeWifi=FreeWifi;
this.Pool=Pool;


    }

    public String getComplementry() {
        return Complementry;
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
