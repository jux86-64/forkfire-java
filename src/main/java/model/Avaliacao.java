package model;

import javax.persistence.*;

@Entity
@Table(name = "avaliacao")
public class Avaliacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
	private int nota;
	
	@Column(nullable = false)
	private String dataAvaliacao;
	
	@Column(nullable = false)
	private Usuario usuarioID;
	
	@Column(nullable = false)
	private Receita receitaID;
	
	public Avaliacao(Long id, int nota, String dataAvaliacao, Usuario usuarioID, Receita receitaID) {
		super();
		this.id = id;
		this.nota = nota;
		this.dataAvaliacao = dataAvaliacao;
		this.usuarioID = usuarioID;
		this.receitaID = receitaID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public String getDataAvaliacao() {
		return dataAvaliacao;
	}

	public void setDataAvaliacao(String dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}

	public Usuario getUsuarioID() {
		return usuarioID;
	}

	public void setUsuarioID(Usuario usuarioID) {
		this.usuarioID = usuarioID;
	}

	public Receita getReceitaID() {
		return receitaID;
	}

	public void setReceitaID(Receita receitaID) {
		this.receitaID = receitaID;
	}
	
	
	
}
