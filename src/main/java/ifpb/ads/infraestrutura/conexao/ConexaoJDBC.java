/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.infraestrutura.conexao;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class ConexaoJDBC  {

    private Connection conn;
    private static Properties prop = null;

    public ConexaoJDBC() throws SQLException, IOException, ClassNotFoundException, URISyntaxException {
        try {

           String url = "jdbc:postgresql://host-banco:5432/atv_jsf";
            String user = "postgres";
            String password = "123456";
// carrega as propriedades do arquivo connection.properties da pasta resources            
            Class.forName("org.postgresql.Driver");
            this.conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void closeAll(PreparedStatement stat) throws DataBaseExceptionJDBC {
        try {
            stat.close();
            this.conn.close();

        } catch (SQLException e) {
            throw new DataBaseExceptionJDBC("Falha ao fechar conexões: " + e.getMessage());
        }

    }

   
    public void closeAll(Statement stat) throws DataBaseExceptionJDBC {
        try {
            stat.close();
            this.conn.close();
        } catch (SQLException e) {
            throw new DataBaseExceptionJDBC("Falha ao fechar conexões: " + e.getMessage());
        }
    }

}
