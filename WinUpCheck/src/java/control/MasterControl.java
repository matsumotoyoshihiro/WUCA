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
        
    //新規登録入力項目の値取得
    @NotNull
    private String familyName;
    private String name;
    private String pcName;
    private String updateID;
    private String newFamilyName;
    private String newName;
    private String newPcName;
    private String deleteID;  //画面遷移した際に、初期値としてNULLが入ってくるため
                
    //どこのDBに接続するかの
    @EJB
    Db db;    
   
    public String next() {
        create();
        return null;
    }
    
    public String create() {       
        MasterModel master = new MasterModel(familyName, name, pcName);
                
        try {
            db.createMaster(master);
            clear();
        }catch(Exception e) {
            System.out.println("新規登録失敗！！！！！");
        }
    
        return "master?faces-redirect=true";
    }    
    
    //社員名・PC名の変更
    public String update() {
        if(updateID != null) {
            int id = Integer.parseInt(updateID);

            if(newFamilyName.length() > 0) {
                db.familyNameUpdate(id, newFamilyName);
            }

            if(newName.length() > 0) {
                db.nameUpdate(id, newName);
            }

            if(newPcName.length() > 0) {
                db.pcNameUpdate(id, newPcName);
            }
            clear();
        }
        return "master?faces-redirect=true";
    }

    //レコードの論理削除(表示させないようにする)
    public String dalete() {
        //入力値がNULLの場合の対処
        if(deleteID != null) {
            Integer id = Integer.parseInt(deleteID);
            db.rogicDelete(id);
            clear();
        }
        return "master?faces-redirect=true";
    }
    
    //各入力項目のクリア
    public void clear() {
        familyName = name = null;
        newFamilyName = newName = null;
        pcName = newPcName = null;
        updateID = deleteID = null; 
    }
    
    //画面遷移
    public String chDisp() {
        return "input.xhtml";
    }
    
   
    //DBから全てのデータを取得
    public List<MasterModel> getAll() {
         return db.getMstAll();
    }
    
    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public String getUpdateID() {
        return updateID;
    }

    public void setUpdateID(String updateID) {
        this.updateID = updateID;
    }

    public String getNewFamilyName() {
        return newFamilyName;
    }

    public void setNewFamilyName(String newFamilyName) {
        this.newFamilyName = newFamilyName;
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
