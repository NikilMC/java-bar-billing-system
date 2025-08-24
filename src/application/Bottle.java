package application;
public class Bottle {
    private String name;
    private double price;
    private double rating;
    private String imagePath;

    public Bottle(String name, double price, double rating, String imagePath) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public String getImagePath() {
        return imagePath;
    }
    
    @Override
    public String toString() {
        return name + " - ₹" + price;
    }
}