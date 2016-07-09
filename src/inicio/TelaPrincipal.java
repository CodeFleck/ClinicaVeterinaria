package inicio;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import ferramentas.Barra;
import ferramentas.Menu;

import javax.swing.JDesktopPane; //painel de fundo das janelas
import java.awt.Color; //cores

public class TelaPrincipal extends JFrame{
    
    private BorderLayout layout = new BorderLayout();
    private JDesktopPane painelfundo = new JDesktopPane();
    private Color cinza = Color.gray;
    private Menu menu = new Menu();
    private Barra barra = new Barra();
    
    
    //construtor da janela principal
    public TelaPrincipal(){
        setExtendedState(JFrame.MAXIMIZED_BOTH); //maximizada
        setTitle("Bicho Exclusivo - Versão 1.0.0");
        setDefaultCloseOperation(3); //ao fechar esta janela encerra o sistema
        setLayout(layout);
        setJMenuBar(menu);
            
            add(painelfundo, layout.CENTER);
            add(barra, layout.NORTH);
            
            painelfundo.setBackground(cinza);
            
            
        
    }
    
}


