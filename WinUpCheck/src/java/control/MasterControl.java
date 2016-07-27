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
    
    //入力項目の値取得？？
    @NotNull
    private String fristName;
    private String lastName;
    private String pcName;
    private int inputCheck;
    
    private boolean checkBox;    //HTMLからはString型で取得するため、一度こちらで受け取る
    
    //どこのDBに接続するかの宣言？
    @EJB
    InputDb db;

    
   
    public String next() {
        isSelect();
        create();
        return null;
    }
    
    public String create() {
        String name = fristName + " " + lastName;
        MasterModel master = new MasterModel(name, pcName, inputCheck);
        try {
            db.createMaster(master);
            clear();
        }catch(Exception e) {
            System.out.println("新規登録失敗！！！！！");
        }
        return null;
    }
    
    public void clear() {
        fristName = lastName= null;
        pcName = null;
        
        
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
    
    //DBから全てのデータを取得
    public List<MasterModel> getAll() {
         return db.getAll();
    }
}
