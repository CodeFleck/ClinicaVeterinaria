package telaVacina;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane; //painel de moldura para a tabela
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel; //modelo para tabela

import conexao.Conexao;

public class TelaVacina extends JFrame implements ActionListener {

	private JLabel lblnome = new JLabel("Nome: ");
	private JTextField txtnome = new JTextField();
	private String[] titulos = { "Cod", "Animal", "Descrição", "Data Vacinação", "Próx. Vacinação" };
	private DefaultTableModel modeloParaTabela = new DefaultTableModel(titulos, 0);
	private ImageIcon imgnovo = new ImageIcon("./imagens/new.png");
	private ImageIcon imgalterar = new ImageIcon("./imagens/edit.png");
	private ImageIcon imgexcluir = new ImageIcon("./imagens/delete2.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");
	private ImageIcon imgpesquisar = new ImageIcon("./imagens/find2.png");
	private JTable tabela = new JTable(modeloParaTabela) {

		@Override
		public boolean isCellEditable(int row, int col) {

			return false; // se fosse true pderiamos digitar dentro da tabela
		}
	};

	private JScrollPane painel = new JScrollPane(tabela);
	private JButton btnovo = new JButton("Novo", imgnovo);
	// private JButton btalterar = new JButton("Alterar", imgalterar);
	private JButton btexcluir = new JButton("Excluir", imgexcluir);
	private JButton btfechar = new JButton("Fechar", imgfechar);
	private JButton btpesquisar = new JButton("Pesquisar", imgpesquisar);
	private int codAni;
	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	// construtor
	public TelaVacina(int codigo) {
		codAni = codigo;
		setSize(740, 400);
		setTitle("Cadastro de Vacinas");
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);

		lblnome.setBounds(10, 10, 100, 22);
		txtnome.setBounds(60, 10, 150, 22);
		btpesquisar.setBounds(220, 10, 120, 22);

		painel.setBounds(10, 20, 715, 300);
		painel.setViewportView(tabela);
		// redimensionamento das colunas
		tabela.getColumnModel().getColumn(0).setMaxWidth(35);
		tabela.getColumnModel().getColumn(0).setMinWidth(15);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(35);
		tabela.getColumnModel().getColumn(1).setMinWidth(150);
		tabela.getColumnModel().getColumn(1).setMaxWidth(200);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
		tabela.getColumnModel().getColumn(2).setMinWidth(240);
		tabela.getColumnModel().getColumn(2).setMaxWidth(290);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(240);
		tabela.getColumnModel().getColumn(3).setMinWidth(85);
		tabela.getColumnModel().getColumn(3).setMaxWidth(95);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(85);
		tabela.getColumnModel().getColumn(4).setMinWidth(85);
		tabela.getColumnModel().getColumn(4).setMaxWidth(95);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(85);

		btnovo.setBounds(10, 345, 100, 22);
		btexcluir.setBounds(120, 345, 100, 22);
		btfechar.setBounds(340, 345, 100, 22);

		add(btnovo);
		// add(btalterar);
		add(btexcluir);
		add(btfechar);
		add(painel);

		btnovo.addActionListener(this);
		// btalterar.addActionListener(this);
		btexcluir.addActionListener(this);
		btfechar.addActionListener(this);

		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.getTableHeader().setResizingAllowed(false);

		try {
			PreparedStatement ps = retornaConexao.prepareStatement(
					"SELECT v.codigo, a.nome, v.obs, date_format(v.dt_vac,'%d/%m/%Y') dt_vac, date_format(v.dt_venc_vac,'%d/%m/%Y') dt_venc_vac "
							+ "FROM vacina v, animal a WHERE v.ani_cdgo = a.codigo AND a.codigo = ? ");
			ps.setInt(1, codAni);
			ResultSet rs = ps.executeQuery();

			String linha[] = new String[5];
			modeloParaTabela.setRowCount(0);
			while (rs.next()) {
				linha[0] = rs.getString("v.codigo");
				linha[1] = rs.getString("a.nome");
				linha[2] = rs.getString("v.obs");
				linha[3] = rs.getString("dt_vac");
				linha[4] = rs.getString("dt_venc_vac");

				modeloParaTabela.addRow(linha);
			}

			rs.close();
			ps.close();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao pesquisar:" + ex);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btfechar) {
			//conexao.FecharConexao();
			dispose();
		}

		else if (e.getSource() == btnovo) {
			TelaAdicionaVacina vac = new TelaAdicionaVacina(codAni);
			vac.setVisible(true);
			dispose();
		}

		else if (e.getSource() == btexcluir) {

			if (tabela.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "Selecione a linha a excluir!");
			} else {
				if (tabela.getSelectedRowCount() > 1) {// rowCount da qtd de
														// linhas selecionada
					JOptionPane.showMessageDialog(this, "Selecione apenas 1 linha !");

				} else {
					int resposta = JOptionPane.showConfirmDialog(this, "Deseja excluir ?");

					if (resposta == 0) { // sera ZERO se escolher SIM

						String codigo = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();

						try {
							PreparedStatement ps = retornaConexao.prepareStatement("DELETE FROM vacina WHERE codigo=?");

							ps.setString(1, codigo);

							ps.executeUpdate();// executa delecao no banco

							// remove da tabela a linha selecionada
							modeloParaTabela.removeRow(tabela.getSelectedRow());

							JOptionPane.showMessageDialog(this, "Vacina excluida com sucesso!" + "");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(this, "Erro:" + ex);
						}
					}
				}
			}
		}
	}
}
