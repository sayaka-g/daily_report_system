package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.ReactionConverter;
import actions.views.ReactionView;
import constants.JpaConst;
import models.Reaction;

/**
 * リアクションテーブルの操作に関わる処理を行うクラス
 */
public class ReactionService extends ServiceBase {

    /**
     * 指定した日報に対するリアクション日報データを、指定されたページ数の一覧画面に表示する分取得しReactionViewのリストで返却する
     * @param report 日報id
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ReactionView> getLikesPerPage(int report, int page) {

        List<Reaction> reactions = em.createNamedQuery(JpaConst.Q_REA_GET_ALL, Reaction.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, report)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReactionConverter.toViewList(reactions);
    }

    /**
     * 指定した日報に対するリアクションデータの件数を取得し、返却する
     * @param report 日報id
     * @return リアクションデータの件数
     */
    public long countAllLikes(int report) {

        long count = (long) em.createNamedQuery(JpaConst.Q_REA_COUNT_ALL, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, report)
                .getSingleResult();

        return count;
    }

    /**
     * 指定した日報に対する指定した従業員のリアクションデータの件数を取得し、返却する
     * @param report 日報id
     * @param employee 従業員
     * @return リアクションデータの件数
     */
    public long countAllMine(int report, EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_REA_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, report)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }

    /**
     * いいねされた日報とログイン中の従業員情報を元にデータを1件作成し、リアクションテーブルに登録する
     * @param rv リアクションの登録内容
     */
    public void create(ReactionView rv) {

        LocalDateTime ldt = LocalDateTime.now();
        rv.setCreatedAt(ldt);
        rv.setUpdatedAt(ldt);
        createInternal(rv);
    }

    /**
     * リアクションデータを1件登録する
     * @param rv リアクションデータ
     */
    private void createInternal(ReactionView rv) {

        em.getTransaction().begin();
        em.persist(ReactionConverter.toModel(rv));
        em.getTransaction().commit();

    }
}
