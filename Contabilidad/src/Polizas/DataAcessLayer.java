package Polizas;

import java.io.*;
import java.util.Vector;

import javax.swing.JOptionPane;

public class DataAcessLayer {
	
	private File FCuentas, FPolizas, FIndex;
	private RandomAccessFile ArchCuentas, ArchPolizas, ArchIndex;
	private int NoPoliza, UltimaPoliza;
	
	public DataAcessLayer(){
		if(!Abrir()){
			System.out.println("Error al intentar abrir el archivo");
			return;
		}
		try {		UltimaPoliza = NoPoliza= (int)ArchPolizas.length()/18;		
		} catch (IOException e) {
			
		}
	}
	private boolean Abrir() {
		FCuentas=new File("CUENTAS.DAT");
		FPolizas=new File("POLIZAS.DAT");
		FIndex = new File("INDEX.DAT");
		try {
		ArchCuentas=new RandomAccessFile(FCuentas,"rw");
		ArchPolizas=new RandomAccessFile(FPolizas,"rw");
		ArchIndex = new RandomAccessFile(FIndex, "rw");
		} catch (IOException E){
			return false;
		}
		return true;
	}
	public void GrabarCuenta(String Cuenta, String Nombre, float Saldo) throws IOException {
		//GRABAR CUENTA NUEVA
		ArchCuentas.seek(ArchCuentas.length());
		ArchCuentas.writeUTF(Cuenta);										//NoCuenta	String 	6  + 2
		ArchCuentas.writeUTF(Nombre);										//NOMBRE	String 	30 + 2
		ArchCuentas.writeFloat(Saldo);										//SALDO		float	4
		ArchCuentas.writeFloat(0);											//CARGO		float	4
		ArchCuentas.writeFloat(0);											//ABONO		float	4
		ArchCuentas.writeChar('A'); //ESTADO: A-Activa	B-Baja				//ESTADO	char	2		TOTAL: 54 bytes

		GrabarIndexado(Cuenta);
	}
	public void GrabarIndexado(String Cuenta) throws IOException {
		//GRABAR EN INDEXADO
		int NoReg = (int) (ArchCuentas.length()/54);
		ArchIndex.seek(ArchIndex.length());
		ArchIndex.writeUTF(Cuenta);											//NoCuenta	String	6 + 2
		ArchIndex.writeInt(NoReg);											//NoRegistro int	4		TOTAL: 12 bytes

		OrdenarIndex();
	}
	public void GrabarPoliza(int NoPoliza, String Cuenta, float Importe, char Tipo) throws IOException {
		//Grabar en archivo PÓLIZAS
		ArchPolizas.seek(ArchPolizas.length());
		ArchPolizas.writeInt(NoPoliza);								//NoPoliza		int		4
		ArchPolizas.writeUTF(Cuenta);								//Cuenta		String	6 + 2
		ArchPolizas.writeFloat(Importe);							//Importe		float	4
		ArchPolizas.writeChar(Tipo);								//Tipo			char	2		TOTAL: 18 bytes
	}
	public boolean Afectar() throws IOException {
		int length = (int) (ArchPolizas.length()/18);
		String Cuenta;
		float ImportePolizas;
		char Tipo;
		
		for (int i = UltimaPoliza; i < length ; i++) {
			//Leer archivo de PÓLIZAS
			ArchPolizas.seek(i*18 + 4);
			Cuenta = ArchPolizas.readUTF();
			ImportePolizas = ArchPolizas.readFloat();
			Tipo = ArchPolizas.readChar();
			
			//Afectar sub-sub-cuenta
			Afectar(Cuenta, ImportePolizas, Tipo);
			
			//Afectar sub-cuenta
			Afectar(Cuenta.substring(0, 4), ImportePolizas, Tipo);
			
			//Afectar cuenta
			Afectar(Cuenta.substring(0,2), ImportePolizas, Tipo);
		}
		UltimaPoliza = length;
		return true;
	}
	public void Afectar(String Cuenta, float ImportePolizas, char Tipo) throws IOException {
		int NoReg;
		float ImporteCuenta;
		
		//Encontrar el no de registro
		NoReg = BusquedaBinaria(Cuenta);
		
		//Afectar el archivo de CUENTAS
		if(Tipo == 'A' || Tipo == 'a') {	//Es ABONO
			ArchCuentas.seek((NoReg-1)*54+48);
		}
		else 				//Es CARGO
			ArchCuentas.seek((NoReg-1)*54+44);
		
			//Afectar cargos o abonos
		ImporteCuenta = ArchCuentas.readFloat();
		ArchCuentas.seek(ArchCuentas.getFilePointer()-4);
		ArchCuentas.writeFloat(ImporteCuenta + ImportePolizas);
	}
	public Vector InicializaTabla() throws IOException {
		int longitud = (int) (ArchCuentas.length()/54);
		if(longitud==0)
			return null;
		Vector<String> fila;
		Vector<Vector> filas = new Vector<Vector>();
		
		String Cuenta, Nombre;
		float Saldo, Abonos, Cargos;
		char Estado;
		
		for(int i = 0 ; i< longitud ; i++) {
			fila = new Vector<String>();
			
			ArchCuentas.seek(i*54);
			
			Cuenta = ArchCuentas.readUTF();
			Nombre = ArchCuentas.readUTF().trim();
			Saldo = ArchCuentas.readFloat();
			Abonos = ArchCuentas.readFloat();
			Cargos = ArchCuentas.readFloat();
			Estado = ArchCuentas.readChar();
			
			fila.add(Cuenta);
			fila.add(Nombre);
			fila.add(Saldo+"");
			fila.add(Abonos+"");
			fila.add(Cargos+"");
			fila.add(Estado+"");
			filas.add(fila);
		}
		return filas;
	}
	public void ModificarCuenta(int pos, String NvoNombre) throws IOException {
		ArchCuentas.seek((pos-1)*54 + 8);
		ArchCuentas.writeUTF(NvoNombre);
	}
	public boolean BajaCuentas(String Cuenta) throws IOException {
		int pos = BusquedaBinaria(Cuenta);
		if(pos == 0)										//No se encontró la cuenta
			return false;
		
		int option = JOptionPane.showConfirmDialog(null, "¿Seguro que desea dar de baja?");
		if(option!=0)
			return false;
		
		ArchCuentas.seek((pos-1)*54 + 52);
		ArchCuentas.writeChar('B');
		
		return true;
	}
	private void OrdenarIndex() throws IOException {
		//Ordenar el archivo indexado por cuenta
		String Cuentai, Cuentaj;
		int Regi, Regj;
		int longitud = (int) (ArchIndex.length()/12);
		for (int i = 0; i < longitud - 1; i++) {
			for (int j = i+1 ; j < longitud ; j++) {
				ArchIndex.seek(i*12);
				Cuentai = ArchIndex.readUTF();
				Regi = ArchIndex.readInt();
				ArchIndex.seek(j*12);
				Cuentaj = ArchIndex.readUTF();
				Regj = ArchIndex.readInt();
				if(Cuentai.compareTo(Cuentaj)>0) {
					ArchIndex.seek(i*12);
					ArchIndex.writeUTF(Cuentaj);
					ArchIndex.writeInt(Regj);

					ArchIndex.seek(j*12);
					ArchIndex.writeUTF(Cuentai);
					ArchIndex.writeInt(Regi);
				}
			}
		}
	}
	public int BusquedaBinaria(String Cuenta) throws IOException {
		int LimI=0,  LimS=(int)ArchIndex.length()/12-1, Centro=0;
		Cuenta=Rutinas.PonCerosDerecha(Cuenta, 6);
		String CuentaC;
		int RegC;
		while (LimI<=LimS){
			Centro=(LimI+LimS)/2;
			
			ArchIndex.seek(Centro*12);
			CuentaC = ArchIndex.readUTF();
			RegC    = ArchIndex.readInt();
			
			if(Cuenta.equals(CuentaC)) {
				ArchCuentas.seek((RegC-1)*54 + 52);
				char Estado = ArchCuentas.readChar();
				if(Estado=='B')
					return 0;
				else
					return RegC;
			}
			if(Cuenta.compareTo(CuentaC)>0)
				LimI=Centro+1;
			else
				LimS=Centro-1;
		}
		return 0;
	}
	public int getNoPoliza() {
		return NoPoliza;
	}
	public int getUltimaPoliza() {
		return UltimaPoliza;
	}
	
