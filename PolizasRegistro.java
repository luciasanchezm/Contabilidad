import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PolizasRegistro extends JPanel {
	
	private TextFieldNumeros CuentaTxt, SaldoTxt;
	private TextFieldLetras NombreTxt;
	JButton BtnGrabar, BtnLimpiar;
	
	public PolizasRegistro() {
		super();
		HazInterfaz();
	}
	public void HazInterfaz() {
		CuentaTxt = new TextFieldNumeros(false);
		SaldoTxt  = new TextFieldNumeros(true);
		NombreTxt = new TextFieldLetras();
		BtnGrabar = new JButton("Grabar");
		BtnLimpiar = new JButton("Limpiar");
		
		CuentaTxt.setLongitud(6); CuentaTxt.setSiguiente(NombreTxt);
		NombreTxt.setLongitud(30); NombreTxt.setSiguiente(SaldoTxt);
		SaldoTxt.setSiguiente(BtnGrabar);
		
		setLayout(new GridLayout(0,1,10,10));
		
		//Utilice paneles auxiliares para que se vea centrado y bonito
		JPanel Aux1 = new JPanel();
		JLabel CuentaLbl = new JLabel("Cuenta"); CuentaLbl.setFont(new Font ("Times New Roman", Font.PLAIN, 20));
		Aux1.add(CuentaLbl); Aux1.add(CuentaTxt);
		
		JPanel Aux2 = new JPanel();
		JLabel NombreLbl = new JLabel("Nombre"); NombreLbl.setFont(new Font ("Times New Roman", Font.PLAIN, 20));
		Aux2.add(NombreLbl); Aux2.add(NombreTxt);
		
		JPanel Aux3 = new JPanel();
		JLabel SaldoLbl = new JLabel("Saldo"); SaldoLbl.setFont(new Font ("Times New Roman", Font.PLAIN, 20));
		Aux3.add(SaldoLbl); Aux3.add(SaldoTxt);
		
		JPanel Aux4 = new JPanel();
		Aux4.add(BtnGrabar); Aux4.add(BtnLimpiar);
		
		JLabel Titulo = new JLabel("REGISTRO DE CUENTAS"); Titulo.setFont(new Font ("Times New Roman", Font.BOLD, 20));
		Titulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(Titulo); add(Aux1); add(Aux2); add(Aux3); add(Aux4);
	}
	public void SetFocusReg() {
		CuentaTxt.grabFocus();
	}
	public void setControlador(PolizasControladorCatalogo C) {
		BtnGrabar.addActionListener(C);
		BtnLimpiar.addActionListener(C);
	}
	public String getCuentaTxt() {
		return CuentaTxt.getText();
	}
	public String getNombreTxt() {
		return NombreTxt.getText();
	}
	public String getSaldoTxt() {
		return SaldoTxt.getText();
	}
	public void setCuentaTxt(String Texto) {
		CuentaTxt.setText(Texto);
	}
	public void setNombreTxt(String Texto) {
		NombreTxt.setText(Texto);
	}
	public void setSaldoTxt(String Texto) {
		SaldoTxt.setText(Texto);
	}
}
