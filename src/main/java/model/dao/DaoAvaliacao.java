package model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import controller.ApplicationConfig;
import model.Avaliacao;
import model.ModelError;

public class DaoAvaliacao {
	
	private EntityManager em = ApplicationConfig.entityManager;
	
	public Long incluir(Avaliacao avaliacao) throws ModelError {
		try {
			em.getTransaction().begin();
			em.persist(avaliacao);
			em.getTransaction().commit();
			return avaliacao.getId();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new ModelError("Erro ao incluir avaliação: " + e.getMessage(), e);
		}
	}
	
	public List<Avaliacao> obterPorReceita(Long receitaId) throws ModelError {
		try {
			String jpql = "SELECT a FROM Avaliacao a WHERE a.receitaId = :receitaId ORDER BY a.dataAvaliacao DESC";
			TypedQuery<Avaliacao> query = em.createQuery(jpql, Avaliacao.class);
			query.setParameter("receitaId", receitaId);
			return query.getResultList();
		} catch (Exception e) {
			throw new ModelError("Erro ao obter avaliações por receita: " + e.getMessage(), e);
			
		}
	}
	
	public Double calcularMediaReceita(Long receitaId) throws ModelError {
		try {
			String jpql = "SELECT AVG(a.nota) FROM Avaliacao a WHERE a.receitaId = :receitaId";
			TypedQuery<Double> query = em.createQuery(jpql, Double.class);
			query.setParameter("receitaId", receitaId);
			Double media = query.getSingleResult();
			return media != null ? media : 0.0;
		} catch (Exception e) {
			throw new ModelError("Erro ao calcular média da receita: " + e.getMessage(), e);
		}
	}
	
	public boolean excluir(Long id) throws ModelError {
		try {
			em.getTransaction().begin();
			Avaliacao avaliacao = em.find(Avaliacao.class, id);
			if (avaliacao != null) {
				em.remove(avaliacao);
				em.getTransaction().commit();
				return true;
			}
			em.getTransaction().rollback();
			throw new ModelError("Avaliação com ID" + id + " não encontrada", null);
		} catch (ModelError me) {
			throw me;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new ModelError("Erro ao excluir avaliação: " + e.getMessage(), e);
		}
	}
	
	public Avaliacao obterPorId(Long id) {
		return em.find(Avaliacao.class, id);
	}
	
	public List<Avaliacao> obterPorUsuario(Long usuarioId) throws ModelError {
		try {
			String jpql = "SELECT a FROM Avaliacao a WHERE a.usuarioId = :usuarioId ORDER BY a.dataAvaliacao DESC";
			TypedQuery<Avaliacao> query = em.createQuery(jpql, Avaliacao.class);
			query.setParameter("usuarioId", usuarioId);
			return query.getResultList();
		} catch (Exception e) {
			throw new ModelError("Erro ao obter avaliações por usuário: " + e.getMessage(), e);		
		}
	}
	
	public void atualizar(Avaliacao avaliacao) throws ModelError {
		try {
			em.getTransaction().begin();
			em.merge(avaliacao);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new ModelError("Erro ao atualizar avaliação: " + e.getMessage(), e);
		}
	}
	
	public List<Avaliacao> listarTodas() throws ModelError {
		try {
			String jpql = "SELECT a FROM Avaliacao a ORDER BY a.dataAvaliacao DESC";
			TypedQuery<Avaliacao> query = em.createQuery(jpql, Avaliacao.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new ModelError("Erro ao listar avaliações: " + e.getMessage(), e);
		}
	}
}
