package model;

public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration){
        super();
        this.roomNumber = roomNumber;
        this.price =  price;
        this.enumeration = enumeration;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber){
        this.roomNumber = roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    public void setRoomPrice(Double price){
        this.price = price;
    }
    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    public void setRoomType(RoomType enumeration){
        this.enumeration = enumeration;
    }
    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString(){
        return "Room Number: " + roomNumber + "\nPrice: " + price + "\nRoom Type: " + enumeration;
    }
    //override .equals()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()){
                return false;
        }
        Room room = (Room) obj;
        return (room.price.equals(this.price) && room.roomNumber == this.roomNumber);

    }
    //override .hashCode()
    @Override
    public int hashCode(){
        return Integer.parseInt(this.roomNumber);
    }

}