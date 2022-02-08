package model;

public class FreeRoom extends Room{

    private boolean isFree;

    public FreeRoom(String roomNumber, Double price, RoomType enumeration, boolean isFree){
        super(roomNumber,0.0,enumeration);
        this.isFree = true;

    }
    @Override
    public String toString(){
        return super.toString() + "This is a free room.";
    }
    @Override
    public boolean isFree() {
        return true;
    }
}
