import java.awt.*;
import java.awt.event.*;

public class TextFieldNumeros extends TextField{
	private boolean Band;
	public TextFieldNumeros(boolean Band) {//LA BANDERA INDICA SI QUIERE QUE ACEPTE PUNTOS O NO
		super();
		this.Band = Band;
	}
	public void keyTyped(KeyEvent Evt) {
		if(getText().length()>Longitud-1 && Longitud>0) {
			Evt.consume();
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		if(Evt.getKeyChar()=='.' && Band) {
			Band = false;
			return;
		}
		if(Evt.getKeyChar()=='.' && !Band)
			Evt.consume();
		if((!Character.isDigit(Evt.getKeyChar()))) {
			Evt.consume();
		}
	}
}
