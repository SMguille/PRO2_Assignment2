package Model;

public class BorrowedState implements VinylState
{
  @Override public void onReserve(Vinyl vinyl)
  {
    vinyl.changeToBorrowedReserved();
  }

  @Override public void onBorrow(Vinyl vinyl)
  {
    //Nothing because it is already borrowed
  }

  @Override public void onReturn(Vinyl vinyl)
  {
    vinyl.changeToAvailable();
  }

  @Override public String stateMessage(Vinyl vinyl)
  {
    return "Borrowed by " + vinyl.getBorrowedUserId();
  }

  @Override public String toString(){
    return "BorrowedState";
  }
}
