package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
import models.Report;

/**
 * 日報データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class ReportConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv ReportViewのインスタンス
     * @return Reportのインスタンス
     */
    public static Report toModel(ReportView rv) {
        return new Report(
                rv.getId(),
                EmployeeConverter.toModel(rv.getEmployee()),
                rv.getReportDate(),
                rv.getTitle(),
                rv.getContent(),
                rv.getCreatedAt(),
                rv.getUpdatedAt(),
                rv.getLikeCount(),
                rv.getApprovalStatus() == null
                    ? null
                    : rv.getApprovalStatus() == AttributeConst.STATUS_APPROVED.getIntegerValue()
                            ? JpaConst.STATUS_APPROVED
                            : rv.getApprovalStatus() == AttributeConst.STATUS_REJECTED.getIntegerValue()
                                    ? JpaConst.STATUS_REJECTED
                                    : JpaConst.STATUS_PENDING,
                EmployeeConverter.toModel(rv.getApprover()),
                rv.getApprovedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param r Reportのインスタンス
     * @return ReportViewのインスタンス
     */
    public static ReportView toView(Report r) {

        if(r == null) {
            return null;
        }

        return new ReportView(
                r.getId(),
                EmployeeConverter.toView(r.getEmployee()),
                r.getReportDate(),
                r.getTitle(),
                r.getContent(),
                r.getCreatedAt(),
                r.getUpdatedAt(),
                r.getLikeCount(),
                r.getApprovalStatus() == null
                    ? null
                    : r.getApprovalStatus() == JpaConst.STATUS_APPROVED
                            ? AttributeConst.STATUS_APPROVED.getIntegerValue()
                            : r.getApprovalStatus() == JpaConst.STATUS_REJECTED
                                    ? AttributeConst.STATUS_REJECTED.getIntegerValue()
                                    : AttributeConst.STATUS_PENDING.getIntegerValue(),
                EmployeeConverter.toView(r.getApprover()),
                r.getApprovedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<ReportView> toViewList(List<Report> list) {
        List<ReportView> evs = new ArrayList<>();

        for(Report r: list) {
            evs.add(toView(r));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Report r, ReportView rv) {
        r.setId(rv.getId());
        r.setEmployee(EmployeeConverter.toModel(rv.getEmployee()));
        r.setReportDate(rv.getReportDate());
        r.setTitle(rv.getTitle());
        r.setContent(rv.getContent());
        r.setCreatedAt(rv.getCreatedAt());
        r.setUpdatedAt(rv.getUpdatedAt());
        r.setLikeCount(rv.getLikeCount());
        r.setApprovalStatus(rv.getApprovalStatus());
        r.setApprover(EmployeeConverter.toModel(rv.getApprover()));
        r.setApprovedAt(rv.getApprovedAt());
    }
}
