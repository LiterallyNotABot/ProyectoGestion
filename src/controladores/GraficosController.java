package controladores;

import modelos.Usuario;
import modelos.UsuarioDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import java.util.List;

public class GraficosController {

	public void crearGrafico() {
		// Instancias del DAO para obtener los datos
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		List<Usuario> usuariosActivos = usuarioDAO.obtenerUsuariosActivos();
		List<Usuario> usuariosInactivos = usuarioDAO.obtenerUsuariosInactivos();
		List<Usuario> usuariosBorrados = usuarioDAO.obtenerUsuariosBorrados();

		// Calcular las cantidades
		int totalActivos = usuariosActivos.size();
		int totalInactivos = usuariosInactivos.size();
		int totalBorrados = usuariosBorrados.size();
		int totalNoBorrados = totalActivos + totalInactivos;

		// Crear el conjunto de datos para el gráfico
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Activos", totalActivos);
		dataset.setValue("Inactivos", totalInactivos);
		dataset.setValue("Borrados", totalBorrados);
		dataset.setValue("No Borrados", totalNoBorrados);

		// Crear el gráfico de tarta
		JFreeChart chart = ChartFactory.createPieChart("Distribución de Usuarios", // Título del gráfico
				dataset, // Datos del gráfico
				true, // Mostrar leyenda
				true, // Mostrar tooltips
				false // URLs
		);

		// Mostrar el gráfico en una ventana
		ChartFrame frame = new ChartFrame("Gráfico de Usuarios", chart);
		frame.pack();
		frame.setVisible(true);
	}
}
