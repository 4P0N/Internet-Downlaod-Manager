package sample;


import java.io.*;
import java.net.Socket;

public class Download extends Thread{

    String serverFile="";
    int serial;
    String destinationFile="";
    client c;
    Socket cs;
    DataInputStream din;
    DataOutputStream dout;

    Download(client c,Socket cs,String serverFile, String destinationFile,int no){
        try {
            this.c=c;
            this.destinationFile=destinationFile;
            this.serverFile=serverFile;
            serial=no;
            this.cs=cs;
            din=new DataInputStream(cs.getInputStream());
            dout=new DataOutputStream(cs.getOutputStream());
            start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void  run(){
        File check = new File(destinationFile);
        long size=0;
        try {
            dout.writeUTF(serverFile);
            dout.writeUTF(String.valueOf(serial));
            size = Long.parseLong(din.readUTF());
        }catch (Exception e) {
            e.printStackTrace();
        }

        if(check.exists() && check.length()==size) {
            try{
                dout.writeUTF("exist");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else {
            FileOutputStream fout;
            try {
                dout.writeUTF("upload");
                fout = new FileOutputStream(destinationFile);
                byte[] b=new byte[20480];
                int read;
                try{
                    while ((read=din.read(b))!= -1) {
                        fout.write(b,0,read);
                        if(check.length()==size) break;

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                fout.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



}