/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.emprestimo;

import ifpb.ads.infraestrutura.repositorio.EmpLivroRepositorio;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author jose2
 */
@RequestScoped
public class EmprestimoService {

    @Inject
    private EmpLivroRepositorio repositorio;

    public EmprestimoService() {

    }

    public List<Emprestimo> pesquisar() {
        return repositorio.getEntidades();
    }

    public Emprestimo buscar(String id) {
        return repositorio.getEntidade(id);
    }

    public String update(Emprestimo entidade) {
      
            return salvar(entidade);
        

    }

    public String salvar(Emprestimo entidade) {
        Map<String, String> result = validarEntidade(entidade);
        if (result.get("passou").equalsIgnoreCase("true")) {
            repositorio.add(entidade);
            return "Salvo com sucesso";
        }
        result.remove("passou");

        return result.values().toString();
    }

    public void remove(String key) {
        repositorio.remove(key);

    }

    private Map<String, String> validarEntidade(Emprestimo emp) {
        Map msg = new HashMap<>();
        if(emp== null)
            msg.put("null","preencha os campos");
        if(emp.getNomeDoCliente()==null |emp.getNomeDoCliente().trim().length()<1)
            msg.put("nomecliente", "cliente invalido");
        if(emp.getSituacao().equals(LivroSituacao.EMPRESTADO));
        msg.put("livroEmp", "Livro emprestado");
        if(!msg.isEmpty())
            msg.put("passou", "false");
        else
            msg.put("passou", "true");
        return msg;
    }
}
