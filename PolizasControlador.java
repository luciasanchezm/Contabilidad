import java.awt.event.*;
import java.io.IOException;
import java.util.Vector;

import javax.swing.*;

public class PolizasControlador implements ActionListener{
	
	PolizasVista Vista; 
	PolizasVistaCatalogo VistaCatalogo;
	PolizasModelo Modelo;
	PolizasModeloCatalogo ModeloCatalogo;
	PolizasControladorCatalogo ControladorCatalogo;
	
	public PolizasControlador(PolizasVista Vista, PolizasModelo Modelo) {
		ModeloCatalogo = new PolizasModeloCatalogo();
		VistaCatalogo = new PolizasVistaCatalogo();
		
		this.Vista = Vista;
		
		this.Modelo = Modelo;
		
		Vista.setCatalogo(VistaCatalogo);
		
		ControladorCatalogo = new PolizasControladorCatalogo(VistaCatalogo, ModeloCatalogo);
		setControlador(this, ControladorCatalogo);
		
		
		try{		Modelo.ImprimirArchivos();		}catch(Exception E) {}
	}
	
	public void setControlador(PolizasControlador CP, PolizasControladorCatalogo CC) {
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
		Vista.PolizassetTipoTxt("");
	}
	
	public void AgregarTabla() {
		String Cuenta = Vista.PolizasgetSSCuentaTxt();
		String ImporteS = Vista.PolizasgetImporteTxt();
		String TipoS = Vista.PolizasgetTipoTxt();
		if(ImporteS.compareTo("")==0 || TipoS.compareTo("")==0) {
			Limpiar();
			Vista.setResultadoAceptar(false, null);
			return;
		}
		
		boolean Band = true;
		float Importe = Float.parseFloat(ImporteS);
		char Tipo = TipoS.charAt(0);
		int NoPoliza = 0;
		try {			NoPoliza = Modelo.AgregarTabla(Cuenta, Importe, Tipo);			} catch (IOException e) {}
		
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
			Band = 	Modelo.VerificarGrabar(V);
		try {		if(Band) Modelo.Grabar(V);				} catch (IOException e) {}
		
		Vista.setResultadoGrabar(Band);
	}
	public void Afectar() {
		try {		Modelo.Afectar();		} catch (IOException e) {}
		Vista.setResultadoAfectar();
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
		String TipoS = Vista.PolizasgetTipoTxt();
		int BB = 0;
		try {		BB = ModeloCatalogo.BusquedaBinaria(Cuenta);		} catch (IOException e) {}
		if(BB == 0 || ImporteS.compareTo("")==0 || (TipoS.compareTo("A")<0 && TipoS.compareTo("C")<0)) {
			Vista.setResultadoEditar(false);
			return;
		}
		float Importe = Float.parseFloat(ImporteS);
		char Tipo = TipoS.charAt(0);
		fila.set(1, Cuenta);
		fila.set(2, Importe);
		fila.set(3, Tipo);
		
		BanderaEditar = false;
		Vista.setResultadoEditar(true);
		Limpiar();
		Vista.setFocus();
	}
}
