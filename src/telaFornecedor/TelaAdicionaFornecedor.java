package telaFornecedor;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ferramentas.GhostText;
import conexao.Conexao;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//classes para conectar ao banco de dados
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class TelaAdicionaFornecedor extends JFrame implements ActionListener {

	private JLabel lblcodigo = new JLabel("Código");
	private JLabel txtcodigo = new JLabel("");
	private JLabel lblNomeFantasia = new JLabel("Nome:");
	private JLabel lblRazaoSocial = new JLabel("Razao Social:");
	private JLabel lblCNPJ = new JLabel("CNPJ:");
	private JLabel lblFone = new JLabel("Fone:");
	private JLabel lblEmail = new JLabel("Email:");
	private JLabel lblEndereco = new JLabel("Endereço:");
	private JLabel lblCidade = new JLabel("Cidade:");
	private JLabel lblEstado = new JLabel("Estado:");

	private JTextField txtNomeFantasia = new JTextField();
	private JTextField txtRazaoSocial = new JTextField();
	private JTextField txtCNPJ = new JTextField();
	private JTextField txtFone = new JTextField();
	private JTextField txtEmail = new JTextField();
	private JTextField txtEndereco = new JTextField();
	private JTextField txtCidade = new JTextField();
	private JTextField txtEstado = new JTextField();

	private JScrollPane painel = new JScrollPane();
	
	private ImageIcon imgalterar = new ImageIcon("./imagens/edit.png");
	private ImageIcon imgsalvar = new ImageIcon("./imagens/save2.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");

	private JButton btSalvar = new JButton("Salvar", imgsalvar);
	private JButton btFechar = new JButton("Fechar", imgfechar);
	private JButton btalterar = new JButton("Alterar", imgalterar);	

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	// CONSTRUTOR
	public TelaAdicionaFornecedor() {
		setSize(370, 400);
		setResizable(false);
		setTitle("Cadastro de Fornecedores");
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		lblNomeFantasia.setBounds(10, 10, 100, 22);
		lblRazaoSocial.setBounds(10, 40, 100, 14);
		lblCNPJ.setBounds(10, 70, 45, 22);
		lblFone.setBounds(10, 100, 100, 22);
		lblEmail.setBounds(10, 130, 100, 22);
		lblEndereco.setBounds(10, 160, 100, 22);
		lblCidade.setBounds(10, 190, 54, 22);
		lblEstado.setBounds(10, 220, 100, 22);

		txtNomeFantasia.setBounds(95, 10, 250, 22);
		txtRazaoSocial.setBounds(95, 40, 250, 20);
		txtCNPJ.setBounds(95, 70, 250, 22); 
		new GhostText(txtCNPJ, "00.000.000/0000-00");	
		txtFone.setBounds(95, 100, 250, 22); 
		new GhostText(txtFone, "(00) 0000 0000");
		txtEmail.setBounds(95, 130, 250, 22);
		txtEndereco.setBounds(95, 160, 250, 22);
		txtCidade.setBounds(95, 190, 250, 22);
		txtEstado.setBounds(95, 220, 40, 22);

		painel.setBounds(75, 280, 275, 100);
		btSalvar.setBounds(76, 326, 100, 30);
		btFechar.setBounds(186, 326, 115, 30);

		getContentPane().add(lblNomeFantasia);
		getContentPane().add(lblRazaoSocial);
		getContentPane().add(lblCNPJ);
		getContentPane().add(lblFone);
		getContentPane().add(lblEmail);
		getContentPane().add(lblEndereco);
		getContentPane().add(lblCidade);
		getContentPane().add(lblEstado);

		getContentPane().add(txtcodigo);
		getContentPane().add(txtNomeFantasia);
		getContentPane().add(txtRazaoSocial);
		getContentPane().add(txtCNPJ);
		getContentPane().add(txtFone);
		getContentPane().add(txtEmail);
		getContentPane().add(txtEndereco);
		getContentPane().add(txtCidade);
		getContentPane().add(txtEstado);

		getContentPane().add(btSalvar);
		getContentPane().add(btFechar);

		// ouvinte de evento
		btSalvar.addActionListener(this);
		btFechar.addActionListener(this);

	}

	// construtor que sera executado para carregar dados alterar

	public TelaAdicionaFornecedor(int id) {
		setSize(370, 400);
		setResizable(false);
		setTitle("Alteração de Fornecedores");
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		lblcodigo.setBounds(10, 20, 100, 22);
		lblNomeFantasia.setBounds(10, 58, 100, 22);
		lblRazaoSocial.setBounds(10, 91, 100, 22);
		lblCNPJ.setBounds(10, 122, 45, 22);
		lblFone.setBounds(10, 148, 100, 22);
		lblEmail.setBounds(10, 178, 100, 22);
		lblEndereco.setBounds(10, 211, 100, 22);
		lblCidade.setBounds(10, 243, 54, 22);
		lblEstado.setBounds(10, 273, 100, 22);

		txtcodigo.setBounds(105, 20, 100, 22);
		txtNomeFantasia.setBounds(100, 58, 245, 22);
		txtRazaoSocial.setBounds(100, 88, 245, 22);
		txtCNPJ.setBounds(100, 122, 245, 22);
		txtFone.setBounds(100, 148, 245, 22);
		txtEmail.setBounds(100, 178, 245, 22);
		txtEndereco.setBounds(100, 211, 245, 22);
		txtCidade.setBounds(100, 243, 245, 22);
		txtEstado.setBounds(100, 273, 40, 22);

		painel.setBounds(75, 280, 275, 100);
		btalterar.setBounds(76, 326, 115, 30);
		btFechar.setBounds(196, 326, 115, 30);

		getContentPane().add(lblcodigo);
		getContentPane().add(lblNomeFantasia);
		getContentPane().add(lblRazaoSocial);
		getContentPane().add(lblCNPJ);
		getContentPane().add(lblFone);
		getContentPane().add(lblEmail);
		getContentPane().add(lblEndereco);
		getContentPane().add(lblCidade);
		getContentPane().add(lblEstado);

		getContentPane().add(txtcodigo);
		getContentPane().add(txtNomeFantasia);
		getContentPane().add(txtRazaoSocial);
		getContentPane().add(txtCNPJ);
		getContentPane().add(txtFone);
		getContentPane().add(txtEmail);
		getContentPane().add(txtEndereco);
		getContentPane().add(txtCidade);
		getContentPane().add(txtEstado);

		getContentPane().add(btalterar);
		getContentPane().add(btFechar);

		// ouvinte de evento
		btalterar.addActionListener(this);
		btFechar.addActionListener(this);

		// aqui carregar dados do cliente a alterar

		try {
			
			PreparedStatement ps = retornaConexao.prepareStatement("Select * from fornecedor where codigo=?");

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {// se encontrou - carregar os dados nas caixas

				txtNomeFantasia.setText(rs.getString("nome_fantasia"));
				txtRazaoSocial.setText(rs.getString("razao_social"));
				txtCNPJ.setText(rs.getString("cnpj"));
				txtFone.setText(rs.getString("fone"));
				txtEmail.setText(rs.getString("email"));
				txtEndereco.setText(rs.getString("endereco"));
				txtCidade.setText(rs.getString("cidade"));
				txtEstado.setText(rs.getString("estado"));
				txtcodigo.setText(rs.getString("codigo"));

			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar dados " + ex);
			System.out.println(ex);
			ex.printStackTrace();

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btalterar) {
			try {

				PreparedStatement ps = retornaConexao.prepareStatement(
						"Update fornecedor set nome_fantasia=?,razao_social=?,cnpj=?,fone=?,email=?,endereco=?,cidade=?,estado=?"
								+ "where codigo=?");

				ps.setString(1, txtNomeFantasia.getText());
				ps.setString(2, txtRazaoSocial.getText());
				ps.setString(3, txtCNPJ.getText());
				ps.setString(4, txtFone.getText());
				ps.setString(5, txtEmail.getText());
				ps.setString(6, txtEndereco.getText());
				ps.setString(7, txtCidade.getText());
				ps.setString(8, txtEstado.getText());
				ps.setString(9, txtcodigo.getText());

				ps.executeUpdate();

				JOptionPane.showMessageDialog(this, "Fornecedor alterado com sucesso!");

				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao alterar " + ex);
			}
		}

		if (e.getSource() == btFechar) {
			dispose();
		}

		if (e.getSource() == btSalvar) {

			try {
				PreparedStatement ps = retornaConexao.prepareStatement(
						"insert into fornecedor(nome_fantasia,razao_social,cnpj,fone,email,endereco,cidade,estado)  values(?,?,?,?,?,?,?,?) ");

				ps.setString(1, txtNomeFantasia.getText());
				ps.setString(2, txtRazaoSocial.getText());
				ps.setString(3, txtCNPJ.getText());
				ps.setString(4, txtFone.getText());
				ps.setString(5, txtEmail.getText());
				ps.setString(6, txtEndereco.getText());
				ps.setString(7, txtCidade.getText());
				ps.setString(8, txtEstado.getText());

				ps.executeUpdate();

				JOptionPane.showMessageDialog(this, "Fornecedor cadastrado com sucesso!");

				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro:" + ex);
				ex.printStackTrace();
			}
		}
	}
}