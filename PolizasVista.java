import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class PolizasVista extends JFrame {
	
	private JTabbedPane FramePrincipal;
	private PolizasVistaCatalogo Catalogo;
	PolizasPolizas Polizas;
	
	public PolizasVista() {
		super("CONTABILIDAD");
		//Edit Frame
		setSize(600, 600); setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		FramePrincipal = new JTabbedPane();
	}
	public void setCatalogo(PolizasVistaCatalogo Catalogo) {
		this.Catalogo = Catalogo;
		Polizas = new PolizasPolizas();
		FramePrincipal.add(Rutinas.PonBlancos("CATÁLOGO", 75), Catalogo);
		FramePrincipal.add(Rutinas.PonBlancos("PÓLIZAS", 75), Polizas);
		add(FramePrincipal);
		setVisible(true);
		setFocus();
	}
	public void setFocus() {
		Catalogo.setFocus();
		Polizas.setFocus();
	}
	public void setControlador(PolizasControlador CP, PolizasControladorCatalogo CC) {
		Catalogo.setControlador(CC);
		Polizas.setControlador(CP);
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
	public void setResultadoAfectar() {
		JOptionPane.showMessageDialog(this, "Afectación exitosa");
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
	public String PolizasgetTipoTxt() {
		return Polizas.getTipoTxt();
	}
	public void PolizassetSSCuentaTxt(String Texto) {
		Polizas.setSSCuentaTxt(Texto);
	}
	public void PolizassetImporteTxt(String Texto) {
		Polizas.setImporteTxt(Texto);
	}
	public void PolizassetTipoTxt(String Texto) {
		Polizas.setTipoTxt(Texto);
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
