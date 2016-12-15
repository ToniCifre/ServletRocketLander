<%-- 
    Document   : login
    Created on : 01-dic-2016, 16:39:54
    Author     : tonix
--%>

<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="services.UserService"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="w3.css">
        <%
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("JocLanderPU");
            EntityManager em = emf.createEntityManager();
            UserService us = new UserService(em);

            Cookie[] cookies = request.getCookies();
            if ((request.getParameter("off") != null ? request.getParameter("off") : "").equals("off")) {
                for (int i = 0; i < cookies.length; i++) {
                    cookies[i].setValue("");
                    cookies[i].setPath("/");
                    cookies[i].setMaxAge(0);
                    response.addCookie(cookies[i]);
                }
            }
            int k=us.isCookies(cookies);
            if (k!=-1) {
                us.iniciarMenu(request, response,cookies[k].getValue());
            }
        %>
        <style>div{
                width: 50%;
                padding: 20px;
                text-align: left;
                margin: auto;
            }
            #bx{box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            }   
            body{
                background-image: url("lander/img/bg.png");
            }
        </style>
    </head>
    <br>
    <body>
        <div id="login" class="w3-container w3-teal">
            <h2>Iniciar Sessio</h2>
        </div>
        <div id="bx">
            <form id="login2" action="UserServlet" method="post" class="w3-container">
                <label class="w3-label w3-text-white"><b>Nikname</b></label>
                <input name="nik"  type="text" class="w3-input w3-border w3-light-grey w3-hover-green">

                <label class="w3-label w3-text-white"><b>Contrasenya</b></label>
                <input name="psd" type="password" class="w3-input w3-border w3-light-grey w3-hover-green">

                <button type="submit" name="login" value="login" class="w3-btn w3-teal w3-hover-green">Iniciar Sessio</button>
            </form>
            <a style="color:red"><%= request.getParameter("enter") != null ? request.getParameter("enter") : ""%></a>
        </div>
        <br>
        <div id="login" class="w3-container w3-teal">
            <h2>Registrarse</h2>
        </div>
        <div id="bx">
            <form id="login2" action="UserServlet" method="post" class="w3-container">
                <label class="w3-label w3-text-white"><b>Nikname</b></label>
                <input name="nik2" type="text" class="w3-input w3-border w3-light-grey w3-hover-green">

                <label class="w3-label w3-text-white"><b>Contrasenya</b></label>
                <input name="psd2" type="password" class="w3-input w3-border w3-light-grey w3-hover-green">

                <button type="submit" name="regist" value="regist" class="w3-btn w3-teal w3-hover-green">Registrarse</button>
            </form>
            <a style="color:red"><%= request.getParameter("regis") != null ? request.getParameter("regis") : ""%></a>
        </div>
        <br>
    </body>
</html>
