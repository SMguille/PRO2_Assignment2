package Model;

public class AvailableState implements VinylState
{
  @Override public void onReserve(Vinyl vinyl)
  {
    vinyl.changeToReserved();
  }

  @Override public void onBorrow(Vinyl vinyl)
  {
    vinyl.changeToBorrowed();
  }

  @Override public void onReturn(Vinyl vinyl)
  {
    //Nothing because it is available
  }
/*
  @Override public void onDelete(Vinyl vinyl)
  {

  }

 */

  @Override public String stateMessage(Vinyl vinyl)
  {
    return "Available";
  }
/*
  @Override public String deleteMessage(Vinyl vinyl)
  {
    //Removed automatically
    return "";
  }

 */

  @Override public String toString(){
    return "AvailableState";
  }
}
