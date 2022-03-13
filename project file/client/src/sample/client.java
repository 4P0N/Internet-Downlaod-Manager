package sample;


import javafx.scene.control.Alert;
import java.io.*;
import java.net.Socket;


public class client {



    Socket ClientSoc;

    static DataInputStream din;
    static DataOutputStream dout;
    BufferedReader br;

    private Main main;

    client(Socket soc)
    {

        try {
            ClientSoc=soc;
            din=new DataInputStream(ClientSoc.getInputStream());
            dout=new DataOutputStream(ClientSoc.getOutputStream());
            br=new BufferedReader(new InputStreamReader(System.in));
        }
        catch(Exception ex)
        {

        }
    }


    client()
    {
        try {
            din=new DataInputStream(ClientSoc.getInputStream());
            dout=new DataOutputStream(ClientSoc.getOutputStream());
            br=new BufferedReader(new InputStreamReader(System.in));
        }
        catch(Exception ex)
        {

        }
    }


    public void check(String user, String password){

        try {
            dout.writeUTF("verification");
            dout.writeUTF(user);
            dout.writeUTF(password);
            String msg= din.readUTF();
            if(msg.equals("correct")){
                try{
                    main.setUserTitle(user);
                    main.showClientPanel();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText("The username and password you provided is not correct.");
                alert.showAndWait();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }



    public String browse(){
        String directory ="";
        try{
            dout.writeUTF("browse");
            directory=din.readUTF();
        }catch (Exception e){
            e.printStackTrace();
        }

        return directory;
    }



    static long mainS;
    static long remainS;

    void splitFile(String filename){

try {
            dout.writeUTF("split");
            dout.writeUTF(filename);
            mainS=Long.parseLong(din.readUTF());
            remainS=Long.parseLong(din.readUTF());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    void downloadFile(client c,String mssg,String saveDirectory) {

        try {
            dout.writeUTF("download");
        } catch (Exception e) {
            e.printStackTrace();
        }

        new DownloadFile(c,mssg,saveDirectory);

    }

    public long getMainSize(){
        return  mainS;
    }
    public long getRemainS(){
        return remainS;
    }


    public void sendMsg(String msg){
        try{
            dout.writeUTF("Request");
            dout.writeUTF(msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void setMain(Main main){
        this.main = main;
    }

}
