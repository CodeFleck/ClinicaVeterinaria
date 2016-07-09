package telaSOC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import conexao.Conexao;

public class TelaSOC extends JFrame implements ActionListener {

	private JLabel lblnome = new JLabel("Cliente: ");
	private JLabel lblanimal = new JLabel("Animal: ");

	private ImageIcon imgnovo = new ImageIcon("./imagens/new.png");
	private ImageIcon imgalterar = new ImageIcon("./imagens/edit.png");
	private ImageIcon imgexcluir = new ImageIcon("./imagens/delete2.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");
	private ImageIcon imgpesquisar = new ImageIcon("./imagens/find2.png");

	private JComboBox<String> cbClientes = new JComboBox<>();
	private JComboBox<String> cbAnimal = new JComboBox<>();
	private JLabel data = new JLabel("Data: ");
	private JTextField txtDtCons = new JTextField();

	private Integer codCli;
	private Integer codAni;
	private String nomeCli = null;
	private String nomeAni = null;
	private String codSOC;
	private Integer codSOCi;

	// painel que lista ultimas consultas
	private String[] titulos = { "Data", "Conduta", " " };
	private DefaultTableModel modeloparatabela = new DefaultTableModel(titulos, 0);
	private JTable tabela = new JTable(modeloparatabela) {

		@Override
		public boolean isCellEditable(int row, int col) {

			return false;
		}
	};
	private JScrollPane painel = new JScrollPane(tabela);

	private JButton btnovo = new JButton("Novo", imgnovo);
	private JButton btalterar = new JButton("Abrir", imgalterar);
	private JButton btexcluir = new JButton("Excluir", imgexcluir);
	private JButton btfechar = new JButton("Fechar", imgfechar);
	private JButton btpesquisar = new JButton("Pesquisar", imgpesquisar);

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	// construtor
	public TelaSOC() {
		setSize(825, 400);
		setTitle("Prontuário");
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);

		// mascara da data e hora
		MaskFormatter formatter = null;

		try {
			formatter = new MaskFormatter("##-##-####");
		} catch (ParseException e) {
		}
		txtDtCons = new JFormattedTextField(formatter);

		// populando o cbClientes e o cbAnimais
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
				codAni = 0;
				nomeCli = (String) cbClientes.getSelectedItem();

				try {
					PreparedStatement ps = retornaConexao
							.prepareStatement("Select nome, codigo from cliente where nome=? ");

					ps.setString(1, nomeCli);

					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						nomeCli = rs.getString(1);
						codCli = rs.getInt(2);
					}

					ps = retornaConexao.prepareStatement("SELECT a.codigo, a.nome FROM animal a "
							+ "WHERE a.cli_cdgo IN (SELECT c.codigo FROM cliente c WHERE c.nome = ? )");
					ps.setString(1, nomeCli);
					rs = ps.executeQuery();

					cbAnimal.removeAllItems();
					cbAnimal.insertItemAt("", 0);

