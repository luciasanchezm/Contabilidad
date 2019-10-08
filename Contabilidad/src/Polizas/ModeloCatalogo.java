package Polizas;

import java.io.*;
import java.util.Vector;

import javax.swing.JOptionPane;

public class ModeloCatalogo {
	
	private DataAcessLayer Archivos;
	
	public ModeloCatalogo(){
		Archivos = new DataAcessLayer();
	}
	public Vector InicializaTabla() throws IOException {
		return Archivos.InicializaTabla();
	}

	public boolean GrabarCuenta(String Cuenta, String Nombre, float Saldo) throws IOException {
		if(Cuenta.length()%2!=0)
			Cuenta=Rutinas.PonCerosIzq(Cuenta, Cuenta.length()+1);
		Cuenta = Rutinas.PonCerosDerecha(Cuenta, 6);
		try {
		if(!Verificar(Cuenta)) {
			return false;
		}
		} catch(Exception E) {
			System.out.println(E + "\tError en modelo catálogo");
		}
		if(Nombre.compareTo("")==0)
			return false;
		Nombre = Rutinas.PonBlancos(Nombre, 30);
		
		Archivos.GrabarCuenta(Cuenta, Nombre, Saldo);
		
		return true;
	}
	
	private boolean Verificar(String Cuenta) throws IOException {
		String cuenta1, cuenta2, cuenta3, CuentaAux;
		
		//Separarla por cuentas
		cuenta1=Cuenta.substring(0,2);
		cuenta2=Cuenta.substring(2,4);
		cuenta3=Cuenta.substring(4,6);
		
		//Verificar que sea válida
		if(cuenta1.compareTo("00")==0)										//Si tiene 0 en la primera
			return false;
		
		if(cuenta2.compareTo("00")==0 && Integer.parseInt(cuenta3)>0 )		//Si la última no es 0								 
			return false;
		
		if(Archivos.BusquedaBinaria(Cuenta)!=0)										//Si está repetida
			return false;
		
		if(cuenta3.compareTo("00")==0 && cuenta2.compareTo("00")==0)		//Si es la primera cuenta
			return true;
		
		//Buscar cuenta previa
		CuentaAux = cuenta1 + cuenta2;
		if(Archivos.BusquedaBinaria(cuenta1)<=0)										//Si no existe la cuenta previa
			return false;
		
		if(cuenta3.compareTo("00")==0)										//Si es la primera sub-cuenta
			return true;
		
		if(Archivos.BusquedaBinaria(CuentaAux)<=0)									//Si no encuentra la sub-cuenta
			return false;
		
		return true;
	}
	
	public boolean ModificarCuenta(String Cuenta, String NvoNombre) throws IOException {
		Cuenta = Rutinas.PonCerosDerecha(Cuenta, 6);
		if(NvoNombre.compareTo("")==0)
			return false;
		NvoNombre = Rutinas.PonBlancos(NvoNombre, 30);
		int pos = Archivos.BusquedaBinaria(Cuenta);
		if(pos <= 0)										//No se encontró la cuenta
			return false;
		
		int option = JOptionPane.showConfirmDialog(null, "¿Seguro que desea modificar?");
		if(option!=0)
			return false;
		
		Archivos.ModificarCuenta(pos, NvoNombre);
		
		return true;
	}
	
	public boolean BajaCuenta(String Cuenta) throws IOException {
//		Cuenta = Rutinas.PonCerosDerecha(Cuenta, 6);
		
		return Archivos.BajaCuentas(Cuenta);
	}
	public int BusquedaBinaria(String Cuenta) throws IOException {
		return Archivos.BusquedaBinaria(Cuenta);
	}
}