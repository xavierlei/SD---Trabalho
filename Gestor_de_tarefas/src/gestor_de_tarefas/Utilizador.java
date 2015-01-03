/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author xavier
 */
public class Utilizador {
    private String username;
    private String password;
    public ReentrantLock l;
    private boolean loged;    

    public Utilizador(String username, String pwd) {
        this.username = username;
        this.password = pwd;
        this.l = new ReentrantLock();
        this.loged = false;
    }

    public boolean validaPass(String passwd) {
        return (this.password.equals(passwd));
    }
    
    public String getUsername() {
        return this.username;
    }

    public boolean login() {
        l.lock();
        try {
            if (!this.loged) {
                this.loged = true;
                return true;
            } else {
                return false;
            }
        } finally {
            l.unlock();
        }
    }

    public void logout() {
        l.lock();
        try {
            this.loged = false;
        } finally {
            l.unlock();
        }
    }

    public boolean getloged() {
        l.lock();
        try {
            return this.loged;
        } finally {
            l.unlock();
        }
    }
}
