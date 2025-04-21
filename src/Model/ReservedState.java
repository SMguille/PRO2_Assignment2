package Model;

public class ReservedState implements VinylState
{

  @Override public void onReserve(Vinyl vinyl)
  {
    //Nothing because it is already reserved (It changes to available if the user is the same in Model)
  }

  @Override public void onBorrow(Vinyl vinyl)
  {
    vinyl.changeToBorrowed();
  }

  @Override public void onReturn(Vinyl vinyl)
  {
    //We should be able to unreserve the vinyl ( by clicking reserve again)
  }

  @Override public String stateMessage(Vinyl vinyl)
  {
    return "Reserved by " + vinyl.getReservedUserId();
  }

  @Override public String toString(){
    return "ReservedState";
  }
}
