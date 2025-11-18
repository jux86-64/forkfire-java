package model;

import javax.persistence.*;

@Entity
@Table(name = "receita")
public class Receita {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
	private String titulo;
	
	@Column(nullable = false)
	private String ingrediente;
	
	@Column(nullable = false)
	private float tempoPreparo;
	
	@Column(nullable = false)
	private String modoPreparo;
	
	@Column(nullable = false)
	private String categoria;

	// TODO foto
	
	public Receita(Long id, String titulo, String ingrediente, float tempoPreparo, String modoPreparo,
			String categoria) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.ingrediente = ingrediente;
		this.tempoPreparo = tempoPreparo;
		this.modoPreparo = modoPreparo;
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(String ingrediente) {
		this.ingrediente = ingrediente;
	}

	public float getTempoPreparo() {
		return tempoPreparo;
	}

	public void setTempoPreparo(float tempoPreparo) {
		this.tempoPreparo = tempoPreparo;
	}

	public String getModoPreparo() {
		return modoPreparo;
	}

	public void setModoPreparo(String modoPreparo) {
		this.modoPreparo = modoPreparo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}	
	
}
