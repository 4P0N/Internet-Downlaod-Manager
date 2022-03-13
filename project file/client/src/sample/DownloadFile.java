package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DownloadFile extends Thread {
    client c;
    String serverFile;
    String saveDirectory;

    List<Thread> thd= new ArrayList<>();

    DownloadFile(client c,String serverFile,String saveDirectory) {

        this.c=c;
        this.serverFile=serverFile;
        this.saveDirectory=saveDirectory;

        start();
    }


    public void  run(){
        try {
            for (int i = 0; i < 16; i++) {
                try {
                    Socket cs = new Socket(InetAddress.getLocalHost(),9754);
                    thd.add(new Download(c,cs, serverFile, saveDirectory + ".part" + i, i));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        int i;
        List<File> list = new ArrayList<File>();
        while (true){
            for (i=0;i<thd.size();i++){
                if(thd.get(i).isAlive()==true){
                    break;
                }
            }
            if(i==thd.size()){
                File ofile = new File(saveDirectory);
                FileOutputStream fos;
                FileInputStream fis;
                byte[] fileBytes;
                int bytesRead = 0;

                for(i=0;i<16;i++){
                    list.add(new File(saveDirectory+".part"+i));
                }

                try {
                    fos = new FileOutputStream(ofile,true);
                    for (File afile : list) {
                        fis = new FileInputStream(afile);
                        fileBytes = new byte[(int) afile.length()];
                        bytesRead = fis.read(fileBytes, 0,(int)  afile.length());
                        assert(bytesRead == fileBytes.length);
                        assert(bytesRead == (int) afile.length());
                        fos.write(fileBytes);
                        fos.flush();
                        fileBytes = null;
                        fis.close();
                        fis = null;
                        boolean b=afile.delete();
                    }
                    fos.close();
                    fos = null;
                }catch (Exception exception){
                    exception.printStackTrace();
                }
                break;
            }
        }

    }

}

