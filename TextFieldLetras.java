import java.awt.*;
import java.awt.event.*;

public class TextFieldLetras extends TextField implements KeyListener{
	public TextFieldLetras() {
		super();
	}
	public void keyTyped(KeyEvent Evt) {
		if(getText().length()>Longitud-1 && Longitud>0) {
			Evt.consume();
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		if(!(Evt.getKeyChar()>='A' && Evt.getKeyChar()<='Z' ||
				Evt.getKeyChar()>='a' && Evt.getKeyChar()<='z' ||
				Evt.getKeyChar()==' ')  ){
			Evt.consume();
			return;
		}
	}
}
