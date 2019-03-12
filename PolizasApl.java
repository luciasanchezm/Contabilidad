import java.io.IOException;

public class PolizasApl {
	PolizasVista Vista;
	PolizasControlador Controlador;
	PolizasModelo Modelo;
	
	public PolizasApl() {
		Vista = new PolizasVista();
		try {
			Modelo = new PolizasModelo();
		} catch (Exception E) {}
		Controlador = new PolizasControlador(Vista, Modelo);
	}
	public static void main(String [] a) {
		new PolizasApl();
	}
}