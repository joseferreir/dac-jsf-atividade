/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.emprestimo;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author jose2
 */
public class ControllerEmp {
     @Inject
    private EmprestimoService service;
    private static final String ESTADO_PESQUISA = "PESQUISA";

    private static final String ESTADO_INSERCAO = "INSERCAO";

    private static final String ESTADO_EDICAO = "EDICAO";

    private static final String ACAO_SALVAR = "salvarCommandButton";

    private static final String ACAO_PESQUISAR = "pesquisarCommandButton";

    private String estadoAtual;

    private String acaoAtual;

    private Emprestimo registro = new Emprestimo();

    private List<Emprestimo> lista = new ArrayList<>();
    @Inject
    private EmprestimoService contatoService;
    

    @PostConstruct
    public void postConstruct() {
        estadoAtual = ESTADO_PESQUISA;

        acaoAtual = ACAO_PESQUISAR;
    }

    public void pesquisar() {
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("Pesquisa feita"));

        lista = contatoService.pesquisar();
    }

    public void novo() {
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("Preparando para nova inserção"));

        estadoAtual = ESTADO_INSERCAO;

        acaoAtual = ACAO_SALVAR;

        registro = new Emprestimo();
    }

    public void editar(Emprestimo registro) {
        this.registro = registro;
        service.update(registro);

        estadoAtual = ESTADO_EDICAO;

        acaoAtual = ACAO_SALVAR;
    }

    public void limpar() {
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("Limpo"));

        registro = new Emprestimo();

        //continua no mesmo estado
    }

    public void salvar() {
        System.err.println("controll");
        String msg = service.salvar(registro);

        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(msg));

        registro = new Emprestimo();
        lista = service.pesquisar();

        estadoAtual = ESTADO_PESQUISA;

        acaoAtual = ACAO_PESQUISAR;
        registro = new Emprestimo();
    }

    public void remover() {
        service.remove(String.valueOf(registro.getId()));
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("Removido com sucesso!"));

        registro = new Emprestimo();
        //lista = new ArrayList<>();

        estadoAtual = ESTADO_PESQUISA;

        acaoAtual = ACAO_PESQUISAR;
    }

    public Emprestimo getRegistro() {
        return registro;
    }

    public List<Emprestimo> getLista() {
        //Autor e = new Autor("josé", "jose@gmali.com", new CPF(("123.435.569-12")));

        lista = service.pesquisar();
        return lista;
    }

    public String getEstadoAtual() {
        return estadoAtual;
    }

    public String getAcaoAtual() {
        return acaoAtual;
    }

}
