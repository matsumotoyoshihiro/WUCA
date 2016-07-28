/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.InputModel;
import model.MasterModel;

@Stateless
public class InputDb {
    @PersistenceContext
    private EntityManager em;
    
    private static final String INPUTCHECK = "inputCkeck";
    private static final String QUERY_NAME = "SELECT name FROM MasterModel c where c.inputcheck=:inputCkeck";

    public void create(InputModel input) {
        em.persist(input);
    }
    
    public void createMaster(MasterModel masterModel) {
        em.persist(masterModel);
    }
    
    
    public List<MasterModel> getAll() {
        return em.createQuery("SELECT c FROM MasterModel c").getResultList();
    }
    
    public List<MasterModel> getAllName() {
        return em.createQuery(QUERY_NAME)
                .setParameter(INPUTCHECK, 1)
                .getResultList();
    }
    
    public List<MasterModel> findAll() {
        javax.persistence.criteria.CriteriaQuery cq
                = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(MasterModel.class));
        return em.createQuery(cq).getResultList();
    }
}
