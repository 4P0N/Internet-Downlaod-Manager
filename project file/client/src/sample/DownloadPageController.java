package sample;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class DownloadPageController{

    private Main main;
    @FXML
    private Label label;
    @FXML
    private Label user;
    @FXML
    private Label labelStatus;
    @FXML
    private TextField savetoText;
    @FXML
    private Button start;
    @FXML
    private Button backButton;
    @FXML
    private ProgressBar pBar;


private String msg;

    client c= new client();

    List<String> poges=new ArrayList<>();
    File savedFile=null;

    @FXML
    public void savetoAction(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            fileChooser.setInitialFileName(msg);
            savedFile = fileChooser.showSaveDialog(null);
            savetoText.setText(savedFile.getAbsolutePath());
        }catch (Exception e){

        }
    }

    public void backAction(ActionEvent actionEvent) {
        try {
            main.showClientPanel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startAction(ActionEvent actionEvent) {
        backButton.setDisable(true);
        start.setDisable(true);
        String savetoDirectory=savetoText.getText();
        for(int i=0;i<16;i++){
            String tem= savetoDirectory+".part"+ i;
            poges.add(tem);
        }


        new SplitFilenDownload(c,msg,savetoDirectory);

        labelStatus.setText("Downloading...");


        Task task= taskWorker(16);
        pBar.setStyle("-fx-accent: green;");
        pBar.progressProperty().unbind();
        pBar.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(e->{
            try{
                main.downloadComplt(msg);
            }catch (Exception err){
                err.printStackTrace();
            }
        });
        Thread t= new Thread(task);
        t.start();


    }

    void setMain(Main main) {
        this.main = main;
    }

    void setLabel(String x){
        msg=x;
        label.setText(x);
    }

    public void setUser(String usr){
        user.setText("Username registered @_"+usr);
    }


    private Task taskWorker(int number){
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int cnt;
                while (true){
                    cnt=0;
                    for(String s : poges){
                        File f= new File(s);
                        if(f.exists() &&(f.length()==c.getMainSize() || f.length()==c.getRemainS())){
                            cnt++;
                        }
                    }
                    updateProgress(cnt, number);
                    if(cnt==number) break;
                }
                return true;
            }
        };
    }


}
