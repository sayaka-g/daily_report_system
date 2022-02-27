package services;

import java.time.LocalDateTime;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.FollowConverter;
import actions.views.FollowView;
import actions.views.ReportView;
import constants.JpaConst;

/**
 * フォローテーブルの操作に関わる処理を行うクラス
 */
public class FollowService extends ServiceBase {

    /**
     * 指定した従業員のフォローデータの件数を取得し、返却する
     * @param report 日報
     * @param employee 従業員
     * @return 日報の件数
     */
    public long countAllMine(ReportView report, EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_FOL_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_FOLLOWER, EmployeeConverter.toModel(employee))
                .setParameter(JpaConst.JPQL_PARM_FOLLOWED, EmployeeConverter.toModel(report.getEmployee()))
                .getSingleResult();

        return count;
    }

    /**
     * 日報を作成した従業員とログイン中の従業員情報を元にデータを1件作成し、フォローテーブルに登録する
     * @param fv フォローの登録内容
     */
    public void create(FollowView fv) {

        LocalDateTime ldt = LocalDateTime.now();
        fv.setCreatedAt(ldt);
        fv.setUpdatedAt(ldt);
        createInternal(fv);
    }

    /**
     * フォローデータを1件登録する
     * @param fv フォローデータ
     */
    private void createInternal(FollowView fv) {

        em.getTransaction().begin();
        em.persist(FollowConverter.toModel(fv));
        em.getTransaction().commit();

    }
}
