package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {
    //dafault access modifier: not specifying one
    static final ReservationService singleton = new ReservationService();

    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Map<String, Collection<Reservation>> reservations = new HashMap<>();

    private ReservationService(){

    }

    public static ReservationService getSingleton(){
        return singleton;
    }

    public void addRoom(IRoom room){
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId){
        return rooms.get(roomId);
    }
    public Collection<IRoom> getAllRooms(){
        return rooms.values();
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer,room,checkInDate,checkOutDate);

        Collection<Reservation> customerReservations = getCustomersReservation(customer);

        if (customerReservations == null){
            customerReservations = new LinkedList<>();
        }

        customerReservations.add(reservation);
        reservations.put(customer.getEmail(), customerReservations);

        return reservation;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        return findAvailableRooms(checkInDate, checkOutDate);

    }

    // default access modifier
    Collection<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {
        Collection<Reservation> allReservations = this.getAllReservation();
        Collection<IRoom> notAvailableRooms = new LinkedList<>();

        for (Reservation reservation: allReservations){
            if (reservationOverlaps(reservation, checkInDate, checkOutDate)){
                notAvailableRooms.add(reservation.getRoom());
            }
        }
        return  rooms.values().stream().filter(room -> notAvailableRooms.stream()
                        .noneMatch(notAvailableRoom -> notAvailableRoom.equals(room)))
                .collect(Collectors.toList());
    }

    private boolean reservationOverlaps(Reservation reservation, Date checkInDate, Date checkOutDate) {
        return checkInDate.before(reservation.getCheckOutDate())&& checkOutDate.after(reservation.getCheckInDate());
    }

    private Collection<Reservation> getAllReservation() {
        Collection <Reservation> allReservations = new LinkedList<>();

        for (Collection<Reservation> reservations: reservations.values()){
            allReservations.addAll(reservations);
        }

        return allReservations;
    }

    public void printAllReservation(){
        Collection<Reservation> reservations = this.getAllReservation();

        if(reservations.isEmpty()){
            System.out.println("No reservations found.");
        }else{
            for (Reservation reservation: reservations){
                System.out.println(reservation + "\n");
            }
        }

    }

}
