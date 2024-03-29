/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.io.BufferedReader;
import java.io.IOException;

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
            while((line = inputServer.readLine()) != null ){
                System.out.println(line);
            }
        } catch (IOException ex) {   
            System.out.println(ex.getMessage());
        }
    }               
}
