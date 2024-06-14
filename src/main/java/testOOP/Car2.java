package testOOP;

public class Car2 extends Vehicle {

    String make;
    String vin;
    String color;

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
        this.showDetails();
    }

    @Override
    public void innerFunction() {
        System.out.println("This is external function which is overridden");
    }
}
