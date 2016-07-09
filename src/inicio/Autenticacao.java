package inicio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import conexao.Conexao;

public class Autenticacao extends JFrame implements ActionListener, KeyListener {

    private JLabel lblusuario = new JLabel("Usuario: ");
    private JLabel lblsenha = new JLabel("Senha: ");

    private ImageIcon imgusuario = new ImageIcon("./imagens/usuario.png");
    private ImageIcon imgsenha = new ImageIcon("./imagens/senha.png");

    private JLabel lblimgusuario = new JLabel(imgusuario);
    private JLabel lblimgsenha = new JLabel(imgsenha);

    private JButton btok = new JButton("OK");
    private JButton btcancelar = new JButton("CANCELAR");

    private JTextField txtusuario = new JTextField();
    private JPasswordField txtsenha = new JPasswordField();
  
    
    private JScrollPane painel = new JScrollPane();
    public static int codigousuario = 0;
    
    private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();
    
    public Autenticacao() {
        setSize(441, 299);
        setTitle("Login");
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);

        lblusuario.setBounds(110, 80, 100, 22);
        lblsenha.setBounds(110, 120, 100, 22);

        txtusuario.setBounds(193, 80, 186, 22);
        txtsenha.setBounds(193, 120, 186, 22);

        lblimgusuario.setBounds(70, 80, 22, 22);
        lblimgsenha.setBounds(70, 120, 22, 22);

        btok.setBounds(100, 190, 115, 22);
        btcancelar.setBounds(240, 190, 115, 22);

        painel.setBounds(10, 10, 422, 257);

        btcancelar.addActionListener(this);
        btok.addActionListener(this);

        txtsenha.addKeyListener(this);
        txtusuario.addKeyListener(this);

        add(lblusuario);
        add(txtusuario);
        add(lblsenha);
        add(txtsenha);
        add(lblimgsenha);
        add(lblimgusuario);
        add(btcancelar);
        add(btok);
        add(painel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btcancelar) {
            System.exit(0); 
        }
        if (e.getSource() == btok) {
            validar();
        }
    }

    @Override //ao pressionar uma tecla
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == txtusuario && e.getKeyCode() == 10) { //tecla 10=ENTER
            txtsenha.requestFocus();
        }
        if (e.getSource() == txtsenha && e.getKeyCode() == 10) {
            validar();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void validar() {

    	try {
    		PreparedStatement ps = retornaConexao.prepareStatement("Select * from usuarios where login=? and senha=?");

            ps.setString(1, txtusuario.getText());
            ps.setString(2, txtsenha.getText());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) { 
                codigousuario = rs.getInt("cdgo_usuario");//guarda id usuario
                TelaPrincipal p = new TelaPrincipal();
                p.setVisible(true);

                //gravar log
                Date data = new Date();
                SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                //abrir arquivo para gravação
                FileWriter fw = new FileWriter("./log.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(f.format(data) + " usuario: " + txtusuario.getText() + " logou\n");

                bw.close();
                fw.close();
                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Usuario e/ou senha incorreto(s). Tente novamente.");
                txtusuario.setText("");
                txtsenha.setText("");
                txtusuario.requestFocus();

            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao tentar autenticar " + ex);
        }
    }
  }


