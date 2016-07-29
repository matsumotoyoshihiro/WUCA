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
    private String familyName;
    private String name;
    private String pcName;
    private int inputCheck;
    private boolean checkBox;    //HTMLからはString型で取得するため、一度こちらで受け取る
    
    private String updateID;
    private String newFamilyName;
    private String newPcName;
    
    private String deleteID;
        
    
    
    //どこのDBに接続するかの宣言？
//    @EJB
//    MasterDb db;
    @EJB
    Db db;
    
   
    public String next() {
        isSelect();
        create();
        return null;
    }
    
    public String create() {
        //同じ人を入力者一覧に追加しないようにフルネームにする
        String fullName = familyName + " " + name;
        checkSameName(fullName);
        
        MasterModel master = new MasterModel(familyName, name, pcName, inputCheck);
                
        try {
            db.createMaster(master);
            clear();
        }catch(Exception e) {
            System.out.println("新規登録失敗！！！！！");
        }
    
        return null;
    }
    
    //同姓同名チェック
    public void checkSameName(String name) {
        //入力者追加のチェックボックスにチェックが付いているか判断
        if(inputCheck == 1) {
            //DBに登録されている全てのレコードを取得 (社員数やPCが増えたら全てのレコード取得は効率が悪い)
            List<MasterModel> dbRecod = db.getAll();
          
            for (MasterModel nameList : dbRecod) {
                //登録しようとしている名前がすでにDBに登録されていないかチェック
                String fullName = nameList.getFamilyName() + " " +nameList.getName();
                if(nameList.getInputCheck() == 1 && name.equals(fullName)){
                    inputCheck = 0;
                }
            }
        }
    }
    
    
    //社員名・PC名の変更
    public String update() {
        int id = Integer.parseInt(updateID);
        
        if(newFamilyName.length() > 0) {
            db.nameUpdate(id, newFamilyName);
        }
        if(newPcName.length() > 0) {
            db.pcNameUpdate(id, newPcName);
        }
        clear();
        return null;
    }

    //レコードの論理削除(表示させないようにする)
    public String dalete() {
        int id = Integer.parseInt(deleteID);
        db.rogicDelete(id);
        clear();
        return null;
    }
    
    //各入力項目のクリア
    public void clear() {
        familyName = name= null;
        pcName = null;
        updateID = deleteID = null; 
        
        
    }
    
    //画面遷移
    public String chDisp() {
        return "input.xhtml";
    }
    
   
    //DBから全てのデータを取得
    public List<MasterModel> getAll() {
         return db.getAll();
    }
    

    //boolean型からint型に変換
    public String isSelect() {
        if(checkBox) {
            inputCheck = 1;
        }else {
            inputCheck = 0;
        }
        return null;   
    }

    //
    //以下のメソッドは各入力項目やボタン等のゲッターセッター
    //
    
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

    public String getNewFamilyName() {
        return newFamilyName;
    }

    public void setNewFamilyName(String newFamilyName) {
        this.newFamilyName = newFamilyName;
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
