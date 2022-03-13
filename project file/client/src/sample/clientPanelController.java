package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class clientPanelController {
    private Main main;

    @FXML
    private Label fileSize;

    @FXML
    private ListView<String> sizeList;

    @FXML
    private Label user;

    @FXML
    private ListView<String> listView;

    @FXML
    private Label msg;


    public void browseAction(ActionEvent actionEvent) {
        fileSize.setText("File Size :");
        msg.setText("Select File to Download :");
        client c= new client();
        String fileDirectory=c.browse();
        try{
            String line;
            BufferedReader br = new BufferedReader(new FileReader(fileDirectory));
            listView.getItems().clear();
            sizeList.getItems().clear();
            int no=0;
            while (true){
                line = br.readLine();
                if (line == null) break;
                File f= new File(line);
                double size=f.length()/1024;
                listView.getItems().add(++no+". "+f.getName());
                sizeList.getItems().add(size + " KB");
            }
            br.close();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Null Server");
            alert.setHeaderText("There is no file in this server.");
            alert.showAndWait();
        }
    }

    public void downloadAction(ActionEvent actionEvent) {
        ObservableList<String> links;
            links= listView.getSelectionModel().getSelectedItems();
        List<String>clientSelectedDownload= new ArrayList<>();
        client c= new client();
        String fileDirectory=c.browse();
        try{
            String line;
            BufferedReader br = new BufferedReader(new FileReader(fileDirectory));
            while (true){
                line = br.readLine();
                if (line == null) break;
                for(int i=0;i<links.size();i++){
                    if(line.contains(links.get(i))){
                        clientSelectedDownload.add(line);
                    }
                }
            }
            br.close();
        }catch (Exception e){
           // e.printStackTrace();
        }


        try{
            String[] strs=links.get(0).split(" ");
            String f=strs[1];
            for(int i=2;i<strs.length;i++){
                f+=" "+strs[i];

            }

            main.downloadPage(f);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Null Selection");
            alert.setHeaderText("No file has been selected.");
            alert.showAndWait();
        }

    }
    @FXML
    public void request(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Request.fxml"));
            Parent root = loader.load();

            RequestController controller = loader.getController();

            Stage stage=new Stage();
            stage.setTitle("Request a message");
            stage.setScene(new Scene(root, 363, 257));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void logoutAction(ActionEvent actionEvent) {
        try {
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUser(String usr){
      user.setText("Username registered @_"+usr);
    }

    void setMain(Main main) {
        this.main = main;
    }


}
