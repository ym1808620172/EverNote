package come.evernote.evernote.model.db;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.util.List;
import java.util.Objects;

import come.evernote.evernote.controler.app.ImpressApp;
import come.evernote.evernote.model.bean.SaveBean;

/**
 * Created by dllo on 16/10/26.
 *
 */
public class LiteOrmInstance {

    private static LiteOrmInstance liteOrmInstance;

    private LiteOrm liteOrm;

    private LiteOrmInstance(String DB_NAME) {
        liteOrm = LiteOrm.newSingleInstance(ImpressApp.getContext(), DB_NAME);
    }

    public static LiteOrmInstance getLiteOrmInstance(String DB_NAME) {
        if (liteOrmInstance == null) {
            synchronized (LiteOrmInstance.class) {
                if (liteOrmInstance == null) {
                    liteOrmInstance = new LiteOrmInstance(DB_NAME);
                }
            }
        }

        return liteOrmInstance;
    }

    /**
     * 添加数据
     **/

    public void insert(SaveBean sb) {
        if (liteOrm != null) {
            liteOrm.insert(sb);
        }
    }

    /**
     * 按条件查询
     */
    public List<SaveBean> queryByName(String title) {
        QueryBuilder<SaveBean> sb = new QueryBuilder<>(SaveBean.class);
        sb.where("noteName  = ? ", new Object[]{title});
        sb.where("title  = ? ", new Object[]{title});
        return liteOrm.query(sb);
    }
    public List<SaveBean> queryById(int id) {
        QueryBuilder<SaveBean> sb = new QueryBuilder<>(SaveBean.class);
        sb.where("id  = ? ", new Object[]{id});
        return liteOrm.query(sb);
    }


    public List<SaveBean> queryByName(String title, String date) {
        QueryBuilder<SaveBean> sb = new QueryBuilder<>(SaveBean.class);
        sb.where("title  = ? ", new Object[]{title}).whereAppendAnd().where("data = ?", new String[]{date});
        sb.where("title  = ? ", new Object[]{title}).whereAppendAnd().where("data = ?", new Object[]{date});
        return liteOrm.query(sb);
    }


    /**
     * 按条件删除
     */
    public void delateByName(String title) {
        WhereBuilder wb = new WhereBuilder(SaveBean.class);
        wb.where("title = ?", new Object[]{title});
        liteOrm.delete(wb);
    }

    public void delateByNames(String title, int id) {
        liteOrm.delete(new WhereBuilder(SaveBean.class)
                .where("title = ?", new Object[]{title})
                .and()
                .greaterThan("id", 0)
                .and().lessThan("id", 10000));

    }
    public List<SaveBean> queryByName(Object title) {
        QueryBuilder<SaveBean> sb = new QueryBuilder<>(SaveBean.class);
        sb.where("title  = ? ", new Object[]{title});
        return liteOrm.query(sb);
    }

    public List<SaveBean> queryByName(Objects title,Objects date) {
        QueryBuilder<SaveBean> sb = new QueryBuilder<>(SaveBean.class);
        sb.where("title  = ? ", new Object[]{title}).whereAppendAnd().where("data = ?", new Object[]{date});
        return liteOrm.query(sb);
    }


    /**
     * 按条件删除
     */
    public void delateByName(Object title) {
        WhereBuilder wb = new WhereBuilder(SaveBean.class);
        wb.where("title = ?", new Object[]{title});
        liteOrm.delete(wb);
    }

    public void delateByNames(Object title, int id) {
        liteOrm.delete(new WhereBuilder(SaveBean.class)
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
    public void delateAll() {
        liteOrm.deleteAll(SaveBean.class);
    }

    /**
     * 查询所有
     */
    public List<SaveBean> queryAll() {
        return liteOrm.query(SaveBean.class);
    }
}

