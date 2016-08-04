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
import javax.faces.model.SelectItem;
import javax.inject.Named;
import model.InputModel;
import model.MasterModel;
//import model.MasterModel;

@Named
@RequestScoped
public class InputControl {
    
    private String Name;
    private String PcName;    
    private String Status;
    private String Note; 
    private String strYear;     
    
    private Map<String, String> yearMap = new HashMap<>();

    @EJB
    Db db;

    private static Map<String, String> itemName;    
    private static Map<String, String> itemPcName;    
    private static Map<String, String> itemStatus;
    private List<SelectItem> itemNameList = null;

    static {
//        MasterModel master = new MasterModel();
        itemName = new LinkedHashMap<>();
        itemName.put("松村", "松村");
        itemName.put("赤間", "赤間"); 
        itemName.put("山内", "山内");
        itemName.put("松本", "松本");
//        itemName.put(master.getName(),master.getName());
    }
    
    static {
        itemPcName = new LinkedHashMap<>();
        itemPcName.put("松村：OCC-PC1", "松村：OCC-PC1");
        itemPcName.put("赤間：OCC-PC2", "赤間：OCC-PC2");
        itemPcName.put("山内：OCC-PC3", "山内：OCC-PC3");
        itemPcName.put("松本：OCC-PC4", "松本：OCC-PC4");
    }      
    
    static {
        itemStatus = new LinkedHashMap<>();
        itemStatus.put("◯", "0");
        itemStatus.put("※", "1");
        itemStatus.put("", null);
    }          

//    public List<SelectItem> getItemNameList() {
//        ArrayList<SelectItem> itemNamelist = new ArrayList<SelectItem>();
//        return Db.getAllName();
//    }

    public void setItemNameList(List<SelectItem> itemNameList) {
        this.itemNameList = itemNameList;
    }
    

    public Map<String, String> getItemName() {
        return itemName;
    }

    public Map<String, String> getItemPcName() {
        return itemPcName;
    }

    public Map<String, String> getItemStatus() {
        return itemStatus;
    } 
    
    
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
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
    
    public String getStrYear() {
        Date date = new Date();
        SimpleDateFormat sdfYear = new SimpleDateFormat("YYYY");         
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
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

    public String masterDisp() {
        return "master";
    }        
    
    public String next() {
        create();
        return null;
    }
        
    public void create() { 
        InputModel input = new InputModel(Name, PcName, Status, Note);
        try {
            db.create(input);
            clear();
        } catch (Exception e) {
            System.out.println("DB登録できていません");
        }                
    }
    
    public void clear() {
        Name = null;
        PcName = null;
        Status = null;
        Note = null;
    }
    
    public List<MasterModel> getAll() {
        return db.getAll();
    }
    
    public List<MasterModel> getAllName() {
        return db.getAllName();
    }
    
    public List<MasterModel> getAllPcName() {
        return db.getAllPcName();
    }    
    
    public List<InputModel> getInputAll() {
        return db.getInputAll();
    }
    
    public List updateList(){
        createYearMap();
        
        //viewに送るリスト
        List<List> updateList = new ArrayList<>();
        
        //ヘッダーの作成
        List<String> haeder = new ArrayList<>();
        haeder.add("社員名：PC名");

        for(int i = 1; i <= yearMap.size(); i++) {
            haeder.add(yearMap.get(String.valueOf(i)));
        }
        
        //ヘッダ用リスト作成
        updateList.add(haeder);
                
        List<MasterModel> masterAll = getAll();
        List<InputModel> inputAll = getInputAll();
        for (int i = 0; i < masterAll.size(); i++) {
            //各社員の情報をリスト化
            List<String> emproyList = new ArrayList<>();
            
            //マスターテーブルを1行ずつ取得
            MasterModel masterRecoed = masterAll.get(i);
            //社員名：PC名の追加
            String emproy = masterRecoed.getName() + "：" + masterRecoed.getPcName();
            emproyList.add(emproy);
            
            //一度リストに値を全て入れる
            for(int j = 1; j <= yearMap.size(); j++) {
                emproyList.add("　");
            }
            
            //ここから下はaddではなくアップデート
            
            //ここからは各月のステータス
            for(int j = 0; j < inputAll.size(); j++) {
                //DBに登録されているインプットテーブルを取得
                InputModel inputRecoed = inputAll.get(j);
                    
                //取得した各テーブルの「名前：PC名」一致しているかチェック
                if(emproy.equals(inputRecoed.getPcName())) {
                        
                    //どの月かをチェックする
                    //まずはデータ加工(月だけ取得)
                    String updateMonth = new SimpleDateFormat("M").format(inputRecoed.getRecodeTime());
                    
                    
                    for(int k = 1; k <= yearMap.size(); k++) {
                        if(updateMonth.equals(yearMap.get(String.valueOf(k)))) {
                            if(inputRecoed.getState() != null) {
                                emproyList.set(k, inputRecoed.getState());
                            }else {
                                emproyList.set(k, "　");
                            }
                        }
                    }

                }            
            }
                
            //最後にこのリストを全体のリストに追加
            updateList.add(emproyList);
        }            
            return updateList;
    }
    
    //4月始まりのカレンダー
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
    
}
