/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author filiperibeiro
 */
public class Servidor {
    
    public static void main(String[] args) throws Exception{
        
        int port = 12345;
        ServerSocket ss;
        boolean exit = false;
        
        ss = new ServerSocket(port);
        System.out.println("Soc Criado!");
        
        /*Cria uma thread para a consola do proprio servidor com o armazem em referencia*/
        
        while(!exit){ /*exit é alterada na consola do servidor */
            
            Socket s = ss.accept();   //aceita o cliente
            PrintWriter o = new PrintWriter(s.getOutputStream());            
            BufferedReader i = new BufferedReader(
                                    new InputStreamReader(s.getInputStream())); 
            String l = i.readLine();
            
            
            if(true/*! password correcta*/){
                s.close();
            }
            else{
                while(l!=null){
                    System.out.println("Tarefa em curso..."+l);
                    /*
                    cria a thread e envia o s e o armazem por referençia 
                    */
                    l = i.readLine();
                }
            s.close();
            }   
        }
        ss.close();
    } 
}
