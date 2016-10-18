package come.evernote.evernote.model.bean;

/**
 * Created by dllo on 16/10/18.
 */
public class DrawerShowBean {

    private  String content;
    private  int img;

    DrawerShowBean(){

    }

    public DrawerShowBean(String content, int img) {
        this.content = content;
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
