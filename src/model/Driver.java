package model;

public class Driver {
    public static void main(String[] args) {
        Customer customer = new Customer("huiming ", "wang","wws@gmail.com");
        System.out.println(customer);

        Customer customer1 = new Customer("first","last","email");
        System.out.println(customer1);
    }
}
