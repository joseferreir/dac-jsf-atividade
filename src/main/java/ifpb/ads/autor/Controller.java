/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.autor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jose2
 */
@Named
@RequestScoped
public class Controller {
    private Autor autor = new Autor();
    @Inject
    private Service service;
    public String savar(){
        autor.setCpf(new CPF("13242143245"));
        //autor.setNome("jose");
        autor.setEmail("jose@gmail.com");
        service.savar(autor);
        return null;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    
    
}
