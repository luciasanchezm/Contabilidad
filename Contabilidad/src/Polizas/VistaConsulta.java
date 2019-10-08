package Polizas;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class VistaConsulta extends JPanel {
	
	JTable Tabla;
	JScrollPane TablaScroll;
	Vector<Vector> filas;
	
	public VistaConsulta() {
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
		SwingUtilities.updateComponentTreeUI(TablaScroll);
		//TablaScroll.update(TablaScroll.getGraphics());
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
