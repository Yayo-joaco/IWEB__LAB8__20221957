package com.example.pruebalaboratorio1.servlets;

import com.example.pruebalaboratorio1.daos.actorDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "crear-actor-servlet", value = "/crearActor")
public class CrearActorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idPelicula = Integer.parseInt(request.getParameter("idPelicula"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        int anoNacimiento = Integer.parseInt(request.getParameter("anoNacimiento"));
        boolean premioOscar = request.getParameter("premioOscar") != null;

        actorDao actorDao = new actorDao();
        actorDao.crearActor(idPelicula, nombre, apellido, anoNacimiento, premioOscar);

        response.sendRedirect("listaActores?idPelicula=" + idPelicula);
    }
}
