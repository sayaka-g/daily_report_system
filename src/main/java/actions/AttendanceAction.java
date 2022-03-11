package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.AttendanceView;
import actions.views.EmployeeView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import services.AttendanceService;

/**
 * 出退勤に関する処理を行うActionクラス
 *
 */
public class AttendanceAction extends ActionBase {

    private AttendanceService service;

    @Override
    public void process() throws ServletException, IOException {

        service = new AttendanceService();

        // メソッドを実行
        invoke();
        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //当日日付を取得
        LocalDate today = LocalDate.now();
        //月初を取得する
        LocalDate firstDate = today.withDayOfMonth(1);
        //月末を取得する
        LocalDate lastDate = today.withDayOfMonth(1).plusMonths(1).minusDays(1);

        //ログイン中の従業員の当月の出退勤データを取得
        List<AttendanceView> attendances = service.getAllMine(ev, firstDate, lastDate);

        //日付とログイン中の従業員情報を条件に出退勤データを取得する
        AttendanceView av = service.getTodayAttendance(ev, today);

        putRequestScope(AttributeConst.ATTENDANCES, attendances); // 取得した出退勤データ
        putRequestScope(AttributeConst.ATT_TODAY, today); // 当日日付
        putRequestScope(AttributeConst.ATT_CLOCK_IN_FLAG, checkClockIn(av)); // 出勤済みかどうか
        putRequestScope(AttributeConst.ATT_CLOCK_OUT_FLAG, checkClockOut(av)); // 退勤済みかどうか

        // セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if(flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        // 一覧画面を表示
        forward(ForwardConst.FW_ATT_INDEX);
    }


    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        // セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //当日日付を取得
        LocalDate date = LocalDate.now();
        //現在時刻を取得
        LocalTime time = LocalTime.now();

        // パラメータの値をもとにフォロー情報のインスタンスを作成する
        AttendanceView av = new AttendanceView(
                null,
                ev, // ログインしている従業員
                date,
                time, //現在時刻を出勤時間として登録
                null,
                null,
                null);

        // 出退勤情報登録
        service.create(av);

        // セッションに「出勤時間の登録が完了しました。」のフラッシュメッセージを設定
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_CLOCKEDIN.getMessage());

        // 出退勤ページ画面にリダイレクト
        redirect(ForwardConst.ACT_ATT, ForwardConst.CMD_INDEX);

    }

    /*
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        // セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //当日日付を取得
        LocalDate date = LocalDate.now();

        //日付とログイン中の従業員情報を条件に出退勤データを取得する
        AttendanceView av = service.getTodayAttendance(ev, date);

        //現在時刻を取得
        LocalTime time = LocalTime.now();

        //退勤時間に現在時刻を設定する
        av.setClockedOut(time);

        //出退勤データを更新する
        service.update(av);

        //セッションに「退勤時間の登録が完了しました。」のフラッシュメッセージを設定
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_CLOCKEDOUT.getMessage());

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_ATT, ForwardConst.CMD_INDEX);
    }

    /**
     * 出勤ボタン押下済みかチェック
     * @return true: 出勤ボタン押下済み false: 出勤ボタン未押下
     */
    private boolean checkClockIn(AttendanceView av) {

        if(av != null && av.getClockedIn() != null) {
            return true;
        }

        return false;
    }

    /**
     * 退勤ボタン押下済みかチェック
     * @return true: 退勤ボタン押下済み false: 退勤ボタン未押下
     */
    private boolean checkClockOut(AttendanceView av) {

        if(av != null && av.getClockedOut() != null) {
            return true;
        }

        return false;
    }
}
