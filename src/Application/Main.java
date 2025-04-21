package Application;

import Factory.ModelFactory;
import Factory.ViewFactory;
import Factory.ViewModelFactory;
import Network.NetworkClient;
import View.ViewHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
  private ViewHandler viewHandler;

  public static void main(String[] args)
  {
    launch();
  }

  @Override public void start(Stage primaryStage) throws Exception
  {
    NetworkClient network = new NetworkClient("172.25.32.1", 8000);
    network.start(); // Connects to server

    ModelFactory modelFactory = new ModelFactory();
    ViewModelFactory viewModelFactory = new ViewModelFactory(network);
    ViewFactory viewFactory = new ViewFactory(viewModelFactory, primaryStage);

    ViewHandler viewHandler = new ViewHandler();
    viewHandler.start(primaryStage, viewFactory);
    viewFactory.getVinylListView();
    viewFactory.getCreateVinylView();


/*
    Model model = modelFactory.getModel();

    List<User> userList = new ArrayList<>();
    List<Thread> threads = new ArrayList<>();

    // Number of users
    int numUsers = 3;

    // Create users in a loop
    for (int i = 1; i <= numUsers; i++) {
      User user = new User("User " + i, model);
      userList.add(user);

      // Create a thread for each user
      Thread thread = new Thread(user);
      thread.setDaemon(true);
      threads.add(thread);
    }
    for (Thread thread : threads){
      thread.start();
    }

 */
  }
}
