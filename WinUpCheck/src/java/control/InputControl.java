package control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import model.InputModel;
import model.MasterModel;

@Named
@RequestScoped
public class InputControl {
    
    private String FamilyName;
    private String PcName;    
    private String Status;
    private String Note; 
    private String strYear;        
    SimpleDateFormat sdfYear = new SimpleDateFormat("YYYY");         
    SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
    private static Map<String, String> itemStatus;    
    private Map<String, String> yearMap = new HashMap<>();    
    
    @EJB
    Db db;            
    
    public String getFamilyName() {
        return FamilyName;
    }

    public void setFamilyName(String FamilyName) {
        this.FamilyName = FamilyName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
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
    
    public List<MasterModel> getMstAll() {
        return db.getMstAll();
    }    
    
    public List<MasterModel> getMstFamNameAll() {
        return db.getMstFamNameAll();
    }
    
    public List<MasterModel> getMstPcNameAll() {
        return db.getMstPcNameAll();
    }     
    
    public List<InputModel> getInputAll() {
        return db.getInputAll();
    }

    public List<InputModel> getInputNoteList() {
        return db.getInputNoteList();
    }    
    
    public List<InputModel> getInputTimeList() {
        return db.getInputTimeList();
    }      
    
    public void setNote(String Note) {
        this.Note = Note;
    }         
        
    public String masterDisp() {
        return "master";
    }  
    
    public String create() { 
        InputModel input = new InputModel(FamilyName, PcName, Status, Note);
        //何も入力せずに更新ボタンを押した場合
        if(!FamilyName.equals("")) {
            try {
                db.create(input);
                clear();
            } catch (Exception e) {
                System.out.println("DB登録できませんでした。");
            }       
            return "input?faces-redirect=true";
        } else if(FamilyName.equals("")){
            FacesContext context = FacesContext.getCurrentInstance();
            UIComponent component = context.getViewRoot().findComponent("myForm:familyNameField");            
            String clientId = component.getClientId(context);
            FacesMessage message = new FacesMessage("※入力者が未選択です。");
            context.addMessage(clientId, message);
            return null;
        } else {
            return null;
        }
    }  
    
    public void clear() {
        FamilyName = null;
        PcName = null;
        Status = null;
        Note = null;
    }
   
    //年度
    public String getStrYear() {
        Date date = new Date();
        String forYear = sdfYear.format(date);        
        String forMonth = sdfMonth.format(date);
        int year = Integer.parseInt(forYear);                  
        int month = Integer.parseInt(forMonth);
        
        if (month < 4) {
            year = year - 1 ;
        }
        
        strYear = String.valueOf(year);        
        return strYear;        
    }

    public List updateList(){
        createYearMap();
         
        //viewに送るリスト
        List<List> updateList = new ArrayList<>();
        String[] data = new String[2];
        
        //ヘッダーの作成
        List<String[]> haeder = new ArrayList<>();
        List<MasterModel> masterAll = getMstAll();
        List<InputModel> inputTime = getInputTimeList();
        data[0] = "社員名：PC名";
        data[1] = null;
        
        haeder.add(data.clone());
        for(int i = 1; i <= yearMap.size(); i++) {
            data[0] = yearMap.get(String.valueOf(i));
            data[1] = null;
            haeder.add(data.clone());
        }
         
        //ヘッダ用リスト作成
        updateList.add(haeder);
                         
        for (int i = 0; i < masterAll.size(); i++) {
            //各社員の情報をリスト化
            List<String> empList = new ArrayList<>();
            List<String[]> empList2 = new ArrayList<>();
            //マスターテーブルを1行ずつ取得
            MasterModel masterRecoed = masterAll.get(i);
            //社員名：PC名の追加
            data[0] = masterRecoed.getFamilyName()+ "：" + masterRecoed.getPcName();
            data[1] = null;
            
            String emp = masterRecoed.getFamilyName()+ "：" + masterRecoed.getPcName();
            
            empList.add(emp);
            empList2.add(data.clone());
            
            //一度リストに値を全て入れる
            for(int j = 1; j <= yearMap.size(); j++) {
                data[0] = null;
                data[1] = null;
                empList2.add(data.clone());
            }
             
            //ここから下はaddではなくアップデート             
            //ここからは各月のステータス
            for(int j = 0; j < inputTime.size(); j++) {
                //DBに登録されているインプットテーブルを取得
                InputModel inputTime2 = inputTime.get(j);
                     
                //取得した各テーブルの「名前：PC名」一致しているかチェック
                if(emp.equals(inputTime2.getPcName())) {
                         
                    //どの月かをチェックする
                    //まずはデータ加工(月だけ取得)
                    String updateMonth = new SimpleDateFormat("M").format(inputTime2.getRecodeTime());
                                        
                    for(int k = 1; k <= yearMap.size(); k++) {
                        if(updateMonth.equals(yearMap.get(String.valueOf(k)))) {
                            if(inputTime2.getState() != null) {
                                data[0] = inputTime2.getState();
                                data[1] = inputTime2.getNote();
                                empList2.set(k, data.clone());
                            } else {
                                data[0] = null;
                                data[1] = null;
                                empList2.set(k, data.clone());
                            }
                        }
                    }
                }
            }
            //最後にこのリストを全体のリストに追加
            updateList.add(empList2);            
        }                  
        return updateList;
    }                 

    //DB登録時の値
    static {
        itemStatus = new LinkedHashMap<>();        
        itemStatus.put("◯", "◯");
        itemStatus.put("※", "※");
        itemStatus.put("", null);
        
    }              

    public Map<String, String> getItemStatus() {
        return itemStatus;
    } 
    
    //4月始まりの月カレンダー
    private void createYearMap() {
        yearMap.put("1", "4");
        yearMap.put("2", "5");
        yearMap.put("3", "6");
        yearMap.put("4", "7");
        yearMap.put("5", "8");
        yearMap.put("6", "9");
        yearMap.put("7", "10");
        yearMap.put("8", "11");
        yearMap.put("9", "12");
        yearMap.put("10", "1");
        yearMap.put("11", "2");
        yearMap.put("12", "3");
    }
    
    //同姓同名チェック
    public List inputName() {
        List<MasterModel> famNameAll = getMstFamNameAll();
        List<String> inputNameList = new ArrayList<>();
        
        int flag = 0;
        
        for (int i = 0; i < famNameAll.size(); i++) {
            String masterRecoed = String.valueOf(famNameAll.get(i));
            
            if(i == 0) {
                inputNameList.add(masterRecoed);                            
            }
            //登録しようとしている名前がすでにDBに登録されていないかチェック
            for (int j = 0; j < inputNameList.size(); j++) {
                flag = 1;
                String inputRecord = inputNameList.get(j);
                if(masterRecoed.equals(inputRecord)) {
                    flag = 0;
                    break;                  
                }
            }
            if(flag == 1) {
                inputNameList.add(masterRecoed);
            }
        }
        return inputNameList;
    }    
}