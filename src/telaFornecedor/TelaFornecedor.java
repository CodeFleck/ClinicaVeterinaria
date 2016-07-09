package telaFornecedor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import conexao.Conexao;

public class TelaFornecedor extends JFrame implements ActionListener {

	private JLabel lblnome = new JLabel("Nome: ");
	private JTextField txtnome = new JTextField();

	private String[] titulos = { "Cod", "Nome", "CNPJ", "Fone", "Email", "Endereço" };
	private DefaultTableModel modeloparatabela = new DefaultTableModel(titulos, 0);
	private JTable tabela = new JTable(modeloparatabela) {

		@Override
		public boolean isCellEditable(int row, int col) {
			// if(col==1)return true;
			return false; // se fosse true pderiamos digitar dentro da tabela
		}
	};

	private JScrollPane painelScroll = new JScrollPane(tabela);
	private ImageIcon imgnovo = new ImageIcon("./imagens/new.png");
	private ImageIcon imgalterar = new ImageIcon("./imagens/edit.png");
	private ImageIcon imgexcluir = new ImageIcon("./imagens/delete2.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");
	private ImageIcon imgpesquisar = new ImageIcon("./imagens/find2.png");

	private JButton btnovo = new JButton("Novo", imgnovo);
	private JButton btalterar = new JButton("Alterar", imgalterar);
	private JButton btexcluir = new JButton("Excluir", imgexcluir);
	private JButton btfechar = new JButton("Fechar", imgfechar);
	private JButton btpesquisar = new JButton("Pesquisar", imgpesquisar);

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	// construtor
	public TelaFornecedor() {
		setSize(900, 400);
		setTitle("Fornecedores");
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);

		lblnome.setBounds(10, 10, 100, 22);
		txtnome.setBounds(60, 10, 150, 22);
		btpesquisar.setBounds(220, 10, 120, 22);

		painelScroll.setBounds(10, 40, 870, 300);
		painelScroll.setViewportView(tabela);

		// redimensionamento das colunas
		tabela.getColumnModel().getColumn(0).setMaxWidth(35);
		tabela.getColumnModel().getColumn(0).setMinWidth(15);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(35);
		tabela.getColumnModel().getColumn(1).setMinWidth(130);
		tabela.getColumnModel().getColumn(1).setMaxWidth(160);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
		tabela.getColumnModel().getColumn(2).setMinWidth(120);
		tabela.getColumnModel().getColumn(2).setMaxWidth(150);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(140);
		tabela.getColumnModel().getColumn(3).setMinWidth(50);
		tabela.getColumnModel().getColumn(3).setMaxWidth(150);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(4).setMinWidth(130);
		tabela.getColumnModel().getColumn(4).setMaxWidth(160);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(160);
		tabela.getColumnModel().getColumn(5).setMinWidth(170);
		tabela.getColumnModel().getColumn(5).setMaxWidth(220);
		tabela.getColumnModel().getColumn(5).setPreferredWidth(195);

		btnovo.setBounds(10, 345, 100, 22);
		btalterar.setBounds(120, 345, 100, 22);
		btexcluir.setBounds(230, 345, 100, 22);
		btfechar.setBounds(680, 345, 100, 22);

		add(lblnome);
		add(txtnome);
		add(btpesquisar);
		add(btnovo);
		add(btalterar);
		add(btexcluir);
		add(btfechar);
		add(painelScroll);

		btpesquisar.addActionListener(this);
		btnovo.addActionListener(this);
		btalterar.addActionListener(this);
		btexcluir.addActionListener(this);
		btfechar.addActionListener(this);

		tabela.getTableHeader().setReorderingAllowed(true);
		tabela.getTableHeader().setResizingAllowed(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btfechar) {
			conexao.FecharConexao();
			dispose();
		}
		if (e.getSource() == btpesquisar) {
			try {
				PreparedStatement ps = retornaConexao.prepareStatement(
						"Select codigo,nome_fantasia,cnpj,fone,email,endereco from fornecedor where nome_fantasia like ?");

				ps.setString(1, "%" + txtnome.getText() + "%");

				ResultSet rs = ps.executeQuery();

				String linha[] = new String[6];
				modeloparatabela.setRowCount(0);
				while (rs.next()) {
					linha[0] = rs.getString("codigo");
					linha[1] = rs.getString("nome_fantasia");
					linha[2] = rs.getString("cnpj");
					linha[3] = rs.getString("fone");
					linha[4] = rs.getString("email");
					linha[5] = rs.getString("endereco");

					modeloparatabela.addRow(linha);
				}

				rs.close();
				ps.close();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao pesquisar:" + ex);
			}
		}

		if (e.getSource() == btnovo) {
			TelaAdicionaFornecedor t1 = new TelaAdicionaFornecedor();
			t1.setVisible(true);

		}

		if (e.getSource() == btalterar) {

			if (tabela.getSelectedRowCount() > 1) {
				JOptionPane.showMessageDialog(this, "Selecione apenas um registro");
			} else {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(this, "Selecione um registro");
				} else {
					String cod = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
					int id = Integer.parseInt(cod);

					TelaAdicionaFornecedor a = new TelaAdicionaFornecedor(id);

					a.setVisible(true);
				}
			}
		}

		if (e.getSource() == btexcluir) {

			if (tabela.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "Selecione a linha a excluir!");
			} else {
				if (tabela.getSelectedRowCount() > 1) {
					JOptionPane.showMessageDialog(this, "Selecione apenas 1 linha !");

				} else {
					int resposta = JOptionPane.showConfirmDialog(this, "Deseja excluir ?");

					if (resposta == 0) {

						String codigo = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();

						try {
							PreparedStatement ps = retornaConexao
									.prepareStatement("Delete from fornecedor where codigo=?");

							ps.setString(1, codigo);

							ps.executeUpdate();

							modeloparatabela.removeRow(tabela.getSelectedRow());

							JOptionPane.showMessageDialog(this, "Fornecedor excluido com sucesso!" + "");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(this, "Erro:" + ex);
						}
					}
				}
			}
		}
	}
}