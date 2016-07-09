package telaAnimal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//classes para conectar ao banco de dados
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import conexao.Conexao;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ferramentas.GhostText;

public class TelaAdicionaAnimal extends JFrame implements ActionListener {

	SimpleDateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat formatoData2 = new SimpleDateFormat("yyyy-MM-dd");

	private JLabel lblcodigo = new JLabel("Código:");
	private JLabel txtcodigo = new JLabel("");
	private JLabel lblanimal = new JLabel("Animal:");
	private JLabel lblRaca = new JLabel("Raça:");
	private JLabel lblNome = new JLabel("Nome:");
	private JLabel lblPeso = new JLabel("Peso:");
	private JLabel lblIdade = new JLabel("Idade:");
	private JLabel lblSexo = new JLabel("Sexo:");
	private JLabel lblDtNasc = new JLabel("Data Nasc:");
	private JLabel lblPelagem = new JLabel("Pelagem:");
	private JLabel lblCastrado = new JLabel("Castrado:");
	private JLabel lblCliente = new JLabel("Cliente:");
	private JLabel lblClienteTel = new JLabel("Fone:");
	private JLabel lblObs = new JLabel("OBS:");

	private JTextField txtRaca = new JTextField();
	private JTextField txtNome = new JTextField();
	private JTextField txtPeso = new JTextField();
	private JTextField txtIdade = new JTextField();
	private JTextField txtDtNasc = new JTextField();
	private JTextField txtPelagem = new JTextField();
	private JTextField txtcliente = new JTextField();
	private JTextField txtclienteTel = new JTextField();
	private JTextField txtObs = new JTextField();

	private JComboBox<String> cbAnimal = new JComboBox<String>();
	private JComboBox<String> cbSexo = new JComboBox<String>();
	private JComboBox<String> cbCastrado = new JComboBox<String>();
	private JComboBox<String> cbClientes = new JComboBox<>();
	
	private JScrollPane painel = new JScrollPane();
	
	private ImageIcon imgalterar = new ImageIcon("./imagens/edit.png");
	private ImageIcon imgsalvar = new ImageIcon("./imagens/save2.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");

	private JButton btSalvar = new JButton("Salvar", imgsalvar);
	private JButton btFechar = new JButton("Fechar", imgfechar);
	private JButton btSalvarAlteracao = new JButton("Alterar", imgalterar);
	
	private Integer codCli, codAni;
	
	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	// CONSTRUTOR
	public TelaAdicionaAnimal() {
		setSize(420, 400);
		setResizable(false);
		setTitle("Cadastro de Animais");
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);		
		
		lblcodigo.setBounds(10, 11, 100, 22);
		lblanimal.setBounds(10, 70, 100, 22);
		lblRaca.setBounds(210, 70, 100, 22);
		lblNome.setBounds(10, 40, 100, 22);
		lblPeso.setBounds(10, 130, 100, 22);
		lblIdade.setBounds(270, 100, 100, 22);
		lblSexo.setBounds(154, 100, 100, 22);
		lblDtNasc.setBounds(154, 130, 100, 22);
		lblPelagem.setBounds(10, 160, 100, 22);
		lblCastrado.setBounds(10, 100, 100, 22);
		lblObs.setBounds(10, 190, 100, 22);
		lblCliente.setBounds(10, 220, 100, 22);

		txtcodigo.setBounds(80, 11, 100, 22);
		cbAnimal.setBounds(80, 70, 120, 22);
		txtRaca.setBounds(250, 70, 136, 22);
		txtNome.setBounds(80, 40, 305, 22);
		txtPeso.setBounds(80, 130, 60, 22);
		txtIdade.setBounds(315, 100, 70, 22);
		cbSexo.setBounds(196, 100, 63, 22);
		txtDtNasc.setBounds(235, 130, 150, 22);
		txtPelagem.setBounds(80, 160, 305, 22);
		cbCastrado.setBounds(80, 100, 60, 22);
		txtObs.setBounds(80, 190, 305, 22);
		cbClientes.setBounds(80, 220, 150, 22);
		txtclienteTel.setBounds(235, 220, 150, 22);

