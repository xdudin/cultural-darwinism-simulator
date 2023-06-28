package cz.muni.fi.iv109.gui.control_panel.number_tf;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.JTextField;

@AllArgsConstructor
public abstract class JNumberTextField<T extends Number> extends JTextField {

    @Getter
    protected final String name;
    protected final String regex;

    protected final T min;
    protected final T max;

    @Override
    public String getText() throws IllegalArgumentException {
        String text = super.getText();

        if (text.isBlank())
            throw new IllegalArgumentException(name + " \nis empty");

        if (!text.matches(regex))
            throw new IllegalArgumentException(name + " \nis not a number");

        if (!withinInterval(text))
            throw new IllegalArgumentException(name + " \nis not between " + min + " and " + max);

        return text;
    }

    protected abstract boolean withinInterval(String text);

    public abstract T getValue();
}
