package testOOP;

public class Car{

    String make;
    String vin;
    String color;

    Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void show() {
        System.out.println("Make is " + this.make);
        System.out.println("Vin is " + this.vin);
        System.out.println("Color is " + this.color);
        vehicle.showDetails();
    }

}
