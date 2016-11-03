package come.evernote.evernote.model.bean;

import android.graphics.Bitmap;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/10/26.
 * 数据库存储实体类
 */
@Table("save")
public class SaveBean implements Serializable {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    private String title;
    private String content;
    private String date;
    private String allContent;
    private byte[] bitmap;
    private String noteName;

    public SaveBean() {
    }

    public String getNoteName() {
        return noteName;
    }

    public SaveBean(int id, String title, String content, String date, byte[] bitmap, String noteName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.bitmap = bitmap;
        this.noteName = noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public SaveBean(int id, String title, String content, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public SaveBean(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getAllContent() {
        return allContent;
    }

    public void setAllContent(String allContent) {
        this.allContent = allContent;
    }

    public byte[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

