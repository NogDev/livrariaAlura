package br.com.caelum.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

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