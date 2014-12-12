package gestor_de_tarefas;


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
public class Tarefa implements InterfaceTarefa{
    private String ID;
    private Map<String,Integer> pedidos;
    private String utilizador;
    public ReentrantLock l;
    private boolean concluida;
    public Condition c;
    
    public Tarefa(String ID, Map<String, Integer> pedidos, String utilizador) {
        this.ID = ID;
        this.pedidos = pedidos;
        this.utilizador = utilizador;
        this.l = new ReentrantLock();
        this.concluida = false;
        this.c = l.newCondition();
    }
    
    @Override
    public String getID() {
        return ID;
    }

    @Override
    public Map<String, Integer> getPedidos() {
            return pedidos;
    }

    @Override
    public String getUtilizador() {
        return utilizador;
    }
    
    @Override
    public boolean getEstado(){
        l.lock();
        try {
            return concluida;
        } finally {
            l.unlock();
        }
    }
    
    @Override
    public void concluir(){
        l.lock();
        try {
            this.concluida = true;
        } finally {
            l.unlock();
        }
        this.c.signalAll();
    }
    
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("Ferramenta id : "+this.getID()+", user "+this.getUtilizador()+"\n");
        for(String aux : this.pedidos.keySet())
            res.append("--> "+aux+", "+this.pedidos.get(aux)+";\n");
        return res.toString();
    }
    
}

