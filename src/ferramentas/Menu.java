package ferramentas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import agenda.TelaAgenda;
import financeiro.FluxoCaixa1;
import financeiro.TelaAdicionaDespesa;
import financeiro.TelaAdicionaReceita;
import net.sf.jasperreports.engine.JRException;
import relatorio.Relatorios;
import relatorio.TelaParametrosAnimal;
import relatorio.TelaParametrosVacina;
import telaAnimal.TelaAnimal;

import telaCliente.TelaCliente;
import telaFornecedor.TelaFornecedor;
import telaProduto.TelaProduto;
import telaSOC.TelaSOC;

public class Menu extends JMenuBar implements ActionListener {

	private JMenu cadastro = new JMenu("CADASTRO");
	private JMenu financeiro = new JMenu("FINANCEIRO");
	private JMenu consulta = new JMenu("CONSULTAS");
	private JMenu relatorios = new JMenu("RELATORIOS");
	private JMenu utilitarios = new JMenu("UTILITARIOS");
	private JMenu ajuda = new JMenu("AJUDA");

	// itens do menu cadastro
	JMenuItem clientes = new JMenuItem("Clientes");
	JMenuItem fornecedores = new JMenuItem("Fornecedores");
	JMenuItem produtos = new JMenuItem("Produtos");
	JMenuItem animais = new JMenuItem("Animais");
	JMenuItem usuarios = new JMenuItem("Usuários");
	JSeparator s1 = new JSeparator();
	JMenuItem sair = new JMenuItem("Sair");

	// itens do menu Financeiro
	JMenuItem fluxoCaixa = new JMenuItem("Fluxo de Caixa");
	JSeparator s2 = new JSeparator();
	JMenuItem receita = new JMenuItem("Entradas");
	JMenuItem despesa = new JMenuItem("Saídas");

	// intens consulta
	JMenuItem agenda = new JMenuItem("Agenda");
	JMenuItem soc = new JMenuItem("Prontuário");

	// itens relatorio
	JMenuItem relClientes = new JMenuItem("Clientes");
	JMenuItem relAnimais = new JMenuItem("Animais");
	JMenuItem relFornecedores = new JMenuItem("Fornecedores");
	JMenuItem relProdutos = new JMenuItem("Produtos");
	JMenuItem relVacinas = new JMenuItem("Vacinas");

	// itens do menu utilitarios
	JMenuItem calculadora = new JMenuItem("Calculadora");

	// itens menu ajuda
	JMenuItem sobre = new JMenuItem("Sobre");

	// CONSTRUTOR
	public Menu() {
		add(cadastro);
		add(financeiro);
		add(consulta);
		add(relatorios);
		add(utilitarios);
		add(ajuda);

		// montar menus
		cadastro.add(clientes);
		cadastro.add(fornecedores);
		cadastro.add(produtos);
		cadastro.add(animais);
		cadastro.add(s1);
		cadastro.add(sair);

		financeiro.add(fluxoCaixa);
		financeiro.add(s2);
		financeiro.add(receita);
		financeiro.add(despesa);

		consulta.add(agenda);
		consulta.add(soc);

		relatorios.add(relClientes);
		relatorios.add(relAnimais);
		relatorios.add(relFornecedores);
		relatorios.add(relProdutos);
		relatorios.add(relVacinas);

		utilitarios.add(calculadora);

		ajuda.add(sobre);

		// adicionar ouvinte de eventos no menu itens
		clientes.addActionListener(this);
		fornecedores.addActionListener(this);
		produtos.addActionListener(this);
		animais.addActionListener(this);
		usuarios.addActionListener(this);
		sair.addActionListener(this);
		fluxoCaixa.addActionListener(this);
		agenda.addActionListener(this);
		receita.addActionListener(this);
		calculadora.addActionListener(this);
		sobre.addActionListener(this);
		soc.addActionListener(this);
		relClientes.addActionListener(this);
		relAnimais.addActionListener(this);
		relFornecedores.addActionListener(this);
		relProdutos.addActionListener(this);
		relVacinas.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sair) {
			int r = JOptionPane.showConfirmDialog(null, "Deseja encerrar o sistema?");
			if (r == 0) { // sera zero se o usuario escolher sim, sera um para
							// nÃo, 2 para cancelar
				System.exit(0);
			}
		}

		else if (e.getSource() == clientes) {
			TelaCliente cl = new TelaCliente();
			cl.setVisible(true);

		} else if (e.getSource() == fornecedores) {
			TelaFornecedor f = new TelaFornecedor();
			f.setVisible(true);
		} else if (e.getSource() == produtos) {
			TelaProduto p = new TelaProduto();
			p.setVisible(true);
		} else if (e.getSource() == animais) {
			TelaAnimal ta = new TelaAnimal();
			ta.setVisible(true);
		}

		else if (e.getSource() == fluxoCaixa) {
			FluxoCaixa1 fluxoCaixa = new FluxoCaixa1();
			fluxoCaixa.setVisible(true);
		} else if (e.getSource() == receita) {
			TelaAdicionaReceita rec = new TelaAdicionaReceita();
			rec.setVisible(true);
		} else if (e.getSource() == despesa) {
			TelaAdicionaDespesa des = new TelaAdicionaDespesa();
			des.setVisible(true);
		} else if (e.getSource() == agenda) {
			TelaAgenda agenda = new TelaAgenda();
			agenda.setVisible(true);
		} else if (e.getSource() == soc) {
			TelaSOC soc = new TelaSOC();
			soc.setVisible(true);
		} else if (e.getSource() == relClientes) {
			try {
				Relatorios gera = new Relatorios();
				gera.geraRelatorioCliente();
			} catch (JRException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == relAnimais) {
			TelaParametrosAnimal relAnimal = new TelaParametrosAnimal();
			relAnimal.setVisible(true);
		} else if (e.getSource() == relFornecedores) {
			try {
				Relatorios gera = new Relatorios();
				gera.geraRelatorioFornecedor();
			} catch (JRException e1) {

				e1.printStackTrace();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
		} else if (e.getSource() == relProdutos) {
			try {
				Relatorios gera = new Relatorios();
				gera.geraRelatorioProduto();

			} catch (JRException e1) {

				e1.printStackTrace();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
		} else if (e.getSource() == relVacinas) {
			TelaParametrosVacina relAnimal = new TelaParametrosVacina();
			relAnimal.setVisible(true);

		} else if (e.getSource() == calculadora) {
			try {
				Runtime.getRuntime().exec("c:\\windows\\System32\\calcc.exe");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Erro:" + ex, "Atenção", 0);
			}
		} else if (e.getSource() == sobre) {
			JOptionPane.showMessageDialog(null,
					"Desenvolvido por " + "CodeFleck\ndanielfleck@live.com.br\n" + "Fone: (51) 9558 9410");
		}
	}

}
