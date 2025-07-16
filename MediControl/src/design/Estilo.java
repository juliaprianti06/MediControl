
package design;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.net.URL;
import javax.swing.*;
import javax.swing.table.JTableHeader;





public class Estilo {
    //tela de cadastro
    public static void designUsuario(JPanel painel){
        painel.setBackground(new Color(248, 249, 250));
    }
    public static void cabecalho(JPanel painel){
        painel.setBackground(new Color(2, 76, 123));
       }
     
    public static void titulo(JLabel label, String texto){
        label.setText(texto);
        label.setFont(new Font("Gentium Book Plus", Font.BOLD, 17));
        label.setForeground(Color.BLACK);
         
      }
    public static void dados(JLabel label, String texto){
        label.setText(texto);
        label.setFont(new Font("Gentium Book Plus", Font.BOLD, 15));
        label.setForeground(new Color(33, 33, 33)); 
         
       }
     
    public static void estiloApp(JLabel label) {
        label.setText("MediControl");
        label.setFont(new Font("Gentium Book Plus", Font.BOLD, 18));
        label.setForeground(new Color(255, 255, 255)); 
    }
   
    public static void rodape(JPanel painel){
        painel.setBackground(new Color(248, 249, 250));
       }
    public static void botoes(JButton... botoes ){
        for (JButton b : botoes) {
            b.setFont(new Font("Gentium Book Plus", Font.BOLD, 14));
            b.setForeground (Color.WHITE);
            b.setBackground(new Color(2, 76, 123)); 
            b.setFocusPainted(false);
            b.setMargin(new Insets(7, 14, 7, 14)); 
            b.putClientProperty("JButton.buttonType", "roundRect");
            b.putClientProperty("JButton.arc", 6);
            
       }
    }
    public static void botoes(JButton botao, String texto) {
        botao.setText(texto);
        botoes(botao); 
    }
     public static void texto(JLabel label, String texto) {
        label.setText(texto);
        label.setFont(new Font("Gentium Book Plus", Font.PLAIN, 13));
        label.setForeground(new Color(33, 33, 33)); 
    }
     //tela inical
       public static void estiloAppTelaInicial(JLabel label) {
        label.setText("MediControl");
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(new Color(135, 206, 250)); 
    }
     public static void botaoMenu(JButton... botoes ){
        for (JButton b : botoes) {
            b.setFont(new Font("Segoe UI", Font.BOLD, 16));
            b.setForeground(Color.BLACK);
       }
    }
    public static void botaoMenu(JButton botao, String texto) {
        botao.setText(texto);
        botaoMenu(botao); 
    }
    public static void designTelaInicial(JPanel painel){
        painel.setBackground(new Color(214, 226, 235));
    }
    public static void designMenu(JPanel painel){    
    }
    public static void tituloTI(JLabel label, String texto){
        label.setText(texto);
        label.setFont(new Font("Gentium Book Plus", Font.BOLD, 15));
        label.setForeground(new Color(33, 33, 33));
    }
    public static void fonteTabela(JTable table, String texto){
    Font fonte = (new Font("Gentium Book Plus", Font.PLAIN, 13)); 
    table.setForeground(new Color(33, 33, 33));
    table.setFont(fonte);
    table.setRowHeight(22); 

    JTableHeader header = table.getTableHeader();
    if (header != null) {
        header.setFont(new Font(texto, Font.BOLD, 13)); 
    }
   }

}



    
