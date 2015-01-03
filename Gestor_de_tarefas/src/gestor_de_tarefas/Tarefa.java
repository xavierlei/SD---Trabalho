/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author carlosmorais
 */
public class Tarefa {
    private String id;
    private String utilizador;
    private boolean terminado;
    
    final Lock lock = new ReentrantLock();
    final Condition termina = lock.newCondition();

    public Tarefa(String id, String utilizador) {
        this.id = id;
        this.utilizador = utilizador;
        this.terminado = false;
    }

    public String getId() {
        return this.id;
    }

    public String getUtilizador() {
        return this.utilizador;
    }

    public boolean isTerminado() {        
        return this.terminado;
    }        
    
    public void termina(){
        lock.lock();
        try {
            this.terminado = true;
            termina.signalAll();
        } finally {
            lock.unlock();
        }
    }
    
    public void espera() throws InterruptedException{
        lock.lock();
        try {
            while(!this.terminado)
                termina.await();
        } finally {
            lock.unlock();
        }
    }

    public String getIdUser(){
        return "=> Tarefa id = " + id + ", utilizador = " + utilizador +";\n";
    }
    

    @Override
    public String toString() {
        return "=> Tarefa id = " + id + ", utilizador = " + utilizador + ", terminado = " + terminado +";\n";
    }        
    
}
