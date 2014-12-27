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
    /*COMANDOS
    abastece nome,qtd
    adiciona nomeTarefa; nome1,qtd1; nome2,qtd2
    executa nomeTarefa
    notifica id1; id2    
    */
    public static void main(String[] args) throws IOException, InterruptedException {      
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
            Thread.sleep(100);
            output.println("abastece martelo 10");
            output.flush();
            Thread.sleep(100);
            output.println("abastece prego 40");
            output.flush();
            Thread.sleep(100);
            output.println("abastece chave 10");
            output.flush();
            Thread.sleep(100);
            output.println("adiciona cadeira martelo 2 prego 10");
            output.flush();
            Thread.sleep(100);
            output.println("adiciona mesa martelo 2 chave 10");
            output.flush();
            Thread.sleep(100);
            output.println("adiciona secretaria prego 10");
            output.flush();
            Thread.sleep(100);
            output.println("executa cadeira");
            output.flush();
            Thread.sleep(100);
            output.println("executa mesa");
            output.flush();
            Thread.sleep(100);
            output.println("executa cadeira");
            output.flush();
            Thread.sleep(100);
            output.println("executa secretaria");
            output.flush();
            Thread.sleep(100);
            output.println("executa secretaria");
            output.flush();
            Thread.sleep(100);
            output.println("executa mesa");
            output.flush();
            Thread.sleep(100);
            output.println("listagem");
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
