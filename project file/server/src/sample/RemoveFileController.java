package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RemoveFileController {

    private Main main;

    @FXML
    private ListView<String> removeList;

    @FXML
    private Label label;


    private static final String INPUT_FILE_NAME ="src"+File.separator+"resource"+File.separator+"serverFiles.txt";

    @FXML
    public void browseAction(ActionEvent actionEvent) {
        label.setText("Select Files to Remove :");
        try{
            String line;
            BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
            removeList.getItems().clear();
            while (true){
                line = br.readLine();
                if (line == null) break;
                removeList.getItems().add(line);
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        removeList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    @FXML
    public void removeAction(ActionEvent actionEvent) {
        ObservableList<String> links;
        links= removeList.getSelectionModel().getSelectedItems();

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
            e.printStackTrace();
        }
        int index= -1;
        for(String oLink : links){
            for(int i=0;i<linkList.size();i++){
                if(oLink.equals(linkList.get(i))){
                    index=i;
                }
            }
            if(index!=-1){
                linkList.remove(index);
                index=-1;
            }
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


    void setMain(Main main) {
        this.main = main;
    }



}
