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
    private int inputCheck = 1;     //デフォルトで入力者に追加するように設定
    
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
        //DBに登録されている全てのレコードを取得 (社員数やPCが増えたら全てのレコード取得は効率が悪い)
        //新しくWhereを含んだSQLのメソッドを作成するか悩む
        List<MasterModel> dbRecord = db.getMstAll();

        for (MasterModel nameList : dbRecord) {
            //登録しようとしている名前がすでにDBに登録されていないかチェック
            String fullName = nameList.getFamilyName() + " " +nameList.getName();
            if(nameList.getInputCheck() == 1 && name.equals(fullName)){
                inputCheck = 0;
            }
        }
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
        return null;
        
    }

    //レコードの論理削除(表示させないようにする)
    public void dalete() {
        //入力値がNULLの場合の対処
        if(deleteID != null) {
            Integer id = Integer.parseInt(deleteID);
            db.rogicDelete(id);
            clear();
        }
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
    

    //boolean型からint型に変換
//    public String isSelect() {
//        if(checkBox) {
//            inputCheck = 1;
//        }else {
//            inputCheck = 0;
//        }
//        return null;   
//    }

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
    
//    public boolean getCheckBox() {
//        return checkBox;
//    }
//
//    public void setCheckBox(boolean checkBox) {
//        this.checkBox = checkBox;
//    }

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
