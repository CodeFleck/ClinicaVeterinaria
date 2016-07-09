package financeiro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import conexao.Conexao;
import ferramentas.GhostText;

public class TelaAdicionaDespesa extends JFrame implements ActionListener {

	private ImageIcon imgplus = new ImageIcon("./imagens/plus.png");
	private ImageIcon imgminus = new ImageIcon("./imagens/minus.png");
	private ImageIcon imgsalvar = new ImageIcon("./imagens/salvar.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");

	private JButton btsalvar = new JButton("SALVAR", imgsalvar);
	private JButton btfechar = new JButton("FECHAR", imgfechar);

	private JLabel lblpesquisarFornecedor = new JLabel("Fornecedor: ");
	private JLabel lblFornecedorTel = new JLabel("Telefone:");
	private JLabel lblvalor = new JLabel("Valor:  R$");
	private JLabel lbldescricao = new JLabel("Descrição:");
	private JLabel lbldata = new JLabel("Pago em:");

	private JTextField txtFornecedorTel = new JTextField();
	private JTextField txtvalor = new JTextField();
	private JTextField txtdescricao = new JTextField();
	private JTextField txtdata = new JTextField();

	private JLabel lbldataVenc = new JLabel("Data Vencimento:");
	private JTextField txtdataVenc = new JTextField();
	private MaskFormatter formatter = null;

	private JLabel lblcategoria = new JLabel("Categoria");
	String[] categoriasDespesas = new String[] { "Luz", "Água", "Transporte", "Fornecedores", "Telefone",
			"Folha Pagamento", "Outro" };
	String categoriaSelecionada;

	private JComboBox<String> cbCategoriasDespesas = new JComboBox<>(categoriasDespesas);
	private JComboBox<String> cbFornecedores = new JComboBox<>();

	int codTemp;
	boolean selected = true;

	DateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	// CONSTRUTOR
	public TelaAdicionaDespesa() {
		setSize(390, 400);
		setResizable(false);
		setTitle("Despesas");
		setLayout(null);
		setLocationRelativeTo(null);
		try {
			formatter = new MaskFormatter("##/##/####");
		} catch (ParseException e) {
		}
		txtdataVenc = new JFormattedTextField(formatter);
		Calendar cal = Calendar.getInstance();
		txtdata.setText(formatoData.format(cal.getTime()));
		String txtdataS = txtdata.getText();
		txtdata.setText(txtdataS.replace("-", "/"));

		cbCategoriasDespesas.setBounds(120, 235, 210, 22);
		cbFornecedores.setBounds(120, 25, 210, 22);

		lblpesquisarFornecedor.setBounds(10, 25, 210, 22);
		lblFornecedorTel.setBounds(10, 60, 120, 22);
		lbldataVenc.setBounds(10, 95, 140, 22);
		lblvalor.setBounds(10, 130, 120, 22);
		lbldescricao.setBounds(10, 165, 120, 22);
		lbldata.setBounds(10, 200, 120, 22);
		lblcategoria.setBounds(10, 235, 120, 22);

		txtFornecedorTel.setBounds(120, 60, 210, 22);
		txtdataVenc.setBounds(120, 95, 70, 22);
		new GhostText(txtdataVenc, "00/00/0000");
		txtvalor.setBounds(120, 130, 210, 22);
		new GhostText(txtvalor, "0,00");
		txtdescricao.setBounds(120, 165, 210, 22);
		txtdata.setBounds(120, 200, 70, 22);

		btsalvar.setBounds(75, 320, 100, 22);
		btfechar.setBounds(187, 320, 100, 22);

		add(cbFornecedores);
		add(cbCategoriasDespesas);

		add(lblpesquisarFornecedor);
		add(lblFornecedorTel);
		add(lblvalor);
		add(lbldescricao);
		add(lbldata);
		add(lblcategoria);
		add(lbldataVenc);

		add(txtFornecedorTel);
		add(txtvalor);
		add(txtdescricao);
		add(txtdata);
		add(txtdataVenc);

		add(btsalvar);
		add(btfechar);

		btsalvar.addActionListener(this);
		btfechar.addActionListener(this);

		// populando meu cbFornecedores
		try {
			PreparedStatement ps = retornaConexao.prepareStatement("Select * from fornecedor");
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
					PreparedStatement ps = retornaConexao
							.prepareStatement("Select fone, codigo from fornecedor where nome_fantasia=? ");

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

		if (e.getSource() == btfechar) {
			conexao.FecharConexao();
			dispose();
		}

		if (e.getSource() == btsalvar) {

			String formataValor = txtvalor.getText();
			txtvalor.setText(formataValor.replace(",", "."));

			String formataData = txtdata.getText();
			txtdata.setText(formataData.replace("/", "-"));
			String formataDataVenc = txtdataVenc.getText();

			try {
				categoriaSelecionada = (String) cbCategoriasDespesas.getSelectedItem();

				PreparedStatement ps = retornaConexao.prepareStatement(
						"insert into contas_pagar (descricao,dt_venc,valor,dt_pago,categoria,for_cdgo) values (?,?,?,?,?,?) ");

				ps.setString(1, txtdescricao.getText());

				Date dtvenc = new SimpleDateFormat("dd/MM/yyyy").parse(txtdataVenc.getText());
				String dbDateVenc = new SimpleDateFormat("yyyy-MM-dd").format(dtvenc);
				ps.setString(2, dbDateVenc);
				ps.setString(3, txtvalor.getText());

				Date dtpago = new SimpleDateFormat("dd-MM-yyyy").parse(txtdata.getText());
				String dbDate = new SimpleDateFormat("yyyy-MM-dd").format(dtpago);
				ps.setString(4, dbDate);
				ps.setString(5, categoriaSelecionada);
				ps.setInt(6, codTemp);

				ps.executeUpdate();

				JOptionPane.showMessageDialog(this, "Despesa cadastrada com sucesso!");
				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro:" + ex);
				ex.printStackTrace();
			}
		}

	}

}
