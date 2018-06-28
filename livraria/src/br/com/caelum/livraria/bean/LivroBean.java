package br.com.caelum.livraria.bean;


import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();
	
	private Integer livroId;

	public Integer getLivroId() {
	        return livroId;
	}

	public void setLivroId(Integer livroId) {
	        this.livroId = livroId;
	}
	
	
	public void carregaPelaId() {
        this.livro = new DAO<Livro>(Livro.class).buscaPorId(this.livroId);
    }    
	
	private Integer autorId;

	private List<Livro> livros;

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Livro getLivro() {
		return livro;
	}
	
	public List<Autor> getAutores(){
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public List<Autor> getAutoresDoLivro(){
		return this.livro.getAutores();
	}
	
	public void gravarAutor(){
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}

	public void gravar() {
	    System.out.println("Gravando livro " + this.livro.getTitulo());

	    if (livro.getAutores().isEmpty()) {
	        FacesContext.getCurrentInstance().addMessage("autor",
	                new FacesMessage("Livro deve ter pelo menos um Autor."));
	        return;
	    }

	    DAO<Livro> dao = new DAO<Livro>(Livro.class);

	    if(this.livro.getId() == null) {
	        dao.adiciona(this.livro);

	        // Novo livro adicionado, listamos todos os livros novamente
	        this.livros = dao.listaTodos();
	    } else {
	        dao.atualiza(this.livro);
	    }

	    this.livro = new Livro();
	}
	
	public void carregar(Livro livro){
		System.out.println("Carregando o livro");
		this.livro = livro; // carrega os campos do formulário com os dados do item selecionado
	}
	
	public void remover(Livro livro){
		System.out.println("Removendo livro");
		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		dao.remove(livro);
		this.livros = dao.listaTodos();
	}
	
	public void removerAutorDoLivro(Autor autor){
		//this.livro.getAutores().remove(autor);
		this.livro.removeAutor(autor);
	}
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException{
		
		String valor = value.toString();
		if(!valor.startsWith("1")){
			throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
		}
		
	}
	
	public List<Livro> getLivros() {
        DAO<Livro> dao = new DAO<Livro>(Livro.class);
        if(this.livros ==null){
		this.livros = dao.listaTodos();
        }
		return livros;
    }
	
	public String formAutor(){
		System.out.println("Chamando o formulário Autor");
		return "autor?faces-redirect=true";
	}

}
