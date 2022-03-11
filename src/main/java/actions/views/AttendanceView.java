package actions.views;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 出退勤情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class AttendanceView {

    /**
     * id
     */
    private Integer id;

    /**
     * 出退勤した従業員
     */
    private EmployeeView employee;

    /**
     * 出退勤日
     */
    private LocalDate workDate;

    /**
     * 出勤時刻
     */
    private LocalTime clockedIn;

    /**
     * 退勤時刻
     */
    private LocalTime clockedOut;

    /**
     * 登録日時
     */
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    private LocalDateTime updatedAt;
}
