package telaSOC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import conexao.Conexao;

public class TelaAlteraSOC extends JFrame implements ActionListener {

	private JLabel lblsubjetivo = new JLabel("Subjetivo");
	private JLabel lblobjetivo = new JLabel("Objetivo");
	private JLabel lblconduta = new JLabel("Conduta");
	private JLabel lbldataAtual = new JLabel("");
	private JLabel lbldataProxSoc = new JLabel("Proxima consulta: ");
	private JLabel lbldataProxVac = new JLabel("Proxima vacina: ");

	private JTextArea subj = new JTextArea(600, 175);
	private JTextArea obj = new JTextArea(600, 175);
	private JTextArea cond = new JTextArea(600, 175);

	private ImageIcon imgsalvar = new ImageIcon("./imagens/save2.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");
	private JButton btSalvar = new JButton("Salvar", imgsalvar);
	private JButton btFechar = new JButton("Fechar", imgfechar);
	private JScrollPane painel = new JScrollPane();
	private int codAni, codSOC, codVac;

	private JTextField campoProxDataSoc = new JTextField();
	private JTextField campoProxDataVac = new JTextField();
	private DateFormat formatador = new SimpleDateFormat("dd-MM-yyyy");

	Date DataProxVac = null;

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	public TelaAlteraSOC(int codigo) {
		setSize(650, 690);
		setTitle("Alteracao de Registro");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setResizable(false);
		codSOC = codigo;

		lblsubjetivo.setBounds(10, 10, 100, 22);
		subj.setBounds(20, 40, 600, 150);
		lblobjetivo.setBounds(10, 200, 100, 22);
		obj.setBounds(20, 225, 600, 150);
		lblconduta.setBounds(10, 380, 100, 22);
		cond.setBounds(20, 405, 600, 150);
		lbldataAtual.setBounds(540, 10, 250, 22);
		lbldataProxSoc.setBounds(20, 580, 150, 22);
		lbldataProxVac.setBounds(20, 620, 150, 22);
		campoProxDataSoc.setBounds(145, 580, 85, 22);
		campoProxDataVac.setBounds(145, 620, 85, 22);

		painel.setBounds(75, 280, 275, 100);
		btSalvar.setBounds(400, 600, 105, 30);
		btFechar.setBounds(510, 600, 105, 30);

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
		add(campoProxDataVac);
		add(btSalvar);
		add(btFechar);

		btSalvar.addActionListener(this);
		btFechar.addActionListener(this);

		try {
			PreparedStatement ps = retornaConexao.prepareStatement(
					"SELECT date_format(soc_data_atual, '%d-%m-%Y') soc_data_atual, subjetivo, objetivo, conduta, animal_cdgo, data_prox_soc "
							+ "FROM soc WHERE cdgo_soc = ?");

			ps.setInt(1, codigo);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String dataAtual = rs.getString("soc_data_atual");
				lbldataAtual.setText(dataAtual.replace("-", "/"));

				subj.setText(rs.getString("subjetivo"));
				obj.setText(rs.getString("objetivo"));
				cond.setText(rs.getString("conduta"));
				codAni = rs.getInt("animal_cdgo");

				String campoProxDataSocS = formatador.format(rs.getDate("data_prox_soc"));
				campoProxDataSoc.setText(campoProxDataSocS.replace("-", "/"));

			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar dados " + ex);
			ex.printStackTrace();

		}

		// carregando data prox vacina
		try {
			PreparedStatement ps = retornaConexao.prepareStatement(
					"SELECT DATE_FORMAT(dt_venc_vac,'%Y-%m-%d') dt_venc_vac FROM vacina WHERE ani_cdgo = ?");

			ps.setInt(1, codAni);

			ResultSet rs = ps.executeQuery();

			if (rs.next() && lbldataProxVac != null) {
				DataProxVac = rs.getDate("dt_venc_vac");

			}

			if (DataProxVac != null) {
				String campoProxDataVacS = DataProxVac.toString();

				String dia = campoProxDataVacS.substring(8, 10);
				String mes = campoProxDataVacS.substring(5, 7);
				String ano = campoProxDataVacS.substring(0, 4);

				campoProxDataVacS = dia + "/" + mes + "/" + ano;
				campoProxDataVac.setText(campoProxDataVacS);

			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar dados " + ex);
			ex.printStackTrace();

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btFechar) {
			conexao.FecharConexao();
			dispose();
		}

		if (e.getSource() == btSalvar) {

			try {
				PreparedStatement ps = retornaConexao
						.prepareStatement("UPDATE soc SET subjetivo = ?, objetivo = ?, conduta = ?,"
								+ " animal_cdgo = ?, data_prox_soc = ? WHERE cdgo_soc = ?");

				ps.setString(1, subj.getText());
				ps.setString(2, obj.getText());
				ps.setString(3, cond.getText());
				ps.setInt(4, codAni);

				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				java.sql.Date data = new java.sql.Date(format.parse(campoProxDataSoc.getText()).getTime());

				ps.setDate(5, data);

				ps.setInt(6, codSOC);

				ps.executeUpdate();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro: " + ex);
				ex.printStackTrace();
			}

			// updating data prox vacina
			
			// carregando data prox vacina
			try {
				PreparedStatement ps = retornaConexao.prepareStatement(
						"SELECT codigo FROM vacina WHERE ani_cdgo = ?");

				ps.setInt(1, codAni);

				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					
					codVac = rs.getInt("codigo");
					
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao carregar dados " + ex);
				ex.printStackTrace();

			}
			
			
			try {
				PreparedStatement ps = retornaConexao
						.prepareStatement("UPDATE vacina SET dt_venc_vac = ?" + " WHERE codigo = ?");

				String campoProxDataVacS = campoProxDataVac.getText();

				String dia = campoProxDataVacS.substring(0, 2);
				String mes = campoProxDataVacS.substring(3, 5);
				String ano = campoProxDataVacS.substring(6, 10);

				campoProxDataVacS = ano + "/" + mes + "/" + dia;

				ps.setString(1, campoProxDataVacS);

				ps.setInt(2, codVac);

				ps.executeUpdate();

				JOptionPane.showMessageDialog(this, "Consulta atualizada com sucesso!");

				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro: " + ex);
				ex.printStackTrace();
			}
		}

	}

}
