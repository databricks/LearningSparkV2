package main.java.chapter6;

import java.io.Serializable;
// Usage public class
public class Usage implements Serializable {
    int uid;                // user id
    String uname;            // username
    int usage;                // usage

    public Usage(int uid, String uname, int usage) {
        this.uid = uid;
        this.uname = uname;
        this.usage = usage;
    }

    public int getUid() { return this.uid; }
    public void setUid(int uid) { this.uid = uid; }
    public String getUname() { return this.uname; }
    public void setUname(String uname) { this.uname = uname; }
    public int getUsage() { return this.usage; }
    public void setUsage(int usage) { this.usage = usage; }

    public Usage() {
    }

    public String toString() {
        return "uid: '" + this.uid + "', uame: '" + this.uname + "', usage: '" + this.usage + "'";
    }
}
