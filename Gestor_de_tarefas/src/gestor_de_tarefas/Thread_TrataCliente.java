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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlosmorais
 */
public class Thread_TrataCliente implements Runnable {
    private Armazem armazem;
    private final Socket mySocket;
    
    /*No servidor depois do accept, é criada a Thread*/
    public Thread_TrataCliente(Armazem a, Socket s){
        this.armazem = a;    
        this.mySocket = s;
    }
    
    public void run(){
        try {
            String pedido;                        
            PrintWriter out = new PrintWriter(this.mySocket.getOutputStream());
            BufferedReader in = new BufferedReader( new InputStreamReader(this.mySocket.getInputStream()));
            System.out.println("Nova conexão com o cliente " +  mySocket.getInetAddress().getHostAddress());
            //le o pedido do Cliente
            while( ((pedido=in.readLine()) != null) && (!pedido.equals("logout")) ){
                /*fara sentido criar uma Thread que vai executar o pedido ?!
                    assim, esta Thread fica a ler mais pedidos do Cliente
                        quando a nova Thred criada acabar, a própria escreve no Cliente!                              
                */
                
                //Thread para tratar os pedidos do Cliente
                Thread cr = new Thread( new Thread_TrataPedido(this.armazem, out, pedido));
                cr.start(); 
                
            }
            
            System.out.println("logout efetuado com sucesso em "+mySocket.getInetAddress().getHostAddress());
            out.flush();
            
            this.mySocket.shutdownInput();
            this.mySocket.shutdownOutput();
            this.mySocket.close();            
        } catch (IOException ex) {
            Logger.getLogger(Thread_TrataCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
            

    }
    
}
