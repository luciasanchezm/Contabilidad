import java.io.IOException;

public class PolizasApl {
	PolizasVista Vista;
	PolizasControlador Controlador;
	PolizasModelo Modelo;
	
	private PolizasVistaCatalogo VistaCatalogo; 
	private PolizasModeloCatalogo ModeloCatalogo;
	PolizasControladorCatalogo ControladorCatalogo;
	
	public PolizasApl() {
		VistaCatalogo = new PolizasVistaCatalogo();
		Vista = new PolizasVista(VistaCatalogo);
		try {
			Modelo = new PolizasModelo();
		} catch (Exception E) {}
		ModeloCatalogo = new PolizasModeloCatalogo();

		ControladorCatalogo = new PolizasControladorCatalogo(VistaCatalogo, ModeloCatalogo);
		Controlador = new PolizasControlador(Vista, Modelo, ModeloCatalogo, ControladorCatalogo);
		Controlador.setControlador(Controlador, ControladorCatalogo);
		ControladorCatalogo.InicializarConsulta();
		Vista.update(Vista.getGraphics());
	}
	public static void main(String [] a) {
		new PolizasApl();
	}
}