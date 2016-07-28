/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import model.MasterModel;

/**
 *
 * @author User
 */
@Named
@RequestScoped
public class MasterControl implements Serializable{
    
    private MasterControl masterControl;
    
    //新規登録入力項目の値取得
    @NotNull
    private String fristName;
    private String lastName;
    private String pcName;
    private int inputCheck;
    private boolean checkBox;    //HTMLからはString型で取得するため、一度こちらで受け取る
    
    private String updateID;
    private String newName;
    private String newPcName;
    
    private String deleteID;
        
    
    
    //どこのDBに接続するかの宣言？
    @EJB
    MasterDb db;

    
   
    public String next() {
        isSelect();
        create();
        return null;
    }
    
    public String create() {
        String name = fristName + " " + lastName;
        MasterModel master = new MasterModel(name, pcName, inputCheck);
        try {
            db.create(master);
            clear();
        }catch(Exception e) {
            System.out.println("新規登録失敗！！！！！");
        }
        return null;
    }
    
    public String update() {
        int id = Integer.parseInt(updateID);
        
        if(newName.length() > 0) {
            db.nameUpdate(id, newName);
        }
        if(newPcName.length() > 0) {
            db.pcNameUpdate(id, newPcName);
        }
        clear();
        return null;
    }

    public String dalete() {
        int id = Integer.parseInt(deleteID);
        db.rogicDelete(id);
        clear();
        return null;
    }
        
    public void clear() {
        fristName = lastName= null;
        pcName = null;
        updateID = null; 
        
        
    }
    
    public String chDisp() {
        return "input.xhtml";
    }
    
    public String isSelect() {
        if(checkBox) {
            inputCheck = 1;
        }else {
            inputCheck = 0;
        }
        return null;   
    }

    
    
    
    //DBから全てのデータを取得
    public List<MasterModel> getAll() {
         return db.getAll();
    }
    
    
    
    public String getFristName() {
        return fristName;
    }

    public void setFristName(String fristName) {
        this.fristName = fristName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public int getInputCheck() {
        return inputCheck;
    }

    public void setInputCheck(int inputCheck) {
        this.inputCheck = inputCheck;
    }
    
    public boolean getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public String getUpdateID() {
        return updateID;
    }

    public void setUpdateID(String updateID) {
        this.updateID = updateID;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewPcName() {
        return newPcName;
    }

    public void setNewPcName(String newPcName) {
        this.newPcName = newPcName;
    }

    public String getDeleteID() {
        return deleteID;
    }

    public void setDeleteID(String deleteID) {
        this.deleteID = deleteID;
    }

    
    

}
