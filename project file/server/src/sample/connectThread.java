package sample;

import java.net.ServerSocket;

public class connectThread extends Thread{
    ServerSocket ss;

    connectThread(ServerSocket ss){
        this.ss=ss;
        start();
    }

    public void run(){
        try {
            while (true) {
                Server t = new Server(ss.accept());
            }
        }catch (Exception e){
           e.printStackTrace();
        }
    }
}
