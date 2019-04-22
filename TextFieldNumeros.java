package Polizas;

import java.awt.*;
import java.awt.event.*;

public class TextFieldNumeros extends TextField{
	private boolean Band;
	public TextFieldNumeros(int Longitud, boolean Band) {//LA BANDERA INDICA SI QUIERE QUE ACEPTE PUNTOS O NO
		super(Longitud);
		this.Band = Band;
	}
	public void keyTyped(KeyEvent Evt) {
		super.keyTyped(Evt);
		if(Evt.getKeyChar() == KeyEvent.VK_ENTER)
			return;
		if(getText().length()>Longitud-1 && Longitud>0) {
			Evt.consume();
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		if(Band && Evt.getKeyChar()=='.' && getText().indexOf('.')<0) {
			return;
		}
		if((!Character.isDigit(Evt.getKeyChar()))) {
			Evt.consume();
		}
	}
}
