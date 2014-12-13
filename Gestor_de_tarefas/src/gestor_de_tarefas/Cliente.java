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
            Socket s = new Socket("localhost", 44555)) {
            String pedido;
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter output = new PrintWriter(s.getOutputStream());
            
            //Thread para ler as mensagens do Servidor
            Thread cr = new Thread( new Thread_ClienteRead(serverInput));
            cr.start();
            
            /*TESTE*/
            output.println("login carlos 123");
            output.flush();
            output.println("abastece martelo 10");
            output.flush();
            output.println("abastece prego 40");
            output.flush();
            output.println("abastece chave 10");
            output.flush();
            output.println("adiciona cadeira martelo 2 prego 10");
            output.flush();
            output.println("adiciona cmesa martelo 3 chave 2");
            output.flush();
            output.println("print");
            output.flush();
            
            //le as mensagens do Utilizador e envia para o Servidor
            //fara sentido tratar os pedidos aqui, de forma a irem limpos para o servidor
            while( !(pedido=userInput.readLine()).equals("logout") ){
                output.println(pedido);
                output.flush();
            }            
            s.shutdownInput();
            s.shutdownOutput();
            s.close();
        }
 } 
    
}
