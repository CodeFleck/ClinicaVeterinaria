package financeiro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import conexao.Conexao;

public class FluxoCaixa extends JFrame implements ActionListener {

	private ImageIcon imgplus = new ImageIcon("./imagens/plus.png");
	private ImageIcon imgminus = new ImageIcon("./imagens/minus.png");
	private ImageIcon imgexcluir = new ImageIcon("./imagens/delete.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");
	private ImageIcon imgrefresh = new ImageIcon("./imagens/refresh.png");

	private JButton btreceita = new JButton("Receita", imgplus);
	private JButton btdespesa = new JButton("Despesa", imgminus);
	private JButton btexcluirReceita = new JButton("Excluir", imgexcluir);
	private JButton btexcluirDespesa = new JButton("Excluir", imgexcluir);
	private JButton btfechar = new JButton("Fechar", imgfechar);
	private JButton btrefresh = new JButton("Atualizar", imgrefresh);
	private JButton btproxMes = new JButton(">>");
	private JButton btmesAnte = new JButton("<<");

	private JLabel lbltotalReceita = new JLabel("Total receitas:    R$");
	private JLabel lbltotalDespesas = new JLabel("Total despesas:  R$");
	private JLabel lbltotalGeral = new JLabel("Balanço geral:   R$");
	private JLabel lblresultReceita = new JLabel();
	private JLabel lblresultDespesa = new JLabel();
	private JLabel lblresultadoTotalGeral = new JLabel();
	private JLabel lblmesAtual = new JLabel();

	private String resultReceitaS, resultDespesaS, resultadoTotalGeralF;
	private int cont = 0;

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	// receitas
	private String[] titulosReceita = { "Cód", "Descrição", "Cliente", "Valor", "Data" };
	private DefaultTableModel modeloParaTabelaReceita = new DefaultTableModel(titulosReceita, 0);
	private JTable tabelaReceita = new JTable(modeloParaTabelaReceita) {

		@Override
		public boolean isCellEditable(int row, int col) {
			return false;
		}
	};
	private JScrollPane painelReceita = new JScrollPane(tabelaReceita);

	// despesas
	private String[] titulosDespesa = { "Cód", "Descrição", "Fornecedor", "Valor", "Data" };
	private DefaultTableModel modeloParaTabelaDespesa = new DefaultTableModel(titulosDespesa, 0);
	private JTable tabelaDespesa = new JTable(modeloParaTabelaDespesa) {

		@Override
		public boolean isCellEditable(int row, int col) {
			return false;
		}
	};
	private JScrollPane painelDespesa = new JScrollPane(tabelaDespesa);

