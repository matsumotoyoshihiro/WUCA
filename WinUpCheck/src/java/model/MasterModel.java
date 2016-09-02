
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "tbl_master")
public class MasterModel implements Serializable{

    private int ID;             //プライマリキー
    private String familyName;
    private String Name;        //社員名
    private String PcName;      //PC名
    
    //以下のプロパティはちょっとした案
    private Date startDate;     //登録日
    private Date updateDate;    //データ更新日
    private int deleteFlag;     //論理削除用フラグ
    
    public MasterModel() {
        
    }

    public MasterModel(String familyName, String name, String pcName) {
        this.familyName = familyName;
        this.Name = name;
        this.PcName = pcName;
        this.startDate = new Date();
        this.updateDate = new Date();
        this.deleteFlag = 0;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
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

    @Temporal(TemporalType.DATE)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.DATE)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    
    
    
    
    
}
