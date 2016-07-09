
package telaCliente;

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
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import conexao.Conexao;
import ferramentas.GhostText;

public class TelaAdicionaCliente extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblcodigo = new JLabel("Código:");
	private JLabel lblid = new JLabel("");

	private JLabel lblnome = new JLabel("Nome:");
	private JLabel lblendereco = new JLabel("Endereço:");
	private JLabel lblCPF = new JLabel("CPF:");
	private JLabel lblcidade = new JLabel("Cidade:");
	private JLabel lblestado = new JLabel("Estado:");
	private JLabel lblfone = new JLabel("Fone:");
	private JLabel lblemail = new JLabel("Email:");
	private JLabel lblsexo = new JLabel("Sexo:");

	private JTextField txtnome = new JTextField();
	private JTextField txtendereco = new JTextField();
	private JTextField txtCPF = new JTextField();
	private JTextField txtcidade = new JTextField();
	private JTextField txtestado = new JTextField();
	private JTextField txtfone = new JTextField();
	private JTextField txtemail = new JTextField();
	private JComboBox<String> cbSexo = new JComboBox<String>();

	private JScrollPane painel = new JScrollPane();

	private ImageIcon imgalterar = new ImageIcon("./imagens/edit.png");
	private ImageIcon imgsalvar = new ImageIcon("./imagens/save2.png");
	private ImageIcon imgfechar = new ImageIcon("./imagens/sair2.png");

	private JButton btsalvar = new JButton("Salvar", imgsalvar);
	private JButton btfechar = new JButton("Fechar", imgfechar);
	private JButton btalterar = new JButton("Alterar", imgalterar);

	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();

	// CONSTRUTOR
	public TelaAdicionaCliente() {
		setSize(370, 370);
		setResizable(false);
		setTitle("Cadastro de Clientes");
		setLayout(null);
		setLocationRelativeTo(null);

		lblnome.setBounds(10, 10, 100, 22);
		lblendereco.setBounds(10, 40, 100, 22);
		lblCPF.setBounds(10, 70, 100, 22);
		lblcidade.setBounds(10, 100, 100, 22);
		lblestado.setBounds(10, 130, 100, 22);
		lblfone.setBounds(10, 160, 100, 22);
		lblemail.setBounds(10, 190, 100, 22);
		lblsexo.setBounds(125, 130, 40, 22);

		txtnome.setBounds(75, 10, 275, 22);
		txtendereco.setBounds(75, 40, 275, 22);
		txtCPF.setBounds(75, 70, 275, 22);
		new GhostText(txtCPF, "000.000.000-00");
		txtcidade.setBounds(75, 100, 275, 22);
		txtestado.setBounds(75, 130, 40, 22);
		cbSexo.setBounds(165, 130, 65, 22);
		txtfone.setBounds(75, 160, 275, 22);
		new GhostText(txtfone, "(00) 0000 0000");
		txtemail.setBounds(75, 190, 275, 22);
		painel.setBounds(75, 280, 275, 100);

		btsalvar.setBounds(75, 280, 100, 22);
		btfechar.setBounds(185, 280, 100, 22);

		add(lblnome);
		add(lblendereco);
		add(lblCPF);
		add(lblcidade);
		add(lblestado);
		add(lblfone);
		add(lblemail);
		add(lblsexo);

		add(txtnome);
		add(txtendereco);
		add(txtCPF);
		add(txtcidade);
		add(txtestado);
		add(txtfone);
		add(txtemail);
		add(cbSexo);

		add(btsalvar);
		add(btfechar);

		cbSexo.setModel(new DefaultComboBoxModel<String>(new String[] { "M", "F" }));

		// ouvinte de evento
		btsalvar.addActionListener(this);
		btfechar.addActionListener(this);

	}

	// construtor que sera executado para carregar dados alterar

	public TelaAdicionaCliente(int id) {
		setSize(370, 370);
		setResizable(false);
		setTitle("Alteração de Clientes");
		setLayout(null);
		setLocationRelativeTo(null);
		
		lblnome.setBounds(10, 40, 100, 22);
		lblendereco.setBounds(10, 70, 100, 22);
		lblCPF.setBounds(10, 100, 120, 22);
		lblcidade.setBounds(10, 130, 100, 22);
		lblestado.setBounds(10, 160, 100, 22);
		lblfone.setBounds(10, 190, 100, 22);
		lblemail.setBounds(10, 220, 100, 22);
		lblsexo.setBounds(125, 160, 40, 22);
		
		txtnome.setBounds(75, 40, 275, 22);
		txtendereco.setBounds(75, 70, 275, 22);
		txtCPF.setBounds(75, 100, 275, 22);
//		new GhostText(txtCPF, "000.000.000-00");
		txtcidade.setBounds(75, 130, 275, 22);
		txtestado.setBounds(75, 160, 40, 22);
		txtfone.setBounds(75, 190, 275, 22);
//		new GhostText(txtfone, "(00) 0000 0000");
		txtemail.setBounds(75, 220, 275, 22);
		cbSexo.setBounds(165, 160, 65, 22);
		painel.setBounds(75, 310, 275, 100);

		lblcodigo.setBounds(10, 10, 100, 22);
		add(lblcodigo);
		lblid.setBounds(75, 10, 100, 22);
		add(lblid);
		
		btalterar.setBounds(75, 280, 100, 22);
		btfechar.setBounds(185, 280, 100, 22);


		add(lblnome);
		add(lblendereco);
		add(lblCPF);
		add(lblcidade);
		add(lblestado);
		add(lblfone);
		add(lblemail);
		add(lblsexo);

		add(txtnome);
		add(txtendereco);
		add(txtCPF);
		add(txtcidade);
		add(txtestado);
		add(txtfone);
		add(txtemail);
		add(cbSexo);

		add(btalterar);
		add(btfechar);

		cbSexo.setModel(new DefaultComboBoxModel<String>(new String[] { "M", "F" }));

		btalterar.addActionListener(this);
		btfechar.addActionListener(this);

		// carregar dados do cliente a alterar

		try {

			PreparedStatement ps = retornaConexao.prepareStatement("Select * from cliente where codigo=?");

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				txtnome.setText(rs.getString("nome"));
				txtendereco.setText(rs.getString("endereco"));
				txtCPF.setText(rs.getString("CPF"));
				txtcidade.setText(rs.getString("cidade"));
				txtestado.setText(rs.getString("estado"));
				txtfone.setText(rs.getString("fone"));
				txtemail.setText(rs.getString("email"));
				cbSexo.setSelectedItem(rs.getString("sexo"));
				lblid.setText(rs.getString("codigo"));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar dados " + ex);
			System.out.println(ex);
			ex.printStackTrace();

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btalterar) {
			try {
				PreparedStatement ps = retornaConexao.prepareStatement(
						"Update cliente set nome=?,endereco=?,cpf=?,cidade=?,estado=?,fone=?,email=?,sexo=?"
								+ "where codigo=?");

				ps.setString(1, txtnome.getText());
				ps.setString(2, txtendereco.getText());
				ps.setString(3, txtCPF.getText());
				ps.setString(4, txtcidade.getText());
				ps.setString(5, txtestado.getText());
				ps.setString(6, txtfone.getText());
				ps.setString(7, txtemail.getText());
				ps.setString(8, cbSexo.getSelectedItem().toString());
				ps.setString(9, lblid.getText());

				ps.executeUpdate();

				JOptionPane.showMessageDialog(this, "Cliente alterado com sucesso!");

				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao alterar " + ex);
			}
		}

		if (e.getSource() == btfechar) {
			dispose();
		}

		if (e.getSource() == btsalvar) {

			try {
				PreparedStatement ps = retornaConexao.prepareStatement(
						"insert into cliente(nome,endereco,cpf,cidade,estado,fone,email,sexo)  values(?,?,?,?,?,?,?,?) ");

				ps.setString(1, txtnome.getText());
				ps.setString(2, txtendereco.getText());
				ps.setString(3, txtCPF.getText());
				ps.setString(4, txtcidade.getText());
				ps.setString(5, txtestado.getText());
				ps.setString(6, txtfone.getText());
				ps.setString(7, txtemail.getText());
				ps.setString(8, cbSexo.getSelectedItem().toString());

				ps.executeUpdate();

				JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");

				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro:" + ex);
				ex.printStackTrace();
			}
		}
	}

}
