package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientController {

    private Main main;

    @FXML
    private ListView<String> list;

    @FXML
    private TextField usr;
    @FXML
    private PasswordField pass;

    private static final String PASSFILE ="src"+File.separator+"resource"+File.separator+ "clientPassword.txt";
    @FXML
    public void browseAction(ActionEvent actionEvent) {
        try{
            String line;
            BufferedReader br = new BufferedReader(new FileReader(PASSFILE));
            list.getItems().clear();
            int cnt=0;
            while (true){
                line = br.readLine();
                if (line == null) break;
                String[] lines=line.split(" ");
                list.getItems().add(++cnt + " : " + lines[0]);
            }
            br.close();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Null Client");
            alert.setHeaderText("There is no client registered to the server.");
            alert.setContentText("First, add clients to register into this server.");
            alert.showAndWait();
        }
    }
    @FXML
    public void removeAction(ActionEvent actionEvent) {
       try{
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("RemoveClient.fxml"));
           Parent root = loader.load();

           RemoveClientController controller = loader.getController();

           Stage stage=new Stage();
           stage.setTitle("Remove File");
           stage.setScene(new Scene(root, 266, 327));
           stage.show();
       }catch (Exception e){
           e.printStackTrace();
       }
    }
    @FXML
    public void resetAction(ActionEvent actionEvent) {
        usr.setText(null);
        pass.setText(null);
    }

    @FXML
    public void confirmAction(ActionEvent actionEvent) {
        String user=usr.getText();
        String pas=pass.getText();
        String wrt=user + " " + pas;
        List<String>list=new ArrayList<>();
        list.add(wrt);
        try{
            String line;
            BufferedReader br = new BufferedReader(new FileReader(PASSFILE));
            while (true){
                line = br.readLine();
                if (line == null) break;
                list.add(line);
            }
            br.close();
        }catch (Exception e){

        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PASSFILE));
            for (int i = 0; i < list.size(); i++) {
                bw.write(list.get(i));
                bw.write("\n");
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        usr.setText(null);
        pass.setText(null);
    }

    @FXML
    public void backAction(ActionEvent actionEvent) {
        try{
            main.showAdmin();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void setMain(Main main) {
        this.main = main;
    }
}
