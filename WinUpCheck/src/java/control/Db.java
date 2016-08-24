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
    private static final String QUERY_MST = "SELECT mst FROM MasterModel mst WHERE mst.deleteFlag = 0 ORDER BY mst.familyName desc";
    private static final String QUERY_MSTFAMNAME = "SELECT mst.familyName from MasterModel mst WHERE mst.deleteFlag = 0 ORDER BY mst.familyName desc";
    private static final String QUERY_MSTPCNAME = "SELECT concat(mst.familyName, '：' , mst.pcName) from MasterModel mst ORDER BY mst.familyName desc";
    private static final String QUERY_INP = "SELECT inp FROM InputModel inp ORDER BY inp.FamilyName desc";
    private static final String QUERY_INPNOTE = "SELECT inp.status, imp.note FROM InputModel inp ORDER BY inp.FamilyName desc";
    private static final String QUERY_INPTIME = "SELECT inp FROM InputModel inp ORDER BY inp.RecodeTime";

    public void create(InputModel input) {
        em.persist(input);
    }
    
    public void createMaster(MasterModel masterModel) {
        em.persist(masterModel);
    }
    
    public List<MasterModel> getMstAll() {
        return em.createQuery(QUERY_MST).getResultList();
    }
    
    public List<MasterModel> getMstFamNameAll() {
        return em.createQuery(QUERY_MSTFAMNAME).getResultList();
    }
    
    public List<MasterModel> getMstPcNameAll() {
        return em.createQuery(QUERY_MSTPCNAME).getResultList();
    }

    public List<InputModel> getInputAll() {
        return em.createQuery(QUERY_INP).getResultList();
    }
    
    public List<InputModel> getInputNoteList() {
        return em.createQuery(QUERY_INPNOTE).getResultList();
    }

    public List<InputModel> getInputTimeList() {
        return em.createQuery(QUERY_INPTIME).getResultList();
    }     
 
    public void rogicDelete(int id) {
        MasterModel master = em.find(MasterModel.class, id);
        master.setDeleteFlag(1);
    }
    
    public void familyNameUpdate(int id, String newFamilyName){
        MasterModel master = em.find(MasterModel.class, id);
        master.setFamilyName(newFamilyName);
        master.setUpdateDate(new Date());
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