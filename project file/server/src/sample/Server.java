package sample;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends  Thread {
    Socket ClientSoc;
    DataInputStream din;
    DataOutputStream dout;

    InfoClass in=new InfoClass();

    private static final String PASSFILE = "src"+File.separator+"resource"+File.separator+"clientPassword.txt";
    private static final String FILENAME="src"+File.separator+"resource"+File.separator+"serverFiles.txt";
    File file=new File(FILENAME);

    String usrname;

    Server(Socket soc) {
        try {
            ClientSoc = soc;
            din = new DataInputStream(ClientSoc.getInputStream());
            dout = new DataOutputStream(ClientSoc.getOutputStream());
            start();

        } catch (Exception ex) {
        }
    }



    public void run() {


        while (true) {
            try {
                String activator = din.readUTF();
                if (activator.compareTo("verification")==0){
                    in.infoSet("A client has connected.");
                    verify();
                }
                else if (activator.compareTo("browse") == 0) {
                    in.infoSet("Client is surfing on server.");
                    browse();
                }

                else if(activator.compareTo("split")==0){
                    in.infoSet("Client has initiated the download.");
                    split();
                }

                else if (activator.compareTo("download")==0){
                    in.infoSet("Connection established for uploading..");
                    in.infoSet("Passing data...");
                    upload();
                }

                else if (activator.compareTo("Request")==0){
                    in.infoSet("Client has sent a message.");
                    message();
                }
            } catch (Exception e) {

            }
        }

    }

    void verify() {
        String determiner="wrong";
        while (true) {
            try {
                String user = din.readUTF();
                String pass = din.readUTF();
                BufferedReader br = new BufferedReader(new FileReader(PASSFILE));
                String line;
                while (true) {
                    line = br.readLine();

                    if (line == null) {
                        dout.writeUTF(determiner);
                        break;
                    }
                    String[] lines=line.split(" ");
                    if (user.equals(lines[0])) {
                        if (pass.equals(lines[1])) {
                            dout.writeUTF("correct");
                            in.infoSet("Client verified @"+lines[0]);
                            setUsername(lines[0]);
                            determiner="correct";
                            break;
                        }
                    }

                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(determiner.equals("correct")) break;
            else{
                in.infoSet("Unauthorized client");
            }
        }

    }

    void setUsername(String name){
        usrname=name;
    }


    public void browse(){
        try{
            dout.writeUTF(file.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void split() throws FileNotFoundException {
        String filename="";
        try{
            filename=din.readUTF();
        }catch (Exception e){
            e.printStackTrace();
        }

        String fileToSplit="";
        String line;
        try {

            BufferedReader br = new BufferedReader(new FileReader(FILENAME));
            while (true) {
                line = br.readLine();
                if (line == null) break;
               if(line.contains(filename)){
                   break;
               }

            }
            fileToSplit=line;
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
            RandomAccessFile raf = new RandomAccessFile(fileToSplit, "r");
            long numSplits = 15;
            long sourceSize = raf.length();
            long bytesPerSplit = sourceSize / numSplits;
            long remainingBytes = sourceSize % numSplits;


            int maxReadBufferSize = 8 * 1024;
            for (int destIx = 1; destIx <= numSplits; destIx++) {
                BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(fileToSplit + ".part" + (destIx - 1)));
                if (bytesPerSplit > maxReadBufferSize) {
                    long numReads = bytesPerSplit / maxReadBufferSize;
                    long numRemainingRead = bytesPerSplit % maxReadBufferSize;
                    for (int i = 0; i < numReads; i++) {
                        readWrite(raf, bw, maxReadBufferSize);
                    }
                    if (numRemainingRead > 0) {
                        readWrite(raf, bw, numRemainingRead);
                    }
                } else {
                    readWrite(raf, bw, bytesPerSplit);
                }
                bw.close();
            }
            if (remainingBytes > 0) {
                BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(fileToSplit + ".part" + (numSplits)));
                readWrite(raf, bw, remainingBytes);
                bw.close();
            }
            raf.close();

            try{
                dout.writeUTF(Long.toString(bytesPerSplit));
                dout.writeUTF(Long.toString(remainingBytes));
            }catch (Exception e){
                System.out.println("The client is disconnected.");
                for(int i=0;i<16;i++){
                    File file=new File(fileToSplit+".part"+i);
                    boolean b=file.delete();
                }

            }
        }catch (Exception e){
           e.printStackTrace();
        }

    }

    static void readWrite(RandomAccessFile raf, BufferedOutputStream bw, long numBytes) throws IOException {
        byte[] buf = new byte[(int) numBytes];
        int val = raf.read(buf);
        if(val != -1) {
            bw.write(buf);
        }
    }




    void upload() throws Exception{

        ServerSocket ss=new ServerSocket(9754);
        try {
            for (int i = 0; i < 16; i++) {
                new UploadFile(ss.accept());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ss.close();
        }


    }

    void message(){
        String msg=null;
        try{
            msg=din.readUTF();
        }catch (Exception e){

        }
        in.msg("@"+usrname+" :-"+msg);
    }


}
