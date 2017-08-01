/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.infraestrutura.repositorio;

import ifpb.ads.autor.Autor;
import ifpb.ads.autor.CPF;
import ifpb.ads.livro.Livro;

/**
 *
 * @author jose2
 */
public class NovoClass {
    
    public static void main(String[] args) {
        LivroRepositorio r = new LivroRepositorio();
        CPF c = new CPF("132.421.432-45");
        System.err.println(c.formatado());
        Autor a = new Autor("maria deu certo", "maria@gmail.com", c);
        System.err.println("teste de salvar"+a.getCpf().formatado());
       // Livro l = new Livro("descriçao 3", "isbn1234564", "02");
       // r.add(l);
        System.err.println("busca "+r.getEntidades().get(0).getDescricao());
   //    r.update(a);
     //  r.remove(c.formatado());
      //  System.err.println("en "+r.getEntidade(c.formatado()));
        
    }
    
}
