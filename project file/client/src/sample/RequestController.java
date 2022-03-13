package sample;

import javafx.event.ActionEvent;

public class RequestController {
    public javafx.scene.control.TextArea msgField;

    public void sendAction(ActionEvent actionEvent) {
        client c=new client();
        String tem=msgField.getText();
        msgField.clear();
        c.sendMsg(tem);
    }
}
