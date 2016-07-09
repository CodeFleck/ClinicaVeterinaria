package telaProduto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import conexao.Conexao;

public class TelaAdicionaProduto extends JFrame implements ActionListener {

	private JLabel lblcodigo = new JLabel("Código: ");
	private JLabel txtcodigo = new JLabel("");
	private JLabel lblpesquisarFornecedor = new JLabel("Fornecedor: ");
	private JLabel lblFornecedorTel = new JLabel("Telefone:");	
	private JLabel lbldescricao = new JLabel("Descrição: ");
	private JLabel lblmarca = new JLabel("Marca: ");
	private JLabel lblobservacao = new JLabel("Observação: ");
	
	private JTextField txtdescricao = new JTextField();
	private JTextField txtmarca = new JTextField();
	private JTextField txtfornecedor = new JTextField();
	private JTextField txtFornecedorTel = new JTextField();
	
	private JTextArea txtobservacao = new JTextArea(200,120);

	private JScrollPane painel = new JScrollPane();
	
	private String nome;
	
	private JComboBox<String> cbFornecedores = new JComboBox<>();
		
	private ImageIcon imgalterar = new ImageIcon("./imagens/edit.png");
	private ImageIcon imgsalvar = new ImageIcon("./imagens/save2.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");

	private JButton btsalvar = new JButton("Salvar", imgsalvar);
	private JButton btfechar = new JButton("Fechar", imgfechar);
	private JButton btalterar = new JButton("Alterar", imgalterar);	
	