	// construtor
	public FluxoCaixa() {

		tabelaReceita.getColumnModel().getColumn(0).setPreferredWidth(5);
		tabelaDespesa.getColumnModel().getColumn(0).setPreferredWidth(5);
		tabelaReceita.getColumnModel().getColumn(3).setPreferredWidth(15);
		tabelaDespesa.getColumnModel().getColumn(3).setPreferredWidth(15);
		tabelaReceita.getColumnModel().getColumn(4).setPreferredWidth(40);
		tabelaDespesa.getColumnModel().getColumn(4).setPreferredWidth(40);

		setSize(950, 595);
		setTitle("Fluxo de Caixa");
		setLayout(null);
		setLocationRelativeTo(null);

		lblmesAtual.setBounds(435, 455, 160, 22);

		btreceita.setBounds(10, 10, 100, 22);
		btreceita.setToolTipText("Adicionar receita");
		btdespesa.setBounds(475, 10, 120, 22);
		btdespesa.setToolTipText("Adicionar despesa");
		btexcluirReceita.setBounds(115, 10, 100, 22);
		btexcluirDespesa.setBounds(600, 10, 100, 22);
		btfechar.setBounds(830, 515, 100, 22);
		btrefresh.setBounds(700, 515, 120, 22);
		btproxMes.setBounds(515, 455, 30, 22);
		btmesAnte.setBounds(385, 455, 30, 22);

		add(painelReceita);
		add(painelDespesa);
		add(btreceita);
		add(btdespesa);
		add(btexcluirReceita);
		add(btexcluirDespesa);

		add(lblmesAtual);
		add(lbltotalDespesas);
		add(lbltotalReceita);
		add(lbltotalGeral);
		add(lblresultReceita);
		add(lblresultDespesa);
		add(lblresultadoTotalGeral);

		add(btrefresh);
		add(btfechar);
		add(btproxMes);
		add(btmesAnte);

		painelReceita.setBounds(10, 40, 455, 400);
		painelReceita.setViewportView(tabelaReceita);
		painelDespesa.setBounds(475, 40, 455, 400);
		painelDespesa.setViewportView(tabelaDespesa);

		tabelaReceita.getTableHeader().setReorderingAllowed(true);
		tabelaReceita.getTableHeader().setResizingAllowed(true);

		lbltotalReceita.setBounds(10, 455, 130, 22);
		lbltotalDespesas.setBounds(10, 475, 130, 22);
		lbltotalGeral.setBounds(10, 515, 130, 22);
		lblresultReceita.setBounds(140, 455, 140, 22);
		lblresultDespesa.setBounds(140, 475, 140, 22);
		lblresultadoTotalGeral.setBounds(140, 515, 140, 22);

		// buscando receita
		try {
			PreparedStatement ps = retornaConexao.prepareStatement(
					"SELECT cr.codigoReceita,cr.descricao,cr.cli_cdgo,c.nome,REPLACE(cr.valor,'.',',') valor, date_format(cr.dt_recebimento, '%d/%m/%Y') dt_recebimento FROM contas_receber cr,cliente c "
							+ "WHERE cr.dt_recebimento BETWEEN adddate(LAST_DAY(subdate(curdate(), INTERVAL 1 MONTH)), 1) "
							+ "AND LAST_DAY(SYSDATE()) AND c.codigo = cr.cli_cdgo AND cr.dt_recebimento IS NOT NULL");

			ResultSet rs = ps.executeQuery();

			String linha[] = new String[5];
			modeloParaTabelaReceita.setRowCount(0);
			while (rs.next()) {
				linha[0] = rs.getString("cr.codigoReceita");
				linha[1] = rs.getString("cr.descricao");
				linha[2] = rs.getString("c.nome");
				String formataVirgula = rs.getString("valor");
				linha[3] = rs.getString("valor");
				linha[4] = rs.getString("dt_recebimento");

				modeloParaTabelaReceita.addRow(linha);
			}

			rs.close();
			ps.close();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao pesquisar receita:" + ex);
			ex.printStackTrace();
		}

		// buscando despesa
		try {
			PreparedStatement ps = retornaConexao.prepareStatement(
					"SELECT cr.codigoDespesa,cr.descricao,cr.for_cdgo,c.nome_fantasia,REPLACE(cr.valor,'.',',') valor, date_format(cr.dt_pago, '%d/%m/%Y') dt_pago FROM contas_pagar cr,fornecedor c "
							+ "WHERE cr.dt_pago BETWEEN adddate(LAST_DAY(subdate(curdate(), INTERVAL 1 MONTH)), 1) "
							+ "AND LAST_DAY(SYSDATE()) AND c.codigo = cr.for_cdgo AND cr.dt_pago IS NOT NULL");

			ResultSet rs = ps.executeQuery();

			String linha[] = new String[5];
			modeloParaTabelaDespesa.setRowCount(0);
			while (rs.next()) {

				linha[0] = rs.getString("codigoDespesa");
				linha[1] = rs.getString("descricao");
				linha[2] = rs.getString("c.nome_fantasia");
				// String formataVirgula = rs.getString("valor");
				linha[3] = rs.getString("valor");
				linha[4] = rs.getString("dt_pago");

				modeloParaTabelaDespesa.addRow(linha);
			}

			rs.close();
			ps.close();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao pesquisar despesas:" + ex);
		}

		// somando as receitas
		try {
			PreparedStatement ps = retornaConexao.prepareStatement(
					"SELECT REPLACE(ROUND(SUM(valor),2),'.',',') soma FROM contas_receber cr WHERE cr.dt_recebimento BETWEEN "
							+ "adddate(LAST_DAY(subdate(curdate(), INTERVAL 1 MONTH)), 1) AND LAST_DAY(SYSDATE());");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				resultReceitaS = rs.getString("soma");
				lblresultReceita.setText(resultReceitaS);

			}

			rs.close();
			ps.close();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao pesquisar soma receitas:" + ex);
		}

		// somando as despesas
		try {
			PreparedStatement ps = retornaConexao.prepareStatement(
					"SELECT REPLACE(ROUND(SUM(valor),2),'.',',') soma FROM contas_pagar cr WHERE cr.dt_pago BETWEEN "
							+ "adddate(LAST_DAY(subdate(curdate(), INTERVAL 1 MONTH)), 1) AND LAST_DAY(SYSDATE());");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				resultDespesaS = rs.getString("soma");
				lblresultDespesa.setText(resultDespesaS);
			}

