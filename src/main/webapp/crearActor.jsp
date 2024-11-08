<%@page import="java.util.ArrayList"%>
<%@page import="com.example.pruebalaboratorio1.beans.actor"%>
<%@ page import="com.example.pruebalaboratorio1.beans.pelicula" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Crear Actor para <%= request.getParameter("tituloPelicula") %></title>
</head>
<body>

<h1>Crear Actor para "<%= request.getParameter("tituloPelicula") %>"</h1>
<form action="crearActor" method="POST">
  <input type="hidden" name="idPelicula" value="<%= request.getParameter("idPelicula") %>">
  <label for="nombre">Nombre:</label>
  <input type="text" id="nombre" name="nombre" required><br><br>

  <label for="apellido">Apellido:</label>
  <input type="text" id="apellido" name="apellido" required><br><br>

  <label for="anoNacimiento">Año Nacimiento:</label>
  <input type="number" id="anoNacimiento" name="anoNacimiento" required><br><br>

  <label for="premioOscar">¿Ha ganado un premio Oscar?</label>
  <input type="checkbox" id="premioOscar" name="premioOscar"><br><br>

  <button type="submit">Guardar</button>
</form>

</body>
</html>
