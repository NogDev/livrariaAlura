package br.com.caelum.livraria.bean;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModel;

@Named
@ViewScoped
public class LivroBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();
	
	@Inject
	LivroDao livroDao;
	
	@Inject
	AutorDao autorDao;
	
	private Integer livroId;
	
	private LivroDataModel livroDataModel = new LivroDataModel();
	
	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");

	public List<String> getGeneros() {
	    return generos;
	}

	public Integer getLivroId() {
	        return livroId;
	}

	public void setLivroId(Integer livroId) {
	        this.livroId = livroId;
	}
	
	
	public void carregaPelaId() {
        this.livro = this.livroDao.buscaPorId(this.livroId);
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
		return this.autorDao.listaTodos();
	}
	
	public List<Autor> getAutoresDoLivro(){
		return this.livro.getAutores();
	}
	
	public void gravarAutor(){
		Autor autor = this.autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}

	public void gravar() {
	    System.out.println("Gravando livro " + this.livro.getTitulo());

	    if (livro.getAutores().isEmpty()) {
	        FacesContext.getCurrentInstance().addMessage("autor",
	                new FacesMessage("Livro deve ter pelo menos um Autor."));
	        return;
	    }


	    if(this.livro.getId() == null) {
	        livroDao.adiciona(this.livro);

	        // Novo livro adicionado, listamos todos os livros novamente
	        this.livros = livroDao.listaTodos();
	    } else {
	        livroDao.atualiza(this.livro);
	    }

	    this.livro = new Livro();
	}
	
	public void carregar(Livro livro){
		System.out.println("Carregando o livro");
		this.livro = livro; // carrega os campos do formulário com os dados do item selecionado
	}
	
	public void remover(Livro livro){
		System.out.println("Removendo livro");
		livroDao.remove(livro);
		this.livros = livroDao.listaTodos();
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
        if(this.livros ==null){
		this.livros = livroDao.listaTodos();
        }
		return livros;
    }
	
	public String formAutor(){
		System.out.println("Chamando o formulário Autor");
		return "autor?faces-redirect=true";
	}
	
	
	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) { // java.util.Locale

        //tirando espaços do filtro
        String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();

        System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " + valorColuna);

        // o filtro é nulo ou vazio?
        if (textoDigitado == null || textoDigitado.equals("")) {
            return true;
        }

        // elemento da tabela é nulo?
        if (valorColuna == null) {
            return false;
        }

        try {
            // fazendo o parsing do filtro para converter para Double
            Double precoDigitado = Double.valueOf(textoDigitado);
            Double precoColuna = (Double) valorColuna;

            // comparando os valores, compareTo devolve um valor negativo se o value é menor do que o filtro
            return precoColuna.compareTo(precoDigitado) < 0;

        } catch (NumberFormatException e) {

            // usuario nao digitou um numero
            return false;
        }
	}

	public LivroDataModel getLivroDataModel() {
		return livroDataModel;
	}

	public void setLivroDataModel(LivroDataModel livroDataModel) {
		this.livroDataModel = livroDataModel;
	}
	
}
