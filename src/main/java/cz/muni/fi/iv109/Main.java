package cz.muni.fi.iv109;

import com.formdev.flatlaf.FlatLightLaf;
import cz.muni.fi.iv109.gui.UIBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.swing.UIManager;

@Slf4j
public class Main {

    private static final int NUMBER_OF_AGENTS = 1;

    private Main() {
        throw new AssertionError("Not initializable");
    }

    public static void main(String[] args) {
        initNimbusLookAndFeel();
        UIBuilder.buildMainWindow(NUMBER_OF_AGENTS).startSimulation();
    }

    private static void initNimbusLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            log.error("LookAndFeel initialization failed", ex);
        }
    }
}
