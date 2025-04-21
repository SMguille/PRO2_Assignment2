package Model;

public class BorrowedReservedState implements VinylState
{
  @Override public void onReserve(Vinyl vinyl)
  {
    vinyl.changeToBorrowed(); //Cancel only the reservation
  }

  @Override public void onBorrow(Vinyl vinyl)
  {
    //Nothing because is already reserved
  }

  @Override public void onReturn(Vinyl vinyl)
  {
    vinyl.changeToReserved();
  }

  @Override public String stateMessage(Vinyl vinyl)
  {
    return "Borrowed by " + vinyl.getBorrowedUserId() + " and reserved by " + vinyl.getReservedUserId();
  }
/*
  @Override public String deleteMessage(Vinyl vinyl)
  {
    return "Marked to be removed";
  }

 */

  @Override public String toString(){
    return "BorrowedReservedState";
  }
}
