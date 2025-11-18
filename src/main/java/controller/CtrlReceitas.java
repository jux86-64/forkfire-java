package controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CtrlReceitas {
	private String base64String = null;
    private Receita receitaAtual;
    
    private static final String[] CATEGORIAS = {
        "Todos", "Aperitivos", "Bebidas", "Bolos", "Carnes", 
        "Comida Árabe", "Comida Asiática", "Comida Mexicana", "Doces", 
        "Gluten-Free", "Lanches", "Low Carb", "Massas", "Pães e Tortas", 
        "Saladas", "Salgados", "Sobremesas", "Sopas e Caldos", 
        "Vegano", "Vegetariano"
    };
    
    private static final Map<String, String> IMAGENS_CATEGORIAS = new HashMap<String, String>() {{
        put("Todos", "/forkfire/src/main/java/assets/images/categorias/todos-min.png");
        put("Aperitivos", "/forkfire/src/main/java/assets/images/categorias/aperitivo-min.png");
        put("Bebidas", "/forkfire/src/main/java/assets/images/categorias/bebidas-min.png");
        put("Bolos", "/forkfire/src/main/java/assets/images/categorias/bolos-min.png");
        put("Carnes", "/forkfire/src/main/java/assets/images/categorias/carnes-min.png");
        put("Comida Árabe", "/forkfire/src/main/java/assets/images/categorias/comida_arabe-min.png");
        put("Comida Asiática", "/forkfire/src/main/java/assets/images/categorias/comida_asiatica-min.png");
        put("Comida Mexicana", "/forkfire/src/main/java/assets/images/categorias/comida_mexicana-min.png");
        put("Doces", "/forkfire/src/main/java/assets/images/categorias/doces-min.png");
        put("Gluten-Free", "/forkfire/src/main/java/assets/images/categorias/gluten_free-min.png");
        put("Lanches", "/forkfire/src/main/java/assets/images/categorias/lanches-min.png");
        put("Low Carb", "/forkfire/src/main/java/assets/images/categorias/low_carb-min.png");
        put("Massas", "/forkfire/src/main/java/assets/images/categorias/massas-min.png");
        put("Pães e Tortas", "/forkfire/src/main/java/assets/images/categorias/paes-min.png");
        put("Saladas", "/forkfire/src/main/java/assets/images/categorias/saladas-min.png");
        put("Salgados", "/forkfire/src/main/java/assets/images/categorias/salgados-min.png");
        put("Sobremesas", "/forkfire/src/main/java/assets/images/categorias/sobremesas-min.png");
        put("Sopas e Caldos", "/forkfire/src/main/java/assets/images/categorias/sopas-min.png");
        put("Vegano", "/forkfire/src/main/java/assets/images/categorias/vegano-min.png");
        put("Vegetariano", "/forkfire/src/main/java/assets/images/categorias/vegetariano-min.png");
    }};
    
    public void processarImagemBase64(String imageBase64) {
        try {
            this.base64String = imageBase64;
            
            int tamanhoKB = imageBase64.length() / 1024;
            System.out.println("Imagem comprimida e convertida. Tamanho: " + tamanhoKB + "KB");
            
        } catch (Exception error) {
            System.err.println("Erro ao processar imagem: " + error.getMessage());
            this.base64String = null;
        }
    }
    
    public void cadastrarReceita(String userId, String titulo, String ingredientes, 
                                 String categoria, String modoPreparo, String tempoPreparo) {
        try {
            titulo = titulo.trim();
            ingredientes = ingredientes.trim();
            categoria = categoria.trim();
            modoPreparo = modoPreparo.trim().replaceAll("\\s+", " ");
            tempoPreparo = tempoPreparo.trim();
            
            long dataHora = System.currentTimeMillis();
            
            Receita novaReceita = new Receita();
            novaReceita.setTitulo(titulo);
            novaReceita.setIngredientes(ingredientes);
            novaReceita.setCategoria(categoria);
            novaReceita.setModoPreparo(modoPreparo);
            novaReceita.setTempoPreparo(tempoPreparo);
            novaReceita.setFoto(base64String);
            novaReceita.setCriadoPor(userId);
            novaReceita.setDataCriacao(dataHora);
            
            salvarReceita(userId, dataHora, novaReceita);
            
            System.out.println("Receita salva com sucesso!");
            
            base64String = null;
            
        } catch (Exception error) {
            System.err.println("Erro ao salvar receita: " + error.getMessage());
        }
    }
    
    public List<Receita> buscarReceitas(String categoriaDesejada) {
        try {
            List<Receita> todasReceitas = buscarTodasReceitasDoBanco();
            
            if (categoriaDesejada == null || categoriaDesejada.equals("Todos")) {
                return todasReceitas;
            }
            
            List<Receita> receitasFiltradas = new ArrayList<>();
            for (Receita receita : todasReceitas) {
                if (receita.getCategoria().equals(categoriaDesejada)) {
                    receitasFiltradas.add(receita);
                }
            }
            
            return receitasFiltradas;
            
        } catch (Exception error) {
            System.err.println("Erro ao buscar receitas: " + error.getMessage());
            return new ArrayList<>();
        }
    }
    
    public List<Receita> buscarTodasReceitas() {
        try {
            return buscarTodasReceitasDoBanco();
        } catch (Exception error) {
            System.err.println("Erro ao buscar receitas: " + error.getMessage());
            return new ArrayList<>();
        }
    }
    
    public void renderizarReceitas(List<Receita> receitas) {
        if (receitas.isEmpty()) {
            System.out.println("Nenhuma receita encontrada.");
            return;
        }
        
        for (Receita receita : receitas) {
            Date dataCriacao = new Date(receita.getDataCriacao());
            
            System.out.println("=== " + receita.getTitulo() + " ===");
            System.out.println("Categoria: " + receita.getCategoria());
            System.out.println("Tempo de Preparo: " + receita.getTempoPreparo() + " min");
            System.out.println("Criado em: " + dataCriacao);
            System.out.println();
        }
    }
    
    public String[] getCategorias() {
        return CATEGORIAS;
    }
    
    public void renderizarCategorias() {
        System.out.println("=== CATEGORIAS ===");
        for (String categoria : CATEGORIAS) {
            System.out.println("- " + categoria);
        }
    }
    
    public void mostrarDetalhesReceita(Receita receita) {
        this.receitaAtual = receita;
        
        Date dataCriacao = new Date(receita.getDataCriacao());
        
        // Buscar nome do usuário
        Usuario usuario = buscarUsuario(receita.getCriadoPor());
        String nomeUsuario = usuario != null ? usuario.getNome() : "Anônimo";
        
        System.out.println("\n========== DETALHES DA RECEITA ==========");
        System.out.println("Título: " + receita.getTitulo());
        System.out.println("Tempo de Preparo: " + receita.getTempoPreparo() + " min");
        System.out.println("Data de Criação: " + dataCriacao);
        System.out.println("Criado por: " + nomeUsuario);
        System.out.println("Categoria: " + receita.getCategoria());
        System.out.println("\n--- INGREDIENTES ---");
        System.out.println(receita.getIngredientes());
        System.out.println("\n--- MODO DE PREPARO ---");
        System.out.println(receita.getModoPreparo());
        System.out.println("=========================================\n");
    }
    
    private void salvarReceita(String userId, long timestamp, Receita receita) {
        // TODO Implementar salvamento no banco de dados
    }
    
    private List<Receita> buscarTodasReceitasDoBanco() {
        // TODO Implementar busca no banco de dados
        return new ArrayList<>();
    }
    
    private Usuario buscarUsuario(String userId) {
        // TODO Implementar busca no banco de dados
        return new Usuario();
    }
    
    public static class Receita {
        private String titulo;
        private String ingredientes;
        private String categoria;
        private String modoPreparo;
        private String tempoPreparo;
        private String foto;
        private String criadoPor;
        private long dataCriacao;
        
        public String getTitulo() { 
        	return titulo; 
        }
        
        public void setTitulo(String titulo) { 
        	this.titulo = titulo; 
        }
        
        public String getIngredientes() { 
        	return ingredientes; 
        }
        
        public void setIngredientes(String ingredientes) { 
        	this.ingredientes = ingredientes; 
        }
        
        public String getCategoria() { 
        	return categoria; 
        }
        
        public void setCategoria(String categoria) { 
        	this.categoria = categoria; 
        }
        
        public String getModoPreparo() { 
        	return modoPreparo; 
        }
        
        public void setModoPreparo(String modoPreparo) { 
        	this.modoPreparo = modoPreparo; 
        }
        
        public String getTempoPreparo() { 
        	return tempoPreparo; 
        }
        
        public void setTempoPreparo(String tempoPreparo) { 
        	this.tempoPreparo = tempoPreparo; 
        }
        
        public String getFoto() { 
        	return foto; 
        }
        
        public void setFoto(String foto) { 
        	this.foto = foto; 
        }
        
        public String getCriadoPor() { 
        	return criadoPor; 
        }
        
        public void setCriadoPor(String criadoPor) { 
        	this.criadoPor = criadoPor; 
        }
        
        public long getDataCriacao() { 
        	return dataCriacao; 
        }
        
        public void setDataCriacao(long dataCriacao) { 
        	this.dataCriacao = dataCriacao; 
        }
    }
    
    public static class Usuario {
        private String nome;
        
        public String getNome() { 
        	return nome != null ? nome : "Anônimo"; 
        }
        
        public void setNome(String nome) { 
        	this.nome = nome; 
        }
    }
}
