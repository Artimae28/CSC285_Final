package patientrecords;
import java.text.DecimalFormat;

public class Patient {
    private int id;
    private String firstName;
    private String lastName;
    private char sex;
    private int age;
    private int floor;
    private int room;
    private double fee;
    private double insurance;
    private double bill;
    private boolean minor;
    private boolean senior;

    DecimalFormat dollars = new DecimalFormat( "$.00" );

    public Patient(int id, String firstName, String lastName, char sex, int age, int floor, int room, double fee, double insurance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.age = age;
        this.floor = floor;
        this.room = room;
        this.fee = fee;
        this.insurance = insurance;
    }

    /*Setters and Getters */
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }
    public char getSex() {
        return sex;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return age;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
    public int getFloor() {
        return floor;
    }

    public void setRoom(int room) {
        this.room = room;
    }
    public int getRoom() {
        return room;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
    public double getFee() {
        return fee;
    }

    public void setInsurance(double insurance) {
        this.insurance = insurance;
    }
    public double getInsurance() {
        return insurance;
    }

    public double getBill() {
        return this.bill;
    }
    public double calculateBill(double fee, double insurance) {
        bill = getFee() - getInsurance();
        return bill;
    }

    public boolean getMinor() {
        return this.minor;
    }

    public boolean isMinor(int age) {
        if (age < 18) {
            return minor = true;
        }
        else {
            return minor = false;
        }
    }

    public boolean getSenior() {
        return this.senior;
    }

    public boolean isSenior(int age) {
        if (age > 65) {
            return senior = true;
        }
        else {
            return senior = false;
        }
    }
    @Override
    public String toString() {
        return(
            "Patient List:\nid = " + id +
            "\nfirstName = " + firstName +
            "\nlastName = " + lastName +
            "\nsex = " + sex +
            "\nage = " + age +
            "\nPatient is a Minor: " + isMinor(age) +
            "\nIs patient a senior? " + isSenior(age) +
            "\nfloor = " + floor +
            "\nroom = " + room +
            "\nfee = " + dollars.format(this.fee) +
            "\nInsurance = " + dollars.format(this.insurance) +
            "\nBill: " + dollars.format(calculateBill(this.fee, this.insurance)));
    }
}
