package relatorio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import agenda.JCalendar;
import conexao.Conexao;
import net.sf.jasperreports.engine.JRException;

public class TelaParametrosVacina extends JFrame implements ActionListener {

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();
	private javax.swing.JComboBox calendario1 = new JCalendar(false);
	private javax.swing.JComboBox calendario2 = new JCalendar(false);
	private JLabel lblCliente = new JLabel("Cliente:");
	private JLabel lblanimal = new JLabel("Animal:");
	private JLabel lblDtVenc = new JLabel("Data Vencimento Vacina");
	private JLabel lblA1 = new JLabel("à");

	private JTextField txtDtVencIni = new JTextField();
	private JTextField txtDtVencFim = new JTextField();
	private JComboBox<String> cbNomeCliente = new JComboBox<String>();
	private JComboBox<String> cbNomeAnimal = new JComboBox<String>();
	private SimpleDateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");
	private MaskFormatter formatter = null;
	private Integer codCli;
	private Integer codAni;
	private String nomeCli;

	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");

	private JButton btGerar = new JButton("Gerar", null);
	private JButton btFechar = new JButton("Fechar", imgfechar);

	public TelaParametrosVacina() {
		setSize(420, 300);
		setResizable(false);
		setTitle("Gera Relatório");
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		lblDtVenc.setBounds(140, 60, 150, 22);
		lblA1.setBounds(207, 100, 10, 22);
		calendario1.setBounds(110, 100, 90, 22);
		calendario2.setBounds(220, 100, 90, 22);

		btGerar.setBounds(95, 180, 115, 22);
		btFechar.setBounds(220, 180, 115, 22);

		add(lblDtVenc);
		add(lblA1);
		add(calendario1);
		add(calendario2);

		add(btGerar);
		add(btFechar);

		btGerar.addActionListener(this);
		btFechar.addActionListener(this);
	}

	private void verificarActionPerformed(java.awt.event.ActionEvent evt) {
		txtDtVencIni.setText(((JCalendar) calendario1).getText().replace("/", "-"));
		txtDtVencFim.setText(((JCalendar) calendario2).getText().replace("/", "-"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btGerar) {
			verificarActionPerformed(e);
			Relatorios gera = new Relatorios();

			try {
				gera.geraRelatorioVacina(txtDtVencIni.getText(), txtDtVencFim.getText());
			} catch (JRException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} else if (e.getSource() == btFechar) {
			dispose();
		}

	}
}
