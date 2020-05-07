package firefly.dev.jiniuser;

public class FoodsGetter {
    String foodName;
    String price;
    String description;

    public FoodsGetter(String foodName, String price,String description) {
        this.foodName = foodName;
        this.price = price;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
