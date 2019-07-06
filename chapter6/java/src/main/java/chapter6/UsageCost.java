package main.java.chapter6;

import java.io.Serializable;
// Usage public class
public class UsageCost implements Serializable {
    int uid;                // user id
    String uname;            // username
    int usage;                // usage
    double cost;           // cost

    public UsageCost(int uid, String uname, int usage, double v) {
        this.uid = uid;
        this.uname = uname;
        this.usage = usage;
        this.cost = v;
    }

    public int getUid() { return this.uid; }
    public void setUid(int uid) { this.uid = uid; }
    public String getUname() { return this.uname; }
    public void setUname(String uname) { this.uname = uname; }
    public int getUsage() { return this.usage; }
    public void setUsage(int usage) { this.usage = usage; }
    public double getCost() { return this.cost;}
    public void setCost(double v) { this.cost =v; }

    public UsageCost() {
    }

    public String toString() {
        return "uid: '" + this.uid + "', uame: '" + this.uname + "', usage: '" + this.usage + "', cost: '" + this.cost;
    }
}
