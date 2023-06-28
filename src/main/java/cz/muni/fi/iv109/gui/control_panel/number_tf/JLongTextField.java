package cz.muni.fi.iv109.gui.control_panel.number_tf;

public class JLongTextField extends JNumberTextField<Long> {

    public JLongTextField(Long value) {
        this(Long.MIN_VALUE, Long.MAX_VALUE, value);
    }

    public JLongTextField(Long min, Long max, Long value) {
        super("^[0-9]*$", min, max);
        this.setText(String.valueOf(value));
    }

    @Override
    protected boolean withinInterval(String text) {
        float number = Long.parseLong(text);
        return min <= number && number <= max;
    }

    @Override
    public Long getValue() {
        return Long.parseLong(getText());
    }
}
