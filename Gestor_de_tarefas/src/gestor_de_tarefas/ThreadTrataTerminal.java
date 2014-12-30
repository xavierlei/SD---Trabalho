/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xavier
 */
public class ThreadTrataTerminal extends Thread {
    
    private Armazem armazem;
    private HashMap <String,Utilizador> utilizadores;
    private String pedido;
    private boolean ok = true;
    
    public ThreadTrataTerminal(Armazem a,HashMap <String,Utilizador> users,String pedido){
        this.armazem = a;
        this.utilizadores = users;
        this.pedido = pedido;
        
    }
    
    public TipoTarefa constroiTarefa(String lista[]){
        
        HashMap<String,Integer> pedidos = new HashMap<>();
        String id = lista[1];
        
        for(int i=2; i<lista.length; ){
            String nome = lista[i];
            i++;
            int qtd = Integer.parseInt(lista[i]);
            pedidos.put(nome, qtd);
            i++;
        }
        return (new TipoTarefa(id, pedidos));
    }
    
    @Override
    public void run(){
        String resposta=null;
        String lista[] = pedido.split(":");
        
        switch (lista[0]){
            case "abastece": 
                try{
                    this.armazem.abastece(lista[1], Integer.parseInt(lista[2]));
                    resposta = "abastecido!";
                }
                catch(Exception e){
                    resposta = "comando errado";
                    ok = false;
                }
                break;
            
            case "executa": 
        {
            try {
                resposta = this.armazem.executaTarefa(lista[1],"terminal");
            } catch (FerramentaException ex) {
                Logger.getLogger(Thread_TrataPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                resposta=  "executa a tarefa "+lista[1]+" como o id "+resposta;                
                break;
            
            case "adiciona": 
                TipoTarefa t = constroiTarefa(lista);
                this.armazem.addTarefa(t);
                resposta = "controi a tarefa " + lista[1];
                break;
            
            case "notifica": 
                List<String> ids = new ArrayList<String>();
                for(int i=1; i<lista.length; i++)
                    ids.add(lista[i]);
            
                    
        {
            try {
                this.armazem.notifica(ids);
            } catch (InterruptedException ex) {
                Logger.getLogger(Thread_TrataPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                resposta = "notifica ";
                break;  
                    
            case "listagem":
                resposta = this.armazem.listagemTarefas();
                break;
            
            case "termina":   
        {
            try {
                this.armazem.terminaTarefa(lista[1], "terminal");
                resposta = "termina a tarefa " + lista[1];
            } catch (TarefaException ex) {
                resposta = ex.getMessage();
            }
        }                
                break;          
            
            case "print": 
                resposta = this.armazem.toString();
                break;        
            
            default: resposta = "comando errado";
                     ok = false;
                     break;               
        }
        if(ok)          
            System.out.println("o pedido ** " +pedido+ " ** foi recebido e tratado");
        System.out.println(resposta);
        System.out.flush();
    }
    
}
