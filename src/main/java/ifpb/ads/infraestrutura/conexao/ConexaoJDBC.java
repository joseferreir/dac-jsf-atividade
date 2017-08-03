/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.infraestrutura.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConexaoJDBC  {
  String driver = "org.postgresql.Driver";
    private Connection connection;
    private final String url = "jdbc:postgresql://host-banco:5432/atv_jsf";
  //  private final String url = "jdbc:postgresql://localhost:5432/atv_jsf";
    private final String senha = "123456";
    private final String usuario = "postgres";

    public ConexaoJDBC() {
        initConnection();
    }

    public Connection initConnection() {
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConexaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

   
    public void closeAll(Statement stat) throws DataBaseExceptionJDBC {
        try {
            stat.close();
            this.connection.close();
        } catch (SQLException e) {
            throw new DataBaseExceptionJDBC("Falha ao fechar conexões: " + e.getMessage());
        }
    }

}
