package come.evernote.evernote.model.bean;

/**
 * Created by dllo on 16/10/31.
 */
public class NoteBookBean {
    private int size;
    private String noteName;

    public NoteBookBean(int size, String noteName) {
        this.size = size;
        this.noteName = noteName;
    }

    public NoteBookBean() {
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }
}
