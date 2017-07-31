/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.infraestrutura.repositorio;

import ifpb.ads.autor.Autor;
import ifpb.ads.autor.CPF;

/**
 *
 * @author jose2
 */
public class NovoClass {
    
    public static void main(String[] args) {
        AutorRepositorio r = new AutorRepositorio();
        CPF c = new CPF("12345612435");
        System.err.println(c.formatado());
        Autor a = new Autor("maria deu certo", "maria@gmail.com", c);
        System.err.println("teste de salvar"+a.getCpf().formatado());
       // r.add(a);
       r.update(a);
     //  r.remove(c.formatado());
      //  System.err.println("en "+r.getEntidade(c.formatado()));
        
    }
    
}
