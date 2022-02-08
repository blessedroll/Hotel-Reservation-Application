package model;

import java.util.Date;

public class Reservation {
    Customer customer;
    IRoom room;
    Date checkInDate;
    Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }



    @Override
    public String toString(){
        return "Customer:" + this.customer.toString() + "\nRoom: " +this.room.toString() + "\nCheck in date: " + this.checkInDate + "\nCheck out date:" + this.checkOutDate;
    }

   public IRoom getRoom(){
        return this.room;
   }

   public Date getCheckInDate(){
        return this.checkInDate;
   }

   public Date getCheckOutDate(){
        return this.checkOutDate;
   }
}
