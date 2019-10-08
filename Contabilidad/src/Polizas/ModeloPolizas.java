package Polizas;

import java.io.*;
import java.util.Vector;

public class ModeloPolizas {
	
	private DataAcessLayer Archivos;
	int NoPoliza, UltimaPoliza;
	boolean BandGrabar;
	
	public ModeloPolizas(){
		Archivos = new DataAcessLayer();
		UltimaPoliza = Archivos.getUltimaPoliza();
		NoPoliza = Archivos.getNoPoliza();
		BandGrabar = false;
	}

	public int AgregarTabla(String Cuenta, float Importe, char Tipo){
		int BusquedaBinaria = 0;
		try {
			BusquedaBinaria = Archivos.BusquedaBinaria(Cuenta);
		} catch (IOException e) {}
		
		if(BusquedaBinaria==0)
			return 0;
		if(!VerificarCuenta(Cuenta))
			return 0;
		if(Importe <=0)
			return 0;
		
		//Si llega aquí, significa que los valores son válidos y la cuenta existe
		NoPoliza++;
		
		return NoPoliza;
	}
	public boolean VerificarCuenta(String Cuenta) {
		if(Cuenta.length()!=6)
			return false;
		
		String cuenta1, cuenta2, cuenta3, CuentaAux;
		
		//Separarla por cuentas
		cuenta1=Cuenta.substring(0,2);
		cuenta2=Cuenta.substring(2,4);
		cuenta3=Cuenta.substring(4,6);
		
		if(cuenta1.compareTo("00")==0 || cuenta2.compareTo("00")==0 || cuenta3.compareTo("00")==0)
			return false;
		return true;
	}
	public boolean VerificarGrabar(Vector V) {
		Vector fila;
		float Abonos = 0, Cargos = 0, Importe;
		String ImporteS;
		int size = V.size();
		for (int i = 1; i < size ; i++) {
			fila = (Vector) V.elementAt(i);
			ImporteS = fila.elementAt(2).toString();
			Importe = Float.parseFloat(ImporteS);
			if((char)fila.elementAt(3)=='A')
				Abonos += Importe;
			else
				Cargos += Importe;
		}
		if(Abonos == Cargos)
			return true;
		return false;
	}
	public boolean Grabar(Vector filas) {
		boolean Band = VerificarGrabar(filas);
		if(!Band)
			return false;
		//GRABAR EN ARCHIVO PÓLIZAS
		Vector fila;
		String NoPolizaS, Cuenta, ImporteS, TipoS;
		int NoPoliza;
		float Importe;
		char Tipo;
		int size = filas.size();
		for (int i = 1; i < size ; i++) {
			fila = (Vector) filas.elementAt(i);
			//Leer datos
			NoPoliza =  (int) fila.elementAt(0);
			Cuenta  = (String) fila.elementAt(1);
			Importe =  (float) fila.elementAt(2);
			Tipo = (char) fila.elementAt(3);
			
			try {
				Archivos.GrabarPoliza(NoPoliza, Cuenta, Importe, Tipo);
			} catch (IOException e) {}
		}
		BandGrabar=true;
		return true;
	}
	public boolean Afectar() {
		if(!BandGrabar)
			return false;
		
		try {
			Archivos.Afectar();
		} catch (IOException e) {}
		
		BandGrabar=false;
		return true;
	}
	public void ImprimirArchivos() {
		try {
			Archivos.ImprimirArchivos();
		} catch (IOException e) {}
	}
}