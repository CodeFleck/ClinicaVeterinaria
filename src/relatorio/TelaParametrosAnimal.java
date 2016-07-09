package relatorio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import conexao.Conexao;
import net.sf.jasperreports.engine.JRException;

public class TelaParametrosAnimal extends JFrame implements ActionListener {

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	private JLabel lblCliente = new JLabel("Cliente:");
	private JLabel lblanimal = new JLabel("Animal:");
	private JLabel lblRaca = new JLabel("Raça:");
	private JLabel lblCastrado = new JLabel("Castrado:");

	private JTextField txtNomeCliente = new JTextField();
	private JTextField txtNomeAnimal = new JTextField();
	private JTextField txtCastrado = new JTextField();
	private JTextField txtRaca = new JTextField();
	private JComboBox<String> cbCastrado = new JComboBox<String>();
	private JComboBox<String> cbNomeCliente = new JComboBox<String>();
	private JComboBox<String> cbNomeAnimal = new JComboBox<String>();
	private JComboBox<String> cbRaca = new JComboBox<String>();

	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");

	private JButton btGerar = new JButton("Gerar", null);
	private JButton btFechar = new JButton("Fechar", imgfechar);

	public TelaParametrosAnimal() {
		setSize(420, 300);
		setResizable(false);
		setTitle("Gera Relatório");
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		lblCliente.setBounds(10, 50, 100, 22);
		lblanimal.setBounds(10, 75, 100, 22);
		lblRaca.setBounds(10, 100, 100, 22);
		lblCastrado.setBounds(10, 125, 100, 22);

		cbNomeCliente.setBounds(80, 50, 300, 22);
		cbNomeAnimal.setBounds(80, 75, 300, 22);
		cbRaca.setBounds(80, 100, 300, 22);
		cbCastrado.setBounds(80, 125, 60, 22);

		btGerar.setBounds(95, 180, 115, 22);
		btFechar.setBounds(220, 180, 115, 22);

		add(lblanimal);
		add(lblRaca);
		add(lblCastrado);
		add(lblCliente);
		
		add(cbNomeCliente);
		add(cbNomeAnimal);
		add(cbRaca);
		add(cbCastrado);
		
		add(btGerar);
		add(btFechar);

		try {
			PreparedStatement ps = retornaConexao.prepareStatement("Select nome FROM cliente");
			ResultSet rs = ps.executeQuery();

			cbNomeCliente.insertItemAt("", 0);

			while (rs.next()) {
				cbNomeCliente.addItem(rs.getString(1));
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

		try {
			PreparedStatement ps = retornaConexao.prepareStatement("Select nome FROM animal");
			ResultSet rs = ps.executeQuery();

			cbNomeAnimal.insertItemAt("", 0);

			while (rs.next()) {
				cbNomeAnimal.addItem(rs.getString(1));
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

		try {
			PreparedStatement ps = retornaConexao.prepareStatement("Select raca FROM animal");
			ResultSet rs = ps.executeQuery();

			cbRaca.insertItemAt("", 0);

			while (rs.next()) {
				cbRaca.addItem(rs.getString(1));
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

		cbCastrado.setModel(new DefaultComboBoxModel<String>(new String[] { "", "N", "S" }));

		btGerar.addActionListener(this);
		btFechar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btGerar) {
			Relatorios gera = new Relatorios();

			try {
				gera.geraRelatorioAnimal(cbNomeCliente.getSelectedItem().toString(),
						cbNomeAnimal.getSelectedItem().toString(), cbRaca.getSelectedItem().toString(),
						cbCastrado.getSelectedItem().toString());
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
