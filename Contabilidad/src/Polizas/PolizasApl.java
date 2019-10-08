package Polizas;

public class PolizasApl {
	private VistaPrincipal Vista;
	private ControladorPolizas Controlador;
	private ModeloPolizas Modelo;
	
	private VistaCatalogo VistaCatalogo; 
	private ModeloCatalogo ModeloCatalogo;
	ControladorCatalogo ControladorCatalogo;
	
	public PolizasApl() {
		VistaCatalogo = new VistaCatalogo();
		Vista = new VistaPrincipal(VistaCatalogo);
		try {
			Modelo = new ModeloPolizas();
		} catch (Exception E) {}
		ModeloCatalogo = new ModeloCatalogo();

		ControladorCatalogo = new ControladorCatalogo(VistaCatalogo, ModeloCatalogo);
		Controlador = new ControladorPolizas(Vista, Modelo, ModeloCatalogo, ControladorCatalogo);
		Controlador.setControlador(Controlador, ControladorCatalogo);
		ControladorCatalogo.InicializarConsulta();
		Vista.update(Vista.getGraphics());
	}
	public static void main(String [] a) {
		new PolizasApl();
	}
}
