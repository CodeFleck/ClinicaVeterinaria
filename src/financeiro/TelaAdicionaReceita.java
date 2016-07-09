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

public class TelaAdicionaReceita extends JFrame implements ActionListener {

	private ImageIcon imgplus = new ImageIcon("./imagens/plus.png");
	private ImageIcon imgminus = new ImageIcon("./imagens/minus.png");
	private ImageIcon imgsalvar = new ImageIcon("./imagens/salvar.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");

	private JButton btsalvar = new JButton("SALVAR", imgsalvar);
	private JButton btfechar = new JButton("FECHAR", imgfechar);

	private JLabel lblpesquisarCliente = new JLabel("Cliente: ");
	private JLabel lblclienteTel = new JLabel("Telefone:");
	private JTextField txtclienteTel = new JTextField();
	private JLabel lblvalor = new JLabel("Valor:  R$");
	private JTextField txtvalor = new JTextField();
	private JLabel lbldescricao = new JLabel("Descrição:");
	private JTextField txtdescricao = new JTextField();
	private JLabel lbldata = new JLabel("Recebido em:");
	private JTextField txtdata = new JTextField();
	private MaskFormatter formatter = null;

	private JLabel lbldataVenc = new JLabel("Data Vencimento:");
	private JTextField txtdataVenc = new JTextField();

	private JLabel lblcategoria = new JLabel("Categoria");
	String[] categoriasReceitas = new String[] { "Consulta", "Cirurgia", "Vacina", "Higienização", "Outro" };
	String categoriaSelecionada;

	private JComboBox<String> cbCategoriasReceitas = new JComboBox<>(categoriasReceitas);
	private JComboBox<String> cbClientes = new JComboBox<>();

	int codTemp;
	boolean selected = true;

	DateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	// CONSTRUTOR
	public TelaAdicionaReceita() {
		setSize(390, 400);
		setResizable(false);
		setTitle("Receitas");
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

		cbClientes.setBounds(120, 25, 210, 22);
		cbCategoriasReceitas.setBounds(120, 235, 210, 22);

		lblpesquisarCliente.setBounds(10, 25, 210, 22);
		lblclienteTel.setBounds(10, 60, 120, 22);
		lbldataVenc.setBounds(10, 95, 140, 22);
		lblvalor.setBounds(10, 130, 120, 22);
		lbldescricao.setBounds(10, 165, 120, 22);
		lbldata.setBounds(10, 200, 120, 22);
		lblcategoria.setBounds(10, 235, 120, 22);

		txtclienteTel.setBounds(120, 60, 210, 22);
		txtdataVenc.setBounds(120, 95, 70, 22);
		new GhostText(txtdataVenc, "00/00/0000");
		txtvalor.setBounds(120, 130, 210, 22);
		new GhostText(txtvalor, "0,00");
		txtdescricao.setBounds(120, 165, 210, 22);
		txtdata.setBounds(120, 200, 70, 22);

		btsalvar.setBounds(75, 320, 100, 22);
		btfechar.setBounds(187, 320, 100, 22);

		add(cbClientes);
		add(cbCategoriasReceitas);

		add(lblpesquisarCliente);
		add(lblclienteTel);
		add(lblvalor);
		add(lbldescricao);
		add(lbldata);
		add(lblcategoria);
		add(lbldataVenc);

		add(txtclienteTel);
		add(txtvalor);
		add(txtdescricao);
		add(txtdata);
		add(txtdataVenc);

		add(btsalvar);
		add(btfechar);

		btsalvar.addActionListener(this);
		btfechar.addActionListener(this);

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
					PreparedStatement ps = retornaConexao
							.prepareStatement("Select fone, codigo from cliente where nome=? ");

					ps.setString(1, nome);

					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						txtclienteTel.setText(rs.getString(1));
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
				categoriaSelecionada = (String) cbCategoriasReceitas.getSelectedItem();

				PreparedStatement ps = retornaConexao.prepareStatement(
						"insert into contas_receber (descricao,dt_venc,valor,dt_recebimento,categoria,cli_cdgo) values (?,?,?,?,?,?) ");

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

				JOptionPane.showMessageDialog(this, "Receita cadastrada com sucesso!");
				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro:" + ex);
				ex.printStackTrace();
			}
		}

	}

}
