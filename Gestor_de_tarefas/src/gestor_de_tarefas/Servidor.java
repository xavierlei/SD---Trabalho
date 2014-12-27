package gestor_de_tarefas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        HashMap <String,Utilizador> users = new HashMap<String,Utilizador>(); //users que estao registados
        Armazem armazem = new Armazem();
        Thread t;       
        
        ss = new ServerSocket(port);
        System.out.println("Socket Criado!");        
        
        /*adiciona utilizadores para teste*/
        Utilizador u1 = new Utilizador("xavier","123");
        Utilizador u2 = new Utilizador("carlos","123");
        Utilizador u3 = new Utilizador("filipe","123");
        users.put("xavier", u1);
        users.put("carlos", u2); 
        users.put("filipe", u3); 
        
        /*Cria aqui uma thread para a consola do proprio servidor com o armazem em referencia*/
        //apenas se liga um utilizador (...)
             
        //cria conexão com Clientes
        while(!exit){            
            Socket s = ss.accept(); 
            t = new Thread(new Thread_TrataCliente(armazem,s,users));
            t.start();
        }        
        ss.close();
    }
}
