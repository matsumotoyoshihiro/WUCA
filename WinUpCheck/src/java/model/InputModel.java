package model;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Id;
import javax.persistence.Table;

@Named
@RequestScoped
@Table(name = "tbl_input")
public class InputModel {
    private int ID;
    private String Name;
    private String State;
    private String RecodeTime;
    private String PcName;
    private String Note;

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

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getRecodeTime() {
        return RecodeTime;
    }

    public void setRecodeTime(String RecodeTime) {
        this.RecodeTime = RecodeTime;
    }

    public String getPcName() {
        return PcName;
    }

    public void setPcName(String PcName) {
        this.PcName = PcName;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }
    
    public String next() {
        return "master.xhtml";
    }
}
