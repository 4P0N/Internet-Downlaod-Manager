package sample;

import java.io.*;
import java.net.Socket;

public class UploadFile extends Thread{
    Socket cs;
    DataInputStream din;
    DataOutputStream dout;

    private static final String FILENAME="src"+File.separator+"resource"+File.separator+"serverFiles.txt";

    public UploadFile(Socket cs) {
        try {
            this.cs = cs;
            din = new DataInputStream(cs.getInputStream());
            dout = new DataOutputStream(cs.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
        start();
    }

    public void run(){
        String file = "";
        String no = "";
        try {
            file = din.readUTF();
            no = din.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String fileToSend = "";
        String line;
        try {
            BufferedReader brr = new BufferedReader(new FileReader(FILENAME));
            while (true) {
                line = brr.readLine();
                if (line == null) break;
                if (line.contains(file)) {
                    break;
                }

            }
            fileToSend = line;
            brr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        String finalFile = fileToSend + ".part" + no;
        File f=new File(finalFile);
        String msg=null;
        try{
            dout.writeUTF(Long.toString(f.length()));
            msg=din.readUTF();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(msg.equalsIgnoreCase("upload")) {
            downloadFile(finalFile);
        }
        else{
            boolean b=f.delete();
        }


    }

    void downloadFile(String file){
        File f=new File(file);
        FileInputStream fin=null;
        try {
            fin=new FileInputStream(f);
            byte[] b=new byte[20480];
            int read;
            while ((read=fin.read(b))!= -1){
                dout.write(b,0,read);
            }
        }catch (Exception e){
            System.out.println("The Client is disconnected.");
        }finally {
            try{
                fin.close();
                File fi=new File(file);
                boolean b=fi.delete();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        boolean b=f.delete();

    }
}
