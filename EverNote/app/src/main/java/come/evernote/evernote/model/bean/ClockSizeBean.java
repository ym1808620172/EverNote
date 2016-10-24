package come.evernote.evernote.model.bean;

/**
 * Created by dllo on 16/10/21.
 */
public class ClockSizeBean {
    private int width;
    private int height;

    public ClockSizeBean() {
    }

    public ClockSizeBean(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
