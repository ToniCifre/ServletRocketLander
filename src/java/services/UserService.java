/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.mysql.jdbc.Connection;
import entityClasses.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tonix
 */
public class UserService {

    protected EntityManager em;

    public UserService(EntityManager em) {
        this.em = em;
    }

    public List<Usuari> listaAlumnes() {
        Query query = em.createQuery("SELECT a FROM Usuari a");
        @SuppressWarnings("unchecked")
        List<Usuari> la = (List<Usuari>) query.getResultList();
        return la;
    }

    public boolean isContrasenya(String nom, String psd) {
        Query query = em.createQuery("SELECT a.contra FROM Usuari a WHERE a.nik=:nom");
        query.setParameter("nom", nom);
        try {
            String contra = (String) query.getSingleResult();
            return contra.equals(psd);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNom(String nom) {
        for (int i = 0; i < listaAlumnes().size(); i++) {
            if (listaAlumnes().get(i).getNik().toLowerCase().equals(nom.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public Usuari getUsuari(String nom) {
        Query query = em.createQuery("SELECT a FROM Usuari a WHERE a.nik=:nom");
        query.setParameter("nom", nom);
        return (Usuari) query.getSingleResult();
    }

    public void crearUsuari(String nom, String contra) {
        try {
            Usuari u = new Usuari();
            u.setId(listaAlumnes().size() + 1);
            u.setNik(nom);
            u.setContra(contra);
            u.setEmail("");
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(u);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error creant el alumne");
        }
    }
    public int isCookies(Cookie[] ck) {
        List<Usuari> lu = listaAlumnes();
        for (int i = 0; i<ck.length;i++) {
            for (Usuari u : lu) {
                if (u.getNik().equals(ck[i].getValue())) {
                    return i;
                }
            }
        }
        return -1;
    }
    public void iniciarMenu(HttpServletRequest request, HttpServletResponse response, String nom) {
        try {
            HttpSession session = request.getSession();
            partidesService ps = new partidesService(em);
            Usuari u = getUsuari(nom);
            session.setAttribute("menu", ps.listaPuntiacionsUsuari(u.getId()));
            session.setAttribute("top10", ps.listaPuntiacions());
            session.setAttribute("conectats", ps.listaUltimsConectats());
            session.setAttribute("use", u);
            RequestDispatcher a = request.getRequestDispatcher("menu.jsp");
            a.forward(request, response);
        } catch (Exception e) {
            System.out.println("--------Error en el metodo de iniciar menu -----------------");
        }
    }
}
