package cz.muni.fi.iv109.gui.control_panel;


import cz.muni.fi.iv109.core.Agent;
import cz.muni.fi.iv109.core.PrngHolder;
import cz.muni.fi.iv109.core.Simulation;
import cz.muni.fi.iv109.core.SimulationParameters;
import cz.muni.fi.iv109.gui.SimulationPanel;
import cz.muni.fi.iv109.gui.control_panel.number_tf.JFloatTextField;
import cz.muni.fi.iv109.gui.control_panel.number_tf.JLongTextField;
import cz.muni.fi.iv109.setup.Disposition;
import cz.muni.fi.iv109.setup.SimulationFactory;
import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ControlPanel extends JPanel {

    private static final Random random = new Random();

    private final JButton resetButton = new JButton("Reset");
    private final JButton restartButton = new JButton("Restart");
    private final JButton startStopButton = new JButton("Start");

    private final JLabel tickCountLabel = new JLabel("Ticks: 0");
    private final JComboBox<Disposition> disposition = new JComboBox<>(new DefaultComboBoxModel<>(Disposition.values()));
    private final JLongTextField seed = new JLongTextField("seed", 4232L);
    private final JButton seedButton = new JButton("Seed");

    private final JLongTextField ups = new JLongTextField("UPS", 1L, 500L, 50L);
    private final JLongTextField numberOfAgents = new JLongTextField("Number of agents", 1L, 15000L, 1000L);
    private final JFloatTextField agentRadius = new JFloatTextField("Agent radius", 0.3f, 1.5f, 1.15f);
    private final JFloatTextField communicationRadius = new JFloatTextField("Communication radius", 0.5f, 50f, 5f);
    private final JFloatTextField distancePerStep = new JFloatTextField("Distance per step", 0.01f, 0.2f, 0.08f);
    private final JFloatTextField messageFactor = new JFloatTextField("Message factor", 0.0001f, 0.01f, 0.001f);
    private final JFloatTextField assimilationFactor = new JFloatTextField("Assimilation factor", 1f, 10f, 3f);
    private final JFloatTextField k_fertility = new JFloatTextField("Blue fertility", 0.5f, 3f, 1.5f);
    private final JFloatTextField r_fertility = new JFloatTextField("Red fertility", 3f, 7f, 4.5f);

    private final JTextArea errorTextArea = new JTextArea();
    private final JButton aboutProject = new JButton("About project");

    private final AtomicBoolean suspendFlag;
    private final SimulationPanel simulationPanel;

    public ControlPanel(
            int preferredHeight,
            AtomicBoolean suspendFlag,
            SimulationPanel simulationPanel
    ) {
        this.suspendFlag = suspendFlag;
        this.simulationPanel = simulationPanel;

        placeComponents(preferredHeight);
        simulationPanel.setSimulation(initSimulation());
        simulationPanel.startSimulationThread();
        simulationPanel.setTickCountCallback(tick -> tickCountLabel.setText("Ticks: " + tick));

        seedButton.addActionListener(al -> resetSeed());
        restartButton.addActionListener(al -> restartSimulation());

        resetButton.addActionListener(al -> {
            resetSeed();
            restartSimulation();
        });

        startStopButton.addActionListener(al -> {
            synchronized (suspendFlag) {
                suspendFlag.set(!suspendFlag.get());
                startStopButton.setText(getStartStopButtonLabel(startStopButton.getText()));
                suspendFlag.notify();
            }
        });
    }

    private void resetSeed() {
        long newSeed = random.nextLong();
        seed.setText(String.valueOf(Math.abs(newSeed % 10_000_000_000_000L)));
    }

    private Simulation initSimulation() {
        try {
            errorTextArea.setText("");
            SimulationParameters parameters = new SimulationParameters(
                    new PrngHolder(seed.getValue()),
                    distancePerStep.getValue(),
                    communicationRadius.getValue(),
                    messageFactor.getValue(),
                    assimilationFactor.getValue(),
                    k_fertility.getValue(),
                    r_fertility.getValue()
            );

            Agent[] agents = SimulationFactory.agents(
                    parameters,
                    Math.toIntExact(numberOfAgents.getValue()),
                    (Disposition) Objects.requireNonNull(disposition.getSelectedItem())
            );

            simulationPanel.setUPS(Math.toIntExact(ups.getValue()));
            simulationPanel.setAgentRadius(agentRadius.getValue());

            return new Simulation(parameters, agents);
        }
        catch (IllegalArgumentException e) {
            log.warn("User input error", e);
            errorTextArea.setText(e.getMessage());
            return null;
        }
    }

    private void restartSimulation() {
        synchronized (suspendFlag) {
            simulationPanel.setSimulation(initSimulation());
            simulationPanel.repaint();
            tickCountLabel.setText("Ticks: 0");
        }
    }

    private void placeComponents(int preferredHeight) {
        setLayout(new MigLayout("wrap 2", "[]push[]", ""));
        setPreferredSize(new Dimension(280, preferredHeight));

        add(restartButton, "growx");
        add(startStopButton, "growx");

        add(resetButton, "growx");
        add(tickCountLabel, "growx");

        add(new JSeparator(), "span 2, growx");

        add(seedButton, "growx");
        add(seed, "growx");

        add(new JLabel("Disposition"), "growx");
        add(disposition, "growx");

        add(new JLabel("UPS"), "growx");
        add(ups, "growx");

        add(new JLabel("Number of agents"), "growx");
        add(numberOfAgents, "growx");

        add(new JSeparator(), "span 2, growx");

        add(new JLabel("Assimilation factor"), "growx");
        add(assimilationFactor, "growx");

        add(new JLabel("Blue fertility"), "growx");
        add(k_fertility, "growx");

        add(new JLabel("Red fertility"), "growx");
        add(r_fertility, "growx");

        add(new JSeparator(), "span 2, growx");

        add(new JLabel("Agent radius"), "growx");
        add(agentRadius, "growx");

        add(new JLabel("Communication radius"), "growx");
        add(communicationRadius, "growx");

        add(new JLabel("Distance per step"), "growx");
        add(distancePerStep, "growx");

        add(new JLabel("Message factor"), "growx");
        add(messageFactor, "growx");

        add(errorTextArea, "span 2, hmin 40, hmax 40, wmin 250, wmax 250, pushy, bottom");
        errorTextArea.setBackground(getBackground());
        errorTextArea.setLineWrap(true);

        add(aboutProject, "span 2, growx");
    }

    private String getStartStopButtonLabel(String label) {
        return switch (label) {
            case "Start" -> "Stop";
            case "Stop" -> "Start";
            default -> throw new IllegalArgumentException("Unknown button label");
        };
    }
}
