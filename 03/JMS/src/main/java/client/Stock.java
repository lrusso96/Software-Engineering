package client;

public class Stock {
    private final String company;
    private final float value;

    public Stock(String company, float value) {
        this.company = company;
        this.value = value;
    }

    public String getCompany() {
        return company;
    }

    public float getValue() {
        return value;
    }
}
