/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xavier
 */
public class Armazem implements InterfaceArmazem {
    private Map<String,Ferramenta> ferramentas;
    private Map<String,Tarefa> tarefas;
    public ReentrantLock lt;
    public ReentrantLock lf;

    
    
    public Armazem(){
        this.ferramentas = new HashMap<String,Ferramenta>();
        this.tarefas = new HashMap<String,Tarefa>();
        this.lt = new ReentrantLock();
        this.lf = new ReentrantLock();
    }

    @Override
    public List<String> tarefasActivas() {
        ArrayList<String> res = new ArrayList<String>();
        lt.lock();
        try{
            for(String s : tarefas.keySet()){
            if(!tarefas.get(res).getEstado())
                res.add(s);           
            }
        }
        finally{
         lt.unlock(); 
         return res;
        }
        
        
    }

    @Override
    public void notifications(String userID){
        for(String s : this.tarefas.keySet()){
            if(this.tarefas.get(s).getUtilizador().equals(userID)){
                while(!this.tarefas.get(s).getEstado())
                    try {
                        this.tarefas.get(s).c.await();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Armazem.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }  
        }
    }

    @Override
    public void abastece(String ferramenta, int quant) {
        if(this.ferramentas.containsKey(ferramenta))
            this.ferramentas.get(ferramenta).abastece(quant);
        else{
            Ferramenta f = new Ferramenta(ferramenta,quant);
            this.ferramentas.put(ferramenta,f);
        }        
    }
    

    @Override
    public void addTarefa(Tarefa t) {
        lt.lock();
        try{
            this.tarefas.put(t.getID(), t);
        }
        finally{
            lt.unlock();
        }        
    }

    public void executaTarefa(String id) throws FerramentaException{
        Map<String, Integer> pedidos = this.tarefas.get(id).getPedidos();
        
        for(String s : pedidos.keySet()){
            if(!this.ferramentas.containsKey(s))
                throw new FerramentaException("ferramenta não existe");
        }   
        for(String aux : pedidos.keySet()){
            try {
                this.ferramentas.get(aux).reserva(pedidos.get(aux));
            } catch (InterruptedException ex) {
                Logger.getLogger(Armazem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void terminaTarefa(String id){
        Map<String, Integer> pedidos = this.tarefas.get(id).getPedidos();
        
        for(String aux : pedidos.keySet()){
            this.ferramentas.get(aux).reabastece(pedidos.get(aux));
        }
    }

    @Override
    public Tarefa getTarefa(String tarefa) {
        lt.lock();
        try{
            return this.tarefas.get(tarefa);
        }
        finally{
            lt.unlock();
        }
    }

    public String toString(){
        StringBuilder res = new StringBuilder();
        
        res.append("+++++++     Armazem     +++++++\n");
        res.append("******     Ferrmntas     ******\n");
        for(Ferramenta f : this.ferramentas.values())
            res.append(f.toString());
        res.append("******     Tarefas     ******\n");
        for(Tarefa t : this.tarefas.values())
            res.append(t.toString());
        res.append("++++++++++++++++++++++++++++++++\n");
        return res.toString();
    }
    
    
    
}
