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
import model.MasterModel;

/**
 *
 * @author User
 */
@Stateless
public class MasterDb {
    @PersistenceContext
    private EntityManager em;
    
    public void create(MasterModel masterModel) {
        em.persist(masterModel);
    }
    
       
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
    
    
    public List<MasterModel> getAll() {
        //createQuery出ないと全てのレコードを取得できないのかな？
        return em.createQuery("SELECT c FROM MasterModel c WHERE c.deleteFlag = 0").getResultList();
        
    }
}
