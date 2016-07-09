package telaVacina;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import agenda.JCalendar;
import conexao.Conexao;

public class TelaAdicionaVacina extends JFrame implements ActionListener {

	private JLabel txtCodigo = new JLabel("");
	private JLabel lblAnimal = new JLabel("Animal: ");
	private JTextField txtAnimal = new JTextField();
	private JTextField txtAniDesc = new JTextField();
	private JLabel lblDescricao = new JLabel("Descrição: ");
	private JTextField txtDescricao = new JTextField();
	private JTextField txtProdDesc = new JTextField();
	private JLabel lblDtVac = new JLabel("Data Vac.: ");
	private JLabel lblDtVencVac = new JLabel("Próx. Vac.: ");
	private JTextField txtDtVac = new JTextField();
	private JTextField txtDtVencVac = new JTextField();
	private JLabel lblObs = new JLabel("OBS: ");
	private JTextField txtObs = new JTextField();
	private ImageIcon imgsalvar = new ImageIcon("./imagens/salvar.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");
	private JButton btSalvar = new JButton("SALVAR", imgsalvar);
	private JButton btFechar = new JButton("FECHAR", imgfechar);
	private ImageIcon imgalterar = new ImageIcon("./imagens/alterar.png");
	private JButton btalterar = new JButton("Alterar", imgalterar);
	private JScrollPane painel = new JScrollPane();
	private javax.swing.JComboBox calendario1 = new JCalendar(false);
	private javax.swing.JComboBox calendario2 = new JCalendar(false);
	private int codAni;

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	DateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");

	public TelaAdicionaVacina(int codigo) {
		codAni = codigo;
		setSize(370, 400);
		setTitle("Cadastro de Vacina");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setResizable(false);

		lblAnimal.setBounds(10, 62, 120, 22);
		txtAniDesc.setBounds(100, 62, 250, 20);
		lblDescricao.setBounds(10, 95, 120, 22);
		txtDescricao.setBounds(100, 95, 250, 22);
		lblDtVac.setBounds(10, 128, 120, 22);
		calendario1.setBounds(100, 128, 90, 22);
		lblDtVencVac.setBounds(10, 161, 120, 22);
		calendario2.setBounds(100, 161, 90, 22);
		painel.setBounds(75, 280, 275, 100);
		btSalvar.setBounds(85, 315, 100, 30);
		btFechar.setBounds(195, 315, 115, 30);

		add(lblAnimal);
		add(txtAniDesc);
		add(lblDescricao);
		add(txtDescricao);
		add(txtProdDesc);
		add(calendario1);
		add(calendario2);
		add(lblDtVac);
		add(lblDtVencVac);
		add(btFechar);
		add(btSalvar);

		btSalvar.addActionListener(this);
		btFechar.addActionListener(this);

		try {
			PreparedStatement ps = retornaConexao
					.prepareStatement("SELECT a.codigo, a.nome " + "FROM animal a " + "WHERE a.codigo = ? ");

			ps.setInt(1, codigo);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				txtAnimal.setText(rs.getString("a.codigo"));
				txtAniDesc.setText(rs.getString("a.nome"));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar dados " + ex);
			System.out.println(ex);
			ex.printStackTrace();

		}
	}

	private void verificarActionPerformed(java.awt.event.ActionEvent evt) {
		txtDtVac.setText(((JCalendar) calendario1).getText().replace("/", "-"));
		txtDtVencVac.setText(((JCalendar) calendario2).getText().replace("/", "-"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btFechar) {
			TelaVacina vac = new TelaVacina(codAni);
			vac.setVisible(true);
			dispose();
		}

		if (e.getSource() == btSalvar) {
			verificarActionPerformed(e);

			try {
				PreparedStatement ps = retornaConexao
						.prepareStatement("INSERT INTO vacina(ani_cdgo,obs,dt_vac,dt_venc_vac) VALUES (?,?,?,?)");

				ps.setString(1, txtAnimal.getText());
				ps.setString(2, txtDescricao.getText());
				Date DtVac = formatoData.parse(txtDtVac.getText());
				ps.setDate(3, new java.sql.Date(DtVac.getTime()));
				Date dtVencVac = formatoData.parse(txtDtVencVac.getText());
				ps.setDate(4, new java.sql.Date(dtVencVac.getTime()));

				ps.executeUpdate();

				JOptionPane.showMessageDialog(this, "Vacina cadastrada com sucesso!");

				TelaVacina vac = new TelaVacina(codAni);
				vac.setVisible(true);
				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro:" + ex);
				ex.printStackTrace();
			}
		}
	}
}
