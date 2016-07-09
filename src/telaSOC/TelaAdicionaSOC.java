package telaSOC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import conexao.Conexao;
import ferramentas.GhostText;
import telaVacina.TelaVacina;

public class TelaAdicionaSOC extends JFrame implements ActionListener {

	private JLabel lblsubjetivo = new JLabel("Subjetivo");
	private JLabel lblobjetivo = new JLabel("Objetivo");
	private JLabel lblconduta = new JLabel("Conduta");
	private JLabel lbldataAtual = new JLabel("");
	private JLabel lbldataProxSoc = new JLabel("Próxima Consulta: ");
	private JLabel lbldataProxVac = new JLabel("Adicionar Vacina: ");

	private JTextArea subj = new JTextArea(600, 147);
	private JTextArea obj = new JTextArea(600, 147);
	private JTextArea cond = new JTextArea(600, 147);

	private ImageIcon imgsalvar = new ImageIcon("./imagens/save2.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");
	private JButton btSalvar = new JButton("Salvar", imgsalvar);
	private JButton btFechar = new JButton("Fechar", imgfechar);
	private JButton btVacina = new JButton("Vacina");

	private JTextField campoProxDataSoc = new JTextField();
	private JTextField campoProxVac = new JTextField();
	private JScrollPane painel = new JScrollPane();
	private DateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");
	private int codAni;

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	public TelaAdicionaSOC(int codigo) {
		setSize(650, 690);
		setTitle("Registro de Prontuario");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setResizable(false);
		codAni = codigo;

		// exibindo data atual
		Calendar cal = Calendar.getInstance();
		String dataAtual = (formatoData.format(cal.getTime()));
		lbldataAtual.setText(dataAtual.replace("-", "/"));

		lblsubjetivo.setBounds(10, 10, 100, 22);
		lblobjetivo.setBounds(10, 200, 100, 22);
		lblconduta.setBounds(10, 380, 100, 22);
		subj.setBounds(20, 40, 600, 150);
		obj.setBounds(20, 225, 600, 150);
		cond.setBounds(20, 405, 600, 150);
		subj.setLineWrap(true);
		obj.setLineWrap(true);
		cond.setLineWrap(true);
		subj.setWrapStyleWord(true);
		obj.setWrapStyleWord(true);
		cond.setWrapStyleWord(true);

		painel.setBounds(75, 280, 275, 100);
		btSalvar.setBounds(400, 600, 105, 30);
		btFechar.setBounds(510, 600, 105, 30);
		lbldataAtual.setBounds(540, 10, 250, 22);
		lbldataProxSoc.setBounds(20, 580, 150, 22);
		lbldataProxVac.setBounds(20, 620, 150, 22);
		campoProxDataSoc.setBounds(145, 580, 100, 22);
		new GhostText(campoProxDataSoc, "dia/mes/ano");
		btVacina.setBounds(145, 620, 100, 22);

		add(lblconduta);
		add(lblsubjetivo);
		add(lblobjetivo);
		add(cond);
		add(subj);
		add(obj);
		add(lbldataAtual);
		add(lbldataProxSoc);
		add(lbldataProxVac);
		add(campoProxDataSoc);
		add(btVacina);
		add(btSalvar);
		add(btFechar);

		btVacina.addActionListener(this);
		btSalvar.addActionListener(this);
		btFechar.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btFechar) {
			conexao.FecharConexao();
			dispose();
		}

		else if (e.getSource() == btVacina) {

			// se for add vacina ele primeiro salva o soc atual e depois fecha a
			// janela

			String campoProxDataSocS = campoProxDataSoc.getText();
			campoProxDataSoc.setText(campoProxDataSocS.replace("/", "-"));
			// String campoProxVacS = campoProxVac.getText();
			// campoProxVac.setText(campoProxVacS.replace("/", "-"));
			String lbldataAtualS = lbldataAtual.getText();
			lbldataAtual.setText(lbldataAtualS.replace("/", "-"));

			try {
				PreparedStatement ps = retornaConexao.prepareStatement("INSERT INTO soc (subjetivo, objetivo, conduta,"
						+ " animal_cdgo, soc_data_atual, data_prox_soc) VALUES (?,?,?,?,?,?)");

				ps.setString(1, subj.getText());
				ps.setString(2, obj.getText());
				ps.setString(3, cond.getText());
				ps.setInt(4, codAni);

				Date dtDataAtual = new SimpleDateFormat("dd-MM-yyyy").parse(lbldataAtual.getText());
				String dbDtDataAtual = new SimpleDateFormat("yyyy-MM-dd").format(dtDataAtual);

				ps.setString(5, dbDtDataAtual);

				Date dtCampoProxDataSoc = new SimpleDateFormat("dd-MM-yyyy").parse(campoProxDataSoc.getText());
				String dbCampoProxDataSoc = new SimpleDateFormat("yyyy-MM-dd").format(dtCampoProxDataSoc);

				ps.setString(6, dbCampoProxDataSoc);

				ps.executeUpdate();

				JOptionPane.showMessageDialog(this, "Consulta registrada com sucesso!");

				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro: " + ex);
				ex.printStackTrace();
			}

			TelaVacina vac = new TelaVacina(codAni);
			vac.setVisible(true);

		}

		else if (e.getSource() == btSalvar) {

			String campoProxDataSocS = campoProxDataSoc.getText();
			campoProxDataSoc.setText(campoProxDataSocS.replace("/", "-"));
			String lbldataAtualS = lbldataAtual.getText();
			lbldataAtual.setText(lbldataAtualS.replace("/", "-"));

			try {
				PreparedStatement ps = retornaConexao.prepareStatement("INSERT INTO soc (subjetivo, objetivo, conduta,"
						+ " animal_cdgo, soc_data_atual, data_prox_soc) VALUES (?,?,?,?,?,?)");

				ps.setString(1, subj.getText());
				ps.setString(2, obj.getText());
				ps.setString(3, cond.getText());
				ps.setInt(4, codAni);

				Date dtDataAtual = new SimpleDateFormat("dd-MM-yyyy").parse(lbldataAtual.getText());
				String dbDtDataAtual = new SimpleDateFormat("yyyy-MM-dd").format(dtDataAtual);

				ps.setString(5, dbDtDataAtual);

				Date dtCampoProxDataSoc = new SimpleDateFormat("dd-MM-yyyy").parse(campoProxDataSoc.getText());
				String dbCampoProxDataSoc = new SimpleDateFormat("yyyy-MM-dd").format(dtCampoProxDataSoc);

				ps.setString(6, dbCampoProxDataSoc);

				ps.executeUpdate();

				JOptionPane.showMessageDialog(this, "Consulta registrada com sucesso!");

				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro: " + ex);
				ex.printStackTrace();
			}
		}
	}
}
