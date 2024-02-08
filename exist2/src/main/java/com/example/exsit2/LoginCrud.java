package com.example.exsit2;

import java.util.Scanner;

public class LoginCrud extends metodoscrud {
    ConexionExistDB conec = new ConexionExistDB();
   
metodoscrud metodos = new metodoscrud();
    public static void main(String[] args) {
        // Ejemplo: crear un nuevo documento XML
        
            System.out.println("1. Crear usuario");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Actualizar contraseña");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt();
            scanner.nextLine();  

            switch (opcion) {
                case 1:
                System.out.print("Nombre de usuario: ");
                String user = scanner.nextLine();
                System.out.print("Contraseña: ");
                String pass = scanner.nextLine();
                    crearUsuario(user, pass);
                    break;
    
                case 2:
                    
                    System.out.print("Nombre de usuario: ");
                    String nombreUsuario = scanner.nextLine();
                    System.out.print("Contraseña: ");
                    String contraseña = scanner.nextLine();
                    if (iniciarSesion(nombreUsuario, contraseña) ) {
                        System.out.println("Inicio de sesión exitoso.");
                    } else {
                        System.out.println("Credenciales incorrectas o usuario no existe.");
                    }
                    break;
    
                case 3:
                   
                    System.out.print("Nombre de usuario: ");
                    String usuarioActualizar = scanner.nextLine();
                    System.out.print("Nueva contraseña: ");
                    String nuevaContraseña = scanner.nextLine();
                  actualizarContraseña(usuarioActualizar, nuevaContraseña);
                    break;

                case 4:
                    //  eliminar usuario
                    System.out.print("Nombre de usuario a eliminar: ");
                    String usuarioEliminar = scanner.nextLine();
                    eliminarUsuario(usuarioEliminar);
                    break;
    
                case 5:
                    // Salir del programa
                    System.out.println("Saliendo del programa.");
                    break;
    
                default:
                    System.out.println("Opción no válida.");
            }
        }
      
            }
 
    

   

