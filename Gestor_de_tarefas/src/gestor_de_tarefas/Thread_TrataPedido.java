/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlosmorais
 */
public class Thread_TrataPedido implements Runnable {
    private Armazem armazem;
    private PrintWriter out;
    private String pedido;
    private String userID;

    public Thread_TrataPedido(Armazem armazem, String userID, PrintWriter out, String pedido) {
        this.armazem = armazem;
        this.out = out;
        this.pedido = pedido;
        this.userID = userID;
    }
    
    public Tarefa constroiTarefa(String lista[]){
        
        HashMap<String,Integer> pedidos = new HashMap<String,Integer>();
        String id = lista[1];
        
        for(int i=2; i<lista.length; ){
            String nome = lista[i];
            i++;
            //System.out.println("***"+lista[i]+"***");
            int qtd = Integer.parseInt(lista[i]);
            pedidos.put(nome, qtd);
            i++;
        }
        return (new Tarefa(id, pedidos,userID));
    }
    
    public void run(){
        String resposta="";
        System.out.println("o pedido ** " +pedido+ " ** foi recebido e tratado");
        String lista[] = pedido.split(" ");
        /*for(String aux : lista)
            System.out.println("->"+aux+"<-");*/
        
        switch (lista[0]){
            case "abastece": 
                this.armazem.abastece(lista[1], Integer.parseInt(lista[2]));
                resposta = "abastecido!";
                break;
            
            case "executa": 
        {
            try {
                this.armazem.executaTarefa(lista[1]);
            } catch (FerramentaException ex) {
                Logger.getLogger(Thread_TrataPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                resposta = "executou a tarefa "+lista[1];
                break;
            
            case "adiciona": 
                Tarefa t = constroiTarefa(lista);
                this.armazem.addTarefa(t);
                resposta = "controi a tarefa " + lista[1];
                break;
            
            case "notifica": 
                this.armazem.notifications(userID);
                resposta = "notifica " + lista[1];
                break;    
            
            case "termina":   
                this.armazem.terminaTarefa(lista[1]);
                resposta = "termina a tarefa " + lista[1];
                break;     
            
            case "print": 
                System.out.println("OK");
                resposta = this.armazem.toString();
                break;        
            
            default:
                break;                
        }
                     
        //envia resposta ao Cliente
        out.println(resposta);
        out.flush();
    }
    
    
}
