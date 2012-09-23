/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.facade;

import com.asecon.entity.Planilla;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Antonio
 */
@Stateless
public class PlanillaFacade extends AbstractFacade<Planilla> {
    @PersistenceContext(unitName = "SCANF.PU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanillaFacade() {
        super(Planilla.class);
    }
    
}
