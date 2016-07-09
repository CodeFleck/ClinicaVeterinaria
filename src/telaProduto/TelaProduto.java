package telaProduto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.*;
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

public class TelaProduto extends JFrame implements ActionListener {

	private JLabel lblnome = new JLabel("Nome: ");
	private JTextField txtnome = new JTextField();
	private String[] titulos = { "Cód", "Descrição", "Marca", "Fornecedor", "Observação" };
	private DefaultTableModel modeloParaTabela = new DefaultTableModel(titulos, 0);
	
	private ImageIcon imgnovo = new ImageIcon("./imagens/new.png");
	private ImageIcon imgalterar = new ImageIcon("./imagens/edit.png");
	private ImageIcon imgexcluir = new ImageIcon("./imagens/delete2.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");
	private ImageIcon imgpesquisar = new ImageIcon("./imagens/find2.png");
	
	private JTable tabela = new JTable(modeloParaTabela) {

		@Override
		public boolean isCellEditable(int row, int col) {
			return false;
		}

	};
	private JScrollPane painelScroll = new JScrollPane(tabela);

	private JButton btnovo = new JButton("Novo", imgnovo);
	private JButton btalterar = new JButton("Alterar", imgalterar);
	private JButton btexcluir = new JButton("Excluir", imgexcluir);
	private JButton btfechar = new JButton("Fechar", imgfechar);
	private JButton btpesquisar = new JButton("Pesquisar", imgpesquisar);

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	// construtor
	public TelaProduto() {
		setSize(800, 400);
		setTitle("Produtos");
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);

		lblnome.setBounds(10, 10, 100, 22);
		txtnome.setBounds(60, 10, 150, 22);
		btpesquisar.setBounds(220, 10, 120, 22);

		painelScroll.setBounds(10, 40, 770, 300);
		painelScroll.setViewportView(tabela);

		btnovo.setBounds(10, 345, 100, 22);
		btalterar.setBounds(120, 345, 100, 22);
		btexcluir.setBounds(230, 345, 100, 22);
		btfechar.setBounds(580, 345, 100, 22);

		// redimensionamento das colunas
		tabela.getColumnModel().getColumn(0).setMaxWidth(35);
		tabela.getColumnModel().getColumn(0).setMinWidth(15);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(35);
		tabela.getColumnModel().getColumn(1).setMinWidth(160);
		tabela.getColumnModel().getColumn(1).setMaxWidth(190);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
		tabela.getColumnModel().getColumn(2).setMinWidth(150);
		tabela.getColumnModel().getColumn(2).setMaxWidth(180);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(170);
		tabela.getColumnModel().getColumn(3).setMinWidth(80);
		tabela.getColumnModel().getColumn(3).setMaxWidth(180);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(150);
		tabela.getColumnModel().getColumn(4).setMinWidth(160);
		tabela.getColumnModel().getColumn(4).setMaxWidth(190);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(190);

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

		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.getTableHeader().setResizingAllowed(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btfechar) {
			conexao.FecharConexao();
			dispose();
		}

		else if (e.getSource() == btpesquisar) {

			try {
				PreparedStatement ps = retornaConexao.prepareStatement(
						"SELECT codigo, descricao, marca, for_cdgo, obs FROM produto WHERE descricao LIKE ?");

				ps.setString(1, "%" + txtnome.getText() + "%");
				ResultSet rs = ps.executeQuery();

				String linha[] = new String[5];
				modeloParaTabela.setRowCount(0);

				while (rs.next()) {

					linha[0] = rs.getString("codigo");
					linha[1] = rs.getString("descricao");
					linha[2] = rs.getString("marca");
					linha[3] = rs.getString("for_cdgo");
					linha[4] = rs.getString("obs");

					modeloParaTabela.addRow(linha);
				}
				rs.close();
				ps.close();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao pesquisar:" + ex);
			}
		}
		else if (e.getSource() == btnovo) {
			TelaAdicionaProduto addProduto = new TelaAdicionaProduto();
			addProduto.setVisible(true);
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

					TelaAdicionaProduto p = new TelaAdicionaProduto(id);
					p.setVisible(true);
				}
			}
		} else if (e.getSource() == btexcluir) {
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
									.prepareStatement("DELETE FROM produto WHERE codigo=?");
							ps.setString(1, codigo);
							ps.executeUpdate();
							modeloParaTabela.removeRow(tabela.getSelectedRow());

							JOptionPane.showMessageDialog(this, "Produto excluido com sucesso!" + "");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(this, "Erro:" + ex);
						}
					}
				}
			}
		}
	}
}
