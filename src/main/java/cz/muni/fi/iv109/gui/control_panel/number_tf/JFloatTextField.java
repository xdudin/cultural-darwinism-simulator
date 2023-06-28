package cz.muni.fi.iv109.gui.control_panel.number_tf;

public class JFloatTextField extends JNumberTextField<Float> {

    public JFloatTextField(Float min, Float max, Float value) {
        super("(^[0-9]+.[0-9]+$)|(^[0-9]*$)", min, max);
        setText(String.valueOf(value));
    }

    @Override
    protected boolean withinInterval(String text) {
        float number = Float.parseFloat(text);
        return min <= number && number <= max;
    }

    @Override
    public Float getValue() {
        return Float.parseFloat(getText());
    }
}
