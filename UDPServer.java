import java.io.*;
import java.net.*; 
 
class UDPServer {
	
	public static String check(String sentence){
		String checked;
		
		char option = sentence.charAt(0);
		checked = sentence;
			
		if(option == '1'){
			
			sentence = sentence.replaceFirst("1",""); //removendo a opcao da string
			
			try{
				int aux = Integer.parseInt(String.valueOf(sentence.trim()));
				aux = aux + 1;
				checked = Integer.toString(aux);
			}catch(NumberFormatException e){
				checked = "O numero enviado nao eh valido";
			}
		}
		
		if(option == '2'){
			
			sentence = sentence.replaceFirst("2",""); //removendo a opcao da string
			sentence = sentence.trim(); //limpando os espacos em branco
			if(sentence.length() == 1){
				checked = sentence.toUpperCase();
			}
			else{
				checked = "Por favor, para essa opcao. Digite apenas um caractere";
			}
			
		}
		
		if(option == '3'){
	
			sentence = sentence.replaceFirst("3",""); //removendo a opcao da string
			checked  = new StringBuffer(sentence).reverse().toString();
		}
	
		return checked.trim();
	}
	
	public static void main(String args[]) throws Exception {
 
		int serverPort = 8022;
		
		DatagramSocket serverSocket = new DatagramSocket(serverPort);
 
		while (true) {
			
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
		
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			System.out.println("Esperando por datagrama UDP na porta " + serverPort);
			//mensagem recebida
			serverSocket.receive(receivePacket);
			//convertendo para string
			String sentence = new String(receivePacket.getData());
			

			//informacoes do cliente
			InetAddress clientIP = receivePacket.getAddress();
			int clientPort = receivePacket.getPort();
			System.out.print("Host: (" + clientIP +"|" + clientPort+ "), mensagem: " + sentence + "\n");
			
			// fazendo o check na mensagem e preparando a mensagem de resposta do servidor
			String capitalizedSentence = UDPServer.check(sentence);	
			sendData = capitalizedSentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, clientPort);
			System.out.print("Enviando " + capitalizedSentence + "...");
			
			//Enviando mensagem
			serverSocket.send(sendPacket);
			System.out.println("Mensagem enviada !");
			
		}
	}
}