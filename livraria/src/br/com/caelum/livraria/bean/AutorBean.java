package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;

@Named
@ViewScoped
public class AutorBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();
	
	private Integer autorId;

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void carregarAutorPelaId(){
		this.autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Autor getAutor() {
		return autor;
	}

	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());
		
		if (this.autor.getId() == null ) {
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		}else{
			new DAO<Autor>(Autor.class).atualiza(this.autor);
		}
		
		this.autor = new Autor();
		
	}
	
	public List<Autor> getAutores() {
        return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public void carregar(Autor autor){
		System.out.println("Carregando o autor");
		this.autor = autor; 
	}
	
	public void remover(Autor autor){
		System.out.println("Removendo Autor");
		new DAO<Autor>(Autor.class).remove(autor);//não entendo pq não utiliza o this
	}
	
	
}
