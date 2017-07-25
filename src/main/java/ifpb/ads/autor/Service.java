/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.autor;

import ifpb.ads.infraestrutura.repositorio.AutorRepositorio;
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
    public void savar(Autor autor){
        repositorio.add(autor);
    }
}
