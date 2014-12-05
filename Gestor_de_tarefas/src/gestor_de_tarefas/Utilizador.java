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
public class Utilizador implements InterfaceUtilizador {
    
    private String username;
    private String password;
    public ReentrantLock l;
    
    public Utilizador(String username, String pwd){
        this.username = username;
        this.password = pwd;
        this.l = new ReentrantLock();
    }

    @Override
    public boolean validaPass(String passwd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUsername() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
