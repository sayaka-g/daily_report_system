package services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.AttendanceConverter;
import actions.views.AttendanceView;
import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import constants.JpaConst;
import models.Attendance;

/**
 * 出退勤テーブルの操作に関わる処理を行うクラス
 */
public class AttendanceService extends ServiceBase {

    /**
     * 指定した従業員の当月の出退勤データを取得しAttendanceViewのリストで返却する
     * @param employee 従業員
     * @param firstDate 月初
     * @param lastDate 月末
     * @return 一覧画面に表示するデータのリスト
     */
    public List<AttendanceView> getAllMine(EmployeeView employee, LocalDate firstDate, LocalDate lastDate) {

        List<Attendance> attendances = em.createNamedQuery(JpaConst.Q_ATT_GET_ALL_MINE, Attendance.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setParameter(JpaConst.JPQL_PARM_FIRST, firstDate)
                .setParameter(JpaConst.JPQL_PARM_LAST, lastDate)
                .getResultList();
        return AttendanceConverter.toViewList(attendances);
    }

    /**
     * 日付とログイン中の従業員情報を条件に日報データを取得したデータをAttendanceViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public AttendanceView getTodayAttendance(EmployeeView employee, LocalDate date) {

        Attendance attendance = null;

        try {
            attendance = em.createNamedQuery(JpaConst.Q_ATT_GET_TODAY, Attendance.class)
                    .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                    .setParameter(JpaConst.JPQL_PARM_DATE, date)
                    .getSingleResult();
        }catch (NoResultException ex) {

        }

        return AttendanceConverter.toView(attendance);
    }

    /**
     * 出勤ボタン押下時の時刻を元にデータを1件作成し、出退勤テーブルに登録する
     * @param av 出退勤の登録内容
     */
    public void create(AttendanceView av) {

        LocalDateTime ldt = LocalDateTime.now();
        av.setCreatedAt(ldt);
        av.setUpdatedAt(ldt);
        createInternal(av);
    }

    /**
     * 退勤ボタン押下時の時刻を元に出退勤テーブルを更新する
     * @param av 日報の更新内容
     */
    public void update(AttendanceView av) {

        // 更新日時を現在時刻に設定
        LocalDateTime ldt = LocalDateTime.now();
        av.setUpdatedAt(ldt);

        updateInternal(av);

    }

    /**
     * idを条件にデータを1件取得し、Attendanceのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Attendance findOneInternal(int id) {
        Attendance a = em.find(Attendance.class, id);

        return a;
    }

    /**
     * 出退勤データを1件登録する
     * @param av 出退勤データ
     */
    private void createInternal(AttendanceView av) {

        em.getTransaction().begin();
        em.persist(AttendanceConverter.toModel(av));
        em.getTransaction().commit();

    }

    /**
     * 出退勤データを更新する
     * @param av 出退勤データ
     */
    private void updateInternal(AttendanceView av) {

        em.getTransaction().begin();
        Attendance a = findOneInternal(av.getId());
        AttendanceConverter.copyViewToModel(a, av);
        em.getTransaction().commit();

    }
}
