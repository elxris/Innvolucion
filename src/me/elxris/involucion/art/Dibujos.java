package me.elxris.involucion.art;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Random;

public class Dibujos {
    private final int z = 500;
    //private final int b = z/20; //25
    private final int c = z/50; //10
    private final int d = c/2;//5
    private final int[] x;
    private final int[] y;
    private Random rndm = new Random();
    public Dibujos(int X[], int Y[]){
        x = X;
        y = Y;
    }
    public Polygon forma(int x[], int y[], int a){
        Polygon p = new Polygon();
        for(int i = x.length-1; i >= 0; i--){
            p.addPoint(this.x[a]+x[i], this.y[a]+y[i]);
        }
        return p;
    }
    public Polygon forma(int x[], int y[], int a, double posX, double posY, double z, double theta){
        Polygon p = new Polygon();
        double centerX, centerY, pointX, pointY;
        centerX = centro(x);
        centerY = centro(y);
        for(int i = x.length-1; i >= 0; i--){
            pointX = (x[i]-centerX)*z;
            pointY = (y[i]-centerY)*z;
            p.addPoint((int)((pointX*Math.cos(theta*Math.PI))-(pointY*Math.sin(theta*Math.PI))+posX), 
                    (int)((pointX*Math.sin(theta*Math.PI))+(pointY*Math.cos(theta*Math.PI))+posY));
        }
        return p;
    }
    public double centro(int n[]){
        double suma = 0;
        for(int i = n.length-1; i >= 0; i--){
            suma += n[i];
        }
        return suma/(n.length-1);
    }
    public void dibujo(int i, Graphics g, Boolean cuerpo, double x, double y, double z, double theta){
        Polygon p = new Polygon();
        Color col = new Color(rndm.nextInt(156)+100, rndm.nextInt(156)+100, rndm.nextInt(156)+100);
        int a = i*2;
        switch (i) {
        case 1: //Galletas
            int pX[] = {2*c, 3*c, 4*c, 4*c, 3*c, 2*c, c, c};
            int pY[] = {c, c, 2*c, 3*c, 4*c, 4*c, 3*c, 2*c};
            if(cuerpo){
                p = forma(pX, pY, a, x, y, z, theta);
            }else{
                p = forma(pX, pY, a);
            }
            g.setColor(new Color(255,196,140));
            g.fillPolygon(p);
            
            int pX1[] = {2*c+d, 2*c+2*d, 2*c+2*d, 2*c+d};
            int pY1[] = {c+d, c+d, c+2*d, c+2*d};
            if(cuerpo){
                p = forma(pX1, pY1, a, x, y, z, theta);
            }else{
                p = forma(pX1, pY1, a);
            }
            g.setColor(new Color(168,144,120));
            g.fillPolygon(p);
            
            int pX2[] = {c+d, c+2*d, c+2*d, c+d};
            int pY2[] = {2*c+d, 2*c+d, 2*c+2*d, 2*c+2*d};
            if(cuerpo){
                p = forma(pX2, pY2, a, x, y, z, theta);
            }else{
                p = forma(pX2, pY2, a);
            }
            g.setColor(new Color(168,144,120));
            g.fillPolygon(p);
            break;
        case 2: //Popó
            int pX3[] = {2*c, 3*c, 3*c, 3*c+d, 3*c+d, 4*c, 4*c, c, c, c+d, c+d, 2*c+d, 2*c+d};
            int pY3[] = {c, c+d, 2*c, 2*c+d, 3*c, 3*c+d, 4*c, 4*c, 3*c+d, 3*c, 2*c+d, 2*c, c+d, c};
            if(cuerpo){
                p = forma(pX3, pY3, a, x, y, z, theta);
            }else{
                p = forma(pX3, pY3, a);
            }
            g.setColor(new Color(168,144,120));
            g.fillPolygon(p);
            break;
        case 3: //Burbuja
            int pX4[] = {c+d, 3*c+d, 4*c, 4*c, 3*c+d, c+d, c, c};
            int pY4[] = {c, c, c+d, 3*c+d, 4*c, 4*c, 3*c+d, c+d};
            if(cuerpo){
                p = forma(pX4, pY4, a, x, y, z, theta);
            }else{
                p = forma(pX4, pY4, a);
            }
            g.setColor(new Color(179,199,235));
            g.fillPolygon(p);
            
            int pX5[] = {3*c, 3*c+d, 3*c+d, 3*c, 2*c+d};
            int pY5[] = {c+d, 2*c, 2*c+d, 2*c, c+d};
            if(cuerpo){
                p = forma(pX5, pY5, a, x, y, z, theta);
            }else{
                p = forma(pX5, pY5, a);
            }
            g.setColor(new Color(250,250,250));
            g.fillPolygon(p);
            break;
        case 4: //Dioxido
            int pX6[] = {c, 2*c+d, 4*c, 3*c, 4*c, 2*c+d, c, 2*c};
            int pY6[] = {c, 2*c, c, 2*c+d, 4*c, 3*c, 4*c, 2*c+d};
            if(cuerpo){
                p = forma(pX6, pY6, a, x, y, z, theta);
            }else{
                p = forma(pX6, pY6, a);
            }
            g.setColor(new Color(204,204,204));
            g.fillPolygon(p);
            break;
        case 5: //Comida-Oxigeno
            int pX7[] = {2*c+d, 4*c, c};
            int pY7[] = {c, 4*c, 4*c};
            if(cuerpo){
                p = forma(pX7, pY7, a, x, y, z, theta);
            }else{
                p = forma(pX7, pY7, a);
            }
            g.setColor(col);
            g.fillPolygon(p);
            break;
        case 6: //Comida-Dioxido
            int pX8[] = {c, 4*c, 4*c, c};
            int pY8[] = {c, c, 4*c, 4*c};
            if(cuerpo){
                p = forma(pX8, pY8, a, x, y, z, theta);
            }else{
                p = forma(pX8, pY8, a);
            }
            g.setColor(col);
            g.fillPolygon(p);
            break;
        case 7: //Desechos-Oxigeno
            int pX9[] = {2*c+d, 4*c, 3*c+d, c+d, c};
            int pY9[] = {c, 2*c, 4*c, 4*c, 2*c};
            if(cuerpo){
                p = forma(pX9, pY9, a, x, y, z, theta);
            }else{
                p = forma(pX9, pY9, a);
            }
            g.setColor(col);
            g.fillPolygon(p);
            break;
        case 8: //Desechos-Dioxido
            int pX10[] = {c+d, 3*c+d, 4*c, 3*c+d, c+d, c};
            int pY10[] = {c, c, 2*c+d, 4*c, 4*c, 2*c+d};
            if(cuerpo){
                p = forma(pX10, pY10, a, x, y, z, theta);
            }else{
                p = forma(pX10, pY10, a);
            }
            g.setColor(col);
            g.fillPolygon(p);
            break;
        default:
            break;
        }
    }
}
