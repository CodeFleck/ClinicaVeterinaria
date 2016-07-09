package agenda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane; //painel de moldura para a tabela
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel; //modelo para tabela
import javax.swing.text.MaskFormatter;

import conexao.Conexao;

public class TelaAgenda extends JFrame implements ActionListener {

	private JLabel lblnome = new JLabel("Data: ");
	private JTextField txtDtCons = new JTextField();
	private javax.swing.JComboBox calendario = new JCalendar(false);
	private GregorianCalendar gregorianCalendar;
	private String[] titulos = { "Cod", "Data", "Hora", "Titulo", "Compromisso" };
	private DefaultTableModel modeloParaTabela = new DefaultTableModel(titulos, 0);
	private ImageIcon imgnovo = new ImageIcon("./imagens/new.png");
	private ImageIcon imgalterar = new ImageIcon("./imagens/edit.png");
	private ImageIcon imgexcluir = new ImageIcon("./imagens/delete2.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");
	private ImageIcon imgpesquisar = new ImageIcon("./imagens/find2.png");
	private SimpleDateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");
	private JTable tabela = new JTable(modeloParaTabela) {
		@Override
		public boolean isCellEditable(int row, int col) {
			return false;
		}
	};
	private JScrollPane painel = new JScrollPane(tabela);

	private JButton btnovo = new JButton("Novo", imgnovo);
	private JButton btalterar = new JButton("Alterar", imgalterar);
	private JButton btexcluir = new JButton("Excluir", imgexcluir);
	private JButton btfechar = new JButton("Fechar", imgfechar);
	private JButton btpesquisar = new JButton("Pesquisar", imgpesquisar);
	private MaskFormatter formatter = null;

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	public TelaAgenda() {
		setSize(900, 400);
		setTitle("Agenda");
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);

		lblnome.setBounds(10, 10, 100, 22);
		btpesquisar.setBounds(145, 10, 120, 22);
		calendario.setBounds(50, 10, 90, 22);
		painel.setBounds(10, 40, 870, 300);
		painel.setViewportView(tabela);

		// redimensionamento das colunas
		tabela.getColumnModel().getColumn(0).setMaxWidth(35);
		tabela.getColumnModel().getColumn(0).setMinWidth(15);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(35);
		tabela.getColumnModel().getColumn(1).setMinWidth(90);
		tabela.getColumnModel().getColumn(1).setMaxWidth(100);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(90);
		tabela.getColumnModel().getColumn(2).setMinWidth(80);
		tabela.getColumnModel().getColumn(2).setMaxWidth(90);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(80);
		tabela.getColumnModel().getColumn(3).setMinWidth(200);
		tabela.getColumnModel().getColumn(3).setMaxWidth(220);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(200);
		tabela.getColumnModel().getColumn(4).setMinWidth(400);
		tabela.getColumnModel().getColumn(4).setMaxWidth(440);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(400);

		btnovo.setBounds(10, 345, 100, 22);
		btalterar.setBounds(120, 345, 100, 22);
		btexcluir.setBounds(230, 345, 100, 22);
		btfechar.setBounds(680, 345, 100, 22);

		add(lblnome);
		add(btpesquisar);
		add(btnovo);
		add(btalterar);
		add(btexcluir);
		add(btfechar);
		add(painel);
		add(calendario);

		calendario.addActionListener(this);
		btpesquisar.addActionListener(this);
		btnovo.addActionListener(this);
		btalterar.addActionListener(this);
		btexcluir.addActionListener(this);
		btfechar.addActionListener(this);

		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.getTableHeader().setResizingAllowed(false);

