package cz.muni.fi.iv109.gui;

import cz.muni.fi.iv109.core.Agent;
import cz.muni.fi.iv109.core.Simulation;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import static cz.muni.fi.iv109.core.Simulation.PLAYGROUND_SIZE;

public class SimulationPanel extends JPanel implements Runnable {

    private static final int FPS = 50;
    private static final long RENDER_INTERVAL = 1_000_000_000 / FPS; // nanoseconds
    private static final float RELATIVE_AGENT_RADIUS = 2f;

    private final float simulationPanelScale;
    private final Simulation simulation;
    private final Thread simulationThread;
    private final int agentRadius;

    public SimulationPanel(int simulationPlaneSize, Simulation simulation) {
        this.simulation = simulation;

        simulationPanelScale = simulationPlaneSize / PLAYGROUND_SIZE;
        simulationThread = new Thread(this);
        agentRadius = (int) (RELATIVE_AGENT_RADIUS * simulationPanelScale);

        this.setPreferredSize(new Dimension(simulationPlaneSize, simulationPlaneSize));
        this.setBackground(Color.BLACK);
    }

    public void startSimulationThread() {
        simulationThread.start();
    }

    @Override
    public void run() {
        long nextRenderTime = System.nanoTime() + RENDER_INTERVAL;

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

        for (Agent agent: simulation.getAgents()) {
            g2.setColor(computeColor(agent.getCulture()));

            int x = (int) (agent.getPosition().getX() * simulationPanelScale - agentRadius / 2);
            int y = (int) (agent.getPosition().getY() * simulationPanelScale - agentRadius / 2);
            g2.fillOval(x, y, agentRadius, agentRadius);
        }

        g2.dispose();
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * r-strategy red color
     * <br>
     * k-strategy blue color
     */
    private Color computeColor(float culture) {
        int red, green, blue;

        if (culture < 0) { // r-dominant
            int color = (int) (255 + culture * (255 / 100));
            red = 255;
            green = color;
            blue = color;
        } else { // k-dominant
            int color = (int) (255 - culture * (255 / 100));
            red = color;
            green = color;
            blue = 255;
        }

        return new Color(red, green, blue);
    }

    private long waitFps(long nextRenderTime) {
        long timeToSleep = (nextRenderTime - System.nanoTime()) / 1_000_000;
        if (timeToSleep < 0) timeToSleep = 0;
        sleep(timeToSleep);
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
