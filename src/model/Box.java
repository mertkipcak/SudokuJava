package model;

public class Box {
    public int value;
    public boolean viable;
    public boolean locked;

    public Box() {
        value = 0;
        viable = true;
        locked = false;
    }

    public Box(int i) {
        value = i;
        viable = true;
        locked = false;
    }

    public void setValue(int i) {
        if(!locked) {
            value = i;
        }
    }
}
