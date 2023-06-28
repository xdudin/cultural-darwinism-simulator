package cz.muni.fi.iv109.gui.control_panel.number_tf;

public class JLongTextField extends JNumberTextField<Long> {

    public JLongTextField(String name, Long min, Long max, Long value) {
        super(name, "^[0-9]*$", min, max);
        this.setText(String.valueOf(value));
    }

    @Override
    protected boolean withinInterval(String text) {
        Long number = Long.parseLong(text);
        return min <= number && number <= max;
    }

    @Override
    public Long getValue() {
        return Long.parseLong(getText());
    }
}
