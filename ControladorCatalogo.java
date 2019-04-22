package Polizas;

import java.awt.event.*;
import java.io.IOException;
import java.util.Vector;
import java.awt.*;
import javax.swing.*;

public class ControladorCatalogo implements ActionListener{
	private VistaCatalogo Vista; 
	private ModeloCatalogo Modelo;
	
	public ControladorCatalogo(VistaCatalogo Vista, ModeloCatalogo Modelo) {
		this.Vista = Vista;
		this.Modelo = Modelo;
	}
	
	public void InicializarConsulta() {
		Vista.RemoveFilas();
		Vector filas = null;
		try {
			filas = Modelo.InicializaTabla();
		} catch (IOException E) {System.out.println(E + "\tError en controlador catálogo");}
		if(filas==null)
			return;
		for (int i = 0; i < filas.size(); i++) {
			Vista.Consulta_addFila((Vector) filas.elementAt(i));
		}
	}

	public void actionPerformed(ActionEvent Evt) {
		if(Evt.getSource() == Vista.getR_BtnGrabar()) {
			Grabar();
			return;
		}
		if(Evt.getSource() == Vista.getR_BtnLimpiar()) {
			Limpiar();
			return;
		}
		if(Evt.getSource() == Vista.getM_BtnModificar()) {
			Modificar();
			return;
		}
		if(Evt.getSource() == Vista.getB_BtnAceptar()) {
			Baja();
		}
	}
	public void Limpiar() {
		Vista.setR_Cuenta("");
		Vista.setR_Nombre("");
		Vista.setR_Saldo("");
		Vista.setM_Cuenta("");
		Vista.setM_NvoNombre("");
		Vista.setB_Cuenta("");
		Vista.setFocus();
	}
	public void Grabar() {
		boolean Bandera = false;
		String Nombre = Vista.getR_Nombre();
		String Cuenta = Vista.getR_Cuenta();
		String SaldoAux = Vista.getR_Saldo();
		if(SaldoAux.compareTo("")==0) {
			Limpiar();
			Vista.ResultadoRegistro(Bandera);
			return;
		}
		float Saldo = Float.parseFloat(Vista.getR_Saldo());
		try {
			Bandera = Modelo.GrabarCuenta(Cuenta, Nombre, Saldo);
		} catch(Exception E) {}
		Limpiar();
		Vista.ResultadoRegistro(Bandera);
		if(Bandera) {
			//Añadir a consulta
			Vector V = new Vector();
			if(Cuenta.length()%2!=0)
				Cuenta=Rutinas.PonCerosIzq(Cuenta, Cuenta.length()+1);
			V.add(Rutinas.PonCerosDerecha(Cuenta, 6)); V.add(Nombre); V.add(Saldo); V.add(0); V.add(0); V.add('A');
			Vista.Consulta_addFila(V);
			Vista.update(Vista.getGraphics());
		}
	}
	public void Modificar() {
		boolean Band = false;
		String Cuenta = Vista.getM_Cuenta();
		String NvoNombre = Vista.getM_NvoNombre();
		try {
			Band = Modelo.ModificarCuenta(Cuenta, NvoNombre);
		} catch (IOException E) {}
		
		if(Band) {
			//MODIFICAR LA CONSULTA
			Vector<String> V = Vista.Consulta_BuscarFila(Cuenta);
			V.set(1, NvoNombre);
			Limpiar();
		}
		Vista.ResultadoModifica(Band);
	}
	public void Baja() {
		boolean Band = false;
		String Cuenta = Vista.getB_Cuenta();
		try {
			Band = Modelo.BajaCuenta(Cuenta);
		} catch (IOException E) {}
		Vista.ResultadoBaja(Band);
		Limpiar();
		if(Band) {
			//MODIFICAR LA CONSULTA
			Vector<String> V = Vista.Consulta_BuscarFila(Cuenta);
			V.set(5, "B");
		}
	}
}
