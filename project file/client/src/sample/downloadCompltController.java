package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class downloadCompltController {
    private Main main;


    @FXML
    private Label label;

    public void okAction(ActionEvent actionEvent) {
        try{
            main.showClientPanel();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setLabel(String s){
        label.setText(s);
    }

    void setMain(Main main) {
        this.main = main;
    }
}