					while (rs.next()) {
						cbAnimal.addItem(rs.getString(2));
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}

			}
		});

		cbAnimal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				event.getSource();

				nomeAni = (String) cbAnimal.getSelectedItem();
				try {
					PreparedStatement ps = retornaConexao.prepareStatement("Select codigo from animal where nome=? ");

					ps.setString(1, nomeAni);

					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						codAni = rs.getInt(1);
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}

			}
		});

		cbAnimal.setBounds(360, 10, 200, 22);
		cbClientes.setBounds(70, 10, 200, 22);
		add(cbAnimal);
		add(cbClientes);

		painel.setBounds(10, 40, 800, 300);
		painel.setViewportView(tabela);

		lblanimal.setBounds(300, 10, 100, 22);
		lblnome.setBounds(10, 10, 100, 22);
		btnovo.setBounds(10, 345, 100, 22);
		btalterar.setBounds(120, 345, 100, 22);
		btexcluir.setBounds(230, 345, 100, 22);
		btfechar.setBounds(680, 345, 100, 22);
		btpesquisar.setBounds(650, 10, 130, 22);

		add(lblnome);
		add(lblanimal);
		add(btnovo);
		add(btalterar);
		add(btexcluir);
		add(btfechar);
		add(btpesquisar);
		add(painel);

		btnovo.addActionListener(this);
		btalterar.addActionListener(this);
		btexcluir.addActionListener(this);
		btfechar.addActionListener(this);
		btpesquisar.addActionListener(this);

		tabela.getTableHeader().setReorderingAllowed(true);
		tabela.getTableHeader().setResizingAllowed(true);

		tabela.getColumnModel().getColumn(0).setMinWidth(50);
		tabela.getColumnModel().getColumn(0).setMaxWidth(150);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(90);
		tabela.getColumnModel().getColumn(1).setWidth(MAXIMIZED_HORIZ);

		// tornando coluna 3 invisivel
		tabela.removeColumn(tabela.getColumnModel().getColumn(2));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btfechar) {
			dispose();
		}

		if (e.getSource() == btpesquisar) {
			if (nomeAni == null || nomeCli == null) {
				JOptionPane.showMessageDialog(this, "Escolha um cliente e um animal!");
			} else {
				try {
					PreparedStatement ps = retornaConexao.prepareStatement(
							"SELECT cdgo_soc, date_format(soc_data_atual, '%d-%m-%Y') soc_data_atual, conduta FROM soc where animal_cdgo like ?");

					ps.setInt(1, codAni);
					ResultSet rs = ps.executeQuery();

					String linha[] = new String[3];

					modeloparatabela.setRowCount(0);

					while (rs.next()) {

						linha[0] = rs.getString("soc_data_atual");
						linha[1] = rs.getString("conduta");
						linha[2] = rs.getString(("cdgo_soc"));

						modeloparatabela.addRow(linha);
					}

					rs.close();
					ps.close();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao pesquisar: " + ex);
				}
			}
		}

		else if (e.getSource() == btnovo) {
			if (codAni == null) {
				JOptionPane.showMessageDialog(this, "Selecione um animal!");
			} else {
				TelaAdicionaSOC tas = new TelaAdicionaSOC(codAni);
				tas.setVisible(true);
			}
		}

		else if (e.getSource() == btalterar) {
			if (tabela.getSelectedRowCount() > 1) {
				JOptionPane.showMessageDialog(this, "Selecione apenas um registro");
			} else {
				if (tabela.getSelectedRow() == -1) {

					JOptionPane.showMessageDialog(this, "Selecione um registro");
				} else {
					codSOC = tabela.getModel().getValueAt(tabela.getSelectedRow(), 2).toString();
					codSOCi = Integer.parseInt(codSOC);
					TelaAlteraSOC tals = new TelaAlteraSOC(codSOCi);
					tals.setVisible(true);
				}
			}
		}
		if (e.getSource() == btexcluir) {

			if (tabela.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "Selecione a linha a excluir!");
			} else {
				if (tabela.getSelectedRowCount() > 1) {
					JOptionPane.showMessageDialog(this, "Selecione apenas 1 linha!");

				} else {
					int resposta = JOptionPane.showConfirmDialog(this, "Deseja excluir?");

					// recebendo o valor do cdgo_soc da tabela oculta vazia
					codSOC = tabela.getModel().getValueAt(tabela.getSelectedRow(), 2).toString();
					codSOCi = Integer.parseInt(codSOC);

					if (resposta == 0) {

						try {
							PreparedStatement ps = retornaConexao.prepareStatement("Delete from soc where cdgo_soc =?");

							ps.setInt(1, codSOCi);

							ps.executeUpdate();

							modeloparatabela.removeRow(tabela.getSelectedRow());

							JOptionPane.showMessageDialog(this, "Registro excluido com sucesso!");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(this, "Erro:" + ex);
						}
					}
				}
			}
		}
	}
}
