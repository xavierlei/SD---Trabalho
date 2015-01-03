package gestor_de_tarefas;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author filiperibeiro
 */

public class Servidor {
    
    public static void main(String[] args) throws Exception{
        
        int port = 44555;
        ServerSocket ss;
        boolean exit = false;
        HashMap <String,Utilizador> users = new HashMap<>(); //users que estao registados
        Armazem armazem = new Armazem();
        Thread t;       
        
        ss = new ServerSocket(port);
        System.out.println("Socket Criado!");        
        
        /*adiciona utilizadores*/
        Utilizador u1 = new Utilizador("xavier","55838");
        Utilizador u2 = new Utilizador("carlos","64306");
        Utilizador u3 = new Utilizador("filipe","64315");
        users.put(u1.getUsername(), u1);
        users.put(u2.getUsername(), u2); 
        users.put(u3.getUsername(), u3); 
        
        /*Cria aqui uma thread para a consola do proprio servidor*/
        ThreadTerminal terminal = new ThreadTerminal(armazem, users,exit,ss); 
        terminal.start();
        //cria conexão com Clientes
        while(!exit){            
            Socket s = ss.accept(); 
            t = new Thread(new Thread_TrataCliente(armazem,s,users));
            t.start();
        }        
        ss.close();
    }
}
