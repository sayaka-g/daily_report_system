package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.ReactionView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.ReactionService;
import services.ReportService;

/**
 * リアクションに関する処理を行うActionクラス
 *
 */
public class ReactionAction extends ActionBase {

    private ReactionService service;
    private ReportService reportService;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ReactionService();
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

        //idを条件に日報データを取得する
        ReportView rv = reportService.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));
        //指定した日報の指定されたページ数の一覧画面に表示するリアクションデータを取得
        int page = getPage();
        List<ReactionView> reactions = service.getLikesPerPage(rv.getId(), page);

        //指定した日報の全リアクションデータデータの件数を取得
        long reactionsCount = service.countAllLikes(rv.getId());

        putRequestScope(AttributeConst.REPORT, rv); //取得した日報データ
        putRequestScope(AttributeConst.REACTIONS, reactions); //取得したリアクションデータ
        putRequestScope(AttributeConst.REA_COUNT, reactionsCount); //全てのリアクションデータの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //一覧画面を表示
        forward(ForwardConst.FW_REA_INDEX);
    }

}
