package gestor_de_tarefas;


import java.util.Map;
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
    
    public Tarefa(String ID, Map<String, Integer> pedidos, String utilizador) {
        this.ID = ID;
        this.pedidos = pedidos;
        this.utilizador = utilizador;
        this.l = new ReentrantLock();
        this.concluida = false;
    }
    
    public String getID() {
        return ID;
    }

    public Map<String, Integer> getPedidos() {
        return pedidos;
    }

    public String getUtilizador() {
        return utilizador;
    }
    
    public boolean getEstado(){
        return concluida;
    }
    
    public void concluir(){
        this.concluida = true;
    }
    
}

