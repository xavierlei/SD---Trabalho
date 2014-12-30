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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlosmorais
 */
public class Thread_TrataCliente implements Runnable {
    private Armazem armazem;
    private final Socket mySocket;
    HashMap <String,Utilizador> users; //users que estao registados
    Utilizador user;
    PrintWriter out;
    BufferedReader in;
    
    /*No servidor depois do accept, é criada a Thread*/
    public Thread_TrataCliente(Armazem a, Socket s, HashMap <String,Utilizador> u){
        this.armazem = a;    
        this.mySocket = s;
        this.users = u;
    }    
    
    public void run(){
        try {
            this.out = new PrintWriter(mySocket.getOutputStream());            
            this.in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            String pedido;
            String l;
            boolean continua = true;
            //Login -> tornar mais legivel?
            while( continua && ((l = in.readLine()) != null)){
                String parse[] =  l.split(" ");
                if( parse[0].equals("login") && (parse.length >= 3) ){ 
                    if(users.containsKey(parse[1])){ //verifica se user existe
                        Utilizador u = users.get(parse[1]);
                        if(!u.getloged()){ //verifica se já esta loged
                            if(u.validaPass(parse[2])){ // verifica pass              
                                if(u.login()){
                                    out.println("Loged: ..."); 
                                    this.user = u;
                                    out.flush();          
                                    continua = false;
                                } else {out.println("Utilizador já de encontra autenticado!"); out.flush();}                                  
                            } else { out.println("Password Errada!!"); out.flush();}
                        } else { out.println("Utilizador já de encontra autenticado!"); out.flush();}
                    }else { out.println("Utilizador não existe!"); out.flush();} 
                } else { out.println("Comando errado!"); out.flush();}
            }
            
            //teste -> APAGAR ISTO
            System.out.println("Nova conexão com o cliente " +  mySocket.getInetAddress().getHostAddress());
            
            //le o pedido do Cliente e cria Thread para o tartar
            while( (pedido=in.readLine()) != null ){
                Thread cr = new Thread( new Thread_TrataPedido(this.armazem, this.user.getUsername(), out, pedido));
                cr.start();                 
            }
            //teste -> APAGAR ISTO
            System.out.println("logout efetuado com sucesso em "+mySocket.getInetAddress().getHostAddress());
            
            //caso seja logout terá de fazer mais alguma coisa! (já nao sei o que...)
            this.user.logout();
            
            this.mySocket.shutdownInput();
            this.mySocket.shutdownOutput();
            this.mySocket.close();            
        } catch (IOException ex) {
            Logger.getLogger(Thread_TrataCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
            

    }
    
}
