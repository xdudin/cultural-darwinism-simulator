package cz.muni.fi.iv109.gui.control_panel.number_tf;

import lombok.AllArgsConstructor;

import javax.swing.JTextField;

@AllArgsConstructor
public abstract class JNumberTextField<T extends Number> extends JTextField {

    protected final String regex;

    protected final T min;
    protected final T max;

    @Override
    public String getText() throws IllegalArgumentException {
        String text = super.getText();

        if (text.isBlank())
            throw new IllegalArgumentException("text is empty");

        if (!text.matches(regex))
            throw new IllegalArgumentException("not a number");

        if (!withinInterval(text))
            throw new IllegalArgumentException("not between " + min + " and " + max);

        return text;
    }

    protected abstract boolean withinInterval(String text);

    public abstract T getValue();
}
