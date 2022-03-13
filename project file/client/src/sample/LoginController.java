package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;


public class LoginController {

    private client c;
    private  Main main;

    @FXML
    private TextField userText;

    @FXML
    private PasswordField passText;


    @FXML
    public void loginAction(ActionEvent actionEvent) {
        String userName = userText.getText();
        String password = passText.getText();

        c.check(userName,password);
        }


    @FXML
    public void resetAction(ActionEvent actionEvent) {
        userText.setText(null);
        passText.setText(null);
    }

    void setClient(client c) {
        this.c = c;
    }
    void setMain(Main main) {
        this.main = main;
    }
}
