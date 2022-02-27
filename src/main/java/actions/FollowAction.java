package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.FollowView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.FollowService;
import services.ReportService;

public class FollowAction extends ActionBase {

    private FollowService service;
    private ReportService reportService;
    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new FollowService();
        reportService = new ReportService();

        //メソッドを実行
        invoke();
        service.close();
        reportService.close();

    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        // セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        // 指定されたページ数の一覧画面に表示する日報データを取得
        int page = getPage();
        List<ReportView> reports = reportService.getAllFollowingPerPage(ev, page);

        // 全日報データの件数を取得
        long reportsCount = reportService.countAllFollowing(ev);

        putRequestScope(AttributeConst.REPORTS, reports); // 取得した日報データ
        putRequestScope(AttributeConst.REP_COUNT, reportsCount); // 全ての日報データの件数
        putRequestScope(AttributeConst.PAGE, page); // ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); // 1ページに表示するレコードの数

        // セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if(flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        // 一覧画面を表示
        forward(ForwardConst.FW_FOL_INDEX);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        // idを条件に日報データを取得する
        ReportView rv = reportService.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

        // セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        // パラメータの値をもとにフォロー情報のインスタンスを作成する
        FollowView fv = new FollowView(
                null,
                ev, // ログインしている従業員を、フォローした人として登録する
                rv.getEmployee(), // 日報の作成者を、フォローされた人として登録する
                null,
                null);

        // フォロー情報登録
        service.create(fv);

        // セッションに「フォローしました」のフラッシュメッセージを設定
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_FOLLOWED.getMessage());

        // タイムライン画面にリダイレクト
        redirect(ForwardConst.ACT_FOL, ForwardConst.CMD_INDEX);

    }
}
