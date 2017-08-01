/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.infraestrutura.repositorio;

import ifpb.ads.autor.Autor;
import ifpb.ads.autor.AutorService;
import ifpb.ads.autor.CPF;
import ifpb.ads.infraestrutura.conexao.ConexaoJDBC;
import ifpb.ads.livro.Livro;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author jose2
 */
public class LivroRepositorio implements Repositorio<Livro> {

    private ConexaoJDBC conn = null;
    @Inject
    private AutorService autorService;

    @Override
    public void add(Livro entidade) {
        String query = "INSERT INTO livro (ISBN, descricao, edicao) VALUES(?,?,?)";
        System.err.println("view" + entidade.toString());
        System.err.println("chama salvar");
        saveBD(entidade, query);
    }

    @Override
    public void update(Livro entidade) {

        boolean result = false;
        PreparedStatement pst = null;

        try {
            conn = new ConexaoJDBC();
            String sql = "UPDATE livro SET descricao=?, edicao=? "
                    + "WHERE ISBN ='" + entidade.getISBN() + "'";
            pst = conn.initConnection().prepareStatement(sql);
            pst.setString(1, entidade.getDescricao());
            pst.setString(2, entidade.getEdicao());

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
    public Livro getEntidade(String key) {
        StringBuffer consulta = new StringBuffer();
        System.err.println("comm "+consulta.toString());
        consulta.append("SELECT A.nome nome, A.cpf, A.email, L.isbn, L.descricao,L.edicao");
        consulta.append(" FROM AUTOR A, LIVRO L, AUTORLIVRO AL WHERE");
        consulta.append("A.CPF=AL.CPFAUTOR AND L.ISBN=");
        consulta.append(key);
        try {
            Livro r = buscarLivro(consulta.toString()).get(0);

        } catch (Exception ex) {
            Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Livro();
    }

    @Override
    public List<Livro> getEntidades() {
        StringBuffer consulta = new StringBuffer();
        consulta.append("SELECT A.nome nome, A.cpf, A.email, L.isbn isbnlivro, L.descricao,L.edicao");
        consulta.append(" FROM AUTOR A, LIVRO L, AUTORLIVRO AL WHERE");
        consulta.append("A.CPF=AL.CPFAUTOR AND L.ISBN=");
        consulta.append("AL.ISBNLIVRO");
        try {
            List<Livro> r = buscarLivro("SELECT * from autor");
            return r;
        } catch (Exception ex) {
            Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    private void saveBD(Livro livro, String query) {
        PreparedStatement stat = null;

        try {
            System.err.println("view" + livro.toString());
            this.conn = new ConexaoJDBC();
            System.err.println("conexao " + conn.toString());
            stat = conn.initConnection().prepareStatement(query);
            stat.setString(1, livro.getISBN());
            stat.setString(2, livro.getDescricao());
            stat.setString(3, livro.getEdicao());
            stat.executeUpdate();

        } catch (Exception e) {
            System.err.println("erron no banco: " + e.getMessage());

        } finally {
            //System.err.println("DAO ======= DAO ======== resultado ==============" + resultado.toString());

            try {
                conn.closeAll(stat);
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<Livro> buscarLivro(String sql) throws Exception {
        List<Livro> lista = new ArrayList<>();

        PreparedStatement pst = null;

        try {
            conn = new ConexaoJDBC();
            pst = conn.initConnection().prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                lista.add(montarlivro(rs));
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

    private Livro montarlivro(ResultSet rs) {
        String ISBN = "", descricao = "", edicao = "";
        String nome = "", email = "", cpf = "";
        try {
            nome = rs.getString("nome");
            email = rs.getString("email");
            cpf = rs.getString("cpf");

            ISBN = rs.getString("isbnlivro");
            descricao = rs.getString("descricao");
            edicao = rs.getString("edicao");

        } catch (SQLException ex) {
            Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        Livro livro = new Livro(descricao, ISBN, edicao);
        livro.adicionarAutor(new Autor(nome, email,new CPF(cpf)));
        return livro;
    }

    @Override
    public void remove(String key) {
        PreparedStatement ps = null;

        try {
            conn = new ConexaoJDBC();
            String sql = "DELETE FROM livro WHERE isb = '" + key + "' ";
            ps = conn.initConnection().prepareStatement(sql);
            //ps.setString(1, key);
            ps.executeUpdate();
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
