import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class PolizasConsulta extends JPanel {
	
	JTable Tabla;
	JScrollPane TablaScroll;
	Vector<Vector> filas;
	
	public PolizasConsulta() {
		super();
		HazInterfaz();
	}
	public void HazInterfaz() {
		Vector<String> columnas = new Vector<String>();
		filas = new Vector<Vector>();
		columnas.add("Cuenta"); columnas.add("Nombre"); columnas.add("Saldo"); columnas.add("Cargos");
		columnas.add("Abonos"); columnas.add("Estado");
		
		
		Tabla = new JTable(filas, columnas);
		Tabla.setEnabled(false);
		TablaScroll = new JScrollPane(Tabla);
		add(TablaScroll);
	}
	public void addFila(Vector<String> f) {
		filas.add(f);
		System.out.println("1");
		try{		SwingUtilities.updateComponentTreeUI(TablaScroll);		}catch(Exception E) {System.out.println("JAJA");}	//AQUÍ LANZA EL ERROR
		System.out.println("2");
	}
	public Vector BuscarFila(String Buscar) {
		String CuentaAux;
		for (int i = 0; i < filas.size(); i++) {
			CuentaAux = (String) filas.get(i).elementAt(0);
			if(CuentaAux.compareTo(Buscar)==0)
				return filas.get(i);
		}
		return null;
	}
}
