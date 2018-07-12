package de.webtwob.the.base.game.api.gui;

public class Color {

    public final float red;
    public final float green;
    public final float blue;
    public final float alpha;

    public Color(){
        this(0,0,0,0);
    }

    public Color(float red,float green, float blue, float alpha){
        this.red = red>1?red-(int)red:red;
        this.green = green>1?green-(int)green:green;
        this.blue = blue>1?blue-(int)blue:blue;
        this.alpha = alpha>1?alpha-(int)alpha:alpha;
    }


}
