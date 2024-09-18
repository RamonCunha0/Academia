



Passo a passo do código

1. Classe abstrata Pessoa

public abstract class Pessoa {
    protected String nome;
    protected String cpf;
    protected String telefone;

    public Pessoa(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public abstract void mostrarDetalhes();
}

2. Interface Gerenciavel

public interface Gerenciavel {
    void adicionar();
    void remover();
    void listar();
}

3. Classe Aluno

public class Aluno extends Pessoa implements Gerenciavel {
    private String matricula;

    public Aluno(String nome, String cpf, String telefone, String matricula) {
        super(nome, cpf, telefone);
        this.matricula = matricula;
    }

    @Override
    public void mostrarDetalhes() {
        System.out.println("Aluno: " + nome + " | CPF: " + cpf + " | Telefone: " + telefone + " | Matrícula: " + matricula);
    }

    @Override
    public void adicionar() {
        System.out.println("Aluno adicionado: " + nome);
    }

    @Override
    public void remover() {
        System.out.println("Aluno removido: " + nome);
    }

    @Override
    public void listar() {
        mostrarDetalhes();
    }
}

4. Classe Pagamento

public class Pagamento {
    private String data;
    private double valor;
    private String metodoPagamento;

    public Pagamento(String data, double valor, String metodoPagamento) {
        this.data = data;
        this.valor = valor;
        this.metodoPagamento = metodoPagamento;
    }

    public void mostrarDetalhes() {
        System.out.println("Pagamento de " + valor + " realizado em " + data + " via " + metodoPagamento);
    }

    public void processarPagamento() {
        System.out.println("Processando pagamento...");
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Pagamento processado com sucesso.");
            } catch (InterruptedException e) {
                System.out.println("Erro ao processar pagamento.");
            }
        });
        thread.start();
    }
}

5. Classe Academia para gerenciar alunos e pagamentos

import java.util.ArrayList;
import java.util.List;

public class Academia {
    private List<Aluno> alunos = new ArrayList<>();
    private List<Pagamento> pagamentos = new ArrayList<>();

    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
        System.out.println("Aluno adicionado: " + aluno.nome);
    }

    public void removerAluno(Aluno aluno) {
        alunos.remove(aluno);
        System.out.println("Aluno removido: " + aluno.nome);
    }

    public void listarAlunos() {
        System.out.println("Listando alunos...");
        for (Aluno aluno : alunos) {
            aluno.mostrarDetalhes();
        }
    }

    public void registrarPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
        pagamento.processarPagamento();
    }

    public void listarPagamentos() {
        System.out.println("Listando pagamentos...");
        for (Pagamento pagamento : pagamentos) {
            pagamento.mostrarDetalhes();
        }
    }
}

6. Conexão JDBC para gerenciar alunos e pagamentos

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexaoJDBC {

    private static final String URL = "jdbc:mysql://localhost:3306/academia";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public void inserirAluno(String nome, String cpf, String telefone, String matricula) {
        String sql = "INSERT INTO alunos (nome, cpf, telefone, matricula) VALUES (?, ?, ?, ?)";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, telefone);
            stmt.setString(4, matricula);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir aluno", e);
        }
    }

    public void inserirPagamento(String data, double valor, String metodoPagamento) {
        String sql = "INSERT INTO pagamentos (data, valor, metodo_pagamento) VALUES (?, ?, ?)";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, data);
            stmt.setDouble(2, valor);
            stmt.setString(3, metodoPagamento);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir pagamento", e);
        }
    }
}

7. Classe Main

public class Main {
    public static void main(String[] args) {
        Academia academia = new Academia();

        Aluno aluno1 = new Aluno("João Silva", "12345678901", "99999-9999", "A001");
        Aluno aluno2 = new Aluno("Maria Souza", "98765432100", "88888-8888", "A002");

        academia.adicionarAluno(aluno1);
        academia.adicionarAluno(aluno2);

        academia.listarAlunos();

        Pagamento pagamento1 = new Pagamento("2024-09-17", 150.00, "Cartão de Crédito");
        Pagamento pagamento2 = new Pagamento("2024-09-18", 100.00, "Dinheiro");

        academia.registrarPagamento(pagamento1);
        academia.registrarPagamento(pagamento2);

        academia.listarPagamentos();

        // Simulação de inserção de dados no banco de dados
        ConexaoJDBC conexao = new ConexaoJDBC();
        conexao.inserirAluno("João Silva", "12345678901", "99999-9999", "A001");
        conexao.inserirPagamento("2024-09-17", 150.00, "Cartão de Crédito");
    }
}

