package services;

import java.io.Serializable;

public class Printer implements Serializable{
    private String host;
    private int port;
    
    public Printer(String host,int port){
        this.host = host;
        this.port = port;
    }

    public String getHost(){ return this.host; }
    public int getPort(){ return this.port; }
}