			rs.close();
			ps.close();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao pesquisar soma despesas:" + ex);
		}

		// somando o balanço geral
		try {
			PreparedStatement ps = retornaConexao
					.prepareStatement("SELECT REPLACE((soma_receita - soma_despesa),'.',',') total "
							+ "FROM (SELECT ROUND(SUM(valor),2) soma_despesa FROM contas_pagar cr WHERE cr.dt_pago BETWEEN adddate(LAST_DAY(subdate(curdate(), INTERVAL 1 MONTH)), 1) AND LAST_DAY(SYSDATE())) despesa"
							+ ",(SELECT ROUND(SUM(valor),2) soma_receita FROM contas_receber cr WHERE cr.dt_recebimento BETWEEN adddate(LAST_DAY(subdate(curdate(), INTERVAL 1 MONTH)), 1) AND LAST_DAY(SYSDATE())) receita");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				resultadoTotalGeralF = rs.getString("total");
				lblresultadoTotalGeral.setText(resultadoTotalGeralF);
			}

			rs.close();
			ps.close();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao pesquisar total:" + ex);
		}
		// resultadoTotalGeralF = (Float.parseFloat(resultReceitaS) -
		// Float.parseFloat(resultDespesaS));
		// String resultadoTotalGeralS = String.valueOf(resultadoTotalGeralF);
		// lblresultadoTotalGeral.setText(resultadoTotalGeralS.replace(".",
		// ","));

		btdespesa.addActionListener(this);
		btreceita.addActionListener(this);
		btexcluirDespesa.addActionListener(this);
		btexcluirReceita.addActionListener(this);
		btfechar.addActionListener(this);
		btrefresh.addActionListener(this);
		btproxMes.addActionListener(this);
		btmesAnte.addActionListener(this);

		// mostrando o mês atual
		java.util.Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Integer month = cal.get(Calendar.MONTH);
		lblmesAtual.setText(NomeDoMes(month, 0));

	}

	public static String NomeDoMes(int i, int tipo) {
		String mes[] = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro",
				"Outubro", "Novembro", "Dezembro" };
		if (tipo == 0)
			return (mes[i]);
		else
			return (mes[i].substring(0, 3));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btfechar) {
			conexao.FecharConexao();
			dispose();
		}
		if (e.getSource() == btrefresh) {
			FluxoCaixa1 f = new FluxoCaixa1();
			f.setVisible(true);
			dispose();
		}

		if (e.getSource() == btreceita) {

			TelaAdicionaReceita a = new TelaAdicionaReceita();
			a.setVisible(true);

		}

		if (e.getSource() == btdespesa) {
			TelaAdicionaDespesa ad = new TelaAdicionaDespesa();
			ad.setVisible(true);
		}
		if (e.getSource() == btproxMes) {
			cont++;
		}
		if (e.getSource() == btmesAnte) {
			cont--;
		}

		if (e.getSource() == btexcluirReceita) {

			if (tabelaReceita.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "Selecione a linha a excluir!");
			} else {
				if (tabelaReceita.getSelectedRowCount() > 1) {
					JOptionPane.showMessageDialog(this, "Selecione apenas 1 linha!");

				} else {
					int resposta = JOptionPane.showConfirmDialog(this, "Deseja excluir?");

					if (resposta == 0) {

						String codigo = tabelaReceita.getValueAt(tabelaReceita.getSelectedRow(), 0).toString();

						try {
							PreparedStatement ps = retornaConexao
									.prepareStatement("Delete from contas_receber where codigoReceita=?");

							ps.setString(1, codigo);

							ps.executeUpdate();

							modeloParaTabelaReceita.removeRow(tabelaReceita.getSelectedRow());

							JOptionPane.showMessageDialog(this, "Lançamento excluído com sucesso!" + "");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(this, "Erro:" + ex);
						}
					}
				}
			}
		}

		if (e.getSource() == btexcluirDespesa) {

			if (tabelaDespesa.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "Selecione a linha a excluir!");
			} else {
				if (tabelaDespesa.getSelectedRowCount() > 1) {
					JOptionPane.showMessageDialog(this, "Selecione apenas 1 linha !");

				} else {
					int resposta = JOptionPane.showConfirmDialog(this, "Deseja excluir ?");

					if (resposta == 0) {

						String codigo = tabelaDespesa.getValueAt(tabelaDespesa.getSelectedRow(), 0).toString();

						try {
							PreparedStatement ps = retornaConexao
									.prepareStatement("Delete from contas_pagar where codigoDespesa=?");

							ps.setString(1, codigo);

							ps.executeUpdate();

							modeloParaTabelaDespesa.removeRow(tabelaDespesa.getSelectedRow());

							JOptionPane.showMessageDialog(this, "Lançamento excluído com sucesso!" + "");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(this, "Erro:" + ex);
						}
					}
				}
			}
		}
	}
}
