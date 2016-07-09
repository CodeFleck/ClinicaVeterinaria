package agenda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import conexao.Conexao;

public class TelaAdicionaAgenda extends JFrame implements ActionListener {

	private JLabel lblDtCons = new JLabel("Data: ");
	private JLabel lblHora = new JLabel("Hora: ");
	private JLabel lblTitulo = new JLabel("Título: ");
	private JLabel lblDescricao = new JLabel("Descrição: ");
	private JTextArea txtDescricao = new JTextArea(200, 90);
	private JTextField txtTitulo = new JTextField();

	private JTextField txtDtCons = new JTextField();
	private JTextField txtHora = new JTextField();
	private int compromissoCod;

	private ImageIcon imgsalvar = new ImageIcon("./imagens/salvar.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");
	private ImageIcon imgalterar = new ImageIcon("./imagens/alterar.png");

	private JButton btSalvar = new JButton("Salvar", imgsalvar);
	private JButton btFechar = new JButton("Fechar", imgfechar);
	private JButton btalterar = new JButton("Alterar", imgalterar);

	private JScrollPane painel = new JScrollPane();
	private javax.swing.JComboBox calendario = new JCalendar(false);

	private DateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");
	private MaskFormatter formatter = null;

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	public TelaAdicionaAgenda() {
		setSize(350, 300);
		setTitle("Cadastro de Compromisso");
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("##:##:##");
		} catch (ParseException e) {
		}
		txtHora = new JFormattedTextField(formatter);

		lblDtCons.setBounds(10, 45, 100, 22);
		lblHora.setBounds(186, 45, 100, 22);
		lblTitulo.setBounds(10, 75, 100, 22);
		lblDescricao.setBounds(10, 105, 100, 22);
		calendario.setBounds(85, 45, 90, 22);

		txtDtCons.setBounds(85, 45, 70, 22);
		txtHora.setBounds(226, 45, 60, 22);
		txtDescricao.setBounds(85, 105, 200, 90);
		txtTitulo.setBounds(85, 75, 200, 22);
		btSalvar.setBounds(71, 226, 100, 30);
		btFechar.setBounds(181, 226, 115, 30);

		add(lblDtCons);
		add(lblHora);
		add(lblTitulo);
		add(lblDescricao);
		add(txtDescricao);
		add(txtHora);
		add(calendario);
		add(txtTitulo);
		add(btSalvar);
		add(btFechar);

		btSalvar.addActionListener(this);
		btFechar.addActionListener(this);

	}

	public TelaAdicionaAgenda(int codigo) {
		setSize(350, 300);
		setTitle("Alteração de Consultas");
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		compromissoCod = codigo;
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("##:##:##");
		} catch (ParseException e) {
		}
		txtHora = new JFormattedTextField(formatter);

		// try {
		// formatter = new MaskFormatter("##-##-####");
		// } catch (ParseException e) {
		// }
		// txtDtCons = new JFormattedTextField(formatter);

		lblDtCons.setBounds(10, 45, 100, 22);
		lblHora.setBounds(186, 45, 100, 22);
		lblTitulo.setBounds(10, 75, 100, 22);
		lblDescricao.setBounds(10, 105, 100, 22);
		calendario.setBounds(85, 45, 90, 22);

		txtDtCons.setBounds(85, 45, 70, 22);
		txtHora.setBounds(226, 45, 60, 22);
		txtDescricao.setBounds(85, 105, 200, 90);
		txtTitulo.setBounds(85, 75, 200, 22);
		btalterar.setBounds(71, 226, 100, 30);
		btFechar.setBounds(181, 226, 115, 30);

		add(lblDtCons);
		add(lblHora);
		add(lblTitulo);
		add(lblDescricao);
		add(txtDescricao);
		add(txtHora);
		add(calendario);
		add(txtTitulo);
		add(btalterar);
		add(btFechar);

		btalterar.addActionListener(this);
		btFechar.addActionListener(this);

		try {

			PreparedStatement ps = retornaConexao.prepareStatement(
					"SELECT data, hora, titulo, descricao " + "FROM compromisso WHERE cdgo_compromisso = ?");

			ps.setInt(1, codigo);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				txtDtCons.setText(rs.getString("data"));
				txtHora.setText(rs.getString("hora"));
				txtTitulo.setText(rs.getString("titulo"));
				txtDescricao.setText(rs.getString("descricao"));

			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar dados " + ex);
			System.out.println(ex);
			ex.printStackTrace();
		}

	}

	private void verificarActionPerformed(java.awt.event.ActionEvent evt) {
		txtDtCons.setText(((JCalendar) calendario).getText().replace("/", "-"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btalterar) {
			
			
			try {

				PreparedStatement ps = retornaConexao.prepareStatement(
						"UPDATE compromisso SET data = ?, hora = ?, titulo = ?,  descricao = ? WHERE cdgo_compromisso = ?");

				
				String strDate = txtDtCons.getText();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = sdf.parse(strDate);
				String dbDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				ps.setString(1, dbDate);
				ps.setString(2, txtHora.getText());
				ps.setString(3, txtTitulo.getText());
				ps.setString(4, txtDescricao.getText());
				ps.setInt(5, compromissoCod);

				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Compromisso alterado!");
				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao alterar " + ex);
			}
		}

		else if (e.getSource() == btFechar) {
			dispose();
		}

		else if (e.getSource() == btSalvar) {

			try {
				verificarActionPerformed(e);
				PreparedStatement ps = retornaConexao
						.prepareStatement("INSERT INTO compromisso( data, hora, titulo, descricao) VALUES (?,?,?,?)");
				Date DtCons = formatoData.parse(txtDtCons.getText());
				ps.setDate(1, new java.sql.Date(DtCons.getTime()));
				ps.setString(2, txtHora.getText());
				ps.setString(3, txtTitulo.getText());
				ps.setString(4, txtDescricao.getText());

				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Novo compromisso agendado!");
				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro:" + ex);
				ex.printStackTrace();
			}
		}
	}
}
