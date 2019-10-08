package Polizas;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class VistaPrincipal extends JFrame {
	
	private JTabbedPane FramePrincipal;
	private VistaCatalogo Catalogo;
	VistaPolizas Polizas;
	
	public VistaPrincipal(VistaCatalogo Catalogo) {
		super("CONTABILIDAD");
		//Edit Frame
		setSize(600, 600); setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		FramePrincipal = new JTabbedPane();
		
		setCatalogo(Catalogo);
	}
	public void setCatalogo(VistaCatalogo Catalogo) {
		this.Catalogo = Catalogo;
		Polizas = new VistaPolizas();
		FramePrincipal.add(Rutinas.PonBlancos("CATÁLOGO", 75), Catalogo);
		FramePrincipal.add(Rutinas.PonBlancos("PÓLIZAS", 75), Polizas);
		add(FramePrincipal);
		setVisible(true);
		Catalogo.update(Catalogo.getGraphics());
		FramePrincipal.setFocusable(true);
		setFocus();
	}
	public void setFocus() {
		Catalogo.setFocus();
		Polizas.setFocus();
	}
	public void setControlador(ControladorPolizas CP, ControladorCatalogo CC) {
		Catalogo.setControlador(CC);
		Polizas.setControlador(CP);
	}
	public void RemoveFilas() {
		Catalogo.RemoveFilas();
	}
	public void setResultadoAceptar(boolean Band, Vector V) {
		if(Band)
			Polizas.addTabla(V);
		else
			JOptionPane.showMessageDialog(this, "Error");
	}
	public void setResultadoGrabar(boolean Band) {
		String Texto;
		if(Band) {
			Texto = "Grabado exitoso";
			Polizas.LimpiarTabla();
		}
		else
			Texto = "Error al intentar grabar";
		JOptionPane.showMessageDialog(this, Texto);
	}
	public void setResultadoAfectar(boolean Band) {
		if(Band)
			JOptionPane.showMessageDialog(this, "Afectación exitosa");
		else
			JOptionPane.showMessageDialog(this, "No hay nuevas pólizas");
	}
	public void setResultadoEditar(boolean Band) {
		if(Band) {
			JOptionPane.showMessageDialog(this, "Editado exitoso");
			Polizas.UpdateTabla();
		}
		else
			JOptionPane.showMessageDialog(this, "Faltan datos");
	}
	
	//MÉTODOS GETTERS Y SETTERS
	public String PolizasgetSSCuentaTxt() {
		return Polizas.getSSCuentaTxt();
	}
	public String PolizasgetImporteTxt() {
		return Polizas.getImporteTxt();
	}
	public void PolizassetSSCuentaTxt(String Texto) {
		Polizas.setSSCuentaTxt(Texto);
	}
	public void PolizassetImporteTxt(String Texto) {
		Polizas.setImporteTxt(Texto);
	}
	public void PolizasAddLista(Vector V) {
		Polizas.addTabla(V);
	}
	public Vector getTablaContent() {
		return Polizas.getTablaContent();
	}
	public Vector mostrarFila(JButton Btn) {
		return Polizas.mostrarFila(Btn);
	}
}
