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
    
//    public String list(String pcName, int month) {
////        List<String> list = new ArrayList<String>();
////        Date date = new Date();
////        String forMonth = sdfMonth.format(date);
////        int realMonth = Integer.parseInt(forMonth);      
//        String status = null;  
//
//
//        for(int k = 0; k < getInputAll().size(); k++) {
//            InputModel inputAll = getInputAll().get(k);    
//            Integer inputMonth = inputAll.getRecodeTime().getMonth();
//            System.out.println(pcName +"="+ inputAll.getPcName());
//            System.out.println(month +"="+ inputMonth);
//            if(pcName.equals(inputAll.getPcName()) && month == inputMonth) {                
////            if(m == inputMonth) {
////                if (month == inputMonth) {
//                    
//                status = inputAll.getState();
//                        
//            } else {
//                status = null;
//            }
//        }
//        return status;
//    }
    
    public List testList() {        
        ArrayList<String> texts0=new ArrayList<String>();
        texts0.add("あ");
        texts0.add("い");
        texts0.add("う");
        texts0.add("え");
        texts0.add("お");
        
        ArrayList<String> texts1=new ArrayList<String>();
        texts1.add("か");
        texts1.add("き");
        texts1.add("く");
        texts1.add("け");
        texts1.add("こ");
        
        ArrayList<ArrayList<String>> textsList=new ArrayList<ArrayList<String>>();
        textsList.add(texts0);
        textsList.add(texts1);
        
        return texts0;
    }
    
    public List testList2() {  
        ArrayList<Integer> list=new ArrayList<Integer>();
        int a[][] = new int[9][9];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++) {
                a[i][j] = (i+1)*(j+1);
                list.add(a[i][j]);
            }
        }
        return list;    
    }
    public static void main() 
	  {
		    System.out.println("九九の表");

		    // 配列の宣言。
		    int kuku[][] = new int[10][10];

		    // 九九の結果を配列に保存。
		    for( int i=1; i<=9; i++ ) {
		      for( int j=1; j<=9; j++ ){
		    	  kuku[i][j] = i * j;
		      }
		    }

		    // 九九の結果を表示。
		    for( int i=1; i<=9; i++ ) {
		      for( int j=1; j<=9; j++ ) { 
		    	  System.out.print(kuku[i][j] + " "); 
		      }
		      	  System.out.println(); 
		    }
	  }
    
    public List list(int month) {
        List<String> list = new ArrayList<String>();
        List<InputModel> listInput = new ArrayList<InputModel>();
        String status = null;
        String m = String.valueOf(month);
        String m1 = String.valueOf(month+1);
//        for(int i = 0; i < 12; i++)  {
//            Object month = month().get(i);
        list.add("社員名＋PC名");
        for(int mst = 0; mst < getPcNameAll().size(); mst++) {
            MasterModel master = getAll().get(mst);
            String mstPcName = master.getName() + "：" +master.getPcName();
            list.add(mstPcName);
        }
            list.add(m1);
            
            for(int j = 0; j < getInputAll().size(); j++) {
                InputModel inputAll = getInputAll().get(j);
                Object inputMonth = inputAll.getRecodeTime().getMonth();
                String objInputMonth = inputMonth.toString();
                String inpPcName = inputAll.getPcName();
                
                for(int k = 0; k < getAll().size(); k++) {
                    MasterModel master = getAll().get(k);
                    String mstPcName = master.getName() + "：" +master.getPcName();
                    if (m.equals(objInputMonth) && mstPcName.equals(inpPcName)) {
                        status = inputAll.getState();
                    } else {
                        status = "null";
                    }
                    list.add(status);
                }                
            }
//        }
        return list;
    }
    
    public List<InputModel> getlist() {
        return db.getList();
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
