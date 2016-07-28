package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_input")
public class InputModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    
    @NotNull
    private String Name;
    
    @NotNull
    private String PcName;

    private String Status;
    
    private String Note;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date RecodeTime;               
    
    public InputModel() {
    }

    public InputModel(String Name, String PcName, String Status, String Note) {
        
        this.Name = Name;
        this.PcName = PcName;
        this.Status = Status;
        this.Note = Note;
        this.RecodeTime = new Date();
    }    
    
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

    public String getState() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }    

    public Date getRecodeTime() {
        return RecodeTime;
    }

    public void setRecodeTime(Date RecodeTime) {
        this.RecodeTime = RecodeTime;
    }    
}
