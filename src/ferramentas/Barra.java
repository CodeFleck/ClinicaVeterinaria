package ferramentas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import agenda.TelaAgenda;
import financeiro.FluxoCaixa1;
import telaAnimal.TelaAnimal;
import telaCliente.TelaCliente;
import telaFornecedor.TelaFornecedor;
import telaProduto.TelaProduto;
import telaSOC.TelaSOC;

public class Barra extends JToolBar implements ActionListener {

	
	
	private ImageIcon imgclientes = new ImageIcon("./imagens/clientes.png");
	private ImageIcon imgfornecedores = new ImageIcon("./imagens/fornecedores.png");
	private ImageIcon imgprodutos = new ImageIcon("./imagens/produtos.png");
	private ImageIcon imgfinanceiro = new ImageIcon("./imagens/financeiro.png");
	private ImageIcon imgbackup = new ImageIcon("./imagens/backup.png");
	private ImageIcon imgcalculadora = new ImageIcon("./imagens/calculadora.png");
	private ImageIcon imgconfiguracoes = new ImageIcon("./imagens/configuracoes.png");
	private ImageIcon imgajuda = new ImageIcon("./imagens/ajuda.jpg");
	private ImageIcon imgsair = new ImageIcon("./imagens/sair.png");
	private ImageIcon imgagenda = new ImageIcon("./imagens/agenda.png");
	private ImageIcon imgsoc = new ImageIcon("./imagens/soc.png");
	private ImageIcon imganimal = new ImageIcon("./imagens/animal.png");
	private JButton btclientes = new JButton(imgclientes);
	private JButton btfornecedores = new JButton(imgfornecedores);
	private JButton btprodutos = new JButton(imgprodutos);
	private JButton btsoc = new JButton(imgsoc);
	private JButton btfinanceiro = new JButton(imgfinanceiro);
	private JButton btcalculadora = new JButton(imgcalculadora);
	private JButton btsair = new JButton(imgsair);
	private JButton btagenda = new JButton(imgagenda);
	private JButton btanimal = new JButton(imganimal);

	// construtor
	public Barra() {
		add(btclientes);
		add(btfornecedores);
		add(btprodutos);
		add(btanimal);
		add(btsoc);
		add(btfinanceiro);
		add(btagenda);
		add(btcalculadora);
		add(btsair);

		btclientes.setToolTipText("Clientes");
		btfornecedores.setToolTipText("Fornecedores");
		btprodutos.setToolTipText("Produtos");
		btsoc.setToolTipText("Prontuário");
		btfinanceiro.setToolTipText("Financeiro");
		btcalculadora.setToolTipText("Calculadora");
		btsair.setToolTipText("Sair");
		btagenda.setToolTipText("Agenda");
		btanimal.setToolTipText("Animal");

		btclientes.addActionListener(this);
		btfornecedores.addActionListener(this);
		btprodutos.addActionListener(this);
		btsoc.addActionListener(this);
		btfinanceiro.addActionListener(this);
		btagenda.addActionListener(this);
		btcalculadora.addActionListener(this);
		btanimal.addActionListener(this);
		btsair.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btclientes) {
			TelaCliente c = new TelaCliente();
			c.setVisible(true);
		}
		if (e.getSource() == btfornecedores) {
			TelaFornecedor f = new TelaFornecedor();
			f.setVisible(true);
		}
		if (e.getSource() == btprodutos) {
			TelaProduto p = new TelaProduto();
			p.setVisible(true);
		}
		if (e.getSource() == btsoc) {
			TelaSOC v = new TelaSOC();
			v.setVisible(true);
		}
		if (e.getSource() == btfinanceiro) {
			FluxoCaixa1 fluxocaixa = new FluxoCaixa1();
			fluxocaixa.setVisible(true);
		}
		if (e.getSource() == btagenda) {
			TelaAgenda agenda = new TelaAgenda();
			agenda.setVisible(true);
		}
		if (e.getSource() == btanimal) {
			TelaAnimal animal = new TelaAnimal();
			animal.setVisible(true);
		}
		if (e.getSource() == btcalculadora) {
			try {
				Runtime.getRuntime().exec("calc.exe");
			} catch (Exception ex) {
			}
		}
		if (e.getSource() == btsair) {
			int r = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?");
			if (r == 0) {
				System.exit(0);
			}
		}

	}
}
