import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PolizasBaja extends JPanel {
	
	private TextFieldNumeros CuentaTxt;
	JButton BtnAceptar;
	
	public PolizasBaja() {
		super();
		HazInterfaz();
	}
	public void HazInterfaz() {
		CuentaTxt = new TextFieldNumeros(false);
		BtnAceptar = new JButton("Aceptar");
		CuentaTxt.setLongitud(6);
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
	public void setControlador(PolizasControladorCatalogo C) {
		BtnAceptar.addActionListener((ActionListener) C);
	}
	public String getCuentaTxt() {
		return CuentaTxt.getText();
	}
	public void setCuentaTxt(String Texto) {
		CuentaTxt.setText(Texto);
	}
}
