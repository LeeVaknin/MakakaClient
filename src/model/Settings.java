package model;


import java.io.Serializable;
import java.util.Observable;

public class Settings extends Observable implements Serializable {

    private static String ip;
    private static Integer port;
    private String defaultIp = "127.0.0.1";
    private Integer defaultPort = 8080;

    public Settings(String ip, int port) {
        setIp(ip);
        setPort(port);
    }

    public Settings() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setDefaultValues() {
        setIp(defaultIp);
        setPort(defaultPort);
    }

}