		painel.setBounds(75, 280, 325, 100);
		btSalvar.setBounds(100, 326, 100, 22);
		btFechar.setBounds(210, 326, 115, 22);

		getContentPane().add(lblanimal);
		getContentPane().add(lblRaca);
		getContentPane().add(lblNome);
		getContentPane().add(lblPeso);
		getContentPane().add(lblIdade);
		getContentPane().add(lblSexo);
		getContentPane().add(lblDtNasc);
		getContentPane().add(lblPelagem);
		getContentPane().add(lblCastrado);
		getContentPane().add(lblObs);
		getContentPane().add(lblCliente);

		getContentPane().add(txtcodigo);
		getContentPane().add(cbAnimal);
		getContentPane().add(txtRaca);
		getContentPane().add(txtNome);
		getContentPane().add(txtPeso);
		getContentPane().add(txtIdade);
		getContentPane().add(cbSexo);
		getContentPane().add(txtDtNasc);
		new GhostText(txtDtNasc, "00/00/0000");
		getContentPane().add(txtPelagem);
		getContentPane().add(cbCastrado);
		getContentPane().add(txtObs);
		getContentPane().add(cbClientes);
		getContentPane().add(txtclienteTel);

		getContentPane().add(btSalvar);
		getContentPane().add(btFechar);

		cbCastrado.setModel(new DefaultComboBoxModel<String>(new String[] { "N", "S" }));
		cbSexo.setModel(new DefaultComboBoxModel<String>(new String[] { "M", "F" }));
		cbAnimal.setModel(new DefaultComboBoxModel<String>(new String[] { "Cachorro", "Gato", "Peixe", "Passaro", "Tartaruga", "Ramster" }));

		
		// ouvinte de evento
		btSalvar.addActionListener(this);
		btFechar.addActionListener(this);

		// populando o JCombo com nomes dos clientes

