package Polizas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;

public class VistaPolizas extends JPanel {
	
	private TextFieldNumeros SSCuentaTxt, ImporteTxt;
	JButton BtnAceptar, BtnGrabar, BtnAfectar;
	JRadioButton rbtnCargo, rbtnAbono;
	
	JScrollPane TablaScroll;
	JPanel PanelBotones;
	private JTable Tabla;
	private Vector filas;
	Vector<JButton> botones;
	
	public VistaPolizas() {
		super();
		HazInterfaz();
		
	}
	public void HazInterfaz() {
		SSCuentaTxt = new TextFieldNumeros(6, false);
		ImporteTxt = new TextFieldNumeros(8, true);
		BtnAceptar = new JButton("Aceptar");
		BtnGrabar = new JButton("Grabar");
		BtnAfectar = new JButton("Afectar");
		rbtnCargo = new JRadioButton("Cargo");
		rbtnAbono = new JRadioButton("Abono");
		
		SSCuentaTxt.setSiguiente(ImporteTxt);
		ImporteTxt.setSiguiente(BtnAceptar);
		
		setLayout(new GridLayout(0,1));
		
		JPanel AuxGeneral = new JPanel();
		
		JPanel Aux1 = new JPanel();
		JLabel SSCuentaLbl = new JLabel(Rutinas.PonBlancos("Sub-sub-cuenta", 15)); SSCuentaLbl.setFont(new Font ("Times New Roman", Font.PLAIN, 20));
		Aux1.add(SSCuentaLbl); Aux1.add(SSCuentaTxt);
		
		JPanel Aux2 = new JPanel();
		JLabel ImporteLbl = new JLabel(Rutinas.PonBlancos("Importe", 17)); ImporteLbl.setFont(new Font ("Times New Roman", Font.PLAIN, 20));
		Aux2.add(ImporteLbl); Aux2.add(ImporteTxt);
		
		JPanel Aux3 = new JPanel();
		ButtonGroup radioBtns = new ButtonGroup();
		radioBtns.add(rbtnCargo);
		radioBtns.add(rbtnAbono);
		rbtnAbono.setSelected(true);
		Aux3.add(rbtnAbono);
		Aux3.add(rbtnCargo);
		
		JPanel Aux4 = new JPanel();
		Aux4.add(BtnAceptar); Aux4.add(BtnGrabar); Aux4.add(BtnAfectar);
		
		JLabel Titulo = new JLabel("CAPTURA DE PÓLIZAS"); Titulo.setFont(new Font ("Times New Roman", Font.BOLD, 20));
		Titulo.setHorizontalAlignment(SwingConstants.CENTER);
		AuxGeneral.add(Titulo); AuxGeneral.add(Aux1); AuxGeneral.add(Aux2); AuxGeneral.add(Aux3); AuxGeneral.add(Aux4);
		add(AuxGeneral);
		
//Panel de la lista
		//Inicializar atributos;
		filas = new Vector();
		botones = new Vector<JButton>();
		PanelBotones = new JPanel();
		JPanel PanelTabla = new JPanel();
		Vector columnas = new Vector();
		Vector fila = new Vector();
		
		//Añadir las columnas y filas
		columnas.addElement("NO. DE PÓLIZA"); columnas.addElement("CUENTA"); columnas.addElement("IMPORTE"); columnas.addElement("TIPO");
		fila.add("NO. DE PÓLIZA"); fila.add("CUENTA"); fila.add("IMPORTE"); fila.add("TIPO"); 
		filas.add(fila);
		
		//Editar tabla
		Tabla = new JTable(filas, columnas);
		Tabla.setFont(new Font("Arial", Font.PLAIN, 15));
		Tabla.setRowHeight(25); Tabla.setEnabled(false);
		
		//Añadir a paneles
		PanelTabla.setLayout(new BorderLayout());
		PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.Y_AXIS));
		PanelBotones.setPreferredSize(new Dimension(50,3));
			//Esta parte es para que los botones queden alineados
		JButton B = new JButton("E");B.setFont(new Font("Arial", Font.PLAIN, 12));
		B.setEnabled(false);
		PanelBotones.add(B);
		
		PanelTabla.add(Tabla); PanelTabla.add(PanelBotones, BorderLayout.EAST);
		PanelTabla.setBounds(0, 200, 550, 300);
		
		TablaScroll = new JScrollPane(PanelTabla);
		add(TablaScroll);
	}
	public void setFocus() {
		SSCuentaTxt.grabFocus();
	}
	public void addTabla(Vector V) {
		filas.add(V);
		CrearBoton();
	}
	public void CrearBoton() {
		JButton Btn = new JButton("E"); Btn.setFont(new Font("Arial", Font.PLAIN, 12));
		Btn.addActionListener((ActionListener)BtnAceptar.getActionListeners()[0]);
		botones.add(Btn);
		PanelBotones.add(Btn);
		SwingUtilities.updateComponentTreeUI(TablaScroll);
	}
	public void setControlador(ControladorPolizas C) {
		BtnAceptar.addActionListener(C);
		BtnGrabar.addActionListener(C);
		BtnAfectar.addActionListener(C);
	}
	public Vector getTablaContent() {
		return filas;
	}
	public void UpdateTabla() {
		SwingUtilities.updateComponentTreeUI(TablaScroll);
	}
	public Vector mostrarFila(JButton Btn) {
		int i;
		for (i = 0; i < botones.size() ; i++) {
			if(botones.elementAt(i)==Btn) {
				i++;
				break;
			}
		}
		
		//MOSTRAR VECTOR
		Vector fila = (Vector) filas.elementAt(i);
		String Cuenta = (String) fila.elementAt(1);
		float Importe = (float) fila.elementAt(2);
		char Tipo = (char) fila.elementAt(3);
		SSCuentaTxt.setText(Cuenta);
		ImporteTxt.setText(Importe+"");
		
		return (Vector) filas.elementAt(i);
	}
	public void LimpiarTabla() {
		Tabla.removeAll();
		filas.removeAllElements();
		botones.removeAllElements();
		PanelBotones.removeAll();
		Vector fila = new Vector();
		fila.add("NO. DE PÓLIZA"); fila.add("CUENTA"); fila.add("IMPORTE"); fila.add("TIPO"); 
		filas.add(fila);
		JButton B = new JButton("E");B.setFont(new Font("Arial", Font.PLAIN, 12));
		B.setEnabled(false);
		PanelBotones.add(B);
		SwingUtilities.updateComponentTreeUI(TablaScroll);
		
	}
	
	//MÉTODOS GETTERS AND SETTERS
	public String getSSCuentaTxt() {
		return SSCuentaTxt.getText();
	}
	public String getImporteTxt() {
		return ImporteTxt.getText();
	}
	public void setSSCuentaTxt(String Texto) {
		SSCuentaTxt.setText(Texto);
	}
	public void setImporteTxt(String Texto) {
		ImporteTxt.setText(Texto);
	}
}
