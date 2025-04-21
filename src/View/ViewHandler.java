package View;

import javafx.stage.Stage;
import Factory.ViewFactory;

public class ViewHandler {

  private Stage primaryStage;
  private ViewFactory viewFactory;

  public void start(Stage primaryStage, ViewFactory viewFactory) {
    this.viewFactory = viewFactory;
    this.primaryStage = primaryStage;
    openFirstView();
  }

  public void openFirstView() {

  }

  public void openSecondView() {
  }
}
