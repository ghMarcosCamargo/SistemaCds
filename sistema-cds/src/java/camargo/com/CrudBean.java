/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camargo.com;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Marcos
 */
public abstract class CrudBean<E, D extends CrudDAO> {
    private String estadoTela = "buscar";//Inserindo, editando ou buscando
    
    
    private E entidade;
    private List<E> entidades;
    
    public void novo() {
        entidade = criarNovaEntidade();
        mudarParaInseri();
    }
    
    public void salvar() {
        try {
            getDao().salvar(entidade);
            entidade = criarNovaEntidade();
            adicionarMensagem("Salvo com sucesso.", FacesMessage.SEVERITY_INFO);
            mudarParaBusca();
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    
    
    public void editar(E entidade) {
         this.entidade = entidade;
         mudarParaEdita();
    }
    public void deletar(E entidade) {
        try {
            getDao().deletar(entidade);
            entidades.remove(entidade);
            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);

        }
        
    }
    
    public void buscar() {
        if(!isBusca()) {
            mudarParaBusca();
            return;
        }
        try {
            entidades = getDao().buscar();
            if(entidades == null || entidades.isEmpty()) {
                adicionarMensagem("Não há nada cadastrado!", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);        
        }
        
    }
    
    public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro) {
        FacesMessage fm = new FacesMessage(tipoErro, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public E getEntidade() {
        return entidade;
    }

    public void setEntidade(E entidade) {
        this.entidade = entidade;
    }

    public List<E> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<E> entidades) {
        this.entidades = entidades;
    }
    
    
    
    
    
    //Responsáel por criar os métodos nas classes bean.
    public abstract D getDao();
    public abstract E criarNovaEntidade();
    
    //Métodos para controle da tela
    public boolean isInseri() {
        return "inserir".equals(estadoTela);
    }
    public boolean isEdita() {
        return "editar".equals(estadoTela);
    }
    public boolean isBusca() {
        return "buscar".equals(estadoTela);
    }
    //Métodos controlar estado da tela
    public void mudarParaInseri() {
        estadoTela = "inserir";
    }
    public void mudarParaEdita() {
        estadoTela = "editar";
    }
    public void mudarParaBusca() {
        estadoTela = "buscar";
    }
}
