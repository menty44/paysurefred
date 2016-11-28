package helb;

import java.io.Serializable;

/**
 *
 * @author Joseph
 */
public class Student implements Serializable{
    
    private int id;
    private String fname;
    private String lname;
    private int idNo;

    public Student() {
    }

    public Student(int id, String fname, String lname, int idNo) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.idNo = idNo;
    }
    
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdNo() {
        return idNo;
    }

    public void setIdNo(int idNo) {
        this.idNo = idNo;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
            
}
