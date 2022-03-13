package sample;

import java.util.ArrayList;
import java.util.List;

public class InfoClass {

    static List<String>s=new ArrayList<>();
    static List<String>sm=new ArrayList<>();

    public  void infoSet(String str){
        s.add(str);

    }

    public static List<String> infoGet(){
        return s;
    }

    public void msg(String s){
        sm.add(s);
    }

    public static List<String> msgGet() {
        return sm;

    }

}
