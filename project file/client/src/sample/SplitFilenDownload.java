package sample;

public class SplitFilenDownload extends Thread {
    String file;
    client c;
    String saveDirectory;

    SplitFilenDownload(client c,String file,String saveDirectory){
        this.c=c;
        this.file=file;
        this.saveDirectory=saveDirectory;
        start();
    }

    public void run(){
        c.splitFile(file);
        c.downloadFile(c,file,saveDirectory);
    }
}
