package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Reaction;

/**
 * リアクションデータのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class ReactionConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv ReactionViewのインスタンス
     * @return Reactionのインスタンス
     */
    public static Reaction toModel(ReactionView rv) {
        return new Reaction(
                rv.getId(),
                rv.getRep_id(),
                EmployeeConverter.toModel(rv.getEmployee()),
                rv.getCreatedAt(),
                rv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param r Reactionのインスタンス
     * @return ReactionViewのインスタンス
     */
    public static ReactionView toView(Reaction r) {

        if (r == null) {
            return null;
        }

        return new ReactionView(
                r.getId(),
                r.getRep_id(),
                EmployeeConverter.toView(r.getEmployee()),
                r.getCreatedAt(),
                r.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<ReactionView> toViewList(List<Reaction> list) {
        List<ReactionView> evs = new ArrayList<>();

        for (Reaction r : list) {
            evs.add(toView(r));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Reaction r, ReactionView rv) {
        r.setId(rv.getId());
        r.setRep_id(rv.getRep_id());
        r.setEmployee(EmployeeConverter.toModel(rv.getEmployee()));
        r.setCreatedAt(rv.getCreatedAt());
        r.setUpdatedAt(rv.getUpdatedAt());

    }

}
