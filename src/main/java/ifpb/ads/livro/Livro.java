package ifpb.ads.livro;

import ifpb.ads.autor.Autor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 17/06/2017, 12:25:12
 */
public class Livro {

    private String descricao;
    private String ISBN;
    private String edicao;
    private List<Autor> autores;

    public Livro(String descricao, String ISBN, String edicao) {
        this.descricao = descricao;
        this.ISBN = ISBN;
        this.edicao = edicao;
         this.autores = new ArrayList<>();
    }
    

    public Livro() {
        this.autores = new ArrayList<>();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public List<Autor> getAutores() {
        return Collections.unmodifiableList(autores);
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }
    
    public void adicionarAutor(Autor autor){
        this.autores.add(autor);
    }
    public void removerAutor(Autor autor){
        this.autores.remove(autor);
    }

}
