package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.util.List;



public class FinalUIController{

    @FXML
    private  ListView<String> activity;
    @FXML
    private ListView<String> msg;
    @FXML
    private Label port;


    public void refreshAction(ActionEvent actionEvent) {
        activity.getItems().clear();
        msg.getItems().clear();
        List<String>str=InfoClass.infoGet();
        List<String>str1=InfoClass.msgGet();

        for (String s:str){
            activity.getItems().add(s);
        }

        for (String s:str1){
            msg.getItems().add(s);
        }
    }

    public void setPort(int p){
        port.setText(Integer.toString(p));
    }
}
