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
        System.out.println("Soc Criado!");        
        
        /* * * * * * * * * * * *
         *       Teste            
         * * * * * * * * * * * */
        Utilizador u1 = new Utilizador("xavier","123");
        users.put("xavier", u1);        
        
        /*Cria uma thread para a consola do proprio servidor com o armazem em referencia*/
        
        /*Algoritmo:login (Carlos) 
            ->recebe ligação  
            ->verifica se user existe, se está logado e se passo esta correta
            ->cria Thread TrataCliente
            (se calhar a Thread TrataCliente vai passar a receber como args o out e o in, ou talvez não...)
        */        
        
        while(!exit){ /*exit é alterada na consola do servidor */            
            Socket s = ss.accept();   //aceita o cliente
            t = new Thread(new Thread_TrataCliente(armazem,s,users)); // falta enviar tambem o utilizador
            t.start();
            //s.close();
        }
        
        ss.close();
    }
}
