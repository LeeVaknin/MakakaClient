package Model;

import netscape.javascript.JSObject;

public class Settings {
    private int id;
    private String key;
    private JSObject value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public JSObject getValue() {
        return value;
    }

    public void setValue(JSObject value) {
        this.value = value;
    }
}
