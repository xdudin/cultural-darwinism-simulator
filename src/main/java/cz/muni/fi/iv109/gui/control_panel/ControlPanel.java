package cz.muni.fi.iv109.gui.control_panel;


import cz.muni.fi.iv109.gui.control_panel.number_tf.JFloatTextField;
import cz.muni.fi.iv109.gui.control_panel.number_tf.JIntegerTextField;
import cz.muni.fi.iv109.setup.Disposition;
import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControlPanel extends JPanel {

    private final JButton resetButton = new JButton("Reset");
    private final JButton startStopButton = new JButton("Start");

    private final JComboBox<Disposition> disposition = new JComboBox<>(new DefaultComboBoxModel<>(Disposition.values()));
    private final JIntegerTextField seed = new JIntegerTextField(1001);

    private final JIntegerTextField ups = new JIntegerTextField(1, 500, 50);
    private final JIntegerTextField numberOfAgents = new JIntegerTextField(1, 15000, 1000);
    private final JFloatTextField agentRadius = new JFloatTextField(0.3f, 1.5f, 1.15f);
    private final JFloatTextField communicationRadius = new JFloatTextField(0.25f, 50f, 5f);
    private final JFloatTextField distancePerStep = new JFloatTextField(0.01f, 0.2f, 0.08f);
    private final JFloatTextField messageFactor = new JFloatTextField(0.0001f, 0.01f, 0.001f);
    private final JFloatTextField assimilationFactor = new JFloatTextField(1f, 10f, 3f);
    private final JFloatTextField k_fertility = new JFloatTextField(0.5f, 3f, 1.5f);
    private final JFloatTextField r_fertility = new JFloatTextField(3f, 7f, 4.5f);

    private final JTextArea errorTextArea = new JTextArea();
    private final JButton aboutProject = new JButton("About project");

    public ControlPanel(int preferredHeight, AtomicBoolean suspendFlag) {
        placeComponents(preferredHeight);

        Random random = new Random();
        resetButton.addActionListener(al -> {
            long newSeed = random.nextLong();
            seed.setText(String.valueOf(Math.abs(newSeed % 10000000000000L)));
        });

        startStopButton.addActionListener(al -> {
            synchronized (suspendFlag) {
                suspendFlag.set(!suspendFlag.get());
                startStopButton.setText(getStartStopButtonLabel(startStopButton.getText()));
                suspendFlag.notify();
            }
        });
    }

    private void placeComponents(int preferredHeight) {
        setLayout(new MigLayout("wrap 2", "[]push[]", ""));
        setPreferredSize(new Dimension(280, preferredHeight));

        add(resetButton, "growx");
        add(startStopButton, "growx");

        add(new JSeparator(), "span 2, growx");

        add(new JLabel("Seed"));
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

        add(errorTextArea, "span 2, hmin 100, hmax 100, wmin 250, wmax 250, pushy, bottom");
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
