package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.genero;
import com.example.pruebalaboratorio1.beans.pelicula;

import java.sql.*;
import java.util.ArrayList;

public class peliculaDao extends baseDao{

    public ArrayList<pelicula> listarPeliculas() {

        ArrayList<pelicula> listaPeliculas = new ArrayList<>();


        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()){

            String sql = "SELECT A.*, B.NOMBRE FROM  \n" +
                    "(SELECT * FROM PELICULA ) AS A \n" +
                    "INNER JOIN \n" +
                    "(SELECT * FROM GENERO) AS B\n" +
                    "ON A.IDGENERO = B.IDGENERO\n" +
                    "ORDER BY RATING DESC , BOXOFFICE DESC";


            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                pelicula movie = new pelicula();
                genero genero = new genero();
                int idPelicula = rs.getInt(1);
                movie.setIdPelicula(idPelicula);
                String titulo = rs.getString("titulo");
                movie.setTitulo(titulo);
                String director = rs.getString("director");
                movie.setDirector(director);
                int anoPublicacion = rs.getInt("anoPublicacion");
                movie.setAnoPublicacion(anoPublicacion);
                double rating = rs.getDouble("rating");
                movie.setRating(rating);
                double boxOffice = rs.getDouble("boxOffice");
                movie.setBoxOffice(boxOffice);
                int idGenero = rs.getInt("idGenero");
                String nombregenero = rs.getString("nombre");
                movie.setGenero(nombregenero);

                listaPeliculas.add(movie);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaPeliculas;
    }


    public void editarPelicula(int idPelicula, String titulo, String director, int anoPublicacion, double rating, double boxOffice){
        try {
            String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";
            String username = "root";
            String password = "root";

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password);) {
                String sql = "UPDATE PELICULA SET titulo = ?, director = ?, anoPublicacion = ? ," +
                        "rating = ?, boxOffice = ? WHERE IDPELICULA = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, titulo);
                    pstmt.setString(2, director);
                    pstmt.setInt(3, anoPublicacion);
                    pstmt.setDouble(4, rating);
                    pstmt.setDouble(5, boxOffice);
                    pstmt.setInt(6, idPelicula);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    public void borrarPelicula(int idPelicula) {

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            // Eliminar de la tabla protagonistas
            String sqlProtagonistas = "DELETE FROM PROTAGONISTAS WHERE IDPELICULA = ?";
            try (PreparedStatement pstmtProtagonistas = conn.prepareStatement(sqlProtagonistas)) {
                pstmtProtagonistas.setInt(1, idPelicula);
                pstmtProtagonistas.executeUpdate();
            }

            // Eliminar de la tabla peliculas
            String sqlPelicula = "DELETE FROM PELICULA WHERE IDPELICULA = ?";
            try (PreparedStatement pstmtPelicula = conn.prepareStatement(sqlPelicula)) {
                pstmtPelicula.setInt(1, idPelicula);
                pstmtPelicula.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<pelicula> buscarPeliculasPorTitulo(String searchTerm) {
        ArrayList<pelicula> listaPeliculas = new ArrayList<>();

        String sql = "SELECT A.*, B.NOMBRE FROM PELICULA A " +
                "INNER JOIN GENERO B ON A.IDGENERO = B.IDGENERO " +
                "WHERE A.TITULO LIKE ? " +
                "ORDER BY A.RATING DESC, A.BOXOFFICE DESC";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pelicula movie = new pelicula();

                movie.setIdPelicula(rs.getInt(1));
                movie.setTitulo(rs.getString("titulo"));
                movie.setDirector(rs.getString("director"));
                movie.setAnoPublicacion(rs.getInt("anoPublicacion"));
                movie.setRating(rs.getDouble("rating"));
                movie.setBoxOffice(rs.getDouble("boxOffice"));
                movie.setGenero(rs.getString("nombre"));

                listaPeliculas.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaPeliculas;
    }
}
