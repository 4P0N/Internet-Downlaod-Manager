package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminController {

    private Main main;

    @FXML
    private ListView<String> listView;
    @FXML
    private ListView<String> sizeList;
   @FXML
   private Label fileName;
   @FXML
   private Label fileSize;

    private static final String INPUT_FILE_NAME ="src"+File.separator+"resource"+File.separator+"serverFiles.txt";


    @FXML
    public void browseAction(ActionEvent actionEvent) {
        try{
            fileName.setText("File Names : ");
            fileSize.setText("File Size :");
            String line;
            BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
            listView.getItems().clear();
            sizeList.getItems().clear();
            int no=0;
            while (true){
                line = br.readLine();
                if (line == null) break;
                File f=new File(line);
                double size=f.length()/1024;
                listView.getItems().add(++no+". "+line);
                sizeList.getItems().add(size+ " KB");
            }
            br.close();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Null Server");
            alert.setHeaderText("There is no file in server.");
            alert.setContentText("First, click 'Add File' button to add file into the server");
            alert.showAndWait();
        }
    }
    @FXML
    public void addAction(ActionEvent actionEvent) {
        List<String> linkList = new ArrayList();
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
            while (true) {
                line = br.readLine();
                if (line == null) break;
                linkList.add(line);

            }
            br.close();
        } catch (Exception e) {

        }
        listView.getItems().clear();
        FileChooser fc= new FileChooser();
        fc.setTitle("Select File :");
        List<File>selectedFiles= fc.showOpenMultipleDialog(null);

        if(selectedFiles!=null){
            boolean b=false;
            for(int i=0;i< selectedFiles.size();i++){
                int j;
                for(j=0;j<linkList.size();j++){
                    if((selectedFiles.get(i).getAbsolutePath().equals(linkList.get(j)))){
                        break;
                    }
                }
                if(j==linkList.size()) linkList.add(selectedFiles.get(i).getAbsolutePath());
            }
        }
        for(String str : linkList){
            listView.getItems().add(str);
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(INPUT_FILE_NAME));
            for (int i = 0; i < linkList.size(); i++) {
                String tem = linkList.get(i);
                bw.write(tem);
                bw.write("\n");
            }

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @FXML
    public void removeAction(ActionEvent actionEvent) {
       try{
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("RemoveFile.fxml"));
           Parent root = loader.load();

           RemoveFileController controller = loader.getController();

           Stage stage=new Stage();

           stage.setTitle("Remove File");
           stage.setScene(new Scene(root, 500, 400));
           stage.show();
       }catch(Exception e){
           e.printStackTrace();
       }
    }

    @FXML
    public void nextAction(ActionEvent actionEvent) {
        try{
            main.Connect();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void logoutAction(ActionEvent event) {
        try {
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void clientAction(ActionEvent actionEvent) {
        try {
            main.clientPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void setMain(Main main) {
        this.main = main;
    }



}
