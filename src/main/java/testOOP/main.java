package testOOP;

public class main {
    public static void main(String[] args) {
        Vehicle suv = new Vehicle();
        suv.setNumberOfWheels(4);
        suv.setNumOfPassengers(6);

        Car volove = new Car();
        volove.setColor("silver");
        volove.setMake("volove");
        volove.setVin("JIWEOS8WISO");
        volove.setVehicle(suv);

        volove.show();
        volove.vehicle.innerFunction();

        System.out.println();


        Car2 jetta = new Car2();
        jetta.setColor("silver");
        jetta.setMake("jetta");
        jetta.setVin("23sdg23g23g");
        jetta.setNumOfPassengers(4);
        jetta.setNumberOfWheels(4);

        jetta.show();
        jetta.innerFunction();
    }
}
