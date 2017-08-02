/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.livro;

import ifpb.ads.autor.*;
import ifpb.ads.infraestrutura.repositorio.AutorRepositorio;
import ifpb.ads.infraestrutura.repositorio.LivroRepositorio;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author jose2
 */
@RequestScoped
public class LivroService {

    @Inject
    private LivroRepositorio repositorio;

    public LivroService() {

    }

    public List<Livro> pesquisar() {
        return repositorio.getEntidades();
    }

    public Livro buscar(String isbn) {
        return repositorio.getEntidade(isbn);
    }

    public String update(Livro entidade) {
        Map<String, String> result = validarEntidade(entidade);
        if (result.get("passou").equalsIgnoreCase("true")) {
            repositorio.update(entidade);
            return "Salvo com sucesso";
        }
        result.remove("passou");

        return result.values().toString();
        

    }

    public String salvar(Livro entidade) {
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

    private Map<String, String> validarEntidade(Livro Livro) {
        Map msg = new HashMap<>();
        if (Livro.getDescricao()== null || Livro.getDescricao().trim().length() == 0) 
            msg.put("descricao", "Digite um descridção");
        
        if(Livro.getISBN() == null || Livro.getISBN().trim().length()==0 ){
             msg.put("isbn", "Digite o ISBN");
        }
        if(msg.isEmpty())
         msg.put("passou", "true");
        return msg;
        
    
}
}
    


