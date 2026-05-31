package com.askal.ispbillingsystem;

import javax.swing.*;
import java.awt.*;

public class RoundPanel extends JPanel {
    private int arc; 
    private Color bg, border;
    
    public RoundPanel() {
        // Default constructor for the GUI builder palette
        this(10, Color.WHITE, new Color(220, 225, 235));
    }

    public RoundPanel(int arc, Color bg, Color border){
        this.arc = arc;
        this.bg = bg;
        this.border = border;
        setOpaque(false);
    }
    
    // Getters and setters so NetBeans can see properties in the inspector
    public void setBg(Color bg) { this.bg = bg; repaint(); }
    public void setBorderColor(Color border) { this.border = border; repaint(); }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2=(Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bg); g2.fillRoundRect(0,0,getWidth(),getHeight(),arc,arc);
        g2.setColor(border); g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,arc,arc);
        g2.dispose(); 
        super.paintComponent(g);
    }
}

