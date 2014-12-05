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
import java.util.concurrent.locks.ReentrantLock;

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
            return res;
            }
        }
        finally{
         lt.unlock();   
        }
        
    }

    @Override
    public void notifications(List<String> tarefas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void abastece(String ferramenta, int quant) {
        lf.lock();
        try{
            if(this.ferramentas.containsKey(ferramenta))
                this.ferramentas.get(ferramenta).abastece(quant);
            else{
                Ferramenta f = new Ferramenta(ferramenta,quant);
                this.ferramentas.put(ferramenta,f);
            }
        }
        finally{
            lf.unlock();
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

    @Override
    public void executaTarefa(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
