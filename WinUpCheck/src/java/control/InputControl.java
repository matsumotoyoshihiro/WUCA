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
    
    @EJB
    Db db;

    private static Map<String, String> itemStatus;
    
    static {
        itemStatus = new LinkedHashMap<>();
        itemStatus.put("◯", "0");
        itemStatus.put("※", "1");
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
    
    public String getStrYear() {
        InputModel input = new InputModel(Name, PcName, Status, Note);
        Date date = new Date();
        SimpleDateFormat sdfYear = new SimpleDateFormat("YYYY");         
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
        String forYear = sdfYear.format(date);        
        String forMonth = sdfMonth.format(date);
        int year = Integer.parseInt(forYear);                  
        int month = Integer.parseInt(forMonth);
//int month = input.getRecodeTime().getMonth();
        
        if (month < 4) {
            year = year - 1 ;
        }
        
        strYear = String.valueOf(year);        
        
        return strYear;        
    }

    public String masterDisp() {
        return "master";
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
    
    public void indicate() {
        InputModel input = new InputModel(Name, PcName, Status, Note);
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
//        String strRecodeMonth = sdfMonth.format(input.getRecodeTime());
int strRecodeMonth = input.getRecodeTime().getMonth();
//        int intRecodeMonth = Integer.parseInt(strRecodeMonth);
        int numRows = getAll().size();
        
        for (int i = 0; i < numRows; i++) {
        if(getAll().contains(PcName)) {
            System.out.println("aaa");
        }
        }

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
    
    public List<MasterModel> getAll() {
        return db.getAll();
    }
    
//    public void list() {
//        InputModel input = new InputModel();
//        for (int i = 0; i < getAll().size; i++) {
//            String pc = getall(i);
//            for (int j = 0; j < 13 ; j++) {
//                m = montt.get(j);
//                pc , m
//            }
//        }
//    }
    
    public List<MasterModel> getAllName() {
        return db.getAllName();
    }
    
    public List<MasterModel> getAllPcName() {
        return db.getAllPcName();
    }    
}
