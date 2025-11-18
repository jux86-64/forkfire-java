package model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import controller.ApplicationConfig;
import model.ModelError;
import model.Receita;

public class DaoReceita {
	
	private EntityManager em = ApplicationConfig.entityManager;

	public Long incluir(Receita receita) throws ModelError {
		try {
			em.getTransaction().begin();
			em.persist(receita);
			em.getTransaction().commit();
			return receita.getId();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new ModelError("Erro ao incluir receita: " + e.getMessage(), e);
		}
	}

	public Receita obterPorId(Long id) throws ModelError {
		try {
			Receita receita = em.find(Receita.class, id);
			return receita;
		} catch (Exception e) {
			throw new ModelError("Erro ao obter receita por ID: " + e.getMessage(), e);
		}
	}

	public List<Receita> obterTodas() throws ModelError {
		try {
			String jpql = "SELECT r FROM Receita r ORDER BY r.titulo";
			TypedQuery<Receita> query = em.createQuery(jpql, Receita.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new ModelError("Erro ao obter todas as receitas: " + e.getMessage(), e);
		}
	}

	public boolean atualizar(Receita receita) throws ModelError {
		try {
			em.getTransaction().begin();
			em.merge(receita);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new ModelError("Erro ao atualizar receita: " + e.getMessage(), e);
		}
	}

	public boolean excluir(Long id) throws ModelError {
		try {
			em.getTransaction().begin();
			Receita receita = em.find(Receita.class, id);
			if (receita != null) {
				em.remove(receita);
				em.getTransaction().commit();
				return true;
			}
			em.getTransaction().rollback();
			throw new ModelError("Receita com ID " + id + " não encontrada", null);
		} catch (ModelError me) {
			throw me;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new ModelError("Erro ao excluir receita: " + e.getMessage(), e);
		}
	}

	public List<Receita> obterPorCategoria(String categoria) throws ModelError {
		try {
			String jpql = "SELECT r FROM Receita r WHERE r.categoria = :categoria ORDER BY r.titulo";
			TypedQuery<Receita> query = em.createQuery(jpql, Receita.class);
			query.setParameter("categoria", categoria);
			return query.getResultList();
		} catch (Exception e) {
			throw new ModelError("Erro ao obter receitas por categoria: " + e.getMessage(), e);
		}
	}
	
	
	public List<Receita> obterPorUsuario(Long usuarioId) throws ModelError {
		try {
			String jpql = "SELECT r FROM Receita r WHERE r.usuarioId = :usuarioId ORDER BY r.titulo";
			TypedQuery<Receita> query = em.createQuery(jpql, Receita.class);
			query.setParameter("usuarioId", usuarioId);
			return query.getResultList();
		} catch (Exception e) {
			throw new ModelError("Erro ao obter receitas por usuário: " + e.getMessage(), e);
		}
	}

	
	public List<Receita> buscarPorTitulo(String titulo) throws ModelError {
		try {
			String jpql = "SELECT r FROM Receita r WHERE LOWER(r.titulo) LIKE LOWER(:titulo) ORDER BY r.titulo";
			TypedQuery<Receita> query = em.createQuery(jpql, Receita.class);
			query.setParameter("titulo", "%" + titulo + "%");
			return query.getResultList();
		} catch (Exception e) {
			throw new ModelError("Erro ao buscar receitas por título: " + e.getMessage(), e);
		}
	}

	
	public List<Receita> obterPorTempoMaximo(Integer tempoMaximo) throws ModelError {
		try {
			String jpql = "SELECT r FROM Receita r WHERE r.tempoPreparo <= :tempoMaximo ORDER BY r.tempoPreparo";
			TypedQuery<Receita> query = em.createQuery(jpql, Receita.class);
			query.setParameter("tempoMaximo", tempoMaximo);
			return query.getResultList();
		} catch (Exception e) {
			throw new ModelError("Erro ao obter receitas por tempo: " + e.getMessage(), e);
		}
	}
}
