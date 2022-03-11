package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Attendance;

/**
 * 出退勤データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class AttendanceConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param av AttendanceViewのインスタンス
     * @return Attendanceのインスタンス
     */
    public static Attendance toModel(AttendanceView av) {
        return new Attendance(
                av.getId(),
                EmployeeConverter.toModel(av.getEmployee()),
                av.getWorkDate(),
                av.getClockedIn(),
                av.getClockedOut(),
                av.getCreatedAt(),
                av.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param a Attendanceのインスタンス
     * @return AttendanceViewのインスタンス
     */
    public static AttendanceView toView(Attendance a) {

        if(a == null) {
            return null;
        }

        return new AttendanceView(
                a.getId(),
                EmployeeConverter.toView(a.getEmployee()),
                a.getWorkDate(),
                a.getClockedIn(),
                a.getClockedOut(),
                a.getCreatedAt(),
                a.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<AttendanceView> toViewList(List<Attendance> list) {
        List<AttendanceView> evs = new ArrayList<>();

        for(Attendance a: list) {
            evs.add(toView(a));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param a DTOモデル(コピー先)
     * @param av Viewモデル(コピー元)
     */
    public static void copyViewToModel(Attendance a, AttendanceView av) {
        a.setId(av.getId());
        a.setEmployee(EmployeeConverter.toModel(av.getEmployee()));
        a.setWorkDate(av.getWorkDate());
        a.setClockedIn(av.getClockedIn());
        a.setClockedOut(av.getClockedOut());
        a.setCreatedAt(av.getCreatedAt());
        a.setUpdatedAt(av.getUpdatedAt());
    }
}
