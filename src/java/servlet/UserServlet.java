/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import entityClasses.Usuari;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.*;

/**
 *
 * @author tonix
 */
public class UserServlet extends HttpServlet {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JocLanderPU");
    EntityManager em = emf.createEntityManager();
    UserService us = new UserService(em);
    partidesService ps = new partidesService(em);
    Usuari u;
    HttpSession session;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ps.guardarPuntuacions(Float.valueOf(request.getParameter("x")), u);
            if ("Restart".equals(request.getParameter("restart"))) {
                RequestDispatcher a = request.getRequestDispatcher("/lander/index.html");
                a.forward(request, response);
            }
            if ("Menu".equals(request.getParameter("submit"))) {
                HttpSession session = request.getSession();
                session.setAttribute("menu", ps.listaPuntiacionsUsuari(u.getId()));
                session.setAttribute("top10", ps.listaPuntiacions());
                session.setAttribute("conectats", ps.listaUltimsConectats());
                session.setAttribute("use", u);
                RequestDispatcher a = request.getRequestDispatcher("menu.jsp");
                a.forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("-----------Error en el doGet del UserServlet---------");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            session = request.getSession();
            String nom = request.getParameter("nik");
            String psd = request.getParameter("psd");
            if ("login".equals(request.getParameter("login"))) {
                if (us.isContrasenya(nom, psd)) {
                    saveCookies(nom, psd, response);
                    iniciarMenu(request, response);
                }
                RequestDispatcher a = request.getRequestDispatcher("login.jsp?enter=Usuario y/o contraseña incorrectes");
                a.forward(request, response);
            }
            if ("regist".equals(request.getParameter("regist"))) {
                if (!"".equals(request.getParameter("nik2")) && !"".equals(request.getParameter("psd2"))) {
                    if (!us.isNom(request.getParameter("nik2"))) {
                        us.crearUsuari(request.getParameter("nik2"), request.getParameter("psd2"));
                        RequestDispatcher a = request.getRequestDispatcher("login.jsp?regis=T'has Registrat amb exit "+request.getParameter("nik2"));
                        a.forward(request, response);
                    }
                    RequestDispatcher a = request.getRequestDispatcher("login.jsp?regis=Aquest nikname ja existeix");
                    a.forward(request, response);
                }
                RequestDispatcher a = request.getRequestDispatcher("login.jsp?regis=Has de rellenar tots els camps");
                a.forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("-----------Error en el doPost del UserServlet---------");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void saveCookies(String nik, String pwd, HttpServletResponse response) {
        Cookie nom = new Cookie("usuario", nik);
        Cookie contra = new Cookie("contra", pwd);
        nom.setMaxAge(60);
        contra.setMaxAge(60);
        response.addCookie(nom);
        response.addCookie(contra);
    }

    public void iniciarMenu(HttpServletRequest request, HttpServletResponse response) {
        try {
            u = us.getUsuari(request.getParameter("nik"));
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
