// Chase Batchelor
// CPSC-39
// March 9, 2026
// This program will create and initialize a Car class.

public class Car {
    private String carId, brand, model, fuelType, color;
    private int year;
    private double mileageKmpl;

    public Car(String carId, String brand, String model, int year, String fuelType, String color, double mileageKmpl) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
        this.color = color;
        this.mileageKmpl = mileageKmpl;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getColor() {
        return color;
    }

    public double getMileageKmpl() {
        return mileageKmpl;
    }

    @Override
    public String toString() {
        return "ID: " + carId + ", Brand: " + brand + ", Model: " + model + ", Year: " + year + ", Fuel: " + fuelType + ", Color: " + color + ", Mileage: " + mileageKmpl;
    }
}