package cz.muni.fi.iv109.gui;

import com.formdev.flatlaf.FlatLightLaf;
import lombok.extern.slf4j.Slf4j;

import javax.swing.UIManager;

@Slf4j
public class Main {

    public static void main(String[] args) {
        initFlatLightLookAndFeel();
        UIBuilder.buildMainWindow();
    }

    private static void initFlatLightLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            log.error("LookAndFeel initialization failed", ex);
        }
    }
}
