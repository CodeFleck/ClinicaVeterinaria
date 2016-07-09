package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexao {
	private Connection conexao;
	private String url = "jdbc:mysql://localhost:3306/bicho_exclusivo";
	private String user = "root";
	private String senha = "root";
	private static Conexao instancia;

	private Conexao() {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			this.conexao = DriverManager.getConnection(url, user, senha);

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Erro ao pesquisar: " + e);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "N„o foi possÌvel conectar ao Banco de Dados: " + e);
		}

	}

	// Instancia uma nova Conex√£o com o Banco de Dados
	public static Conexao getInstancia() {

		if (instancia == null) {
			instancia = new Conexao();
		}

		return instancia;
	}

	// Retorna a conexao com o Banco de Dados
	public Connection getConexao() {
		return this.conexao;
	}

	// Fecha a conex√£o com o Banco de Dados
	public boolean FecharConexao() {
		try {
			instancia = null;
			getConexao().close();
			return true;

		} catch (SQLException e) {
			return false;
		}
	}

}
