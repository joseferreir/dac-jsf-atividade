/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.infraestrutura.repositorio;

import ifpb.ads.autor.Autor;
import ifpb.ads.autor.CPF;
import ifpb.ads.infraestrutura.conexao.ConexaoJDBC;
import ifpb.ads.infraestrutura.conexao.DataBaseExceptionJDBC;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author jose2
 */
@RequestScoped
public class AutorRepositorio implements Repositorio<Autor> {

    private ConexaoJDBC conn = null;

    @Override
    public void add(Autor entidade) {
        String query = "INSERT INTO Autor (CPF, nome, email) VALUES(?,?,?)";
        System.err.println("view" + entidade.toString());
        System.err.println("chama salvar");
        saveBD(entidade, query);
    }

    @Override
    public void update(Autor entidade) {
        System.err.println("chamou update");
        boolean result = false;
        PreparedStatement pst = null;

        try {
            conn = new ConexaoJDBC();
            String sql = "UPDATE autor SET nome = ?, email = ?, "
                    + "WHERE id = ?";
            pst = conn.initConnection().prepareStatement(sql);
            pst.setString(1, entidade.getNome());
            pst.setString(2, entidade.getEmail());
            pst.setString(3, entidade.getCpf().valor());
            pst.executeUpdate(sql);

        } catch (SQLException e) {
            System.err.println("Erro " + e.getMessage());
            Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                conn.closeAll(pst);
            } catch (Exception ex) {
                Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public Autor getEntidade(String key) {
        try {
            Autor r = buscarAutor("SELECT * from autor where CPF '"+key+"' ").get(0);

        } catch (Exception ex) {
            Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Autor("", "", new CPF(""));
    }

    @Override
    public List<Autor> getEntidades() {
        try {
            List<Autor> r = buscarAutor("SELECT * from autor");
            return r;
        } catch (Exception ex) {
            Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    private void saveBD(Autor autor, String query) {
        PreparedStatement stat = null;

        try {
            System.err.println("view" + autor.toString());
            this.conn = new ConexaoJDBC();
            System.err.println("conexao " + conn.toString());
            stat = conn.initConnection().prepareStatement(query);
            stat.setString(1, autor.getCpf().formatado());
            stat.setString(2, autor.getNome());
            stat.setString(3, autor.getEmail());
            stat.executeUpdate();

        } catch (Exception e) {
            System.err.println("erron no banco: " + e.getMessage());

        } finally {
            //System.err.println("DAO ======= DAO ======== resultado ==============" + resultado.toString());

            try {
                // conn.closeAll(stat);
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<Autor> buscarAutor(String sql) throws Exception {
        List<Autor> lista = new ArrayList<>();

        PreparedStatement pst = null;

        try {
            conn = new ConexaoJDBC();
            pst = conn.initConnection().prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                lista.add(montarAutor(rs));
            }

            conn.closeAll(pst);
        } catch (SQLException ex) {
            Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    private Autor montarAutor(ResultSet rs) {
        String nome = "", email = "", cpf = "";
        try {

            nome = rs.getString("nome");
            email = rs.getString("email");
            cpf = rs.getString("cpf");

        } catch (SQLException ex) {
            Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Autor(nome, email, new CPF(cpf));
    }

    @Override
    public void remove(String key) {
        PreparedStatement ps = null;

        try {
            conn = new ConexaoJDBC();
            String sql = "DELETE FROM Autor WHERE cpf = ?";
            ps = conn.initConnection().prepareStatement(sql);
            ps.setString(1, key);
            ps.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Erro " + e.getMessage());
            Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                conn.closeAll(ps);
            } catch (Exception ex) {
                Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
