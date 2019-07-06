package main.java.chapter6;

import java.io.Serializable;

/* create a Java class */
public class Person implements Serializable {
    String uname;			// username
    int age;				// usage

    public Person(String uname, int age) {
        this.uname = uname;
        this.age = age;
    }

    public void setAge(int age) {this.age = age;}
    public int getAge() { return this.age; }
    public void setUname(String name) {this.uname = name;}
    public String getUname() {return this.uname;}
}