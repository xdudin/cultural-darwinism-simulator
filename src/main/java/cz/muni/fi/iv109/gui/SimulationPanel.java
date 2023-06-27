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
import java.util.concurrent.atomic.AtomicBoolean;

import static cz.muni.fi.iv109.core.Simulation.PLAYGROUND_SIZE;

public class SimulationPanel extends JPanel implements Runnable {

    private static final int UPS = 10;
    private static final long RENDER_INTERVAL = 1_000_000_000 / UPS; // nanoseconds
    private static final float AGENT_RADIUS = 1.15f;
    private static final boolean DEBUG = false;

    private final float simulationPanelScale;
    private final Simulation simulation;
    private final Thread simulationThread;
    private final int agentRadius;

    private final AtomicBoolean suspendFlag;

    public SimulationPanel(
            int simulationPlaneSize,
            Simulation simulation,
            AtomicBoolean suspendFlag
    ) {
        this.simulation = simulation;
        this.suspendFlag = suspendFlag;

        simulationPanelScale = (float) simulationPlaneSize / PLAYGROUND_SIZE;
        simulationThread = new Thread(this);
        agentRadius = (int) (AGENT_RADIUS * simulationPanelScale);

        this.setPreferredSize(new Dimension(simulationPlaneSize, simulationPlaneSize));
        this.setBackground(Color.BLACK);
    }

    public void startSimulationThread() {
        simulationThread.start();
    }

    @Override
    public void run() {
        long nextRenderTime = System.nanoTime() + RENDER_INTERVAL;

        //noinspection InfiniteLoopStatement
        while (true) {
            synchronized (suspendFlag) {
                while (suspendFlag.get()) {
                    waitInterrupt();
                    nextRenderTime = System.nanoTime() + RENDER_INTERVAL;
                }
            }

            simulation.doStep();
            this.repaint();
            nextRenderTime = waitFps(nextRenderTime);
        }
    }

    private void waitInterrupt() {
        try {
            suspendFlag.wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Agent agent: simulation.getGrid().getAgents()) {
            g2.setColor(computeColor(agent.getCulture()));

            int x = (int) (agent.getPosition().getX() * simulationPanelScale - agentRadius);
            int y = (int) (agent.getPosition().getY() * simulationPanelScale - agentRadius);
            g2.fillOval(x, y, agentRadius * 2, agentRadius * 2);

            if (DEBUG) {
                int radius = (int) (simulation.getParameters().communicationRadius() * simulationPanelScale);
                int xr = (int) (agent.getPosition().getX() * simulationPanelScale - radius);
                int yr = (int) (agent.getPosition().getY() * simulationPanelScale - radius);
                g2.drawOval(xr, yr, radius * 2, radius * 2);
            }
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
        sleepInterrupt(timeToSleep);
        return nextRenderTime + RENDER_INTERVAL;
    }

    private void sleepInterrupt(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
