package Polizas;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class TextField extends JTextField implements FocusListener, KeyListener {
	
	private Border raised;
	private Border lowered;
	protected int Longitud;
	private Component Siguiente;
	
	public TextField(int Longitud) {
		super(30);
		raised = BorderFactory.createRaisedBevelBorder();
		lowered = BorderFactory.createLoweredBevelBorder();
		this.Longitud = Longitud;
		setSelectionColor(new Color(192, 192, 255));
		setFont(new Font("Times New Roman", Font.PLAIN, 20)); 
		setBorder(raised);
		HazEscuchas();
	}
	
	public void HazEscuchas() {
		addFocusListener(this);
		addKeyListener(this);
	}
	
	public void setSiguiente(Component Siguiente) {
		this.Siguiente=Siguiente;
	}
	
	public void focusGained(FocusEvent Evt) {
		setBackground(new Color(230, 230, 230));
		setBorder(lowered);
	}

	public void focusLost(FocusEvent Evt) {
		setBackground(new Color(255, 255, 255));
		setBorder(raised);
	}
	
	public void keyPressed(KeyEvent Evt) {}

	public void keyReleased(KeyEvent Evt) {}

	public void keyTyped(KeyEvent Evt) {
		if(Siguiente!= null && Evt.getKeyChar() == KeyEvent.VK_ENTER) {
			Siguiente.requestFocus();
			
			if(Siguiente instanceof JButton) {
				JButton Aux = (JButton) Siguiente;
				Aux.doClick();
			}
			return;
		}
	}
}
