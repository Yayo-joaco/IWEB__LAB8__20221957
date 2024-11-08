package com.example.pruebalaboratorio1.servlets;

import com.example.pruebalaboratorio1.beans.pelicula;
import com.example.pruebalaboratorio1.daos.peliculaDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "pelicula-servlet", value = "/listaPeliculas")
public class PeliculaServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        peliculaDao peliculaDao = new peliculaDao();
        ArrayList<pelicula> listaPeliculas = peliculaDao.listarPeliculas();
        request.setAttribute("listaPeliculas", listaPeliculas);

        RequestDispatcher dispatcher = request.getRequestDispatcher("listaPeliculas.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String action = request.getParameter("action");
        peliculaDao peliculaDao = new peliculaDao();
        int idPelicula;

        switch (action) {

            case "filtrar":

                String searchTerm = request.getParameter("searchTerm");
                ArrayList<pelicula> peliculasFiltradas = peliculaDao.buscarPeliculasPorTitulo(searchTerm);
                request.setAttribute("listaPeliculas", peliculasFiltradas);
                RequestDispatcher viewFiltro = request.getRequestDispatcher("listaPeliculas.jsp");
                viewFiltro.forward(request, response);
                break;

            case "editar":

                idPelicula = Integer.parseInt(request.getParameter("idPelicula"));
                String titulo = request.getParameter("titulo");
                String director = request.getParameter("director");
                int anoPublicacion = Integer.parseInt(request.getParameter("anoPublicacion"));
                double rating = Double.parseDouble(request.getParameter("rating"));
                double boxOffice = Double.parseDouble(request.getParameter("boxOffice"));

                peliculaDao.editarPelicula(idPelicula, titulo,director,anoPublicacion,rating,boxOffice);
                response.sendRedirect(request.getContextPath()+"/listaPeliculas?action=listar");
                break;

            case "borrar":

                idPelicula = Integer.parseInt(request.getParameter("idPelicula"));
                peliculaDao.borrarPelicula(idPelicula);
                response.sendRedirect(request.getContextPath() + "/listaPeliculas?action=listar");
                break;



        }
    }




}
