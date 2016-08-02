package control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    
    private String Name;
    private String PcName;    
    private String Status;
    private String Note; 
    private String strYear;        
    SimpleDateFormat sdfYear = new SimpleDateFormat("YYYY");         
    SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");    
    
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

    public String masterDisp() {
        return "master";
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
   
    public List month() {
        List<Integer> month = new ArrayList<Integer>();
        int i;
        
        for (i = 4; i <= 12; i++) {
            month.add(i);
        }
        for (i = 1; i <= 3; i++) {
            month.add(i);
        }
        return month;
    }    
    
//    public String list(String pcName, int month) {
////        List<String> list = new ArrayList<String>();
////        Date date = new Date();
////        String forMonth = sdfMonth.format(date);
////        int realMonth = Integer.parseInt(forMonth);      
//        String status = null;  
//
//
//            for(int k = 0; k < getInputAll().size(); k++) {
//                InputModel inputAll = getInputAll().get(k);    
//                Object inputMonth = inputAll.getRecodeTime().getMonth();
//        if(name.equals(inputAll.getPcName())) {                
////            if(m == inputMonth) {
//                if (month == inputMonth) {
//                    
//                    for (int i = 0; i < getAll().size(); i++) {
//                        MasterModel master = getAll().get(i);
//                        String pcName = master.getName() + "：" +master.getPcName();
//                        if(pcName.equals(inputAll.getPcName())) {
//                            status = inputAll.getState();
//                            
//                        }
//                        
//                    } 
//                }
//            }
//                    
//                        
//        }
//
//    }

}
