package come.evernote.evernote.model.bean;

/**
 * Created by dllo on 16/11/3.
 */
public class NewNotesBookBean {
    private String text;

    public NewNotesBookBean() {
        super();
    }

    public NewNotesBookBean(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public NewNotesBookBean setText(String text) {
        this.text = text;
        return this;
    }
}
