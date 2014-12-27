package gestor_de_tarefas;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author filiperibeiro
 */
public class TipoTarefa {

    private String ID;
    private Map<String, Integer> pedidos;
    private Map<String, Tarefa> tarefas;
    public ReentrantLock lockTarefas;

    public TipoTarefa(String ID, Map<String, Integer> pedidos) {
        this.ID = ID;
        this.pedidos = pedidos;
        this.tarefas = new HashMap<String, Tarefa>();
        
        this.lockTarefas = new ReentrantLock();
    }

    public String getID() {
        return ID;
    }

    public Map<String, Integer> getPedidos() {        
        return this.pedidos;
    }
    
    public Tarefa getTarefa(String id){
        return this.tarefas.get(id);
    }
    
    /*
    public void removeTarefa(String id){
        this.tarefas.remove(id);
    }*/
    
    public void addTarefa(Tarefa t){
        lockTarefas.lock();
        this.tarefas.put(t.getId(), t);
        lockTarefas.unlock();
    }
    
    public boolean existeID(String id){
        return this.tarefas.containsKey(id);
    }
    
    /*
    public Integer calcExecutaveis(){
        return this.tarefas.size();
    }
    */
    
    public String lista(){
        StringBuilder res = new StringBuilder();
        res.append("Tipo de Tarefa: " + this.getID() + "\n");
        res.append("Ferramentas: ");
        for (String aux : this.pedidos.keySet()) {            
            res.append(aux + ", " + this.pedidos.get(aux) + "; ");
        }
        res.append("Tarefas em curso:\n");
        for(Tarefa t: this.tarefas.values()){
            if(!t.isTerminado())       
                res.append(t.toString());
        }
        return res.toString();    
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Tipo de Tarefa: " + this.getID() + "\n");
        res.append("Ferramentas: ");
        for (String aux : this.pedidos.keySet()) {
            res.append(aux + ", " + this.pedidos.get(aux) + "; ");
        }
        res.append("Tarefas em curso:\n");
        for(Tarefa t: this.tarefas.values())
            res.append(t.toString());
        return res.toString();
    }

}
