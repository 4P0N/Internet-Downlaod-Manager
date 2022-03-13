package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.net.ServerSocket;
public class Main extends Application {


    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;
        showLoginPage();
    }

    public void showLoginPage() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setMain(this);

        stage.setTitle("Login");
        stage.setScene(new Scene(root, 350, 500));
        stage.show();

    }

    public void showAdmin() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Admin.fxml"));
        Parent root = loader.load();

        AdminController controller = loader.getController();
        controller.setMain(this);

        stage.setTitle("Admin Panel");
        stage.setScene(new Scene(root, 618, 410));
        stage.show();

    }


    public void clientPage() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("client.fxml"));
        Parent root = loader.load();

        ClientController controller = loader.getController();
        controller.setMain(this);

        stage.setTitle("Client Control");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }


    public void Connect() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Connect.fxml"));
        Parent root = loader.load();

        ConnectController controller = loader.getController();
        controller.setMain(this);

        stage.setTitle("Start Server");
        stage.setScene(new Scene(root, 314, 401));
        stage.show();
    }

    public void finalUI(int port) throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FinalUI.fxml"));
            Parent root = loader.load();

            FinalUIController controller = loader.getController();
            controller.setPort(port);

            stage.setTitle("Server Status");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public static void main(String[] args) {
        launch(args);
    }
}




