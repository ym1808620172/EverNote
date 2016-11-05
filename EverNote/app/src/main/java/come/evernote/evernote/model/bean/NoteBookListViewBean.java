package come.evernote.evernote.model.bean;

import java.io.Serializable;

/**
 * Created by dllo on 16/10/31.
 */
public class NoteBookListViewBean implements Serializable {
    private String text;

    public NoteBookListViewBean() {
        super();
    }

    public NoteBookListViewBean(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public NoteBookListViewBean setText(String text) {
        this.text = text;
        return this;
    }
}
