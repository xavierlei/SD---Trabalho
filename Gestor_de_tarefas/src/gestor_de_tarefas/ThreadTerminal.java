/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xavier
 */
public class ThreadTerminal extends Thread{
    
    private Armazem armazem;
    private HashMap <String,Utilizador> users;
    boolean exit;
    Utilizador user;
    ServerSocket ss;
    
    public ThreadTerminal(Armazem a,HashMap <String,Utilizador> users, boolean exit,ServerSocket ss){
        armazem = a;
        this.users = users;
        this.exit = exit;
        this.ss = ss;
    }
    
    public boolean login(){
        boolean continua = true;
        Scanner in = new Scanner(System.in);
        String l = "";
        System.out.println("###EFECTUE LOGIN PARA USAR O TERMINAL###");
        System.out.print(">");
            //Login -> tornar mais legivel?
            while( continua && (!"sair".equals(l = in.nextLine()))){
                String parse[] =  l.split(":");
                if( parse[0].equals("login") && (parse.length >= 3) ){ 
                    if(users.containsKey(parse[1])){ //verifica se user existe
                        Utilizador u = users.get(parse[1]);
                        if(!u.getloged()){ //verifica se já esta loged
                            if(u.validaPass(parse[2])){ // verifica pass              
                                if(u.login()){
                                    System.out.println("Loged: ..."); 
                                    this.user = u;         
                                    continua = false;
                                } else {System.out.println("Utilizador já de encontra autenticado!");}                                  
                            } else { System.out.println("Password Errada!!");}
                        } else { System.out.println("Utilizador já de encontra autenticado!");}
                    }else { System.out.println("Utilizador não existe!");} 
                } else { System.out.println("Comando errado!");}
                if(continua){
                    System.out.println("###EFECTUE LOGIN PARA USAR O TERMINAL###");
                    System.out.print(">");
                } 
            }
        return !l.equals("sair");
    }
    
    @Override
    public void run(){
        boolean continua = true;
        Scanner in = new Scanner(System.in);
        if(!login())
        this.exit = true;
        String pedido;
        while(!exit){
            System.out.print(">");
            pedido = in.nextLine();
            if(pedido.equals("sair")){
                exit = true;
                user.logout();
            }
            else if(pedido.equals("logout")){
                this.user.logout();
                if(!login())
                this.exit = true;
            }
            else{
                //ThreadTrataTerminal t = new ThreadTrataTerminal(this.armazem,this.users,pedido);
                PrintWriter out = new PrintWriter(System.out);
                
                Thread cr = new Thread( new Thread_TrataPedido(this.armazem, this.user.getUsername(), out, pedido));
                cr.start(); 
            }
        }
        
        System.exit(MIN_PRIORITY);
    }
    
}
