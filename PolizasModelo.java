import java.io.*;
import java.util.Vector;

public class PolizasModelo {
	
	File FCuentas, FPolizas, FIndex;
	RandomAccessFile ArchCuentas, ArchPolizas, ArchIndex;
	int NoPoliza;
	
	public PolizasModelo(){
		if(!Abrir()){
			System.out.println("Error al intentar abrir el archivo");
			return;
		}
		try {		NoPoliza= (int)ArchPolizas.length()/18;		} catch (IOException e) {}
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
			
			if(Cuenta.equals(CuentaC)){
				return RegC;
			}
			if(Cuenta.compareTo(CuentaC)>0)
				LimI=Centro+1;
			else
				LimS=Centro-1;
		}
		return 0;
	}
	public int AgregarTabla(String Cuenta, float Importe, char Tipo) throws IOException {

		if(BusquedaBinaria(Cuenta)==0)
			return 0;
		if(!VerificarCuenta(Cuenta))
			return 0;
		if(Importe <=0)
			return 0;
		if(Tipo!='C' && Tipo!='A')
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
			if(fila.contains('A'))
				Abonos += Importe;
			else
				Cargos += Importe;
		}
		if(Abonos == Cargos)
			return true;
		return false;
	}
	public void Grabar(Vector filas) throws IOException {
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
			
			//Grabar en archivo PÓLIZAS
			ArchPolizas.seek(ArchPolizas.length());
			ArchPolizas.writeInt(NoPoliza);								//NoPoliza		int		4
			ArchPolizas.writeUTF(Cuenta);								//Cuenta		String	6 + 2
			ArchPolizas.writeFloat(Importe);							//Importe		float	4
			ArchPolizas.writeChar(Tipo);								//Tipo			char	2		TOTAL: 18 bytes
		}
	}
	public void Afectar() throws IOException {
		int lenght = (int) (ArchPolizas.length()/18);
		int NoReg;
		String Cuenta;
		float ImportePolizas, ImporteCuenta, SaldoCuenta;
		char Tipo;
		
		for (int i = 0; i < lenght ; i++) {
			//Leer archivo de PÓLIZAS
			ArchPolizas.seek(i*18 + 4);
			Cuenta = ArchPolizas.readUTF();
			ImportePolizas = ArchPolizas.readFloat();
			Tipo = ArchPolizas.readChar();
			
			//Encontrar el no de registro
			NoReg = BusquedaBinaria(Cuenta);
			
			//Afectar el archivo de CUENTAS
				//Afectar saldo
			ArchCuentas.seek((NoReg-1)*54+40);
			SaldoCuenta = ArchCuentas.readFloat();
			ArchCuentas.seek((NoReg-1)*54+40);
			if(Tipo == 'A') {	//Es ABONO
				ArchCuentas.writeFloat(SaldoCuenta + ImportePolizas);
				ArchCuentas.seek((NoReg-1)*54+48);
			}
			else 				//Es CARGO
				ArchCuentas.writeFloat(SaldoCuenta - ImportePolizas);
			
				//Afectar cargos o abonos
			ImporteCuenta = ArchCuentas.readFloat();
			ArchCuentas.seek(ArchCuentas.getFilePointer()-4);
			ArchCuentas.writeFloat(ImporteCuenta + ImportePolizas);
		}
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