		try {
			PreparedStatement ps = retornaConexao
					.prepareStatement("SELECT cdgo_compromisso, date_format(data,'%d/%m/%Y') data, hora, titulo, descricao "
							+ "FROM compromisso WHERE data = date_format(CURDATE(),'%Y-%m-%d')");

			// "SELECT c.codigo, c.ani_cdgo, a.nome, c.diagnostico,
			// date_format(c.dt_cons,'%d/%m/%Y') data, c.cli_cdgo,
			// c.nome_cliente, c.hora "
			// + "FROM consulta c, animal a"
			// + " WHERE c.ani_cdgo = a.codigo AND dt_cons =
			// date_format(CURDATE(),'%Y-%m-%d')");

			ResultSet rs = ps.executeQuery();

			String linha[] = new String[5];
			modeloParaTabela.setRowCount(0);
			while (rs.next()) {

				linha[0] = rs.getString("cdgo_compromisso");

				String formataData = rs.getString("data");
				linha[1] = formataData.replace("-", "/");

				linha[2] = rs.getString("hora");
				linha[3] = rs.getString("titulo");
				linha[4] = rs.getString("descricao");

				modeloParaTabela.addRow(linha);
			}

			rs.close();
			ps.close();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao pesquisar:" + ex);
		}
	}

	private void verificarActionPerformed(java.awt.event.ActionEvent evt) {
		txtDtCons.setText(((JCalendar) calendario).getText().replace("/", "-"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btfechar) {
			conexao.FecharConexao();
			dispose();

		} else if (e.getSource() == btpesquisar) {
			verificarActionPerformed(e);
			try {
				PreparedStatement ps = retornaConexao.prepareStatement(

				"SELECT cdgo_compromisso, data, hora, titulo, descricao FROM compromisso WHERE data = ?");

				// "SELECT c.codigo, a.nome, c.diagnostico,
				// date_format(c.dt_cons,'%d/%m/%Y') data, c.cli_cdgo,
				// c.nome_cliente, c.hora "
				// + "FROM consulta c, animal a WHERE c.ani_cdgo = a.codigo "
				// + "AND dt_cons = date_format(IFNULL(?, dt_cons),'%Y-%m-%d') "
				// + "AND EXISTS (SELECT NULL FROM cliente cl WHERE cl.codigo =
				// c.cli_cdgo)");
				if (txtDtCons.getText().equals("")) {
					ps.setString(1, null);
					ResultSet rs = ps.executeQuery();
					String linha[] = new String[5];
					modeloParaTabela.setRowCount(0);
					while (rs.next()) {

						linha[0] = rs.getString("cdgo_compromisso");
						String formataData = rs.getString("data");
						linha[1] = formataData.replace("-", "/");
						linha[3] = rs.getString("titulo");
						linha[4] = rs.getString("descricao");

						modeloParaTabela.addRow(linha);
					}

					rs.close();
					ps.close();
				} else {
					java.util.Date DtCons = formatoData.parse(txtDtCons.getText());
					ps.setDate(1, new java.sql.Date(DtCons.getTime()));
					ResultSet rs = ps.executeQuery();
					String linha[] = new String[5];
					modeloParaTabela.setRowCount(0);
					while (rs.next()) {

						linha[0] = rs.getString("cdgo_compromisso");
						String formataData = rs.getString("data");
						linha[1] = formataData.replace("-", "/");
						linha[2] = rs.getString("hora");
						linha[3] = rs.getString("titulo");
						linha[4] = rs.getString("descricao");

						modeloParaTabela.addRow(linha);
					}

					rs.close();
					ps.close();
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao pesquisar:" + ex);
			}
		}

		else if (e.getSource() == btnovo) {
			TelaAdicionaAgenda addAgenda = new TelaAdicionaAgenda();
			addAgenda.setVisible(true);
		}

		else if (e.getSource() == btalterar) {
			if (tabela.getSelectedRowCount() > 1) {
				JOptionPane.showMessageDialog(this, "Selecione apenas um registro");
			} else {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(this, "Selecione um registro");
				} else {
					String cod = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
					int id = Integer.parseInt(cod);
					TelaAdicionaAgenda ag = new TelaAdicionaAgenda(id);
					ag.setVisible(true);
				}
			}
		}

		else if (e.getSource() == btexcluir) {

			if (tabela.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "Selecione a linha a excluir!");
			} else {
				if (tabela.getSelectedRowCount() > 1) {
					JOptionPane.showMessageDialog(this, "Selecione apenas 1 linha!");

				} else {
					int resposta = JOptionPane.showConfirmDialog(this, "Deseja excluir?");

					if (resposta == 0) {
						String codigo = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();

						try {
							PreparedStatement ps = retornaConexao
									.prepareStatement("DELETE FROM compromisso WHERE codigo=?");
							ps.setString(1, codigo);
							ps.executeUpdate();
							modeloParaTabela.removeRow(tabela.getSelectedRow());

							JOptionPane.showMessageDialog(this, "Compromisso Cancelado!");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(this, "Erro:" + ex);
						}
					}
				}
			}
		}
	}
}
