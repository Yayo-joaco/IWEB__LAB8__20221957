<%@page import="java.util.ArrayList"%>
<%@page import="com.example.pruebalaboratorio1.beans.pelicula"%>
<%@page import="java.text.NumberFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ArrayList<pelicula> listaPeliculas = (ArrayList) request.getAttribute("listaPeliculas");
    NumberFormat formatter = NumberFormat.getInstance();
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Películas</title>
    <script>
        function confirmDelete(form) {
            var confirmBox = confirm("¿Estás seguro de que quieres eliminar esta película?");
            if (confirmBox) {
                form.submit();
            }
        }
    </script>
</head>
<body>

<h1>Lista de películas</h1>
<p>Para recuperar la tabla inicial solo debes buscar sin ningun caracter en la búsqueda</p>

<form action="listaPeliculas" method="POST" style="margin-bottom: 20px;">
    <input type="hidden" name="action" value="filtrar">
    <input type="text" name="searchTerm" placeholder="Buscar película..." value="<%= request.getParameter("searchTerm") != null ? request.getParameter("searchTerm") : "" %>">
    <button type="submit">Buscar</button>
</form>

<table border="1">
    <tr>
        <th>Titulo</th>
        <th>Director</th>
        <th>Año Publicación</th>
        <th>Rating</th>
        <th>BoxOffice</th>
        <th>Género</th>
        <th>Actores</th>
        <th>Accionable</th>
    </tr>
    <%
        if (listaPeliculas != null) {
            for (pelicula movie : listaPeliculas) {
    %>
    <tr>
        <td><a href="viewPelicula?idPelicula=<%= movie.getIdPelicula() %>"><%=movie.getTitulo()%></a></td>
        <td><%=movie.getDirector()%></td>
        <td><%=movie.getAnoPublicacion()%></td>
        <td><%=movie.getRating()%>/10</td>
        <td>$ <%=formatter.format(movie.getBoxOffice())%></td>
        <td><%=movie.getGenero()%></td>
        <td><a href="listaActores?idPelicula=<%= movie.getIdPelicula() %>">Ver actores</a></td>
        <td>
            <form action="listaPeliculas" method="POST" onsubmit="event.preventDefault(); confirmDelete(this);">
                <input type="hidden" name="action" value="borrar">
                <input type="hidden" name="idPelicula" value="<%= movie.getIdPelicula() %>">
                <button type="submit">Eliminar</button>
            </form>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>

</body>
</html>