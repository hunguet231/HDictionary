package main.java.Dictionary_Application;

import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Views/Main.fxml"));
        primaryStage.getIcons().add(new Image("main/resources/icon.png"));

        primaryStage.setTitle("HDictionary");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        new FadeIn(root).play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
