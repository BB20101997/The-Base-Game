package de.webtwob.the.base.game.api.gui;

@SuppressWarnings({"squid:S1845", "FieldNamingConvention"})
public class Color {

    public static final Color BASE  = new Color(0, 0, 0, 0);
    public static final Color BLACK = BASE.withAlpha(1f);
    public static final Color RED   = BLACK.withRed(1f);
    public static final Color GREEN = BLACK.withGreen(1f);
    public static final Color BLUE  = BLACK.withBlue(1f);
    public static final Color WHITE = BLACK.withRed(1f).withGreen(1f).withBlue(1f);

    public final float red;
    public final float green;
    public final float blue;
    public final float alpha;

    private Color(float red, float green, float blue, float alpha) {
        if (red > 1 || green > 1 || blue > 1 || alpha > 1) {
            throw new IllegalArgumentException(
                    "RGBA components need to be less or equal to one but where [" + red + ", " + green + ", " + blue + ", " + alpha + "]");
        }

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public Color withRed(float red) {
        if (this.red == red) {
            return this;
        }
        return new Color(red, green, blue, alpha);
    }

    public Color withGreen(float green) {
        if (this.green == green) {
            return this;
        }
        return new Color(red, green, blue, alpha);
    }

    public Color withBlue(float blue) {
        if (this.blue == blue) {
            return this;
        }
        return new Color(red, green, blue, alpha);
    }

    public Color withAlpha(float alpha) {
        if (this.alpha == alpha) {
            return this;
        }
        return new Color(red, green, blue, alpha);
    }

    public Color withRGB(float red, float green, float blue) {
        if (this.red == red && this.green == green && this.blue == blue) {
            return this;
        }
        return new Color(red, green, blue, alpha);
    }

}
