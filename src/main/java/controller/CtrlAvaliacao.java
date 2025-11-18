package controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CtrlAvaliacao {
	private int estrelas = 0;
    private Receita receitaAtual;
    
    public void obterEstrela(int avaliacao) {
        this.estrelas = avaliacao;
        System.out.println("Estrela selecionada: " + avaliacao);
    }
    
    public void enviarAvaliacao(String usuarioId, String comentario) {
        try {
            if (receitaAtual == null) {
                System.err.println("Nenhuma receita selecionada para avaliação");
                return;
            }
            
            if (estrelas == 0) {
                System.err.println("Por favor, selecione uma avaliação com as estrelas");
                return;
            }
            
            if (comentario == null || comentario.trim().isEmpty()) {
                System.err.println("Por favor, escreva um comentário");
                return;
            }
            
            long dataHora = System.currentTimeMillis();
            
            Avaliacao novaAvaliacao = new Avaliacao();
            novaAvaliacao.setNota(estrelas);
            novaAvaliacao.setDataAvaliacao(dataHora);
            novaAvaliacao.setCriadoPor(usuarioId);
            novaAvaliacao.setReceitaId(String.valueOf(receitaAtual.getDataCriacao()));
            novaAvaliacao.setComentario(comentario);
            
            salvarAvaliacao(usuarioId, dataHora, novaAvaliacao);
            
            System.out.println("Avaliação enviada com sucesso!");
            
            estrelas = 0;
            
            carregarAvaliacoes(receitaAtual);
            
        } catch (Exception error) {
            System.err.println("Erro ao enviar avaliação: " + error.getMessage());
        }
    }
    
    public void atualizarMediaReceita(Receita receita, Integer novaAvaliacao) {
        try {
            List<Avaliacao> avaliacoes = buscarTodasAvaliacoes();
            
            double total = 0;
            int count = 0;
            
            for (Avaliacao avaliacao : avaliacoes) {
                if (avaliacao.getReceitaId().equals(String.valueOf(receita.getDataCriacao()))) {
                    total += avaliacao.getNota();
                    count++;
                }
            }
            
            if (novaAvaliacao != null) {
                total += novaAvaliacao;
                count++;
            }
            
            double media = count > 0 ? (total / count) : 0;
            
            atualizarReceita(receita.getCriadoPor(), receita.getDataCriacao(), media, count);
            
            System.out.println("Média atualizada: " + String.format("%.1f", media));
            
        } catch (Exception error) {
            System.err.println("Erro ao atualizar média: " + error.getMessage());
            throw new RuntimeException(error);
        }
    }
    
    public void carregarAvaliacoes(Receita receita) {
        this.receitaAtual = receita;
        
        try {
            List<Avaliacao> todasAvaliacoes = buscarTodasAvaliacoes();
            List<AvaliacaoComUsuario> avaliacoesDaReceita = new ArrayList<>();
            
            for (Avaliacao avaliacao : todasAvaliacoes) {
                if (avaliacao.getReceitaId().equals(String.valueOf(receita.getDataCriacao()))) {
                    Usuario usuario = buscarUsuario(avaliacao.getCriadoPor());
                    AvaliacaoComUsuario avaliacaoComUsuario = new AvaliacaoComUsuario();
                    avaliacaoComUsuario.setAvaliacao(avaliacao);
                    avaliacaoComUsuario.setUsuario(usuario);
                    avaliacoesDaReceita.add(avaliacaoComUsuario);
                }
            }
            
            if (avaliacoesDaReceita.isEmpty()) {
                System.out.println("Nenhuma avaliação ainda.");
            } else {
                exibirAvaliacoes(avaliacoesDaReceita);
            }
            
        } catch (Exception error) {
            System.err.println("Erro ao carregar avaliações: " + error.getMessage());
        }
    }
    
    private void salvarAvaliacao(String userId, long timestamp, Avaliacao avaliacao) {
        // TODO Implementar salvamento no banco de dados
    }
    
    private List<Avaliacao> buscarTodasAvaliacoes() {
        // TODO Implementar busca no banco de dados
        return new ArrayList<>();
    }
    
    private Usuario buscarUsuario(String userId) {
        // TODO Implementar busca no banco de dados
        return new Usuario();
    }
    
    private void atualizarReceita(String userId, long dataCriacao, double media, int count) {
        // TODO Implementar atualização no banco de dados
    }
    
    private void exibirAvaliacoes(List<AvaliacaoComUsuario> avaliacoes) {
        for (AvaliacaoComUsuario item : avaliacoes) {
            System.out.println("Usuário: " + item.getUsuario().getNome());
            System.out.println("Nota: " + item.getAvaliacao().getNota());
            System.out.println("Comentário: " + item.getAvaliacao().getComentario());
            System.out.println("Data: " + new Date(item.getAvaliacao().getDataAvaliacao()));
            System.out.println("---");
        }
    }
    
    public static class Avaliacao {
        private int nota;
        private long dataAvaliacao;
        private String criadoPor;
        private String receitaId;
        private String comentario;
        
        public int getNota() { 
        	return nota; 
        }
        
        public void setNota(int nota) { 
        	this.nota = nota; 
        }
        
        public long getDataAvaliacao() { 
        	return dataAvaliacao; 
        }
        
        public void setDataAvaliacao(long dataAvaliacao) { 
        	this.dataAvaliacao = dataAvaliacao; 
        }
        
        public String getCriadoPor() { 
        	return criadoPor; 
        }
        
        public void setCriadoPor(String criadoPor) { 
        	this.criadoPor = criadoPor; 
        }
        
        public String getReceitaId() { 
        	return receitaId; 
        }
        
        public void setReceitaId(String receitaId) { 
        	this.receitaId = receitaId; 
        }
        
        public String getComentario() { 
        	return comentario; 
        }
        
        public void setComentario(String comentario) { 
        	this.comentario = comentario; 
        }
    }
    
    public static class Receita {
        private String titulo;
        private long dataCriacao;
        private String criadoPor;
        
        public String getTitulo() { 
        	return titulo; 
        }
        
        public void setTitulo(String titulo) { 
        	this.titulo = titulo; 
        }
        
        public long getDataCriacao() { 
        	return dataCriacao; 
        }
        
        public void setDataCriacao(long dataCriacao) { 
        	this.dataCriacao = dataCriacao; 
        }
        
        public String getCriadoPor() { 
        	return criadoPor; 
        }
        
        public void setCriadoPor(String criadoPor) { 
        	this.criadoPor = criadoPor; 
        }
    }
    
    public static class Usuario {
        private String nome;
        private String fotoPerfil;
        
        public String getNome() { 
        	return nome != null ? nome : "Anônimo"; 
        }
        
        public void setNome(String nome) { 
        	this.nome = nome; 
        }
        
        public String getFotoPerfil() { 
        	return fotoPerfil; 
        }
        
        public void setFotoPerfil(String fotoPerfil) { 
        	this.fotoPerfil = fotoPerfil; 
        }
    }
    
    public static class AvaliacaoComUsuario {
        private Avaliacao avaliacao;
        private Usuario usuario;
        
        public Avaliacao getAvaliacao() { 
        	return avaliacao; 
        }
        
        public void setAvaliacao(Avaliacao avaliacao) { 
        	this.avaliacao = avaliacao; 
        }
        
        public Usuario getUsuario() { 
        	return usuario; 
        }
        
        public void setUsuario(Usuario usuario) { 
        	this.usuario = usuario; 
        }
    }
}
