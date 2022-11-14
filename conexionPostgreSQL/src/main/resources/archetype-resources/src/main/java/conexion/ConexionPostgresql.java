import com.crudPostgreSQL.util.variablesConexionPostgreSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** conexionPostgreSQL - Clase que gestiona la conexión a PostgreSQL.
 * @author garfe
 * 19/10/2022
 */
public class ConexionPostgresql {
	
	//MÉTODOS
	/** generaConexion - Método que genera la conexión a PostgreSQL a partir de la definición existente
	 * en las variables de conexión a PostgreSQL.
	 * @return Conexión a postgreSQL, null si no es válida, y la correspondiente si sí lo es.
	 * @autor garfe
	 * 19/10/2022
	 */
	public static Connection generaConexion() {

		System.out.println("[INFORMACIÓN-conexionPostgresql-generaConexion] Entra en generaConexion");
		
		//VARIABLES
		final String host = variablesConexionPostgreSQL.getHost();
		final String port = variablesConexionPostgreSQL.getPort();
		final String db = variablesConexionPostgreSQL.getDb();
		final String user = variablesConexionPostgreSQL.getUser();
		final String pass = variablesConexionPostgreSQL.getPass();
		/*(Definición local) Definimos connection a null y url a vacío para 
         * asegurarnos de que ambas variables están limpias.
         */
        Connection conexion = null;
        String url = "";            
        url = "jdbc:postgresql://" + host + ":" + port + "/" + db;
		
        //GENERACIÓN CONEXIÓN
        try {
        	
        	/*Class.forName obtiene una instancia de la clase de java especificada.
			*En este caso registra la clase como driver JDBC
			*/
            Class.forName("org.postgresql.Driver");
            
            /*Conexión a la base de datos en PostgreSQL y validación de esta*/
            conexion = DriverManager.getConnection(url, user, pass);           
            boolean esValida = conexion.isValid(50000);
            if(esValida == false) conexion = null;
            System.out.println(esValida ? "[INFORMACIÓN-conexionPostgresql-generaConexion] Conexión a PostgreSQL válida" : "[WARNING-conexionPostgresql-generaConexion] Conexión a PostgreSQL no válida");
            System.out.println("[INFORMACIÓN-conexionPostgresql-generaConexion] Sale de generaConexion");
            
            return conexion;
            
        } catch (java.sql.SQLException jsqle) {
        	
            System.out.println("[ERROR-conexionPostgresql-generaConexion] Error en conexión a PostgreSQL (" + url + "): " + jsqle);
            return conexion;
            
        } catch (ClassNotFoundException cnfe) {
        	
            System.out.println("[ERROR-conexionPostgresql-generaConexion] Error al registrar driver: " + cnfe);
            return conexion;
            
        }
    }
	
	/** cerrarConexion - Método que hace commit y cierra la conexión
	 * @author garfe
	 * @param Connection conexionGenerada
	 * @return void
	 * 19/10/2022
	 */
	public static void cerrarConexion (Connection conexionGenerada) {
		
		System.out.println("[INFORMACIÓN-conexionPostgresql-cerrarConexion] Entra en cerrar conexión");
		
		try {
			
			/*Si el auto commit está habilitado no se hace de forma explícita el commit.
			conexionGenerada.commit();*/
			conexionGenerada.close();
			System.out.println("[INFORMACIÓN-conexionPostgresql-generaConexion] Sale de cerrarConexión");
			
		} catch (SQLException e) {
			
			System.out.println("[ERROR-conexionPostgresql-cerrarConexion] Error al cerrar conexión PostgreSQL: " + e);
		
		}	
		
	}

}

