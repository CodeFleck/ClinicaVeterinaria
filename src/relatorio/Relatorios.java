package relatorio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import conexao.Conexao;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.lang.*;

public class Relatorios {
	private Conexao conexao = Conexao.getInstancia();
	private Connection retornaConexao = conexao.getConexao();
	private static JasperPrint printReport;
	private static JasperViewer view;

	// Gera Relatorio
	public void geraRelatorioCliente() throws JRException, Exception {
		// Gera o arquivo
		printReport = JasperFillManager.fillReport("./relatorios/RelClientes.jasper", null, retornaConexao);
		view = new JasperViewer(printReport);
		view.viewReport(printReport, false);

	}

	public void geraRelatorioAnimal(String nomeCli, String nomeAni, String raca, String castrado)
			throws JRException, Exception {
		// Gera o arquivo
		if (nomeCli.equals("")) {
			nomeCli = null;
		}
		if (nomeAni.equals("")) {
			nomeAni = null;
		}
		if (raca.equals("")) {
			raca = null;
		}
		if (castrado.equals("")) {
			castrado = null;
		}

		Map parametros = new HashMap();
		parametros.put("P_CLI_NOME", nomeCli);
		parametros.put("P_ANI_NOME", nomeAni);
		parametros.put("P_RACA", raca);
		parametros.put("P_CASTRADO", castrado);
		printReport = JasperFillManager.fillReport("./relatorios/RelAnimal.jasper", parametros, retornaConexao);
		view = new JasperViewer(printReport);
		view.viewReport(printReport, false);

	}

	public void geraRelatorioFornecedor() throws JRException, Exception {
		// Gera o arquivo
		printReport = JasperFillManager.fillReport("./relatorios/RelFornecedor.jasper", null, retornaConexao);
		view = new JasperViewer(printReport);
		view.viewReport(printReport, false);

	}

	public void geraRelatorioProduto() throws JRException, Exception {
		// Gera o arquivo
		printReport = JasperFillManager.fillReport("./relatorios/RelProdutos.jasper", null, retornaConexao);
		view = new JasperViewer(printReport);
		view.viewReport(printReport, false);

	}

	public void geraRelatorioVacina(String dtVencIni, String dtVencFim)
			throws JRException, Exception {
		// Gera o arquivo
		if (dtVencIni.equals("")) {
			dtVencIni = null;
			dtVencFim = null;
		}
		if ((dtVencIni != null) && (dtVencFim == null)) {
			dtVencFim = dtVencIni;
		}

		Map parametros = new HashMap();
		parametros.put("P_DT_VENC_INI", dtVencIni);
		parametros.put("P_DT_VENC_FIM", dtVencFim);
		printReport = JasperFillManager.fillReport("./relatorios/RelVacina.jasper", parametros, retornaConexao);
		view = new JasperViewer(printReport);
		view.viewReport(printReport, false);

	}
}