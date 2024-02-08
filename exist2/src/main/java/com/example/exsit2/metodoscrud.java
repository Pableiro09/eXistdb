package com.example.exsit2;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.modules.XMLResource;
import java.util.Scanner;

public class metodoscrud {
   public static final String EXIST_URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    public static final String USUARIO_ADMIN = "admin";
 public static final String CONTRASEÑA_ADMIN = "";
   public static final String NOMBRE_COLECCION = "/db/bd1";
    String nuevoDocumentoXml = "nuevo_documento.xml";
     

    public static void crearUsuario(String nombreUsuario, String contraseña) {
        try {
            Collection collection = obtenerColeccion();
    
            if (collection != null) {
                // Verifica si el usuario ya existe
                XMLResource usuarioResource = (XMLResource) collection.getResource(nombreUsuario + ".xml");
                if (usuarioResource == null) {
                    // Crea un nuevo recurso XML para el usuario
                    usuarioResource = (XMLResource) collection.createResource(nombreUsuario + ".xml", "XMLResource");
    
                    // Define el contenido del nuevo documento XML (por ejemplo, solo la contraseña)
                    String contenidoXml = "<contraseña>" + contraseña + "</contraseña>";  // Reemplaza con la estructura XML real
    
                    // Imprime el contenido para verificar antes de almacenarlo
                    System.out.println("Contenido XML a almacenar: " + contenidoXml);
    
                    // Establece el contenido del nuevo documento XML
                    usuarioResource.setContent(contenidoXml);
    
                    // Almacena el nuevo documento XML en la colección
                    collection.storeResource(usuarioResource);
    
                    System.out.println("Nuevo usuario creado y almacenado en la colección.");
                } else {
                    System.out.println("Error: El usuario ya existe.");
                }
            } else {
                System.out.println("Error: La colección no se inicializó correctamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: No se pudo establecer la conexión o realizar operaciones en la colección XML");
        }
    }

    public static boolean iniciarSesion(String nombreUsuario, String contraseña) {
        try {
            Collection collection = obtenerColeccion();

            if (collection != null) {
                // Intenta obtener el usuario 
                XMLResource usuarioResource = (XMLResource) collection.getResource(nombreUsuario + ".xml");

                // Verifica si el recurso existe y la contraseña coincide
                return usuarioResource != null && usuarioResource.getContent().toString().equals(contraseña);
            } else {
                System.out.println("Error: La colección no se inicializó correctamente");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: No se pudo establecer la conexión o realizar operaciones en la colección XML");
            return false;
        }
    }

   
    public static void actualizarContraseña(String nombreUsuario, String nuevaContraseña) {
        try {
            Collection collection = obtenerColeccion();
    
            if (collection != null) {
                // Actualiza la contraseña del usuario
                XMLResource usuarioResource = (XMLResource) collection.getResource(nombreUsuario + ".xml");
                if (usuarioResource != null) {
                    // Imprime el contenido antes de actualizar para verificar
                    System.out.println("Contenido antes de actualizar: " + usuarioResource.getContent());
    
                    // Obtén el contenido como String y concatena la nueva contraseña
                    String contenidoExistente = (String) usuarioResource.getContent();
                    String nuevoContenido = "<contraseña>" + nuevaContraseña + "</contraseña>";
    
                    // Imprime el nuevo contenido para verificar
                    System.out.println("Nuevo contenido: " + nuevoContenido);
    
                    // Actualiza la contraseña del usuario
                    usuarioResource.setContent(nuevoContenido);
                    collection.storeResource(usuarioResource);
    
                    System.out.println("Contraseña actualizada con éxito.");
                } else {
                    System.out.println("Usuario no encontrado.");
                }
            } else {
                System.out.println("Error: La colección no se inicializó correctamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: No se pudo establecer la conexión o realizar operaciones en la colección XML");
        }
    }
   
    public static void eliminarUsuario(String nombreUsuario) {
        try {
            Collection collection = obtenerColeccion();
    
            if (collection != null) {
                // Elimina el usuario
                String recursoPath = nombreUsuario + ".xml";
                XMLResource usuarioResource = (XMLResource) collection.getResource(recursoPath);
    
                if (usuarioResource != null) {
                    collection.removeResource(usuarioResource);
                    System.out.println("Usuario eliminado con éxito.");
                } else {
                    System.out.println("Usuario no encontrado en la ruta: " + recursoPath);
                }
            } else {
                System.out.println("Error: La colección no se inicializó correctamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        }
    
      
            }
 
    

   

    
   public static Collection obtenerColeccion() throws Exception {
        // Carga el controlador de la base de datos XML:DB
        Class<?> cl = Class.forName("org.exist.xmldb.DatabaseImpl");
        Database database = (Database) cl.newInstance();
        DatabaseManager.registerDatabase(database);

        // Construye la URL de conexión con la colección especificada
        String uri = EXIST_URI + NOMBRE_COLECCION;

        // Establece la conexión
        return DatabaseManager.getCollection(uri, USUARIO_ADMIN, CONTRASEÑA_ADMIN);
    }
}

