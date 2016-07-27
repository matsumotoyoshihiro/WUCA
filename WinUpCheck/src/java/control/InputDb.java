/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.InputModel;

@Stateless
public class InputDb {
    @PersistenceContext
    private EntityManager em;

    public void create(InputModel input) {
        em.persist(input);
    }
}
