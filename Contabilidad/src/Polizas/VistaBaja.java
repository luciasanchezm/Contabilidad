package Polizas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VistaBaja extends JPanel {
	
	private TextFieldNumeros CuentaTxt;
	JButton BtnAceptar;
	
	public VistaBaja() {
		super();
		HazInterfaz();
	}
	public void HazInterfaz() {
		CuentaTxt = new TextFieldNumeros(6, false);
		BtnAceptar = new JButton("Aceptar");
		
		CuentaTxt.setSiguiente(BtnAceptar);
		setLayout(new GridLayout(0,1));

		//Paneles auxiliares para que se vea centrado y bonito
		JPanel AuxGeneral = new JPanel();

		JPanel Aux1 = new JPanel();
		JLabel CuentaLbl = new JLabel("Cuenta"); CuentaLbl.setFont(new Font ("Times New Roman", Font.PLAIN, 20));
		Aux1.add(CuentaLbl); Aux1.add(CuentaTxt);

		JPanel Aux2 = new JPanel();
		Aux2.add(BtnAceptar);

		JLabel Titulo = new JLabel("BAJA DE CUENTAS"); Titulo.setFont(new Font ("Times New Roman", Font.BOLD, 20));
		Titulo.setHorizontalAlignment(SwingConstants.CENTER);
		AuxGeneral.add(Titulo); AuxGeneral.add(Aux1); AuxGeneral.add(Aux2); add(AuxGeneral);
	}
	public void setFocusBaja() {
		CuentaTxt.grabFocus();
	}
	public void setControlador(ControladorCatalogo C) {
		BtnAceptar.addActionListener((ActionListener) C);
	}
	public String getCuentaTxt() {
		return CuentaTxt.getText();
	}
	public void setCuentaTxt(String Texto) {
		CuentaTxt.setText(Texto);
	}
}
