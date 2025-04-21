package Factory;

import Model.Model;

public class ModelFactory
{
  private volatile Model model;

  public Model getModel()
  {
    if (model == null)
    {
      synchronized (this)
      {
        if (model == null)
        {
          model = new Model();
        }
      }
    }
    return model;
  }
}
