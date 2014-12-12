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
            PrintWriter o = new PrintWriter(s.getOutputStream());            
            BufferedReader i = new BufferedReader(
                                    new InputStreamReader(s.getInputStream())); 
            String l;
            boolean continua = true;
            while( continua && ((l = i.readLine()) != null)){
                String parse[] =  l.split(" ");
                    if(parse[0].equals("login")){ 
                        if(users.containsKey(parse[1])){ //verifica se user existe
                            Utilizador u = users.get(parse[1]);
                            if(!u.getloged()){ //verifica se já esta loged
                               if(u.validaPass(parse[2])){ // verifica pass
                                    o.println("Loged: ...");
                                    u.login();
                                    o.flush();
                                    //s.shutdownInput();            
                                    continua = false;
                                    t = new Thread(new Thread_TrataCliente(armazem,s,u,o,i)); // falta enviar tambem o utilizador
                                    t.start();
                               } else { o.println("Password Errada!!"); o.flush();}
                            } else { o.println("Utilizador já de encontra autenticado!"); o.flush();}
                        }else { o.println("Utilizador não existe!"); o.flush();} 
                    } else { o.println("Go fock your self! learn to write!"); o.flush();}
            }
            //s.close();
        }
        
        ss.close();
    }
}
