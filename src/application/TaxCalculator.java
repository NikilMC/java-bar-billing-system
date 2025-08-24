package application;

public class TaxCalculator {
    private static final double GST_RATE = 0.18;
    public static double calculateTax(double amount) {
        double taxAmount = amount * GST_RATE;
        return amount + taxAmount;
    }
    public static double getTaxAmount(double amount) {
        return amount * GST_RATE;
    }
    public static double getTaxRate() {
        return GST_RATE * 100;
    }
}