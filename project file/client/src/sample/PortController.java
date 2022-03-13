package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.Socket;

public class PortController {

    private  Main main;

    @FXML
    private TextField portText;

    @FXML
    private  TextField hostText;


    public void connectAction(ActionEvent actionEvent) {
        String strport=portText.getText();
        String hostport=hostText.getText();
        try {
            int port = Integer.parseInt(strport);
            try {
                Socket socfd = new Socket(hostport, port);
                client t = new client(socfd);
                try {
                    main.showLoginPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Port or Hostname");
                alert.setHeaderText("The port is not valid for any existing server or Hostname is incorrect.");
                alert.setContentText("Enter the correct server port to connect with the legal hostname.");
                alert.showAndWait();
            }


        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Port");
            alert.setHeaderText("The port is not any valid input.");
            alert.setContentText("Enter the correct server port to connect.");
            alert.showAndWait();
        }
    }

    public void resetAction(ActionEvent actionEvent) {
        portText.setText(null);
        hostText.setText(null);
    }

    void setMain(Main main) {
        this.main = main;
    }


}
