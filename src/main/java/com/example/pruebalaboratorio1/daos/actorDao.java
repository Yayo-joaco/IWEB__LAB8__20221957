package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.actor;
import com.example.pruebalaboratorio1.beans.pelicula;

import java.sql.*;
import java.util.ArrayList;

public class actorDao {

    public ArrayList<actor> listarActores(int idPelicula) {

        ArrayList<actor> listaActores = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";
        String username = "root";
        String password = "root";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();

            String sql = "SELECT A.*\n" +
                    "FROM \n" +
                    "(SELECT * FROM ACTOR ) AS A\n" +
                    "INNER JOIN\n" +
                    "(SELECT * FROM PROTAGONISTAS WHERE IDPELICULA = \n" +
                    idPelicula +
                    ") AS B\n" +
                    "on a.idactor = b.idactor\n";


            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                actor actuador = new actor();
                int idActor = rs.getInt(1);
                actuador.setIdActor(idActor);
                String nombre = rs.getString("nombre");
                actuador.setNombre(nombre);
                String apellido = rs.getString("apellido");
                actuador.setApellido(apellido);
                int anoNacimiento = rs.getInt("anoNacimiento");
                actuador.setAnoNacimiento(anoNacimiento);
                boolean oscar = rs.getBoolean("premioOscar");
                actuador.setPremioOscar(oscar);


                listaActores.add(actuador);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaActores;
    }

    public void crearActor(int idPelicula, String nombre, String apellido, int anoNacimiento, boolean premioOscar) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";
        String username = "root";
        String password = "root";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO ACTOR (nombre, apellido, anoNacimiento, premioOscar) VALUES (?, ?, ?, ?)";
            String sqlProtagonista = "INSERT INTO PROTAGONISTAS (idPelicula, idActor) VALUES (?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setInt(3, anoNacimiento);
            pstmt.setBoolean(4, premioOscar);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int idActor = rs.getInt(1);
                PreparedStatement pstmtProtagonista = conn.prepareStatement(sqlProtagonista);
                pstmtProtagonista.setInt(1, idPelicula);
                pstmtProtagonista.setInt(2, idActor);
                pstmtProtagonista.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
