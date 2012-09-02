package me.elxris.involucion.art;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Puntero implements Art {
    int posX, posY;
    public Puntero(){
        posX = 0;
        posY = 0;
    }
    public void dibujar(Graphics g) {
        g.setColor(Color.WHITE);
        Polygon p = new Polygon();
        int an = 10; //Ancho cursor
        int al = 10; //Alto cursor
        p.addPoint(posX+(an/2), posY+al);
        p.addPoint(posX-(an/2), posY+al);
        p.addPoint(posX, posY);
        g.fillPolygon(p);
    }
    public void mover(int x, int y){
        posX = x;
        posY = y;
    }
}
