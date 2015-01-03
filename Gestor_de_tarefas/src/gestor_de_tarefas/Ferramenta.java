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
public class Ferramenta {
    private final String nome; 
    private int quantidade;
    private int disponivel;
    
    final Lock lock = new ReentrantLock();
    final Condition maisDisponivel = lock.newCondition(); 

    public Ferramenta(String nome, int quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.disponivel = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public int getDisponivel() {
        return this.disponivel;
    }        
    
    public void abastece(int qtd){        
        lock.lock();
        try {
            this.quantidade += qtd;
            this.disponivel +=qtd;
            //envia sinal as Threds que estão adormecidas à espera de disponibilidade
            maisDisponivel.signalAll();
        } finally {
            lock.unlock();
        }
    }       
    
    public void reabastece(int qtd){
        lock.lock();
        try {
            this.disponivel += qtd;
            maisDisponivel.signalAll();
        } finally {
            lock.unlock();
        }
    }
    
    public void reserva(int qtd) throws InterruptedException{
        lock.lock();
        try {
            while(this.disponivel < qtd){
                /*System.out.println("A disponibilidade de "+this.nome+" é "+this.disponivel+", vou esperar!");*/
                //adormece e fica a espera que disponibilidade aumente
                maisDisponivel.await();
            }
            this.disponivel -= qtd;
        } finally {
            lock.unlock();
        }            
    }
    
    public String toString() {
        return "Ferramenta: nome=" + this.nome + ", quantidade=" + this.quantidade + ", disponibilidade " + this.disponivel + "; \n";
    }
    
    
}
