package come.evernote.evernote.model.bean;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by dllo on 16/11/3.
 */
@Table("waste")
public class WasteBinBean {
        @PrimaryKey(AssignType.AUTO_INCREMENT)
        private int id;
        private String title;
        private String content;
        private String date;
        private String allContent;
        private byte[] bitmap;
        private String noteName;

        public WasteBinBean(String title, String content, String date, String allContent, byte[] bitmap, String noteName) {
            this.title = title;
            this.content = content;
            this.date = date;
            this.allContent = allContent;
            this.bitmap = bitmap;
            this.noteName = noteName;
        }

    public WasteBinBean() {
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

        public String getNoteName() {
            return noteName;
        }

        public void setNoteName(String noteName) {
            this.noteName = noteName;
        }
}
