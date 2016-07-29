package control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import model.InputModel;
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
    InputDb db;

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
//        return InputDb.getAllName();
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
    
//    public List<MasterModel> getAllName() {
//        return db.getAllName();
//    }
}
