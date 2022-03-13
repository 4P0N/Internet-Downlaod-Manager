package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RemoveClientController {
    private Main main;

    @FXML
    private ListView<String> list;

    @FXML
    private Label label;

    private static final String PASSFILE ="src"+File.separator+"resource"+File.separator+"clientPassword.txt";


    @FXML
    public void browseAction(ActionEvent actionEvent) {
        label.setText("Select Files to Remove :");
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
            e.printStackTrace();
        }
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    public void removeAction(ActionEvent actionEvent) {
        ObservableList<String> links;
        links= list.getSelectionModel().getSelectedItems();


        List<String> linkList = new ArrayList();
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(PASSFILE));
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
            String[] tem=oLink.split(" ");
            for(int i=0;i<linkList.size();i++){
                if((linkList.get(i).contains(tem[2]))){
                    index=i;
                }
            }
            if(index!=-1){
                linkList.remove(index);
                index=-1;
            }
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PASSFILE));
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
