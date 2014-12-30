/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author xavier
 */
public class ThreadTerminal extends Thread{
    
    private Armazem armazem;
    private HashMap <String,Utilizador> utilizadores;
    boolean exit;
    
    public ThreadTerminal(Armazem a,HashMap <String,Utilizador> users, boolean exit){
        armazem = a;
        utilizadores = users;
        this.exit = exit;
    }
    
    @Override
    public void run(){
        Scanner in = new Scanner(System.in);
        System.out.print(">");
        String pedido = in.nextLine();
        while(!exit){
            if(pedido.equals("sair"))
                exit = true;
            else{
                
                ThreadTrataTerminal t = new ThreadTrataTerminal(this.armazem,this.utilizadores,pedido);
                t.start();
                System.out.print(">");
                pedido = in.nextLine();
            }
        }
        System.exit(MIN_PRIORITY);
    }
    
}
