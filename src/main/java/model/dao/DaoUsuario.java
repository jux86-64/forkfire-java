package model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import controller.ApplicationConfig;
import model.ModelError;
import model.Usuario;

public class DaoUsuario {
	
	private EntityManager em = ApplicationConfig.entityManager;

	public boolean incluir(Usuario usuario) throws ModelError {
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ModelError("Erro ao incluir usuário: " + e.getMessage(), e);
        }
    }
    
	
    public Usuario obterPorId(Long id) throws ModelError {
        try {
            Usuario usuario = em.find(Usuario.class, id);
            return usuario;
        } catch (Exception e) {
            throw new ModelError("Erro ao obter usuário por ID: " + e.getMessage(), e);
        }
    }
    
    
    public Usuario obterPorEmail(String email) throws ModelError {
        try {
            String jpql = "SELECT u FROM Usuario u WHERE u.email = :email";
            TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; 
        } catch (Exception e) {
            throw new ModelError("Erro ao obter usuário por email: " + e.getMessage(), e);
        }
    }
    
    
    public boolean atualizar(Usuario usuario) throws ModelError {
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ModelError("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }
    
    
    public boolean excluir(Long id) throws ModelError {
        try {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                em.remove(usuario);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            throw new ModelError("Usuário com ID " + id + " não encontrado", null);
        } catch (ModelError me) {
            throw me;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ModelError("Erro ao excluir usuário: " + e.getMessage(), e);
        }
    }
    
   
    public List<Usuario> listarTodos() throws ModelError {
        try {
            String jpql = "SELECT u FROM Usuario u ORDER BY u.nome";
            TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new ModelError("Erro ao listar usuários: " + e.getMessage(), e);
        }
    }
    
    
    public boolean emailExiste(String email) throws ModelError {
        try {
            Usuario usuario = obterPorEmail(email);
            return usuario != null;
        } catch (Exception e) {
            throw new ModelError("Erro ao verificar email: " + e.getMessage(), e);
        }
    }
    
    
    public Usuario login(String email, String senha) throws ModelError {
        try {
            String jpql = "SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha";
            TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
            query.setParameter("email", email);
            query.setParameter("senha", senha);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; 
        } catch (Exception e) {
            throw new ModelError("Erro ao realizar login: " + e.getMessage(), e);
        }
    }
    
    
    public List<Usuario> buscarPorNome(String nome) throws ModelError {
        try {
            String jpql = "SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(:nome) ORDER BY u.nome";
            TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
            query.setParameter("nome", "%" + nome + "%");
            return query.getResultList();
        } catch (Exception e) {
            throw new ModelError("Erro ao buscar usuários por nome: " + e.getMessage(), e);
        }
    }
    
   
    public boolean atualizarSenha(Long id, String novaSenha) throws ModelError {
        try {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                usuario.setSenha(novaSenha);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            throw new ModelError("Usuário com ID " + id + " não encontrado", null);
        } catch (ModelError me) {
            throw me;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ModelError("Erro ao atualizar senha: " + e.getMessage(), e);
        }
    }
}
