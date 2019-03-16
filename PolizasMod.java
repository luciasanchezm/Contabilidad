import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PolizasMod extends JPanel {
	
	private TextFieldNumeros CuentaTxt;
	private TextFieldLetras NvoNombreTxt;
	JButton BtnModificar;
	
	public PolizasMod() {
		super();
		HazInterfaz();
	}
	public void HazInterfaz() {
		CuentaTxt = new TextFieldNumeros(false);
		NvoNombreTxt = new TextFieldLetras();
		BtnModificar = new JButton("Modificar");
		CuentaTxt.setLongitud(6);
		
		CuentaTxt.setSiguiente(NvoNombreTxt);
		NvoNombreTxt.setSiguiente(BtnModificar);
		
		setLayout(new GridLayout(0,1));
		
		//Paneles auxiliares para que se vea centrado y bonito
		JPanel AuxGeneral = new JPanel();
		
		JPanel Aux1 = new JPanel();
		JLabel CuentaLbl = new JLabel("Cuenta"); CuentaLbl.setFont(new Font ("Times New Roman", Font.PLAIN, 20));
		Aux1.add(CuentaLbl); Aux1.add(CuentaTxt);
		
		JPanel Aux2 = new JPanel();
		JLabel NvoNombreLbl = new JLabel("Nuevo nombre:"); NvoNombreLbl.setFont(new Font ("Times New Roman", Font.PLAIN, 20));
		Aux2.add(NvoNombreLbl); Aux2.add(NvoNombreTxt);
		
		JPanel Aux3 = new JPanel();
		Aux3.add(BtnModificar);
		
		JLabel Titulo = new JLabel("MODIFICACIÓN DE CUENTAS"); Titulo.setFont(new Font ("Times New Roman", Font.BOLD, 20));
		Titulo.setHorizontalAlignment(SwingConstants.CENTER);
		AuxGeneral.add(Titulo); AuxGeneral.add(Aux1); AuxGeneral.add(Aux2); AuxGeneral.add(Aux3); add(AuxGeneral);
	}
	public void setFocusMod() {
		CuentaTxt.grabFocus();
	}
	public void setControlador(PolizasControladorCatalogo C) {
		BtnModificar.addActionListener((ActionListener) C);
	}
	public String getCuentaTxt() {
		return CuentaTxt.getText();
	}
	public void setCuentaTxt(String Texto) {
		CuentaTxt.setText(Texto);
	}
	public String getNvoNombreTxt() {
		return NvoNombreTxt.getText();
	}
	public void setNvoNombreTxt(String Texto) {
		NvoNombreTxt.setText(Texto);
	}
}