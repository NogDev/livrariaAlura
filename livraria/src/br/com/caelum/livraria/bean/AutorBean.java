package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;

@Named
@ViewScoped
public class AutorBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();
	
	@Inject
	private AutorDao dao;
	
	
	private Integer autorId;

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void carregarAutorPelaId(){
		this.autor = this.dao.buscaPorId(autorId);
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
			this.dao.adiciona(this.autor);
		}else{
			this.dao.atualiza(this.autor);
		}
		
		this.autor = new Autor();
		
	}
	
	public List<Autor> getAutores() {
        return this.dao.listaTodos();
	}
	
	public void carregar(Autor autor){
		System.out.println("Carregando o autor");
		this.autor = autor; 
	}
	
	public void remover(Autor autor){
		System.out.println("Removendo Autor");
		this.dao.remove(autor);//não entendo pq não utiliza o this
	}
	
	
}
