<%-- 
    Document   : menu
    Created on : 01-dic-2016, 17:25:10
    Author     : tonix
--%>

<%@page import="entityClasses.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="w3.css">
        <%
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            List<Partides> menu = (List<Partides>) session.getAttribute("menu");
            List<Partides> top10 = (List<Partides>) session.getAttribute("top10");
            List<Partides> conectats = (List<Partides>) session.getAttribute("conectats");
            Usuari u = (Usuari) session.getAttribute("use");
            session.setAttribute("user", u);
        %>
        <script>
            function start() {
                document.getElementById("tablaT").style.display = 'none';
                document.getElementById("tablaC").style.display = 'none';
            }
            function homeF() {
                document.getElementById("tablaH").style.display = 'block';
                document.getElementById("tablaT").style.display = 'none';
                document.getElementById("tablaC").style.display = 'none';
            }
            function top10F() {
                document.getElementById("tablaH").style.display = 'none';
                document.getElementById("tablaT").style.display = 'block';
                document.getElementById("tablaC").style.display = 'none';
            }
            function conectatsF() {
                document.getElementById("tablaH").style.display = 'none';
                document.getElementById("tablaT").style.display = 'none';
                document.getElementById("tablaC").style.display = 'block';
            }
        </script>
        <style>
            h2{
                text-align:center;
                color: greenyellow;
                font-family: verdana;
                font-size: 300%;
                font-weight: bolder;
            }

            body{
                background-image: url("lander/img/bg.png");
            }
            td,th{
                font-weight: bolder;
            }td{color: black;}
            #tablaC, #tablaH, #tablaT{
                width: 50%;
                padding: 20px;
                text-align: left;
                margin: auto;
                box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            }
        </style>
    </head>
</head>
<body onload="start()">
    <div id="menu"class="w3-container" >
        <ul class="w3-navbar w3-border w3-round w3-green w3-xxlarge">
            <form id="login2" action="MenuServlet" method="get">
                <li style="width:22%"><button class="w3-btn-block w3-green w3-ripple w3-round-xlarge w3-hover-black" id="jugar" name="jugar" value="jugar" type="submit">Jugar</button></li>
            </form>
            <li style="width:22%"class="w3-hover-black"><button class="w3-btn-block w3-green w3-ripple w3-round-xlarge w3-hover-black" id="conectats" name="menu" value="menu" type="submit"onclick="homeF()"/>Menu</button></li>
            <li style="width:22%"><button class="w3-btn-block w3-green w3-ripple w3-round-xlarge w3-hover-black" id="top10" name="top10" value="top10" type="submit" onclick="top10F()"/>Top 10</button></li>
            <li style="width:22%"><button class="w3-btn-block w3-green w3-ripple w3-round-xlarge w3-hover-black" id="conectats" name="conectats" value="conectats" type="submit"onclick="conectatsF()"/>Conexions</button></li>
            <li style="width:12%"class="w3-dropdown-hover w3-hover-black">
                <button class="w3-btn-block w3-green w3-ripple w3-round-xlarge w3-hover-black" type="button"id="user" style="font-size: x-large"/><%=u.getNik()%></button>
                <div class="w3-dropdown-content w3-green w3-card-9">
                    <form id="login2" action="MenuServlet" method="get">
                        <button class="w3-btn-block w3-green w3-ripple w3-round-xlarge w3-hover-black" id="tanca" name="off" value="off" style="font-size:large"/>Tanca Sessio</button>
                    </form>
                </div>
            </li>

        </ul>
    </div>
    <br><br>
    <div id="tablaH" class="w3-container">
        <h2>Millors Partides</h2>
        <table class="w3-table-all w3-opacity">
            <thead>
                <tr class="w3-red">
                    <th>Nickname</th>
                    <th>Puntuacio</th>
                    <th>Data</th>
                </tr>
            </thead>
            <%if (menu.isEmpty()) {%>
            <tr>
                <td><%=u.getNik()%></td>
                <td><%="No has jugat cap partida"%></td>
                <td></td>
            </tr>
            <%} else {
                for (Partides p : menu) {
            %><tr>
                <td><%=p.getUId().getNik()%></td>
                <td><%=p.getPunts()%></td>
                <td><%=dateFormat.format(p.getFecha())%></td>
            </tr><%}
                }
            %>
        </table>
        <br>
    </div>
    <div id="tablaT" class="w3-container">
        <h2>TOP 10</h2>
        <table class="w3-table-all w3-opacity">
            <thead>
                <tr class="w3-red">
                    <th>Nickname</th>
                    <th>Puntuacio</th>
                    <th>Data</th>
                </tr>
            </thead>
            <%for (Partides a : top10) {
            %><tr>
                <td><%=a.getUId().getNik()%></td>
                <td><%=a.getPunts()%></td>
                <td><%=dateFormat.format(a.getFecha())%></td>
            </tr><%}
            %>
        </table>
        <br>
    </div>
    <div id="tablaC" class="w3-container">
        <h2>Ultimes Partides</h2>
        <table class="w3-table-all w3-opacity">
            <thead>
                <tr class="w3-red">
                    <th>Nickname</th>
                    <th>Id</th>
                    <th>Puntuacio</th>
                    <th>Data</th>
                </tr>
            </thead>
            <%for (Partides pp : conectats) {
            %><tr>
                <td><%=pp.getUId().getNik()%></td>
                <td><%=pp.getUId().getId()%></td>
                <td><%=pp.getPunts()%></td>
                <td><%=dateFormat.format(pp.getFecha())%></td>
            </tr><%}
            %>
        </table>
        <br>
    </div>
    <br><br>
</body>
</html>
