/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlosmorais
 */

//Classe para ler as mensagens do Servidor, no Cliente
public class Thread_ClienteRead implements Runnable{
    private BufferedReader inputServer;
    
    public Thread_ClienteRead(BufferedReader in){
        this.inputServer = in;
    }
    
    public void run(){
        try {
            //recebe mensagens do servidor e imprime
            String line;
            /*há um pequeno bug aqui, por vezes quando é feito logout, isto ainda 
                    é executado*/
            while((line = inputServer.readLine()) != null ){
                System.out.println(line);
            }
        } catch (IOException ex) {            
        }
    }               
}
