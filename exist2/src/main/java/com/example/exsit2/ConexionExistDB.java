package com.example.exsit2;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.modules.XMLResource;

public class ConexionExistDB {
    public static void main(String[] args) {
        // Configura la información de conexión
        String existUri = "xmldb:exist://localhost:8080/exist/xmlrpc";
        String usuario = "admin";
        String contraseña = "";
        String nombreColeccion = "/db/bd1";  // Reemplaza con la ruta de tu colección
        String nuevoDocumentoXml = "nuevo_documento.xml";  // Reemplaza con el nombre que desees para el nuevo documento

        Collection collection = null;

        try {
            // Carga el controlador de la base de datos XML:DB
            Class<?> cl = Class.forName("org.exist.xmldb.DatabaseImpl");
            Database database = (Database) cl.newInstance();
            DatabaseManager.registerDatabase(database);

            // Construye la URL de conexión con la colección especificada
            String uri = existUri + nombreColeccion;

            // Establece la conexión
            collection = DatabaseManager.getCollection(uri, usuario, contraseña);

            // Verifica si la colección se ha inicializado correctamente
            if (collection != null) {
                // Crea un nuevo recurso XML en la colección
                XMLResource nuevoXmlResource = (XMLResource) collection.createResource(nuevoDocumentoXml, "XMLResource");

                // Define el contenido del nuevo documento XML
                String contenidoXml = "<root><element>Contenido del nuevo XML</element></root>";
                nuevoXmlResource.setContent(contenidoXml);

                // Almacena el nuevo documento XML en la colección
                collection.storeResource(nuevoXmlResource);

                System.out.println("Nuevo documento XML creado y almacenado en la colección.");
            } else {
                System.out.println("Error: La colección no se inicializó correctamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: No se pudo establecer la conexión o realizar operaciones en la colección XML");
        } finally {
            // Asegúrate de cerrar la conexión en caso de excepción
            if (collection != null) {
                try {
                    collection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}