	int codTemp;
	boolean selected = true;

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	public TelaAdicionaProduto() {
		setSize(390, 400);
		setTitle("Cadastro de Produto");
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		
		lblcodigo.setBounds(10, 25, 210, 22);
		lblpesquisarFornecedor.setBounds(10, 60, 120, 22);
		cbFornecedores.setBounds(120, 60, 210, 22);
		lblFornecedorTel.setBounds(10, 95, 140, 22);
		lbldescricao.setBounds(10, 130, 120, 22);
		lblmarca.setBounds(10, 165, 120, 22);
		lblobservacao.setBounds(10, 200, 120, 22);
		
		txtcodigo.setBounds(125, 25, 210, 22);
		txtFornecedorTel.setBounds(120, 95, 210, 22);
		txtdescricao.setBounds(120, 130, 210, 22);
		txtmarca.setBounds(120, 165, 210, 22);
		txtobservacao.setBounds(125, 200, 200, 100);
		txtobservacao.setLineWrap(true);
		txtobservacao.setWrapStyleWord(true);
		
		painel.setBounds(75, 280, 275, 100);
		btsalvar.setBounds(76, 326, 100, 30);
		btfechar.setBounds(186, 326, 115, 30);

		add(lblpesquisarFornecedor);
		add(cbFornecedores);
		add(txtFornecedorTel);
		add(lblFornecedorTel);
		
		add(lblcodigo);
		add(txtcodigo);
		add(lbldescricao);
		add(txtdescricao);
		add(lblmarca);
		add(txtmarca);
		
		add(lblobservacao);
		add(txtobservacao);
		add(btsalvar);
		add(btfechar);

		btsalvar.addActionListener(this);
		btfechar.addActionListener(this);
		
		//Populando meu cbFornecedores
		try {
			PreparedStatement ps = retornaConexao.prepareStatement(
					"Select * from fornecedor");
			ResultSet rs = ps.executeQuery();
			
			cbFornecedores.insertItemAt("", 0);
			
			while (rs.next()) {
				cbFornecedores.addItem(rs.getString(3));
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		cbFornecedores.addActionListener(new ActionListener() {
			 
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        event.getSource();
		        
		        String nome = (String) cbFornecedores.getSelectedItem();
		        try {
		        	
					Class.forName("com.mysql.jdbc.Driver");
					PreparedStatement ps = retornaConexao.prepareStatement(
							"Select fone, codigo from fornecedor where nome_fantasia=? ");
					
					ps.setString(1, nome);
					
					ResultSet rs = ps.executeQuery();
					
					while (rs.next()) {
						txtFornecedorTel.setText(rs.getString(1));
						codTemp = rs.getInt(2);
						String codTempS = Integer.toString(codTemp);
						txtcodigo.setText(codTempS);
					}
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
		        
		    }
		});

	}

	public TelaAdicionaProduto(int codigo) {
		setSize(390, 400);
		setTitle("Alteração de Produto");
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);

		lblcodigo.setBounds(10, 25, 210, 22);
		lblpesquisarFornecedor.setBounds(10, 60, 120, 22);
		cbFornecedores.setBounds(120, 60, 210, 22);
		lblFornecedorTel.setBounds(10, 95, 140, 22);
		lbldescricao.setBounds(10, 130, 120, 22);
		lblmarca.setBounds(10, 165, 120, 22);
		lblobservacao.setBounds(10, 200, 120, 22);
		
		txtcodigo.setBounds(125, 25, 210, 22);
		txtFornecedorTel.setBounds(120, 95, 210, 22);
		txtdescricao.setBounds(120, 130, 210, 22);
		txtmarca.setBounds(120, 165, 210, 22);
		txtobservacao.setBounds(125, 200, 200, 100);
		
		painel.setBounds(75, 280, 275, 100);
		btalterar.setBounds(76, 326, 100, 30);
		btfechar.setBounds(186, 326, 115, 30);

		add(lblpesquisarFornecedor);
		add(cbFornecedores);
		add(txtFornecedorTel);
		add(lblFornecedorTel);
		
		add(lblcodigo);
		add(txtcodigo);
		add(lbldescricao);
		add(txtdescricao);
		add(lblmarca);
		add(txtmarca);
		
		add(lblobservacao);
		add(txtobservacao);
		add(btalterar);
		add(btfechar);

		btalterar.addActionListener(this);
		btfechar.addActionListener(this);

		//Populando meu cbFornecedores
				try {
					PreparedStatement ps = retornaConexao.prepareStatement(
							"Select * from fornecedor");
					ResultSet rs = ps.executeQuery();
					
					cbFornecedores.insertItemAt("", 0);
					
					while (rs.next()) {
						cbFornecedores.addItem(rs.getString(3));
					}
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
		
		
		try {
			PreparedStatement ps = retornaConexao.prepareStatement("SELECT p.codigo, p.descricao, p.marca, p.for_cdgo, f.nome_fantasia, f.fone, p.obs FROM produto p, fornecedor f WHERE p.for_cdgo = f.codigo and p.codigo = ?");

			ps.setInt(1, codigo);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				txtdescricao.setText(rs.getString("p.descricao"));
				txtmarca.setText(rs.getString("p.marca"));
				txtfornecedor.setText(rs.getString("p.for_cdgo"));
				cbFornecedores.setSelectedItem(rs.getString("f.nome_fantasia"));
				txtFornecedorTel.setText(rs.getString("f.fone"));
				txtobservacao.setText(rs.getString("p.obs"));
				txtcodigo.setText(rs.getString("p.codigo"));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar dados " + ex);
			System.out.println(ex);
			ex.printStackTrace();

		}
		
		
		
		cbFornecedores.addActionListener(new ActionListener() {
			 
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        event.getSource();
		        
		         nome = (String) cbFornecedores.getSelectedItem();
		        try {
		        	PreparedStatement ps = retornaConexao.prepareStatement(
							"Select fone, codigo from fornecedor where nome_fantasia=? ");
					
					ps.setString(1, nome);
					
					ResultSet rs = ps.executeQuery();
					
					while (rs.next()) {
						txtFornecedorTel.setText(rs.getString(1));
						codTemp = rs.getInt(2);
					}
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
		        
		    }
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btalterar) {
			try {
				PreparedStatement ps = retornaConexao.prepareStatement(
						"UPDATE produto SET descricao = ?,marca = ?,for_cdgo = ?,obs = ?" + "WHERE codigo = ?");

				ps.setString(1, txtdescricao.getText()); 
				ps.setString(2, txtmarca.getText());
				ps.setString(3, txtfornecedor.getText());
				ps.setString(4, txtobservacao.getText());
				ps.setString(5, txtcodigo.getText());

				ps.executeUpdate();

				JOptionPane.showMessageDialog(this, "Produto alterado com sucesso!");

				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao alterar " + ex);
			}
		}

		if (e.getSource() == btfechar) {
			conexao.FecharConexao();
			dispose();
		}

		if (e.getSource() == btsalvar) {

			try {
				PreparedStatement ps = retornaConexao.prepareStatement(
						"insert into produto (descricao,marca,for_cdgo, obs) values (?,?,?,?) ");

				
				ps.setString(1, txtdescricao.getText());
				ps.setString(2, txtmarca.getText());
				ps.setInt(3, codTemp);
				ps.setString(4, txtobservacao.getText());
				
				ps.executeUpdate();

				JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");

				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro:" + ex);
				ex.printStackTrace();
			}
		}
	}
}
