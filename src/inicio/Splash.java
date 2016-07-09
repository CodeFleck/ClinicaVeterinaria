package inicio;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class Splash extends JWindow {

	private ImageIcon img = new ImageIcon("./imagens/logo.png");
	private JLabel lbimagem = new JLabel(img);
	private JProgressBar barra = new JProgressBar();

	public Splash() {
		setSize(475, 325);
		setLocationRelativeTo(null);
		setLayout(null);

		barra.setBounds(50, 310, 375, 5);
		add(barra);
		lbimagem.setBounds(40, 50, 400, 200);
		add(lbimagem);

		setVisible(true);

		try {
			int cont = 0;
			while (cont <= 100) {
				Thread.sleep(200);
				cont += 10;
				barra.setValue(cont);
			}

			Autenticacao a = new Autenticacao();
			a.setVisible(true);


		} catch (Exception ex) {

		}

	}

	public static void main(String[] args) {

		Splash spl = new Splash();
		spl.setVisible(true);
		spl.dispose();
	}

}
