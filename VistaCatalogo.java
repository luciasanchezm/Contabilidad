package Polizas;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class VistaCatalogo extends JTabbedPane {
	
	private VistaRegistro Registro;
	private VistaModificar Mod;
	private VistaBaja Baja;
	private VistaConsulta Consulta;
	
	public VistaCatalogo(){
		super();
		HazInterfaz();
	}
	
	public void HazInterfaz() {
		Registro = new VistaRegistro();
		Mod = new VistaModificar();
		Baja = new VistaBaja();
		Consulta = new VistaConsulta();
		
		//Añadir paneles al tabbed
		add(Rutinas.PonBlancos("Registro", 20), Registro);
		add(Rutinas.PonBlancos("Modificación", 20), Mod);
		add(Rutinas.PonBlancos("Baja", 20), Baja);
		add(Rutinas.PonBlancos("Consulta", 20), Consulta);
	}
	public void setFocus() {
		Registro.SetFocusReg();	
		Mod.setFocusMod();
		Baja.setFocusBaja();
	}
	public void setControlador(ControladorCatalogo C) {
		Registro.setControlador(C);
		Mod.setControlador(C);
		Baja.setControlador(C);
	}
	public void RemoveFilas() {
		Consulta.filas.removeAllElements();
	}
	public void ResultadoRegistro(boolean Bandera) {
		String Texto;
		if(Bandera)
			Texto = "Se registró exitosamente";
		else
			Texto = "Error";
		JOptionPane.showMessageDialog(null, Texto);
	}
	public void ResultadoModifica(boolean Bandera) {
		String Texto;
		if(Bandera)
			Texto = "Se modificó exitosamente";
		else
			Texto = "Error al intentar modificar";
		JOptionPane.showMessageDialog(null, Texto);
	}
	public void ResultadoBaja(boolean Bandera) {
		String Texto;
		if(Bandera)
			Texto = "Se dio de baja exitosamente";
		else
			Texto = "No se encontró la cuenta";
		JOptionPane.showMessageDialog(null, Texto);
	}
	public JButton getR_BtnGrabar() {
		return Registro.BtnGrabar;
	}
	public JButton getR_BtnLimpiar() {
		return Registro.BtnLimpiar;
	}
	public JButton getM_BtnModificar() {
		return Mod.BtnModificar;
	}
	public JButton getB_BtnAceptar() {
		return Baja.BtnAceptar;
	}
	public String getR_Cuenta() {
		return Registro.getCuentaTxt();
	}
	public String getR_Nombre() {
		return Registro.getNombreTxt();
	}
	public String getR_Saldo() {
		return Registro.getSaldoTxt();
	}
	public void setR_Cuenta(String Texto) {
		Registro.setCuentaTxt(Texto);
	}
	public void setR_Nombre(String Texto) {
		Registro.setNombreTxt(Texto);
	}
	public void setR_Saldo(String Texto) {
		Registro.setSaldoTxt(Texto);
	}
	public String getM_Cuenta() {
		return Mod.getCuentaTxt();
	}
	public void setM_Cuenta(String Texto) {
		Mod.setCuentaTxt(Texto);
	}
	public String getM_NvoNombre() {
		return Mod.getNvoNombreTxt();
	}
	public void setM_NvoNombre(String Texto) {
		Mod.setNvoNombreTxt(Texto);
	}
	public String getB_Cuenta() {
		return Baja.getCuentaTxt();
	}
	public void setB_Cuenta(String Texto) {
		Baja.setCuentaTxt(Texto);
	}
	public void Consulta_addFila(Vector f) {
		Consulta.addFila(f);
	}
	public Vector Consulta_BuscarFila(String Buscar) {
		return Consulta.BuscarFila(Buscar);
	}
}
