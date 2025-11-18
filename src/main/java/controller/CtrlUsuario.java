package controller;

public class CtrlUsuario {
	
	public void atualizarFotoPerfil(String usuarioId, String imagemBase64) {
        if (imagemBase64 == null || imagemBase64.isEmpty()) {
            System.err.println("Nenhuma imagem fornecida");
            return;
        }
        
        try {
            if (imagemBase64.length() > 200 * 1024) { // ~200KB
                System.err.println("Imagem muito grande após compressão. Tente outra foto.");
                return;
            }
            
            atualizarFotoPerfilNoBanco(usuarioId, imagemBase64);
            
            System.out.println("Foto de perfil atualizada com sucesso!");
            System.out.println("Tamanho da imagem: " + imagemBase64.length() + " bytes");
            
        } catch (Exception error) {
            System.err.println("Erro ao atualizar foto: " + error.getMessage());
        }
    }
    
    public Usuario buscarUsuario(String userId) {
        try {
            return buscarUsuarioDoBanco(userId);
        } catch (Exception error) {
            System.err.println("Erro ao buscar usuário: " + error.getMessage());
            return null;
        }
    }
    
    public void atualizarDadosUsuario(String userId, String nome, String email) {
        try {
            Usuario usuario = new Usuario();
            usuario.setId(userId);
            usuario.setNome(nome);
            usuario.setEmail(email);
            
            atualizarUsuarioNoBanco(userId, usuario);
            
            System.out.println("Dados do usuário atualizados com sucesso!");
            
        } catch (Exception error) {
            System.err.println("Erro ao atualizar dados do usuário: " + error.getMessage());
        }
    }
    
    private void atualizarFotoPerfilNoBanco(String userId, String fotoPerfil) {
        // TODO Implementar atualização no banco de dados
        // UPDATE usuarios SET foto_perfil = ? WHERE id = ?
    }
    
    private Usuario buscarUsuarioDoBanco(String userId) {
        // TODO Implementar busca no banco de dados
        // SELECT * FROM usuarios WHERE id = ?
        return new Usuario();
    }
    
    private void atualizarUsuarioNoBanco(String userId, Usuario usuario) {
        // TODO Implementar atualização no banco de dados
        // UPDATE usuarios SET nome = ?, email = ? WHERE id = ?
    }
    
    public static class Usuario {
        private String id;
        private String nome;
        private String email;
        private String senha;
        private String fotoPerfil;
        private String funcao;
        
        public Usuario() {
            this.funcao = "USUARIO";
        }
        
        public Usuario(String id, String nome, String email) {
            this.id = id;
            this.nome = nome;
            this.email = email;
            this.funcao = "USUARIO";
        }
        
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
        public String getNome() {
            return nome;
        }
        
        public void setNome(String nome) {
            this.nome = nome;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getSenha() {
            return senha;
        }
        
        public void setSenha(String senha) {
            this.senha = senha;
        }
        
        public String getFotoPerfil() {
            return fotoPerfil;
        }
        
        public void setFotoPerfil(String fotoPerfil) {
            this.fotoPerfil = fotoPerfil;
        }
        
        public String getFuncao() {
            return funcao;
        }
        
        public void setFuncao(String funcao) {
            this.funcao = funcao;
        }
        
        @Override
        public String toString() {
            return "Usuario{" +
                    "id='" + id + '\'' +
                    ", nome='" + nome + '\'' +
                    ", email='" + email + '\'' +
                    ", funcao='" + funcao + '\'' +
                    '}';
        }
    }
}
