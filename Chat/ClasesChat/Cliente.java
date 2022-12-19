package ClasesChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	
	Socket socket;
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;
	String nombre;
	
	public Cliente (Socket socket, String nombre) {
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.nombre = nombre;
		} catch (Exception e) {
			cerrarTodo(socket,bufferedReader,bufferedWriter);
		}
	}
	
	public void enviarMensaje() {
		try {
			bufferedWriter.write(nombre);
			bufferedWriter.newLine();
			bufferedWriter.flush();

			Scanner scanner = new Scanner(System.in);
			while (socket.isConnected()) {
				String mensaje = scanner.nextLine();
				System.out.printf("Tu: " + mensaje);
				System.out.println();
				bufferedWriter.write(nombre + ": " + mensaje);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}
		} catch (Exception e) {
			cerrarTodo(socket,bufferedReader,bufferedWriter);
		}
	}
	
	public void recibirMensaje() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String mensaje;
				while (socket.isConnected()) {
					try {
						mensaje = bufferedReader.readLine();
						System.out.println(mensaje);
					} catch (Exception e) {
						cerrarTodo(socket,bufferedReader,bufferedWriter);
					}
					
				}
			}
		}).start(); 
	}

	public void cerrarTodo(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		try {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (bufferedWriter != null) {
				bufferedWriter.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	public static void main(String[] args) throws IOException{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Introduce tu nombre: ");
		
			String nombre = scanner.nextLine();
			Socket socket = new Socket("localhost",3000);
			Cliente cliente = new Cliente(socket, nombre);
			cliente.recibirMensaje();
			cliente.enviarMensaje();
		
	}

}
//