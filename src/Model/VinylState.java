package Model;

public interface VinylState
{
  void onReserve(Vinyl vinyl);
  void onBorrow(Vinyl vinyl);
  void onReturn(Vinyl vinyl);
  String stateMessage(Vinyl vinyl);
  //String deleteMessage(Vinyl vinyl);
  default String asString() {
    return this.getClass().getSimpleName();
  }

}
