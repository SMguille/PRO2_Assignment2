package Model;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User implements Runnable
{
  private String name;
  private Model model;

  public User(String name, Model model)
  {
    this.name = name;
    this.model = model;
  }

  public String getName()
  {
    return name;
  }


  @Override public void run()
  {
    while (true)
    {
      Random random = new Random();
      int option = random.nextInt(3) + 1;
      int selectedVinyl = random.nextInt(model.getVinylList().size());
      Vinyl vinyl = model.getVinylList().get(selectedVinyl);

      try
      {
        Thread.sleep(random.nextInt(3000) + 1000);
      }
      catch (InterruptedException e)
      {
        throw new RuntimeException(e);
      }
      switch (option)
      {
        case 1:
          model.reserveVinyl(vinyl, this.getName());
          break;
        case 2:
          model.borrowVinyl(vinyl, this.getName());
          break;
        case 3:
          model.returnVinyl(vinyl, this.getName());
          break;
      }
    }
  }
}
