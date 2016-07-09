package telaAnimal;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import conexao.Conexao;

public class TelaAnimal extends JFrame implements ActionListener {

	private JLabel lblnome = new JLabel("Nome: ");
	private JTextField txtnome = new JTextField();

	private String[] titulos = { "Cód", "Espécie", "Raça", "Nome", "Peso", "Idade", "Sexo", "Data Nasc.", "Pelagem",
			"Castrado", "OBS","Cliente" };
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
	public TelaAnimal() {
		setSize(1015, 415);
		setTitle("Animais");
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(true);

		lblnome.setBounds(10, 10, 100, 22);
		txtnome.setBounds(60, 10, 150, 22);
		btpesquisar.setBounds(220, 10, 120, 22);

		painelScroll.setBounds(10, 40, 980, 300);
		painelScroll.setViewportView(tabela); 

		btnovo.setBounds(10, 345, 100, 22);
		btalterar.setBounds(120, 345, 100, 22);
		btexcluir.setBounds(230, 345, 100, 22);
		btfechar.setBounds(815, 345, 100, 22);

		add(lblnome);
		add(txtnome);
		add(btpesquisar);
		add(btnovo);
		add(btalterar);
		add(btexcluir);
		add(btfechar);
		add(painelScroll);
		
		tabela.getColumnModel().getColumn(0).setPreferredWidth(5);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(5);
		tabela.getColumnModel().getColumn(5).setPreferredWidth(5);
		tabela.getColumnModel().getColumn(6).setPreferredWidth(5);
		tabela.getColumnModel().getColumn(9).setPreferredWidth(5);

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
						"Select a.codigo,a.especie,a.raca,a.nome,a.peso,a.idade,a.sexo, date_format(a.dt_nasc, '%d/%m/%Y') dt_nasc, a.pelagem,a.castrado,a.obs,a.cli_cdgo,c.nome from animal a, cliente c where a.cli_cdgo = c.codigo and a.nome like ?");
				
				ps.setString(1, "%" + txtnome.getText() + "%");
				ResultSet rs = ps.executeQuery();
				
				String linha[] = new String[13];
												
				modeloparatabela.setRowCount(0);
				while (rs.next()) {
					
					linha[0] = rs.getString("a.codigo");
					linha[1] = rs.getString("a.especie");
					linha[2] = rs.getString("a.raca");
					linha[3] = rs.getString("a.nome");
					linha[4] = rs.getString("a.peso");
					linha[5] = rs.getString("a.idade");
					linha[6] = rs.getString("a.sexo");
					linha[7] = rs.getString("dt_nasc");
					linha[8] = rs.getString("a.pelagem");
					linha[9] = rs.getString("a.castrado");
					linha[10] = rs.getString("a.obs");
					linha[11] = rs.getString("c.nome");

					modeloparatabela.addRow(linha);

				}

				rs.close();
				ps.close();
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao pesquisar:" + ex);
			}
		}

		if (e.getSource() == btnovo) {
			TelaAdicionaAnimal t1 = new TelaAdicionaAnimal();
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
					TelaAdicionaAnimal a = new TelaAdicionaAnimal(id);
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
							PreparedStatement ps = retornaConexao.prepareStatement("Delete from animal where codigo=?");

							ps.setString(1, codigo);

							ps.executeUpdate();

							
							modeloparatabela.removeRow(tabela.getSelectedRow());

							JOptionPane.showMessageDialog(this, "Animal excluído com sucesso!" + "");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(this, "Erro:" + ex);
						}
					}
				}
			}
		}
	}
}