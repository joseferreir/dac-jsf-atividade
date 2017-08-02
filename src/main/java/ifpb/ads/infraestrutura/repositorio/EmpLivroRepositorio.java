/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.infraestrutura.repositorio;

import ifpb.ads.emprestimo.Emprestimo;
import ifpb.ads.emprestimo.LivroSituacao;
import ifpb.ads.infraestrutura.conexao.ConexaoJDBC;
import ifpb.ads.livro.LivroService;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class EmpLivroRepositorio implements Repositorio<Emprestimo>{
      private ConexaoJDBC conn = null;
      @Inject
      private LivroService livroService;
    @Override
    public void add(Emprestimo entidade) {
        String query = "INSERT INTO emprestimo (data, cliente, isbnlivro, situacao) VALUES(?,?,?,?)";
        System.err.println("view" + entidade.toString());
        System.err.println("chama salvar");
        saveBD(entidade, query);
    }

    @Override
    public void update(Emprestimo entidade) {
        System.err.println("up livro");
        String query = "UODATE Emprestimo SET data=?, cliente=?, isbnlivro=?, situacao=? WHERE id=?";
        saveBD(entidade,query );

    }

    @Override
    public Emprestimo getEntidade(String key) {
        StringBuilder consulta = new StringBuilder();
        System.err.println("comm "+consulta.toString());
        consulta.append("SELECT * FROM Emprestimo where id = '").append(key).append("'");
        
        consulta.append("'");
        System.err.println("consulta "+consulta.toString());
        try {
            Emprestimo r = bucarEmp(consulta.toString()).get(0);

        } catch (Exception ex) {
            Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Emprestimo();
    }

    @Override
    public List<Emprestimo> getEntidades() {
        StringBuffer consulta = new StringBuffer();
        consulta.append("SELECT * FROM Emprestimo");
        
        try {
            
            List<Emprestimo> r = bucarEmp(consulta.toString());
            return r;
        } catch (Exception ex) {
            Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    private void saveBD(Emprestimo emp, String query) {
        PreparedStatement stat = null;

        try {
            
            this.conn = new ConexaoJDBC();
            System.err.println("conexao " + conn.toString());
            stat = conn.initConnection().prepareStatement(query);
            stat.setDate(1,Date.valueOf(emp.getDataDoEmprestimo()));
            stat.setString(2, emp.getNomeDoCliente());
            stat.setString(3, emp.getLivro().getISBN());
            stat.setString(4, LivroSituacao.EMPRESTADO.name());
            if(emp.getId()>0)
                stat.setInt(5, emp.getId());
            if(stat.executeUpdate()<1)
                 throw new Exception("Erro ao atualixae");

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

    private List<Emprestimo> bucarEmp(String sql) throws Exception {
        List<Emprestimo> lista = new ArrayList<>();

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

    private Emprestimo montarlivro(ResultSet rs) throws SQLException {
       Emprestimo e = new Emprestimo();
       e.setDataDoEmprestimo(rs.getDate("data").toLocalDate());
       e.setId(rs.getInt("id"));
       e.setNomeDoCliente("cliente");
       e.setSituacao(LivroSituacao.valueOf(rs.getString("situacao")));
       e.setLivro(livroService.buscar(rs.getString("isbnlivro")));
       
       
       return e;
    }

    @Override
    public void remove(String key) {
        PreparedStatement ps = null;

        try {
            conn = new ConexaoJDBC();
            String sql = "DELETE FROM emprestimo WHERE id = '" + key + "' ";
            ps = conn.initConnection().prepareStatement(sql);
            //ps.setString(1, key);
            if(ps.executeUpdate()<1)
                 throw new Exception("Erro ao remover");

        } catch (SQLException e) {
            System.err.println("Erro " + e.getMessage());
            Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception ex) {
            Logger.getLogger(LivroRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.closeAll(ps);
            } catch (Exception ex) {
                Logger.getLogger(AutorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
