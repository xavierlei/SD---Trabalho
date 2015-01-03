/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author xavier
 */
public class Armazem {

    private Map<String, Ferramenta> ferramentas;
    private Map<String, TipoTarefa> tiposTarefa;
    private ReentrantLock lockFerr;
    private ReentrantLock lockTipos;
    private Integer idGen = 1000;

    public Armazem() {
        this.ferramentas = new HashMap<>();
        this.tiposTarefa = new HashMap<>();

        this.lockFerr = new ReentrantLock();
        this.lockTipos = new ReentrantLock();
    }

    public String listagemTarefas() {
        StringBuilder res = new StringBuilder();

        for (TipoTarefa t : this.tiposTarefa.values()) {
            res.append(t.lista());
            res.append("\n");
        }
        return res.toString();
    }

    public boolean existeTarefaId(String id) {
        TipoTarefa tipo;
        Iterator it;
        lockTipos.lock();
        try {
            it = this.tiposTarefa.values().iterator();
        } finally {
            lockTipos.unlock();
        }
        while (it.hasNext()) {
            tipo = (TipoTarefa) it.next();
            if (tipo.existeID(id)) {
                return true;
            }
        }
        return false;
    }
    
    public Tarefa getTarefa(String id) {
        TipoTarefa tipo;
        Iterator it;
        lockTipos.lock();
        try {
            it = this.tiposTarefa.values().iterator();
        } finally {
            lockTipos.unlock();
        }
        boolean flag = true;

        while (flag && it.hasNext()) {
            tipo = (TipoTarefa) it.next();
            if (tipo.existeID(id)) {
                return tipo.getTarefa(id);
            }
        }
        return null;
    }

    public void notifica(List<String> ids) throws InterruptedException, TarefaException {
        Tarefa t;
        
        for (String id : ids) {
            if(!this.existeTarefaId(id))
                throw new TarefaException("nao existe a tarefa com o id "+id);
        }
        for (String id : ids) {
            t = this.getTarefa(id);             
            t.espera();           
        }
    }

    public void abastece(String ferramenta, int quant) {
        lockFerr.lock();
        try {
            if (this.ferramentas.containsKey(ferramenta)) {
                this.ferramentas.get(ferramenta).abastece(quant);
            } else {
                Ferramenta f = new Ferramenta(ferramenta, quant);
                this.ferramentas.put(ferramenta, f);
            }
        } finally {
            lockFerr.unlock();
        }
    }

    public void addTarefa(TipoTarefa t) throws TarefaException {
        lockFerr.lock();
        try {
            if (this.tiposTarefa.containsKey(t.getID())) {
                throw new TarefaException("a tarefa que pretende adicionar ja existe");
            }
            this.tiposTarefa.put(t.getID(), t);
        } finally {
            lockFerr.unlock();
        }
    }

    public String executaTarefa(String id, String user) throws FerramentaException, TarefaException {
        if (!this.tiposTarefa.containsKey(id)) {
            throw new TarefaException("a terefa " + id + " não existe");
        }
        TipoTarefa tipo = this.tiposTarefa.get(id);
        Map<String, Integer> pedidos = tipo.getPedidos();

        for (String s : pedidos.keySet()) {
            if (!this.ferramentas.containsKey(s)) {
                throw new FerramentaException("ferramenta " + s + " não existe");
            }
        }
        for (String aux : pedidos.keySet()) {
            try {
                this.ferramentas.get(aux).reserva(pedidos.get(aux));
            } catch (InterruptedException ex) {
            }
        }
        String cod = this.idGen.toString();
        this.idGen++;
        tipo.addTarefa(new Tarefa(cod, user));
        return cod;
    }

    public void terminaTarefa(String id, String user) throws TarefaException {
        TipoTarefa tipo = null;
        Iterator it;

        this.lockTipos.lock();
        try {
            it = this.tiposTarefa.values().iterator();
        } finally {
            this.lockTipos.unlock();
        }
        boolean flag = true;

        while (flag && it.hasNext()) {
            tipo = (TipoTarefa) it.next();
            if (tipo.existeID(id)) {
                flag = false;
            }
        }

        if (flag) {
            throw new TarefaException("a tarefa não existe!");
        }
        Tarefa t = tipo.getTarefa(id);
        if (!t.getUtilizador().equals(user)) {
            throw new TarefaException("a tarefa pertence a outro utilizador!");
        }
        if (t.isTerminado()) {
            throw new TarefaException("a tarefa já foi terminada!");
        }

        Map<String, Integer> pedidos = tipo.getPedidos();
        for (String aux : pedidos.keySet()) {
            lockFerr.lock();
            try {
                this.ferramentas.get(aux).reabastece(pedidos.get(aux));
            } finally {
                lockFerr.unlock();
            }
        }
        t.termina();
    }

    public TipoTarefa getTipoTarefa(String tarefa) {
        lockFerr.lock();
        try {
            return this.tiposTarefa.get(tarefa);
        } finally {
            lockFerr.unlock();
        }
    }

    public String toString() {
        StringBuilder res = new StringBuilder();

        res.append("+++++++     Armazem     +++++++\n");
        res.append("******     Ferramentas     ******\n");
        for (Ferramenta f : this.ferramentas.values()) {
            res.append(f.toString());
        }
        res.append("******     Tarefas     ******\n");
        for (TipoTarefa t : this.tiposTarefa.values()) {
            res.append(t.toString());
        }
        res.append("++++++++++++++++++++++++++++++++\n");
        return res.toString();
    }

}