		try {
			PreparedStatement ps = retornaConexao.prepareStatement("Select * from cliente");
			ResultSet rs = ps.executeQuery();

			cbClientes.insertItemAt("", 0);

			while (rs.next()) {
				cbClientes.addItem(rs.getString(2));
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}


		cbClientes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				event.getSource();

				String nome = (String) cbClientes.getSelectedItem();
				try {
					PreparedStatement ps = retornaConexao.prepareStatement("Select fone, codigo from cliente where nome=? ");

					ps.setString(1, nome);

					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						txtclienteTel.setText(rs.getString(1));
						codCli = rs.getInt(2);
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
	}

	// construtor que sera executado para carregar dados alterar

	public TelaAdicionaAnimal(int id) {
		setSize(420, 400);
		setResizable(false);
		setTitle("Alteração de Animais");
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		codAni = id;

		lblcodigo.setBounds(10, 11, 100, 22);
		lblanimal.setBounds(10, 70, 100, 22);
		lblRaca.setBounds(210, 70, 100, 22);
		lblNome.setBounds(10, 40, 100, 22);
		lblPeso.setBounds(10, 130, 100, 22);
		lblIdade.setBounds(270, 100, 100, 22);
		lblSexo.setBounds(154, 100, 100, 22);
		lblDtNasc.setBounds(154, 130, 100, 22);
		//new GhostText(txtDtNasc, "00/00/0000");
		lblPelagem.setBounds(10, 160, 100, 22);
		lblCastrado.setBounds(10, 100, 100, 22);
		lblObs.setBounds(10, 190, 100, 22);
		lblCliente.setBounds(10, 220, 100, 22);
		lblClienteTel.setBounds(10, 250, 100, 22);

		txtcodigo.setBounds(80, 11, 100, 22);
		cbAnimal.setBounds(80, 70, 120, 22);
		txtRaca.setBounds(250, 70, 136, 22);
		txtNome.setBounds(80, 40, 305, 22);
		txtPeso.setBounds(80, 130, 60, 22);
		txtIdade.setBounds(315, 100, 70, 22);
		cbSexo.setBounds(196, 100, 63, 22);
		txtDtNasc.setBounds(235, 130, 150, 22);
		txtPelagem.setBounds(80, 160, 305, 22);
		cbCastrado.setBounds(80, 100, 60, 22);
		txtObs.setBounds(80, 190, 305, 22);
		txtcliente.setBounds(80, 220, 40, 22);
		cbClientes.setBounds(130, 220, 255, 22);
		txtclienteTel.setBounds(80, 250, 150, 22);

		painel.setBounds(75, 280, 325, 100);

		btSalvarAlteracao.setBounds(100, 326, 100, 22);
		btFechar.setBounds(210, 326, 115, 22);

		getContentPane().add(lblcodigo);
		getContentPane().add(lblanimal);
		getContentPane().add(lblRaca);
		getContentPane().add(lblNome);
		getContentPane().add(lblPeso);
		getContentPane().add(lblIdade);
		getContentPane().add(lblSexo);
		getContentPane().add(lblDtNasc);
		getContentPane().add(lblPelagem);
		getContentPane().add(lblCastrado);
		getContentPane().add(lblObs);
		getContentPane().add(lblCliente);
		getContentPane().add(lblClienteTel);

		getContentPane().add(txtcodigo);
		getContentPane().add(cbAnimal);
		getContentPane().add(txtRaca);
		getContentPane().add(txtNome);
		getContentPane().add(txtPeso);
		getContentPane().add(txtIdade);
		getContentPane().add(cbSexo);
		getContentPane().add(txtDtNasc);
		getContentPane().add(txtPelagem);
		getContentPane().add(cbCastrado);
		getContentPane().add(txtObs);
		getContentPane().add(txtcliente);
		getContentPane().add(cbClientes);
		getContentPane().add(txtclienteTel);

		getContentPane().add(btSalvarAlteracao);
		getContentPane().add(btFechar);

		cbCastrado.setModel(new DefaultComboBoxModel(new String[] { "N", "S" }));
		cbSexo.setModel(new DefaultComboBoxModel(new String[] { "M", "F" }));
		cbAnimal.setModel(new DefaultComboBoxModel(new String[] { "Cachorro", "Gato", "Peixe", "Pássaro", "Tartaruga", "Ramster" }));

		// ouvinte de evento
		btSalvarAlteracao.addActionListener(this);
		btFechar.addActionListener(this);
		
		// populando o JCombo com nomes dos clientes
		
				try {
					PreparedStatement ps = retornaConexao.prepareStatement("Select * from cliente");
					ResultSet rs = ps.executeQuery();

					cbClientes.insertItemAt("", 0);

					while (rs.next()) {
						cbClientes.addItem(rs.getString(2));
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}

		// carregando dados do cliente a alterar

		try {
			PreparedStatement ps = retornaConexao.prepareStatement(
					"SELECT a.codigo, a.especie, a.raca, a.nome, a.peso, a.idade, a.sexo, date_format(a.dt_nasc, '%d-%m-%Y') dt_nasc, a.pelagem, a.castrado, a.obs, a.cli_cdgo, c.nome, c.fone "
							+ "FROM animal a, cliente c " + "WHERE a.cli_cdgo = c.codigo AND a.codigo=?");

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				cbAnimal.setSelectedItem(rs.getString("a.especie"));
				txtRaca.setText(rs.getString("a.raca"));
				txtNome.setText(rs.getString("a.nome"));
				txtPeso.setText(rs.getString("a.peso"));
				txtIdade.setText(rs.getString("a.idade"));
				cbSexo.setSelectedItem(rs.getString("a.sexo"));
				txtDtNasc.setText(rs.getString("dt_nasc"));
				txtPelagem.setText(rs.getString("a.pelagem"));
				cbCastrado.setSelectedItem(rs.getString("a.castrado"));
				txtObs.setText(rs.getString("a.obs"));
				txtcliente.setText(rs.getString("a.cli_cdgo"));
				cbClientes.setSelectedItem(rs.getString("c.nome"));
				txtclienteTel.setText(rs.getString("c.fone"));
				txtcodigo.setText(rs.getString("a.codigo"));

				String formataPeso = txtPeso.getText();
				txtPeso.setText(formataPeso.replace(".", ","));
				
				String formataDtNasc = txtDtNasc.getText();
				txtDtNasc.setText(formataDtNasc.replace("-", "/"));
				
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar dados " + ex);
			System.out.println(ex);
			ex.printStackTrace();

		}

		cbClientes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				event.getSource();

				String nome = (String) cbClientes.getSelectedItem();
				try {
					PreparedStatement ps = retornaConexao.prepareStatement("Select fone, codigo from cliente where nome=? ");

					ps.setString(1, nome);

					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						txtclienteTel.setText(rs.getString(1));
						txtcliente.setText(rs.getString(2));
						codCli = rs.getInt(2);
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}

			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btSalvarAlteracao) {
			
			String formataPeso = txtPeso.getText();
			txtPeso.setText(formataPeso.replace(",", "."));
			
			String formataDtNasc = txtDtNasc.getText();
			txtDtNasc.setText(formataDtNasc.replace("/", "-"));
			
			try {
				PreparedStatement ps = retornaConexao.prepareStatement(
						"Update animal set especie=?,raca=?,nome=?,peso=?,idade=?,sexo=?,dt_nasc=?,pelagem=?,castrado=?,obs=?,cli_cdgo=? where codigo=?");

				ps.setString(1, cbAnimal.getSelectedItem().toString());
				ps.setString(2, txtRaca.getText());
				ps.setString(3, txtNome.getText());
				ps.setString(4, txtPeso.getText());
				ps.setString(5, txtIdade.getText());
				ps.setString(6, cbSexo.getSelectedItem().toString());
				
				Date dtNasc2 = new SimpleDateFormat("dd-MM-yyyy").parse(txtDtNasc.getText());
				String dbDate = new SimpleDateFormat("yyyy-MM-dd").format(dtNasc2);
				ps.setString(7, dbDate);
				
				ps.setString(8, txtPelagem.getText());
				ps.setString(9, cbCastrado.getSelectedItem().toString());
				ps.setString(10, txtObs.getText());
				ps.setString(11, txtcliente.getText());
				//ps.setInt(11, codCli);
				ps.setString(12, txtcodigo.getText());
				//ps.setInt(12, codAni);
				
				ps.executeUpdate();

				JOptionPane.showMessageDialog(this, "Animal alterado com sucesso!");

				dispose();

			} catch (Exception ex) {
				//JOptionPane.showMessageDialog(this, "Favor selecionar o cliente novamente.");
				JOptionPane.showMessageDialog(this, "Erro:" + ex);
				ex.printStackTrace();
			}			
			
		}

		if (e.getSource() == btFechar) {
			dispose();
		}

		if (e.getSource() == btSalvar) {
			
			String formataPeso = txtPeso.getText();
			txtPeso.setText(formataPeso.replace(",", "."));
			
			String formataDtNasc = txtDtNasc.getText();
			txtDtNasc.setText(formataDtNasc.replaceAll("/","-"));
			
			try {
				PreparedStatement ps = retornaConexao.prepareStatement(
						"insert into animal(especie,raca,nome,peso,idade,sexo,dt_nasc,pelagem,castrado,obs,cli_cdgo)  values(?,?,?,?,?,?,?,?,?,?,?) ");

				ps.setString(1, cbAnimal.getSelectedItem().toString());
				ps.setString(2, txtRaca.getText());
				ps.setString(3, txtNome.getText());
				ps.setString(4, txtPeso.getText());
				ps.setString(5, txtIdade.getText());
				ps.setString(6, cbSexo.getSelectedItem().toString());
				
				Date dtNasc = formatoData.parse(txtDtNasc.getText());
				ps.setDate(7, new java.sql.Date(dtNasc.getTime()));

				ps.setString(8, txtPelagem.getText());
				ps.setString(9, cbCastrado.getSelectedItem().toString());
				ps.setString(10, txtObs.getText());
				ps.setInt(11, codCli);

				ps.executeUpdate();

				JOptionPane.showMessageDialog(this, "Animal cadastrado com sucesso!");

				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao inserir dados em algum campo: " + ex);
				ex.printStackTrace();
			}
		}
	}
}