package controller;

public class CtrlAuth {
	public void logar(String email, String senha) {
        try {
            Usuario user = autenticarUsuario(email, senha);
            if (user != null) {
                System.out.println("Usuario Logado com sucesso!");
                redirecionarParaHome();
            }
        } catch (Exception erro) {
            System.err.println("Erro ao logar: " + erro.getMessage());
            
            String mensagemErro = "";
            
            if (erro.getMessage().contains("missing-password")) {
                mensagemErro = "Senha não pode estar vazia.";
            } else if (erro.getMessage().contains("invalid-credential")) {
                mensagemErro = "Senha inválida.";
            } else if (erro.getMessage().contains("invalid-email")) {
                mensagemErro = "Usuário não encontrado.";
            } else {
                mensagemErro = "Erro ao fazer login: " + erro.getMessage();
            }
            
            exibirErro(mensagemErro);
        }
    }
    
    public void cadastrar(String email, String senha, String nome, String segundaSenha) {
        if (!senha.equals(segundaSenha)) {
            exibirErro("As senhas não coincidem.");
            return;
        }
        
        if (nome == null || nome.trim().isEmpty()) {
            exibirErro("Nome não pode estar vazio.");
            return;
        }
        
        try {
            Usuario user = criarUsuarioComEmailESenha(email, senha);
            System.out.println("Usuário Cadastrado: " + user.getEmail());
            
            salvarUsuario(user.getId(), nome, email, senha);
            
            System.out.println("Cadastro realizado com sucesso!");
            
            Thread.sleep(2000);
            
        } catch (Exception erro) {
            System.err.println("Erro ao Cadastrar: " + erro.getMessage());
            
            String mensagemErro = "";
            
            if (erro.getMessage().contains("email-already-in-use")) {
                mensagemErro = "Email já está em uso.";
            } else if (erro.getMessage().contains("invalid-email")) {
                mensagemErro = "Email inválido.";
            } else if (erro.getMessage().contains("missing-password")) {
                mensagemErro = "Senha não pode estar vazia.";
            } else if (erro.getMessage().contains("weak-password")) {
                mensagemErro = "Senha muito fraca.";
            } else {
                mensagemErro = "Erro ao fazer Cadastro: " + erro.getMessage();
            }
            
            exibirErro(mensagemErro);
        }
    }
    
    public void logOut() {
        try {
            deslogarUsuario();
            System.out.println("Usuário deslogado com sucesso!");
            redirecionarParaIndex();
        } catch (Exception erro) {
            System.err.println("Erro ao deslogar: " + erro.getMessage());
        }
    }
     
    private Usuario autenticarUsuario(String email, String senha) {
        return null;
    }
    
    private Usuario criarUsuarioComEmailESenha(String email, String senha) {
        return null;
    }
    
    private void salvarUsuario(String id, String nome, String email, String senha) {
        // TODO Implementar lógica de salvamento no banco de dados
    }
    
    private void deslogarUsuario() {
        // TODO Implementar lógica de logout
    }
    
    private void redirecionarParaHome() {
        // TODO Implementar redirecionamento
    }
    
    private void redirecionarParaIndex() {
        // TODO Implementar redirecionamento
    }
    
    private void exibirErro(String mensagem) {
        System.err.println(mensagem);
    }
    
    public static class Usuario {
        private String id;
        private String email;
        
        public Usuario(String id, String email) {
            this.id = id;
            this.email = email;
        }
        
        public String getId() {
            return id;
        }
        
        public String getEmail() {
            return email;
        }
    }
}
