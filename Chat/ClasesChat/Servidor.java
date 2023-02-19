package ClasesChat;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor{

	ServerSocket serverSocket;
	
	public Servidor (ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	
	public void empezarConexion() {
        try {
           while (!serverSocket.isClosed()) {


			Socket socket = serverSocket.accept();

			ManejoCliente manejoCliente = new ManejoCliente(socket);
		    System.out.println(manejoCliente.nombreCliente + " se ha conectado al servidor!");
			Thread thread = new Thread(manejoCliente);
			thread.start();
		}
        } catch (IOException e) {

        }
    }
	
	public void cerrarConexion() {
        try {
          if (serverSocket != null) {
			serverSocket.close();
		}
	
        } catch (IOException e) {
           e.printStackTrace();
        }
    }
	
	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket = new ServerSocket(3000);
		Servidor servidor = new Servidor(serverSocket);
		System.out.println("Servidor funcionando...");
	  	servidor.empezarConexion();
	}
}
//JP
