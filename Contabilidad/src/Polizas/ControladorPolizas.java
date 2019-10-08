package Polizas;

import java.awt.event.*;
import java.io.IOException;
import java.util.Vector;
import javax.swing.*;

public class ControladorPolizas implements ActionListener{
	
	VistaPrincipal Vista;
	ModeloPolizas Modelo;
	ModeloCatalogo ModeloCatalogo;
	ControladorCatalogo ControladorCatalogo;
	
	public ControladorPolizas(VistaPrincipal Vista, ModeloPolizas Modelo, ModeloCatalogo ModeloCatalogo, ControladorCatalogo ControladorCatalogo) {
		this.Vista = Vista;
		
		this.Modelo = Modelo;	
		
		this.ModeloCatalogo = ModeloCatalogo;
		
		this.ControladorCatalogo = ControladorCatalogo;
		
		Modelo.ImprimirArchivos();
	}
	
	public void setControlador(ControladorPolizas CP, ControladorCatalogo CC) {
		Vista.setControlador(CP, CC);
	}

	public void actionPerformed(ActionEvent Evt) {
		JButton Btn = (JButton)Evt.getSource();
		if(Btn == Vista.Polizas.BtnAceptar) {
			if(!BanderaEditar)
				AgregarTabla();
			else {
				BtnEditar();
				return;
			}
			Limpiar();
			Vista.setFocus();
			return;
		}
		if(Btn == Vista.Polizas.BtnGrabar) {
			GrabarArchivo();
			return;
		}
		if(Btn == Vista.Polizas.BtnAfectar) {
			Afectar();
			return;
		}
		//irse al método para botones de la tabla
		BtnEditar(Btn);
	}
	
	public void Limpiar() {
		Vista.PolizassetSSCuentaTxt("");
		Vista.PolizassetImporteTxt("");
	}
	
	public void AgregarTabla() {
		String Cuenta = Vista.PolizasgetSSCuentaTxt();
		String ImporteS = Vista.PolizasgetImporteTxt();
		char Tipo;
		Tipo = (Vista.Polizas.rbtnAbono.isSelected())? 'A' : 'B';
			
		
		if(ImporteS.compareTo("")==0) {
			Limpiar();
			Vista.setResultadoAceptar(false, null);
			return;
		}
		
		boolean Band = true;
		float Importe = Float.parseFloat(ImporteS);
		int NoPoliza = 0;
		NoPoliza = Modelo.AgregarTabla(Cuenta, Importe, Tipo);
		
		if(NoPoliza==0)
			Band=false;
		
		Vector V = new Vector();
		V.add(NoPoliza); V.add(Cuenta); V.add(Importe); V.add(Tipo);
		
		Vista.setResultadoAceptar(Band, V);
	}
	public void GrabarArchivo() {
		Vector V = Vista.getTablaContent();
		boolean Band;
		if(V.size()==1)
			Band=false;
		else
			Band = Modelo.Grabar(V);
		Vista.setResultadoGrabar(Band);
	}
	public void Afectar() {
		boolean Band = false;
		Band = Modelo.Afectar();
		Vista.setResultadoAfectar(Band);
		ControladorCatalogo.InicializarConsulta();
		SwingUtilities.updateComponentTreeUI(Vista);
	}
	
	boolean BanderaEditar = false;
	Vector fila;
	
	public void BtnEditar(JButton Btn) {
		fila = Vista.mostrarFila(Btn);
		BanderaEditar = true;
	}
	public void BtnEditar() {
		String Cuenta = Vista.PolizasgetSSCuentaTxt();
		String ImporteS = Vista.PolizasgetImporteTxt();
		char Tipo;
		if(Vista.Polizas.rbtnAbono.isSelected())
			Tipo='A';
		else
			Tipo='B';
		
		int BB = 0;
		try {
			BB = ModeloCatalogo.BusquedaBinaria(Cuenta);
		} catch (IOException e) {}
		if(BB == 0 || ImporteS.compareTo("")==0){
			Vista.setResultadoEditar(false);
			return;
		}
		float Importe = Float.parseFloat(ImporteS);
		fila.set(1, Cuenta);
		fila.set(2, Importe);
		fila.set(3, Tipo);
		
		BanderaEditar = false;
		Vista.setResultadoEditar(true);
		Limpiar();
		Vista.setFocus();
	}
}
