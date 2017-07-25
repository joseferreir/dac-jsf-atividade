/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.infraestrutura.repositorio;

import java.util.List;

/**
 *
 * @author jose2
 */
public interface Repositorio<T> {

    public void add(T entidade);

    public void update(T entidade);

    public T getEntidade(String id);

    public List<T> getEntidades();
}