	public void ImprimirArchivos() throws IOException {
		int length;
		System.out.println("ARCHIVO DE PÓLIZAS");
		length = (int) ArchPolizas.length()/18;
		ArchPolizas.seek(0);
		for (int i = 0; i < length ; i++) {
			System.out.println(ArchPolizas.readInt() + "\t|\t" + ArchPolizas.readUTF() + "\t|\t" + ArchPolizas.readFloat() + "\t|\t" + ArchPolizas.readChar());
		}
		System.out.println("ARCHIVO CUENTAS");
		length = (int) ArchCuentas.length()/54;
		ArchCuentas.seek(0);
		for (int i = 0; i < length ; i++) {
			System.out.println(ArchCuentas.readUTF() + "\t|\t" + ArchCuentas.readUTF() + "\t|\t" + Rutinas.PonBlancos(ArchCuentas.readFloat()+"", 10) + "\t\t|\t" + ArchCuentas.readFloat() + "\t|\t" + ArchCuentas.readFloat() + "\t|\t" + ArchCuentas.readChar());
		}
		System.out.println("ARCHIVO INDEXADO");
		length = (int) ArchIndex.length()/12;
		ArchIndex.seek(0);
		for (int i = 0; i < length ; i++) {
			System.out.println(ArchIndex.readUTF() + "\t|\t" + ArchIndex.readInt());
		}
	}
}
