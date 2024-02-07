import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import org.xmldb.api.*;
import java.util.*;

public class ExistDBLogin {
    private static String URI = "http://192.168.0.61:8080/";
    private static String driver = "org.exist.xmldb.DatabaseImpl";
    private static String collectionPath = "/db/users"; // ruta donde se almacenan los usuarios

    public static void main(String[] args) {
        try {
            // Inicializar el driver
            Class cl = Class.forName(driver);
            Database database = (Database) cl.newInstance();
            DatabaseManager.registerDatabase(database);

            // Obtener una colección de la base de datos
            Collection col = DatabaseManager.getCollection(URI + collectionPath);
            if (col == null) {
                System.out.println("La colección no existe.");
                return;
            }

            // Pedir al usuario que ingrese sus credenciales
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese su nombre de usuario: ");
            String username = scanner.nextLine();
            System.out.print("Ingrese su contraseña: ");
            String password = scanner.nextLine();

            // Consultar la base de datos para verificar las credenciales
            String xquery = "xquery version \"3.1\"; " +
                            "for $user in collection('" + collectionPath + "') " +
                            "where $user/usuario/username/text() = '" + username + "' " +
                            "return $user/usuario[username/text() = '" + username + "' and password/text() = '" + password + "']";
            XPathQueryService service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet result = service.query(xquery);
            if (result.getSize() > 0) {
                System.out.println("Inicio de sesión exitoso para el usuario: " + username);
            } else {
                System.out.println("Nombre de usuario o contraseña incorrectos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
