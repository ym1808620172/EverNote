package come.evernote.evernote.model.bean;

/**
 * Created by dllo on 16/10/22.
 * TextNotesActivity页面的附件抽屉的实体类
 */
public class AttachDrawerBean {
    private String text;
    private int image;

    public AttachDrawerBean() {
        super();
    }

    public AttachDrawerBean(String text, int image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public AttachDrawerBean setText(String text) {
        this.text = text;
        return this;
    }

    public int getImage() {
        return image;
    }

    public AttachDrawerBean setImage(int image) {
        this.image = image;
        return this;
    }
}
