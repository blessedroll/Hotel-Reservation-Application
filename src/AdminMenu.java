import api.AdminResource;
import model.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class AdminMenu {
    private static AdminResource adminResource = AdminResource.getSingleton();

    public static void adminMenu(){
        String line;
        Scanner scanner = new Scanner(System.in);

        printMenu();

        try{
            do{
                line = scanner.nextLine();

                if(line.length() == 1){
                    switch (line.charAt(0)){
                        case '1':
                            displayAllCustomers();
                            break;
                        case '2':
                            displayAllRooms();
                            break;
                        case '3':
                            displayAllReservations();
                            break;
                        case '4':
                            addRoom();
                            break;
                        case '5':
                            MainMenu.printMainMenu();
                            break;
                        default:
                            System.out.println("Unknown action\n");
                            break;
                    }
                }else{
                    System.out.println("Error: Invalid action\n");
                }
            }while(line.charAt(0) != '5' || line.length() != 1);
        }catch (StringIndexOutOfBoundsException exception){
            System.out.println("Invalid input");
        }
    }

    private static void addRoom() {
        Scanner scanner = new Scanner(System.in);

        try{
            System.out.println("Please enter room number:");

        String roomNumber = scanner.nextLine();

        System.out.println("Please enter price per night:");
        Double price = Double.parseDouble(scanner.nextLine());

        System.out.println("Please enter room type(SINGLE/DOUBLE)");
        RoomType roomType = RoomType.valueOf(scanner.nextLine());

        Room room = new Room(roomNumber,price,roomType);

        adminResource.addRoom(Collections.singletonList(room));
        System.out.println("Room added successfully!");
        printMenu();
        }catch(Exception exception){
            System.out.println("Invalid input");
            printMenu();
        }


        System.out.println();
    }

    private static void displayAllReservations() {
        adminResource.displayAllReservations();
        printMenu();
    }

    private static void displayAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();

        if(customers.isEmpty()){
            System.out.println("No customers found");
        }else{
            customers.forEach(System.out::println);
        }
        printMenu();
    }


    private static void displayAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if(rooms.isEmpty()){
            System.out.println("No room found");
        }else{
            rooms.forEach(System.out::println);
        }
        printMenu();
    }

    private static void printMenu() {
        System.out.print("\nAdmin Menu\n" +
                "--------------------------------------------\n" +
                "1. See all Customers\n" +
                "2. See all Rooms\n" +
                "3. See all Reservations\n" +
                "4. Add a Room\n" +
                "5. Back to Main Menu\n" +
                "--------------------------------------------\n" +
                "Please enter a number for the menu option:\n");
    }
}
