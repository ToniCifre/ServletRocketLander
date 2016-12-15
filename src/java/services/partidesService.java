/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entityClasses.*;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author tonix
 */
public class partidesService {

    protected EntityManager em;
    private UserService us ;

    public partidesService(EntityManager em) {
        this.em = em;
        us = new UserService(em);
    }

    public List<Partides> listaPuntiacions() {
        Query query = em.createQuery("SELECT a FROM Partides a ORDER BY a.punts");
        @SuppressWarnings("unchecked")
        List<Partides> la = (List<Partides>) query.getResultList();
        return la;
    }

    public List<Partides> listaPuntiacionsUsuari(int id) {
        Query query = em.createQuery("SELECT a FROM Partides a, Usuari u WHERE u.id=:id and a.uId=u ORDER BY a.punts");
        query.setParameter("id", id);
        @SuppressWarnings("unchecked")
        List<Partides> la = (List<Partides>) query.getResultList();
        return la;
    }

    public List<Partides> listaUltimsConectats() {
        int x = 0, nUsers = us.listaAlumnes().size();
        Query query = em.createQuery("SELECT a FROM Partides a ORDER BY a.fecha DESC");
        @SuppressWarnings("unchecked")
        List<Partides> la = (List<Partides>) query.getResultList();
        int[] id = new int[nUsers];

        for (int t = 0; t < la.size(); t++) {
            for (int i = 0; i < id.length; i++) {
                if (id[i] == la.get(t).getUId().getId()) {
                    la.remove(t);
                    i = 100;
                    t--;
                } else if (id[i] == 0) {
                    id[x] = la.get(t).getUId().getId();
                    x++;
                    i = 100;
                }
            }
            if (la.size() < nUsers) {
                t = 100;
            }
        }
        return la;
    }

    public void guardarPuntuacions(float punts, Usuari user) {
        try {
            Date date = new Date();
            UserService us = new UserService(em);
            Partides p = new Partides();
            p.setPunts(punts);
            p.setFecha(date);
            p.setUId(user);
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(p);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error creant la partida");
        }
    }
}
