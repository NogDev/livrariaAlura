package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

@Named
@ViewScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();

    public Usuario getUsuario() {
        return usuario;
    }
    
    public String efetuaLogin() {
        System.out.println("Fazendo login do usuário "
                + this.usuario.getEmail());

        boolean existe = new UsuarioDao().existe(this.usuario);

        FacesContext context = FacesContext.getCurrentInstance();

        if (existe) {
            context.getExternalContext().getSessionMap()
                    .put("usuarioLogado", this.usuario);

            return "livro?faces-redirect=true";
        }
        
        // mantem as menssagens de validação entre requisições
        context.getExternalContext().getFlash().setKeepMessages(true);
        
        
        // para um componete expecífico
        //context.addMessage("login:email", new FacesMessage("Usuário não encontrado"));
        context.addMessage(null, new FacesMessage("Usuário não encontrado"));
    	return "login?faces-redirect=true";
    }
    
    public String deslogar(){

        FacesContext context = FacesContext.getCurrentInstance();
        
        
            context.getExternalContext().getSessionMap()
                    .remove("usuarioLogado");

    	return "login?faces-redirect=true";
    }
}