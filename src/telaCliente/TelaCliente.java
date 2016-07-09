package telaCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;//caixa de mensagem
import javax.swing.JScrollPane; //painel de moldura para a tabela
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;//modelo para tabela

import conexao.Conexao;

public class TelaCliente extends JFrame implements ActionListener {

	private JLabel lblnome = new JLabel("Nome:");
	private JTextField txtnome = new JTextField();

	private String[] titulos = { "Cod", "Nome", "Email", "Fone", "Endere�o" };
	private DefaultTableModel modeloparatabela = new DefaultTableModel(titulos, 0);
	private JTable tabela = new JTable(modeloparatabela) {

		@Override
		public boolean isCellEditable(int row, int col) {

			return false;
		}
	};

	private JScrollPane painelScroll = new JScrollPane(tabela);
	private ImageIcon imgnovo = new ImageIcon("./imagens/new.png");
	private ImageIcon imgalterar = new ImageIcon("./imagens/edit.png");
	private ImageIcon imgexcluir = new ImageIcon("./imagens/delete.png");
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
	public TelaCliente() {
		setSize(800, 400);
		setTitle("Clientes");
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);

		lblnome.setBounds(10, 10, 100, 22);
		txtnome.setBounds(60, 10, 150, 22);
		btpesquisar.setBounds(220, 10, 120, 22);

		painelScroll.setBounds(10, 40, 770, 300);
		painelScroll.setViewportView(tabela);

		// redimensionamento das colunas
		tabela.getColumnModel().getColumn(0).setMaxWidth(35);
		tabela.getColumnModel().getColumn(0).setMinWidth(15);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(35);
		tabela.getColumnModel().getColumn(1).setMinWidth(100);
		tabela.getColumnModel().getColumn(1).setMaxWidth(130);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(2).setMinWidth(130);
		tabela.getColumnModel().getColumn(2).setMaxWidth(175);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(150);
		tabela.getColumnModel().getColumn(3).setMinWidth(50);
		tabela.getColumnModel().getColumn(3).setMaxWidth(150);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(2).setMinWidth(160);
		tabela.getColumnModel().getColumn(2).setMaxWidth(300);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(225);

		btnovo.setBounds(10, 345, 100, 22);
		btalterar.setBounds(120, 345, 100, 22);
		btexcluir.setBounds(230, 345, 100, 22);
		btfechar.setBounds(600, 345, 100, 22);

		add(btfechar);
		add(btnovo);
		add(btexcluir);
		add(btalterar);
		add(lblnome);
		add(txtnome);
		add(btpesquisar);
		add(painelScroll);

		btpesquisar.addActionListener(this);
		btnovo.addActionListener(this);
		btfechar.addActionListener(this);
		btexcluir.addActionListener(this);
		btalterar.addActionListener(this);

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

				PreparedStatement ps = retornaConexao
						.prepareStatement("Select codigo,nome,fone,email,endereco from cliente where nome like ?");

				ps.setString(1, "%" + txtnome.getText() + "%");
				ResultSet rs = ps.executeQuery();

				String linha[] = new String[5];
				modeloparatabela.setRowCount(0);
				while (rs.next()) {

					linha[0] = rs.getString("codigo");
					linha[1] = rs.getString("nome");
					linha[2] = rs.getString("email");
					linha[3] = rs.getString("fone");
					linha[4] = rs.getString("endereco");

					modeloparatabela.addRow(linha);
				}

				rs.close();
				ps.close();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao pesquisar:" + ex);
			}
		}

		if (e.getSource() == btnovo) {
			TelaAdicionaCliente a = new TelaAdicionaCliente();
			a.setVisible(true);
		}

		if (e.getSource() == btalterar) {

			// getSelectedRowCount --> da numero de linhas selecionadas
			if (tabela.getSelectedRowCount() > 1) {
				JOptionPane.showMessageDialog(this, "Selecione apenas um registro");
			} else {
				if (tabela.getSelectedRow() == -1) {// getSelectedRow da numero
													// da linha selecionada
					// -1 caso nã otenha nenhuma linha selcionada
					JOptionPane.showMessageDialog(this, "Selecione um registro");
				} else {
					String cod = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
					int id = Integer.parseInt(cod);// converte o cod em int

					TelaAdicionaCliente a = new TelaAdicionaCliente(id);
					
					a.setVisible(true);
				}
			}
		}

		if (e.getSource() == btexcluir) {
			// 1º Verificar se tem uma linha selecionada
			// 2ºVerificar se tem so uma linha selecionada (se tiver mais de uma nao deixar)
			// 3º Perguntar se quer excluir
			// 4º Se sim conectar ao banco e excluir
			// 5º Retirar a linha da tabela na tela

			if (tabela.getSelectedRow() == -1) {// sera -1 se nã otiver linha selecionada
				JOptionPane.showMessageDialog(this, "Selecione a linha a excluir!");
			} else {
				if (tabela.getSelectedRowCount() > 1) {// rowCount da qtd de linhas selecionada
					JOptionPane.showMessageDialog(this, "Selecione apenas 1 linha!");

				} else {
					int resposta = JOptionPane.showConfirmDialog(this, "Deseja excluir?");

					if (resposta == 0) { 

						String codigo = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
					

						try {

							PreparedStatement ps = retornaConexao
									.prepareStatement("Delete from cliente where codigo=?");

							ps.setString(1, codigo);

							ps.executeUpdate();// executa delecao no banco

							// remove da tabela a linha selecionada
							modeloparatabela.removeRow(tabela.getSelectedRow());

							JOptionPane.showMessageDialog(this, "Cliente excluido com sucesso!");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(this, "Erro:" + ex);
						}
					}
				}
			}
		}
	}
}
