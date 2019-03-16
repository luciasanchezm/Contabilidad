import java.io.*;
import java.util.Vector;

public class PolizasModeloCatalogo {
	
	File FCuentas, FIndex;
	RandomAccessFile ArchCuentas, ArchIndex;
	
	public PolizasModeloCatalogo(){
		if(!Abrir()){
			System.out.println("Error al intentar abrir el archivo");
			return;
		}
	}
	private boolean Abrir() {
		FCuentas=new File("CUENTAS.DAT");
		FIndex = new File("INDEX.DAT");
		try {
		ArchCuentas=new RandomAccessFile(FCuentas,"rw");
		ArchIndex = new RandomAccessFile(FIndex, "rw");
		} catch (IOException E){
			return false;
		}
		return true;
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

	public boolean GrabarCuenta(String Cuenta, String Nombre, float Saldo) throws IOException {
		if(Cuenta.length()%2!=0)
			Cuenta=Rutinas.PonCerosIzq(Cuenta, Cuenta.length()+1);
		Cuenta = Rutinas.PonCerosDerecha(Cuenta, 6);
		try {
		if(!Verificar(Cuenta)) {
			return false;
		}
		} catch(Exception E) {System.out.println(E);}
		if(Nombre.compareTo("")==0)
			return false;
		Nombre = Rutinas.PonBlancos(Nombre, 30);
		
		//GRABAR CUENTA NUEVA
		ArchCuentas.seek(ArchCuentas.length());
		ArchCuentas.writeUTF(Cuenta);										//NoCuenta	String 	6  + 2
		ArchCuentas.writeUTF(Nombre);										//NOMBRE	String 	30 + 2
		ArchCuentas.writeFloat(Saldo);										//SALDO		float	4
		ArchCuentas.writeFloat(0);											//CARGO		float	4
		ArchCuentas.writeFloat(0);											//ABONO		float	4
		ArchCuentas.writeChar('A'); //ESTADO: A-Activa	B-Baja				//ESTADO	char	2		TOTAL: 54 bytes
		
		
		//GRABAR EN INDEXADO
		int NoReg = (int) (ArchCuentas.length()/54);
		ArchIndex.seek(ArchIndex.length());
		ArchIndex.writeUTF(Cuenta);											//NoCuenta	String	6 + 2
		ArchIndex.writeInt(NoReg);											//NoRegistro int	4		TOTAL: 12 bytes
		
		OrdenarIndex();
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
			
		if(BusquedaBinaria(Cuenta)!=0)										//Si está repetida
			return false;
		
		if(cuenta3.compareTo("00")==0 && cuenta2.compareTo("00")==0)		//Si es la primera cuenta
			return true;
		
		//Buscar cuenta previa
		CuentaAux = cuenta1 + cuenta2;
		if(BusquedaBinaria(cuenta1)==0)										//Si no existe la cuenta previa
			return false;
		
		if(cuenta3.compareTo("00")==0)										//Si es la primera sub-cuenta
			return true;
		
		if(BusquedaBinaria(CuentaAux)==0)									//Si no encuentra la sub-cuenta
			return false;
		
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
	
	public boolean ModificarCuenta(String Cuenta, String NvoNombre) throws IOException {
		Cuenta = Rutinas.PonCerosDerecha(Cuenta, 6);
		if(NvoNombre.compareTo("")==0)
			return false;
		NvoNombre = Rutinas.PonBlancos(NvoNombre, 30);
		int pos = BusquedaBinaria(Cuenta);
		if(pos == 0)										//No se encontró la cuenta
			return false;
		
		ArchCuentas.seek((pos-1) + 8);
		ArchCuentas.writeUTF(NvoNombre);
		
		return true;
	}
	
	public boolean BajaCuenta(String Cuenta) throws IOException {
		Cuenta = Rutinas.PonCerosDerecha(Cuenta, 6);
		int pos = BusquedaBinaria(Cuenta);
		if(pos == 0)										//No se encontró la cuenta
			return false;
		
		ArchCuentas.seek(pos + 52);
		ArchCuentas.writeChar('B');
		
		return true;
	}
}