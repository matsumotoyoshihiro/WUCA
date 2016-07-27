
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "tbl_master")
public class MasterModel implements Serializable{

    private int ID;
    private String Name;
    private String PcName;
    private int InputCheck;
    
    public MasterModel() {
        
    }

    public MasterModel(String name, String pcName, int inputCheck) {
        this.Name = name;
        this.PcName = pcName;
        this.InputCheck = inputCheck;
        
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    

    
    
    
}
