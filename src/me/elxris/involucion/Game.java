package me.elxris.involucion;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import me.elxris.involucion.art.Art;
import me.elxris.involucion.art.Cuerpo;


public class Game extends JPanel{
    public static List<Art> gui = new ArrayList<Art>();
    public static List<Cuerpo> art = new ArrayList<Cuerpo>();
    public static int score, cuerpos;
    public Game(){
        super();
        setBackground(Color.BLACK);
    }
    public static void addCuerpo(Cuerpo e){
        art.add(e);
    }
    public void addGui(Art e){
        gui.add(e);
    }
    public void paint(Graphics g){
        super.paint(g);
        //Solo cuerpos
        for(Cuerpo c: art){
            if(c.isVivo()){
                c.dibujar(g);
            }else{
                art.remove(c);
            }
        }
        //Solo gui
        for(Art a: gui){
            a.dibujar(g);
        }
    }
    public static void getCuerpos(){
        cuerpos = 0;
        for(Cuerpo c: art){
            if(c.getTipo() >= 10){
                cuerpos++;
            }
        }
    }
}
