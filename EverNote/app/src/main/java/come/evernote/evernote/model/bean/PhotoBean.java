package come.evernote.evernote.model.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by dllo on 16/10/19.
 * 相片的实体类
 */
public class PhotoBean implements Serializable {
    private byte[] bitmap;
    public PhotoBean() {
    }

    public PhotoBean(byte[] bitmap) {
        this.bitmap = bitmap;
    }

    public byte[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }
}
