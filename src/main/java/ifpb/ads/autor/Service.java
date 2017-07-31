/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.autor;

import ifpb.ads.infraestrutura.repositorio.AutorRepositorio;
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
public class Service {

    @Inject
    private AutorRepositorio repositorio;

    public Service() {

    }

    public List<Autor> pesquisar() {
        return repositorio.getEntidades();
    }

    public Autor buscar(String id) {
        return repositorio.getEntidade(id);
    }

    public String update(Autor entidade) {
        Map<String, String> result = validarEntidade(entidade);
        if (result.get("passou").equalsIgnoreCase("true")) {
            repositorio.update(entidade);
            return "Salvo com sucesso";
        }
        result.remove("passou");

        return result.values().toString();

    }

    public String salvar(Autor entidade) {
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

    private Map<String, String> validarEntidade(Autor autor) {
        Map msg = new HashMap<>();
        if (autor.getNome() == null || autor.getNome().trim().length() == 0) {
            msg.put("nome", "Digite o nome");
        }
        if (autor.getEmail() != null && autor.getEmail().length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(autor.getEmail());
            if (!matcher.matches()) {
                msg.put("email", "Email Invalido! ex: fulano@gmail.com");
            }
            if (!autor.getCpf().isValid()) {
                msg.put("CPF", "CPF invalido! Digite apenas numeros");
            }
            if (msg.isEmpty()) {
                msg.put("passou", "true");
            } else {
                msg.put("passou", "false");
            }
        }
        return msg;
    }
}
