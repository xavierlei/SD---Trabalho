/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author carlosmorais
 */
public class Cliente {
    
    public static void main(String[] args) throws IOException {      
        try ( 
            //a porta no Servidor tem de ser a mesma!
            Socket s = new Socket("localhost", 44555)) {
            String pedido;
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter output = new PrintWriter(s.getOutputStream());
            
            //Thread para ler as mensagens do Servidor
            Thread cr = new Thread( new Thread_ClienteRead(serverInput));
            cr.start();
            
            //le as mensagens do Utilizador e envia para o Servidor
            //em principio o pedido de login é feito aqui, mas é aceite no Servidor (...)
            while( !(pedido=userInput.readLine()).equals("logout") ){
                output.println(pedido);
                output.flush();
                //pedido = serverInput.readLine();
                //System.out.println("Servidor "+pedido);
            }            
            s.shutdownInput();
            s.shutdownOutput();
            s.close();
        }
 } 
    
}
