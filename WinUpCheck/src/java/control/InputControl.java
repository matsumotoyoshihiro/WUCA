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
    
    public List<MasterModel> getAll() {
        return db.getAll();
    }    
    
    public List<MasterModel> getNameAll() {
        return db.getNameAll();
    }
    
    public List<MasterModel> getPcNameAll() {
        return db.getPcNameAll();
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
    
    public void monthMap(int i) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        
        map.put(0, 4);
        map.put(1, 5);
        map.put(2, 6);
        map.put(3, 7);
        map.put(4, 8);
        map.put(5, 9);
        map.put(6, 10);
        map.put(7, 11);
        map.put(8, 12);
        map.put(9, 1);
        map.put(10, 2);
        map.put(11, 3);
        
    }
    
    public List list() {
        List<List> list = new ArrayList<List>();
        List <String> header = new ArrayList<String>();
        String status = null;
        
        //ヘッダーを一行目に配置
        header.add("社員名＋PC名");
        for (int i = 0; i < 12; i++) {
            header.add(month().get(i).toString());
        }
        list.add(header);
        
        //
        for(int j = 0; j < getAll().size(); j++) {
            List<String> listList = new ArrayList<String>();
            MasterModel master = getAll().get(j);
            String mstPcName = master.getFamilyName()+ "：" +master.getPcName();
            listList.add(mstPcName);            
                     
             for(int k = 0; k < getInputAll().size(); k++) {
                        InputModel inputAll = getInputAll().get(k);
                        String inpPcName = inputAll.getPcName();       
                        String inputMonth = sdfMonth.format(inputAll.getRecodeTime());
                        Integer intInputMonth = Integer.parseInt(inputMonth);
                        String strInputMonth = String.valueOf(intInputMonth);
                for(int l = 0; l < 12; l++) {
                    String strMonth = month().get(l).toString();
                    status = null;
                   
                    
                        if (strMonth.equals(strInputMonth) && mstPcName.equals(inpPcName)) {
                            status = inputAll.getState();
                        }
                    listList.add(status);
                    }
                }
            list.add(listList);                        
            }
        return list;
    }
    
    public List list2() {
        List<String> list = new ArrayList<String>();
//        InputModel input = new InputModel();
        Date date = new Date();
        String forMonth = sdfMonth.format(date);
        int realMonth = Integer.parseInt(forMonth);      
        String status = null;

        for (int j = 0; j < 12 ; j++) {
            Object month = month().get(j);
            System.out.println("month:"+month);     
        
            for(int k = 0; k < getInputAll().size(); k++) {
                InputModel inputAll = getInputAll().get(k);    
                Object inputMonth = inputAll.getRecodeTime().getMonth();
                
                if (month == inputMonth) {
                    
                    for (int i = 0; i < getAll().size(); i++) {
                        MasterModel master = getAll().get(i);
                        String pcName = master.getName() + "：" +master.getPcName();
                        if(pcName.equals(inputAll.getPcName())) {
                            status = inputAll.getState();
                            
                        }
                        
                    } 
                        list.add(status);
                    }else {
                        list.add("n");
                    
                }
                                                                 
//                    System.out.println(inputAll.getPcName());
//                    System.out.println(pcName);
//                    if(pcName.equals(inputAll.getPcName())) {
                    
                        
                    }
        }
        return list;
    }

}
