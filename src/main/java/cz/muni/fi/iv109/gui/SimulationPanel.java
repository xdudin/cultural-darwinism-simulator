package cz.muni.fi.iv109.gui;

import cz.muni.fi.iv109.core.Simulation;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

public class SimulationPanel extends JPanel implements Runnable {

    private static final int FPS = 60;
    private static final float RENDER_INTERVAL = 1_000_000_000.0f / FPS;

    private final Simulation simulation;
    private final Thread simulationThread;
    private final int agentRadius;

    public SimulationPanel(int playgroundSize, int numberOfAgents) {
        simulation = new Simulation(playgroundSize, numberOfAgents);
        simulationThread = new Thread(this);
        agentRadius = simulation.getPlaygroundSize() / 40;

        this.setPreferredSize(new Dimension(playgroundSize, playgroundSize));
        this.setBackground(Color.BLACK);
    }

    public void startSimulationThread() {
        simulationThread.start();
    }

    @Override
    public void run() {
        float nextRenderTime = System.nanoTime() + RENDER_INTERVAL;

        while (true) {
            simulation.doStep();
            this.repaint();
            nextRenderTime = waitFps(nextRenderTime);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        simulation.getAgents().forEach(agent -> {
            g2.setColor(computeColor(agent.getCulture()));

            int x = (int) agent.getPosition().getX() - agentRadius / 2;
            int y = (int) agent.getPosition().getY() - agentRadius / 2;
            g2.fillOval(x, y, agentRadius, agentRadius);
        });

        g2.dispose();
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * r-strategy red color
     * <br>
     * k-strategy blue color
     */
    private Color computeColor(byte culture) {
        int red, green, blue;

        if (culture < 0) { // r-dominant
            int color = 255 + culture * (255 / 100);
            red = 255;
            green = color;
            blue = color;
        } else { // k-dominant
            int color = 255 - culture * (255 / 100);
            red = color;
            green = color;
            blue = 255;
        }

        return new Color(red, green, blue);
    }

    private float waitFps(float nextRenderTime) {
        float timeToSleep = (nextRenderTime - System.nanoTime()) / 1_000_000;
        if (timeToSleep < 0) timeToSleep = 0;
        sleep((long) timeToSleep);
        return nextRenderTime + RENDER_INTERVAL;
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
