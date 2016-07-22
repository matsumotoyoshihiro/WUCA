
package model;

import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "tbl_master")
public class MastarModel {
    
    private int ID;
    private String Name;
    private String PcName;
    private int InputCheck;

    @Id
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPcName() {
        return PcName;
    }

    public void setPcName(String PcName) {
        this.PcName = PcName;
    }

    public int getInputCheck() {
        return InputCheck;
    }

    public void setInputCheck(int InputCheck) {
        this.InputCheck = InputCheck;
    }
    
    public String backDisp() {
        return "index";
    }
    
    
    
}
