package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class LoginController {

    private Main main;

    @FXML
    private TextField userText;

    @FXML
    private PasswordField passwordText;


    private static final String PASSFILE = "src"+File.separator+"resource"+File.separator+"adminPassword.txt";

    @FXML
    void loginAction(ActionEvent event) {

        String userName = userText.getText();
        String password = passwordText.getText();
        String state="wrong";

        try {
            BufferedReader br = new BufferedReader(new FileReader(PASSFILE));
            String line;
            while (true) {
                line = br.readLine();

                if (line == null) {

                    break;
                }
                String[] lines=line.split(" ");
                if (userName.equals(lines[0])) {
                    if (password.equals(lines[1])) {
                        try {
                            state="right";
                            main.showAdmin();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }

            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

         if(state.equalsIgnoreCase("wrong")) {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Incorrect Credentials");
             alert.setHeaderText("Incorrect Credentials");
             alert.setContentText("The username and password you provided is not correct.");
             alert.showAndWait();
         }


    }

    @FXML
    void resetAction(ActionEvent event) {
        userText.setText(null);
        passwordText.setText(null);
    }

    void setMain(Main main) {
        this.main = main;
    }



}
