package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.ServerSocket;

public class ConnectController {

    private Main main;

    @FXML
    private TextField portText;

    @FXML
    public void connectAction(ActionEvent actionEvent) {
        String strPort=portText.getText();
        try {
            int port = Integer.parseInt(strPort);

            if(port<1024 || port>65535){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Port");
                alert.setHeaderText("The port is not available.");
                alert.setContentText("Try another port within range from 1024 to 65535.");
                alert.showAndWait();
            }
            else {
                try {
                    ServerSocket ss=new ServerSocket(port);
                    new connectThread(ss);
                    main.finalUI(port);
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Port");
                    alert.setHeaderText("The port is not available.");
                    alert.setContentText("Try another port within range from 1024 to 65535.");
                    alert.showAndWait();
                }
            }

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Port");
            alert.setHeaderText("The port is not any valid input.");
            alert.setContentText("Try a port within integer range from 1024 to 65535.");
            alert.showAndWait();
        }
    }

    public void backAction(ActionEvent actionEvent) {
        try{
            main.showAdmin();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void resetAction(ActionEvent actionEvent) {
        portText.setText(null);
    }

    void setMain(Main main) {
        this.main = main;
    }



}
