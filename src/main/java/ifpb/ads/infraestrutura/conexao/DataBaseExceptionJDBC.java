/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.infraestrutura.conexao;

import java.sql.SQLException;

/**
 *
 * @author jose2
 */
public class DataBaseExceptionJDBC extends SQLException {

    private static final long serialVersionUID = 1L;

    public DataBaseExceptionJDBC(String error) {
        super("Erro na dase de dados:" + error);
    }

}
