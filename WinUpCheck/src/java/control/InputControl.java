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
import javax.inject.Named;
import model.InputModel;
import model.MasterModel;
//import model.MasterModel;

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
    private Map<String, String> yearMap = new HashMap<>();    
    
    @EJB
    Db db;

    private static Map<String, String> itemStatus;
    
    static {
        itemStatus = new LinkedHashMap<>();
        itemStatus.put("◯", "◯");
        itemStatus.put("※", "※");
        itemStatus.put("", null);
    }              

    public Map<String, String> getItemStatus() {
        return itemStatus;
    } 
    
    
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

    public void setNote(String Note) {
        this.Note = Note;
    }         
        
    public void create() { 
        InputModel input = new InputModel(FamilyName, PcName, Status, Note);
        try {
            db.create(input);
            clear();
        } catch (Exception e) {
            System.out.println("DB登録できていません");
        }                
    }
    
    public void clear() {
        FamilyName = null;
        PcName = null;
        Status = null;
        Note = null;
    }

    public String masterDisp() {
        return "master";
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

    public List<InputModel> getInputList() {
        return db.getInputList();
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
        //ヘッダーの作成
        List<String> haeder = new ArrayList<>();
        List<MasterModel> masterAll = getMstAll();
        List<InputModel> inputAll = getInputAll();         
        
        haeder.add("社員名：PC名");
        for(int i = 1; i <= yearMap.size(); i++) {
            haeder.add(yearMap.get(String.valueOf(i)));
        }
         
        //ヘッダ用リスト作成
        updateList.add(haeder);
                         
        for (int i = 0; i < masterAll.size(); i++) {
            //各社員の情報をリスト化
            List<String> empList = new ArrayList<>();
            //マスターテーブルを1行ずつ取得
            MasterModel masterRecoed = masterAll.get(i);
            //社員名：PC名の追加
            String emp = masterRecoed.getFamilyName()+ "：" + masterRecoed.getPcName();
             
            empList.add(emp);
            //一度リストに値を全て入れる
            for(int j = 1; j <= yearMap.size(); j++) {
                empList.add(null);
            }
             
            //ここから下はaddではなくアップデート             
            //ここからは各月のステータス
            for(int j = 0; j < inputAll.size(); j++) {
                //DBに登録されているインプットテーブルを取得
                InputModel inputRecord = inputAll.get(j);
                     
                //取得した各テーブルの「名前：PC名」一致しているかチェック
                if(emp.equals(inputRecord.getPcName())) {
                         
                    //どの月かをチェックする
                    //まずはデータ加工(月だけ取得)
                    String updateMonth = new SimpleDateFormat("M").format(inputRecord.getRecodeTime());
                                        
                    for(int k = 1; k <= yearMap.size(); k++) {
                        if(updateMonth.equals(yearMap.get(String.valueOf(k)))) {
                            if(inputRecord.getState() != null) {
                                empList.set(k, inputRecord.getState());
                            }else {
                                empList.set(k, null);
                            }
                        }
                    }
                }            
            }
            //最後にこのリストを全体のリストに追加
            updateList.add(empList);
        }            
        return updateList;
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
    
    static ArrayList<InputModel> arrayStr = new ArrayList<InputModel>();
//    public
}