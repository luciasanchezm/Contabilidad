package Polizas;

import java.awt.*;
import java.awt.event.*;

public class TextFieldLetras extends TextField implements KeyListener{
	public TextFieldLetras(int Longitud) {
		super(Longitud);
	}
	public void keyTyped(KeyEvent Evt) {
		super.keyTyped(Evt);
		char character = Evt.getKeyChar();
		if(character == KeyEvent.VK_ENTER)
			return;
		if(getText().length()>Longitud-1 && Longitud>0) {
			Evt.consume();
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		if(!(character>='A' && character<='Z' ||
				character>='a' && character<='z' ||
				character==' ')  ){
			Evt.consume();
			return;
		}
		if(character>='a' && character<='z') {
			setText(getText()+Character.toUpperCase(character));
			Evt.consume();
		}
	}
}
