package testOOP;

public class Vehicle {

    int numberOfWheels;
    int numOfPassengers;

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public int getNumOfPassengers() {
        return numOfPassengers;
    }

    public void setNumOfPassengers(int numOfPassengers) {
        this.numOfPassengers = numOfPassengers;
    }

    public void showDetails() {
        System.out.println("Here are numberOfWheels " + numberOfWheels);
        System.out.println("Here are numberOfPassangers " + numOfPassengers);
    }

    public void innerFunction() {
        System.out.println("This is inner function in vehicle");
    }
}
