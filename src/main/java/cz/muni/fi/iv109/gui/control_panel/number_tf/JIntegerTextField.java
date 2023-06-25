package cz.muni.fi.iv109.gui.control_panel.number_tf;

public class JIntegerTextField extends JNumberTextField<Integer> {

    public JIntegerTextField(Integer value) {
        this(Integer.MIN_VALUE, Integer.MAX_VALUE, value);
    }

    public JIntegerTextField(Integer min, Integer max, Integer value) {
        super("^[0-9]*$", min, max);
        this.setText(String.valueOf(value));
    }

    @Override
    protected boolean withinInterval(String text) {
        float number = Integer.parseInt(text);
        return min < number && number < max;
    }

    @Override
    public Integer getValue() {
        return Integer.parseInt(getText());
    }
}
