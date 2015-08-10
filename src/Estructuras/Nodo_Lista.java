/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Denis
 */
public class Nodo_Lista implements Runnable {

    public Nodo_Lista sig;
    public Nodo_Lista ant;
    public int indice;
    public String nombre, tipo, ruta;
    public Nodo_Matriz casilla = null;
    public boolean MovIzq = true;
    public boolean running = false;

    public Nodo_Lista(int indice, String nombre, String tipo, String ruta) {
        this.indice = indice;
        this.nombre = nombre;
        this.tipo = tipo;
        this.ruta = ruta;
    }

    @Override
    public void run() {
        while (running) {
            boolean caida = VerificarCaida();
            if (!tipo.equals("Principal") && !caida) {
                if (MovIzq && VerificarIzq()) {
                    MoverIzquierda();
                } else if (VerificarDer()) {
                    MoverDerecha();
                }
            }
            //System.out.println("Corre");
            if (caida) {
                MoverAbajo();
            }
        }
    }

    private void MoverAbajo() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
        }
        //System.out.println("Abajo");
        casilla.Objeto = null;
        casilla = casilla.abajo;
        casilla.Objeto = this;
        SetIcon(casilla.arriba);
        SetIcon(casilla);
        
    }

    private void MoverIzquierda() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
        }
        //System.out.println("Abajo");
        casilla.Objeto = null;
        casilla = casilla.izquierda;
        casilla.Objeto = this;
        SetIcon(casilla.derecha);
        SetIcon(casilla);
        if (!VerificarIzq()) {
            MovIzq = false;
        }
    }

    private void MoverDerecha() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
        }
        //System.out.println("Abajo");
        casilla.Objeto = null;
        casilla = casilla.derecha;
        casilla.Objeto = this;
        SetIcon(casilla.izquierda);
        SetIcon(casilla);
        if (!VerificarDer()) {
            MovIzq = true;
        }
    }

    private boolean VerificarCaida() {
        if (casilla.abajo != null) {
            if (casilla.abajo.Objeto != null) {
                Nodo_Lista Aux1 = casilla.abajo.Objeto;
                if (!(Aux1.tipo.equals("Suelo") || Aux1.tipo.equals("Pared"))) {
                    if (tipo.equals("Goomba") || tipo.equals("Koopa")) {
                        if(Aux1.tipo.equals("Koopa")){
                            return false;
                        }else if(Aux1.tipo.equals("Goomba")){
                            Aux1.running = false;
                            Aux1.casilla.Objeto=null;
                            SetIcon(Aux1.casilla);
                            Aux1.casilla = null;
                        }
                        
                    }
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean VerificarIzq() {
        if (casilla.izquierda != null) {
            if (casilla.izquierda.Objeto != null) {
                //Nodo_Lista Aux1 = casilla.izquierda.Objeto;
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean VerificarDer() {
        if (casilla.derecha != null) {
            if (casilla.derecha.Objeto != null) {
                //Nodo_Lista Aux1 = casilla.derecha.Objeto;
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void SetIcon(Nodo_Matriz casilla) {
        if(casilla.Principal != null){
            casilla.setIcon(new ImageIcon(casilla.Principal.ruta));
        }else if(casilla.Objeto != null){
            casilla.setIcon(new ImageIcon(casilla.Objeto.ruta));
        }else if(casilla.Item!=null){
            casilla.setIcon(new ImageIcon(casilla.Item.ruta));
        }else{
            casilla.setIcon(null);
        }
    }
}
