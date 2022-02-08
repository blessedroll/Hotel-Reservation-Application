import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static final String dateFormat = "MM/DD/YYYY";
    private static final HotelResource hotelResouce = HotelResource.getSingleton();

    public static void main(String[] args) {
        String line;

        Scanner scanner = new Scanner(System.in);

        printMainMenu();

        try{
            do{
                line = scanner.nextLine();
                if (line.length() == 1){
                    switch (line.charAt(0)){
                        case '1':
                            findAndReserveRoom();
                            break;
                        case '2':
                            seeMyReservations();
                            break;
                        case '3':
                            createAnAccount();
                            break;
                        case '4':
                            AdminMenu.adminMenu();
                            break;
                        case '5':
                            System.out.println("Exit the application");
                            System.exit(0);
                        default:
                            System.out.println("Unknown action\n");
                            break;
                    }
                }else{
                    System.out.println("Error: Invalid action\n");
                }
            }while(line.charAt(0) != '5' || line.length() != 1);
        }catch (StringIndexOutOfBoundsException ex){
            System.out.println("Invalid input");
        }
    }

    private static void createAnAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your Email");
        String email = scanner.nextLine();

        System.out.println("Please enter your first Name:");
        String firstName = scanner.nextLine();

        System.out.println("Please enter your last name");
        String lastName = scanner.nextLine();

        try{
            hotelResouce.createACustomer(email,firstName,lastName);
            System.out.println("Account created successfully！");

        }catch (IllegalArgumentException exception){
            System.out.println(exception.getLocalizedMessage());
            createAnAccount();
        }
        printMainMenu();
    }

    private static void seeMyReservations() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your email.");
        String customerEmail = scanner.nextLine();

        printReservations(hotelResouce.getCustomerReservations(customerEmail));
        printMainMenu();
    }

    private static void printReservations(Collection<Reservation> customerReservations) {
        if(customerReservations == null || customerReservations.isEmpty()){
            System.out.println("No reservation found");
        }else {
            customerReservations.forEach(Reservations -> System.out.println("\n" + Reservations));
        }
    }

    private static void findAndReserveRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter check-in Date (mm/dd/yyyy)");
        Date checkIn = getInputDate(scanner);

        System.out.println("Please enter check-out Date (mm/dd/yyyy)");
        Date checkOut = getInputDate(scanner);

        if (checkIn != null && checkOut != null){
            Collection<IRoom> availableRooms = hotelResouce.findARoom(checkIn, checkOut);
            if (availableRooms.isEmpty()){
                System.out.println("No available room found");
            }else{
                printRooms(availableRooms);
                reserveRoom(scanner, checkIn, checkOut, availableRooms);
            }
        }
    }

    private static Date getInputDate(Scanner scanner) {
        try{
            return new SimpleDateFormat(dateFormat).parse(scanner.nextLine());
        }catch (ParseException exception){
            System.out.println("Error: Invalid date.");
            findAndReserveRoom();
        }
        return null;
    }

    private static void reserveRoom(Scanner scanner, Date checkIn, Date checkOut, Collection<IRoom> availableRooms) {
        System.out.println("Would you like to book? Y/N");

        String bookRoom = scanner.nextLine();

        if ("Y".equals(bookRoom)){
            System.out.println("Do you have an account with us? Y/N");
            String haveAccount = scanner.nextLine();

            if ("Y".equals(haveAccount)){
                System.out.println("Please enter your Email.");
                String customerEmail = scanner.nextLine();

                if (hotelResouce.getCustomer(customerEmail) == null){
                    System.out.println("Customer not found. \n you may need to create a new account.");
                }else{
                    System.out.println("What room number would you like to reserve?");
                    String roomNumber = scanner.nextLine();

                    if (availableRooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomNumber))) {
                        IRoom room = hotelResouce.getRoom(roomNumber);

                        Reservation reservation = hotelResouce.bookARoom(customerEmail,room,checkIn,checkOut);
                        System.out.println("Reservation created successfully!");
                        System.out.println(reservation);
                    }else{
                        System.out.println("Error: room number not available. \n Start reservation again.");
                    }
                }
            }else{
                System.out.println("Please create an account");
                createAnAccount();
            }
        }else if("N".equals(bookRoom)){
            printMainMenu();
        }else{
            reserveRoom(scanner,checkIn,checkOut,availableRooms);
        }
        printMainMenu();
    }

    private static void printRooms(Collection<IRoom> availableRooms) {
        if(availableRooms.isEmpty()){
            System.out.println("No available rooms");
        }else{
            availableRooms.forEach(System.out::println);
        }
    }

    public static void printMainMenu() {
        System.out.println(
                "\nWelcome to the Hotel Reservation Application\n" +
                        "--------------------------------------------\n" +
                        "1. Find and reserve a room\n" +
                        "2. See my reservations\n" +
                        "3. Create an Account\n" +
                        "4. Admin\n" +
                        "5. Exit\n" +
                        "--------------------------------------------\n" +
                        "Please enter a number for the menu option:\n"
        );
    }
}
