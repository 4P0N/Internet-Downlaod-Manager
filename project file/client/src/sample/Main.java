package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {

    Stage stage;
    String username;

    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;
        showPortPage();
    }

    public void showPortPage() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("cPort.fxml"));
        Parent root = loader.load();

        PortController controller = loader.getController();
        controller.setMain(this);

        stage.setTitle("Client");
        stage.setScene(new Scene(root, 331, 400));
        stage.show();


    }

    public void showLoginPage() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("cLogin.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        client c=new client();
        controller.setClient(c);
        controller.setMain(this);
        c.setMain(this);

        stage.setTitle("Client");
        stage.setScene(new Scene(root, 325, 381));
        stage.show();
    }

    public void setUserTitle(String name){
        username=name;
    }


    public void showClientPanel() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("clientPanel.fxml"));
        Parent root = loader.load();

        clientPanelController controller = loader.getController();
        controller.setMain(this);
        controller.setUser(username);

        stage.setTitle("Client Panel");
        stage.setScene(new Scene(root, 600, 381));
        stage.show();
    }

    public void downloadPage(String name) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("cDownload.fxml"));
        Parent root = loader.load();

        DownloadPageController controller = loader.getController();
        controller.setMain(this);
        controller.setUser(username);
        controller.setLabel(name);

        stage.setTitle("Download File");
        stage.setScene(new Scene(root, 600, 271));
        stage.show();
    }

    public void downloadComplt(String file) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("dCmplt.fxml"));
        Parent root = loader.load();

        downloadCompltController controller = loader.getController();
        controller.setMain(this);
        controller.setLabel(file);

        stage.setTitle("Success");
        stage.setScene(new Scene(root, 465, 182));
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
