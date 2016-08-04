/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.InputModel;
import model.MasterModel;

@Stateless
public class Db {
    @PersistenceContext
    private EntityManager em;
    private static final String QUERY_NAME = "SELECT c.familyName from MasterModel c WHERE c.inputCheck = 1 ORDER BY c.familyName desc";
    private static final String QUERY_PCNAME = "SELECT concat(c.familyName, '：' , c.pcName) from MasterModel c ORDER BY c.familyName desc";

    public void create(InputModel input) {
        em.persist(input);
    }
    
    public void createMaster(MasterModel masterModel) {
        em.persist(masterModel);
    }
    
    
    public List<MasterModel> getAll() {
        return em.createQuery("SELECT c FROM MasterModel c order by c.familyName desc").getResultList();
    }
    
    public List<MasterModel> getNameAll() {
        return em.createQuery(QUERY_NAME)
                .getResultList();
    }
    
    public List<MasterModel> getPcNameAll() {
        return em.createQuery(QUERY_PCNAME)
                .getResultList();
    }

    public List<InputModel> getInputAll() {
        return em.createQuery("SELECT i FROM InputModel i order by i.FamilyName desc")
                .getResultList();
    }
//    
//    public List<InputModel> getList() {
//        return em.createQuery("select m.name, i.pcname, i.recodetime, i.status from MasterModelr m left join tbl_input i on i.pcname = concat(m.name, '：' , m.pcName)  order by name desc")
//                .getResultList();
//    }
    
    public void rogicDelete(int id) {
        MasterModel master = em.find(MasterModel.class, id);
        master.setDeleteFlag(1);
    }

	public void nameUpdate(int id, String newName){
        MasterModel master = em.find(MasterModel.class, id);
        master.setName(newName);
        master.setUpdateDate(new Date());
    }
    
    public void pcNameUpdate(int id, String newPcName) {
        MasterModel master = em.find(MasterModel.class, id);
        master.setPcName(newPcName);
        master.setUpdateDate(new Date());
    }
}