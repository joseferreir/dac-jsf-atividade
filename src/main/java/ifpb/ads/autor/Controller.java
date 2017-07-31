/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.autor;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private static final String ESTADO_PESQUISA = "PESQUISA";

    private static final String ESTADO_INSERCAO = "INSERCAO";

    private static final String ESTADO_EDICAO = "EDICAO";

    private static final String ACAO_SALVAR = "salvarCommandButton";

    private static final String ACAO_PESQUISAR = "pesquisarCommandButton";

    private String estadoAtual;

    private String acaoAtual;

    private Autor registro = new Autor();

    private List<Autor> lista = new ArrayList<>();
    @Inject
    private Service contatoService;
    private String CPFValor;

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

        registro = new Autor();
    }

    public void editar(Autor registro) {
        this.registro = registro;
        service.update(registro);

        estadoAtual = ESTADO_EDICAO;

        acaoAtual = ACAO_SALVAR;
    }

    public void limpar() {
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("Limpo"));

        registro = new Autor();

        //continua no mesmo estado
    }

    public void salvar() {
        System.err.println("controll");
        String msg = service.salvar(autor);
        
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("sftrttty"));

        registro = new Autor();
        lista = service.pesquisar();

        estadoAtual = ESTADO_PESQUISA;

        acaoAtual = ACAO_PESQUISAR;
    }

    public void remover() {
        service.remove(CPFValor);
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("Removido com sucesso!"));

        registro = new Autor();
        //lista = new ArrayList<>();

        estadoAtual = ESTADO_PESQUISA;

        acaoAtual = ACAO_PESQUISAR;
    }

    public Autor getRegistro() {
        return registro;
    }

    public List<Autor> getLista() {
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

    public String getCPFValor() {
        return CPFValor;
    }

    public void setCPFValor(String CPFValor) {
        this.CPFValor = CPFValor;
    }

}
