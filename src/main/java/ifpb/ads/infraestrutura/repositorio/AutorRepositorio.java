/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.infraestrutura.repositorio;

import ifpb.ads.autor.Autor;
import ifpb.ads.infraestrutura.conexao.ConexaoJDBC;
import ifpb.ads.infraestrutura.conexao.DataBaseExceptionJDBC;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author jose2
 */
@RequestScoped
public class AutorRepositorio implements Repositorio<Autor>{
    private ConexaoJDBC conn = null;

    @Override
    public void add(Autor entidade) {
        String query = "INSERT INTO Autor(CPF, nome, email) VALUES('?','?','?')";
        saveBD(entidade, query);
    }

    @Override
    public void update(Autor entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Autor getEntidade(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Autor> getEntidades() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     private void saveBD(Autor autor, String query) {
        PreparedStatement stat = null;
        
        try {
             this.conn = new ConexaoJDBC();
            stat = conn.getConnection().prepareStatement(query);
            stat.setString(1,autor.getCpf().formatado());
            stat.setString(2,autor.getNome());
            stat.setString(3, autor.getEmail());
             
        } catch (Exception e) {
            System.err.println("erron no banco: " + e.getMessage());
           
        } finally {
            try {
                conn.closeAll(stat);
            } catch (DataBaseExceptionJDBC ex) {
                Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
            //System.err.println("DAO ======= DAO ======== resultado ==============" + resultado.toString());
        }
     }
}
