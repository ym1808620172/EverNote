package come.evernote.evernote.model.db;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.util.List;
import java.util.Objects;

import come.evernote.evernote.controler.app.ImpressApp;

/**
 * Created by dllo on 16/10/26.
 */
public class LiteOrmInstance {

    private static LiteOrmInstance liteOrmInstance;
    private static final String DB_NAME = "save.db";
    private LiteOrm liteOrm;

    private LiteOrmInstance() {
        liteOrm = LiteOrm.newCascadeInstance(ImpressApp.getContext(), DB_NAME);
    }

    public static LiteOrmInstance getLiteOrmInstance() {
        if (liteOrmInstance == null) {
            synchronized (LiteOrmInstance.class) {
                if (liteOrmInstance == null) {
                    liteOrmInstance = new LiteOrmInstance();
                }
            }
        }

        return liteOrmInstance;
    }

    /**
     * 添加数据
     **/

    public <T> void insert(T sb) {
        if (liteOrm != null) {
            liteOrm.insert(sb);
        }
    }

    /**
     * 按条件查询
     */
    public <T> List<T> queryByName(String title, Class<T> tClass) {
        QueryBuilder<T> sb = new QueryBuilder<>(tClass);
        sb.where("noteName  = ? ", new Object[]{title});
        return liteOrm.query(sb);
    }

    public <T> List<T> queryById(int id, Class<T> tClass) {
        QueryBuilder<T> sb = new QueryBuilder<>(tClass);
        sb.where("id  = ? ", new Object[]{id});
        return liteOrm.query(sb);
    }


    public <T> List<T> queryByName(String title, String date, Class<T> tClass) {
        QueryBuilder<T> sb = new QueryBuilder<>(tClass);
        sb.where("title  = ? ", new Object[]{title}).whereAppendAnd().where("data = ?", new Object[]{date});
        sb.where("title  = ? ", new Object[]{title}).whereAppendAnd().where("data = ?", new Object[]{date});
        return liteOrm.query(sb);
    }


    /**
     * 按条件删除
     */
    public <T> void delateByName(String title, Class<T> tClass) {
        WhereBuilder wb = new WhereBuilder(tClass);
        wb.where("title = ?", new Object[]{title});
        liteOrm.delete(wb);
    }

    public <T> void delateByNames(String title, int id, Class<T> tClass) {
        liteOrm.delete(new WhereBuilder(tClass)
                .where("title = ?", new Object[]{title})
                .and()
                .greaterThan("id", 0)
                .and().lessThan("id", 10000));

    }

    public <T> List<T> queryByName(Object title, Class<T> tClass) {
        QueryBuilder<T> sb = new QueryBuilder<>(tClass);
        sb.where("title  = ? ", new Object[]{title});
        return liteOrm.query(sb);
    }

    public <T> List<T> queryByName(Objects title, Objects date, Class<T> tClass) {
        QueryBuilder<T> sb = new QueryBuilder<>(tClass);
        sb.where("title  = ? ", new Object[]{title}).whereAppendAnd().where("data = ?", new Object[]{date});
        return liteOrm.query(sb);
    }


    /**
     * 按条件删除
     */
    public <T> void delateByName(Object title, Class<T> tClass) {
        WhereBuilder wb = new WhereBuilder(tClass);
        wb.where("title = ?", new Object[]{title});
        liteOrm.delete(wb);
    }

    public <T> void delateByNames(Object title, int id, Class<T> tClass) {
        liteOrm.delete(new WhereBuilder(tClass)
                .where("title = ?", new Object[]{title})
                .and()
                .greaterThan("id", 0)
                .and().lessThan("id", 10000));

    }
//    public List<SaveBean> queryByName(String title) {
//        QueryBuilder<SaveBean> sb = new QueryBuilder<>(SaveBean.class);
//        sb.where("title  = ? ", new String[]{title});
//        return liteOrm.query(sb);
//    }
//
//    public List<SaveBean> queryByName(String title, String date) {
//        QueryBuilder<SaveBean> sb = new QueryBuilder<>(SaveBean.class);
//        sb.where("title  = ? ", new String[]{title}).whereAppendAnd().where("data = ?", new String[]{date});
//        return liteOrm.query(sb);
//    }


//    /**
//     * 按条件删除
//     */
//    public void delateByName(String title) {
//        WhereBuilder wb = new WhereBuilder(SaveBean.class);
//        wb.where("title = ?", new String[]{title});
//        liteOrm.delete(wb);
//    }
//
//    public void delateByNames(String title, int id) {
//        liteOrm.delete(new WhereBuilder(SaveBean.class)
//                .where("title = ?", new String[]{title})
//                .and()
//                .greaterThan("id", 0)
//                .and().lessThan("id", 10000));
//
//    }


    /**
     * 删除所有
     */
    public <T> void delateAll(Class<T> tClass) {
        liteOrm.deleteAll(tClass);
    }

    /**
     * 按条件删除
     */
    public <T> void deleteById(int id,Class<T> tClass) {
        liteOrm.delete(new WhereBuilder(tClass).greaterThan("id",id));
    }

    /**
     * 查询所有
     */
    public <T> List<T> queryAll(Class<T> tClass) {
        return liteOrm.query(tClass);
    }
}

