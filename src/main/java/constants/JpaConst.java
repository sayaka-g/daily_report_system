package constants;

/**
 * DB関連の項目値を定義するインターフェース
 * ※インターフェイスに定義した変数は public static final 修飾子がついているとみなされる
 */
public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "daily_report_system";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 15; //1ページに表示するレコードの数

    //従業員テーブル
    String TABLE_EMP = "employees"; //テーブル名
    //従業員テーブルカラム
    String EMP_COL_ID = "id"; //id
    String EMP_COL_CODE = "code"; //社員番号
    String EMP_COL_NAME = "name"; //氏名
    String EMP_COL_PASS = "password"; //パスワード
    String EMP_COL_ADMIN_FLAG = "admin_flag"; //管理者権限
    String EMP_COL_CREATED_AT = "created_at"; //登録日時
    String EMP_COL_UPDATED_AT = "updated_at"; //更新日時
    String EMP_COL_DELETE_FLAG = "delete_flag"; //削除フラグ
    String EMP_COL_POSITION_FLAG = "position_flag"; //職位

    int ROLE_ADMIN = 1; //管理者権限ON(管理者)
    int ROLE_GENERAL = 0; //管理者権限OFF(一般)
    int EMP_DEL_TRUE = 1; //削除フラグON(削除済み)
    int EMP_DEL_FALSE = 0; //削除フラグOFF(現役)
    int POSITION_GM = 2; //職位(部長)
    int POSITION_AGM = 1; //職位(課長)
    int POSITION_GENERAL = 0; //職位(一般)

    //日報テーブル
    String TABLE_REP = "reports"; //テーブル名
    //日報テーブルカラム
    String REP_COL_ID = "id"; //id
    String REP_COL_EMP = "employee_id"; //日報を作成した従業員のid
    String REP_COL_REP_DATE = "report_date"; //いつの日報かを示す日付
    String REP_COL_TITLE = "title"; //日報のタイトル
    String REP_COL_CONTENT = "content"; //日報の内容
    String REP_COL_CREATED_AT = "created_at"; //登録日時
    String REP_COL_UPDATED_AT = "updated_at"; //更新日時
    String REP_COL_LIKE_COUNT = "like_count"; //いいね数
    String REP_COL_APPROVAL_STATUS = "approval_status"; //承認状況
    String REP_COL_APPROVER = "approver_id"; //日報を承認した従業員のid
    String REP_COL_APPROVED_AT = "approved_at"; //承認日時

    int STATUS_APPROVED = 2; //承認状況(承認済)
    int STATUS_REJECTED = 1; //承認状況(差戻)
    int STATUS_PENDING = 0; //承認状況(承認待)

    //リアクションテーブル
    String TABLE_REA = "reactions"; //テーブル名
    //リアクションテーブルカラム
    String REA_COL_ID = "id"; //id
    String REA_COL_REP = "report_id"; //いいねされた日報id
    String REA_COL_EMP = "employee_id"; //日報にいいねした従業員のid
    String REA_COL_CREATED_AT = "created_at"; //登録日時
    String REA_COL_UPDATED_AT = "updated_at"; //更新日時

    //フォローテーブル
    String TABLE_FOL = "follows"; //テーブル名
    //フォローテーブルカラム
    String FOL_COL_ID = "id"; //id
    String FOL_COL_FOLLOWER = "follower_id"; //フォローした従業員のid
    String FOL_COL_FOLLOWED = "followed_id"; //フォローされた従業員のid(日報を作成した従業員のid)
    String FOL_COL_CREATED_AT = "created_at"; //登録日時
    String FOL_COL_UPDATED_AT = "updated_at"; //更新日時

    //出退勤テーブル
    String TABLE_ATT = "attendances"; //テーブル名
    //出退勤テーブルカラム
    String ATT_COL_ID = "id"; //id
    String ATT_COL_EMP = "employee_id"; //出退勤した従業員のid
    String ATT_COL_WORK_DATE = "work_date"; //出退勤日
    String ATT_COL_CLOCKED_IN = "clocked_in"; //出勤時刻
    String ATT_COL_CLOCKED_OUT = "clocked_out"; //退勤時刻
    String ATT_COL_CREATED_AT = "created_at"; //登録日時
    String ATT_COL_UPDATED_AT = "updated_at"; //更新日時

    //Entity名
    String ENTITY_EMP = "employee"; //従業員
    String ENTITY_REP = "report"; //日報
    String ENTITY_REA = "reaction"; //リアクション
    String ENTITY_FOL = "follow"; //フォロー
    String ENTITY_ATT = "attendance"; //出退勤

    //JPQL内パラメータ
    String JPQL_PARM_CODE = "code"; //社員番号
    String JPQL_PARM_PASSWORD = "password"; //パスワード
    String JPQL_PARM_EMPLOYEE = "employee"; //従業員
    String JPQL_PARM_REPORT = "report"; //日報
    String JPQL_PARM_FOLLOWER = "follower"; //(フォローした)従業員
    String JPQL_PARM_FOLLOWED = "followed"; //(フォローされた)従業員
    String JPQL_PARM_FIRST = "first"; //月初
    String JPQL_PARM_LAST = "last"; //月末
    String JPQL_PARM_DATE = "date"; //日付

    //NamedQueryの nameとquery
    //全ての従業員をidの降順に取得する
    String Q_EMP_GET_ALL = ENTITY_EMP + ".getAll"; //name
    String Q_EMP_GET_ALL_DEF = "SELECT e FROM Employee AS e ORDER BY e.id DESC"; //query
    //全ての従業員の件数を取得する
    String Q_EMP_COUNT = ENTITY_EMP + ".count";
    String Q_EMP_COUNT_DEF = "SELECT COUNT(e) FROM Employee AS e";
    //社員番号とハッシュ化済パスワードを条件に未削除の従業員を取得する
    String Q_EMP_GET_BY_CODE_AND_PASS = ENTITY_EMP + ".getByCodeAndPass";
    String Q_EMP_GET_BY_CODE_AND_PASS_DEF = "SELECT e FROM Employee AS e WHERE e.deleteFlag = 0 AND e.code = :" + JPQL_PARM_CODE + " AND e.password = :" + JPQL_PARM_PASSWORD;
    //指定した社員番号を保持する従業員の件数を取得する
    String Q_EMP_COUNT_RESISTERED_BY_CODE = ENTITY_EMP + ".countRegisteredByCode";
    String Q_EMP_COUNT_RESISTERED_BY_CODE_DEF = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :" + JPQL_PARM_CODE;
    //全ての日報をidの降順に取得する
    String Q_REP_GET_ALL = ENTITY_REP + ".getAll";
    String Q_REP_GET_ALL_DEF = "SELECT r FROM Report AS r ORDER BY r.id DESC";
    //全ての日報の件数を取得する
    String Q_REP_COUNT = ENTITY_REP + ".count";
    String Q_REP_COUNT_DEF = "SELECT COUNT(r) FROM Report AS r";
    //指定した従業員が作成した日報を全件idの降順で取得する
    String Q_REP_GET_ALL_MINE = ENTITY_REP + ".getAllMine";
    String Q_REP_GET_ALL_MINE_DEF = "SELECT r FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE + " ORDER BY r.id DESC";
    //指定した従業員が作成した日報の件数を取得する
    String Q_REP_COUNT_ALL_MINE = ENTITY_REP + ".countAllMine";
    String Q_REP_COUNT_ALL_MINE_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE;
    //指定した日報に対するリアクションを全件idの降順で取得する
    String Q_REA_GET_ALL = ENTITY_REA + ".getAllLikes";
    String Q_REA_GET_ALL_DEF = "SELECT r FROM Reaction AS r WHERE r.report = :" + JPQL_PARM_REPORT + " ORDER BY r.id DESC";
    //指定した日報に対するリアクションの件数を取得する
    String Q_REA_COUNT_ALL = ENTITY_REA + ".countAllLikes";
    String Q_REA_COUNT_ALL_DEF = "SELECT COUNT(r) FROM Reaction AS r WHERE r.report = :" + JPQL_PARM_REPORT;
    //指定した日報に対する指定した従業員のリアクションの件数を取得する
    String Q_REA_COUNT_ALL_MINE = ENTITY_REA + ".countAllMine";
    String Q_REA_COUNT_ALL_MINE_DEF = "SELECT COUNT(r) FROM Reaction AS r WHERE r.report = :" + JPQL_PARM_REPORT + " AND r.employee = :" + JPQL_PARM_EMPLOYEE;
    //指定した従業員がフォローしている従業員が作成した日報を全件idの降順で取得する
    String Q_REP_GET_ALL_FOLLOWING = ENTITY_REP + ".getAllFollowing";
    String Q_REP_GET_ALL_FOLLOWING_DEF = "SELECT r FROM Report AS r LEFT JOIN Follow AS f ON r.employee = f.followed WHERE f.follower = :" + JPQL_PARM_EMPLOYEE + " ORDER BY r.id DESC";
    //指定した従業員がフォローしている従業員が作成した日報の件数を取得する
    String Q_REP_COUNT_ALL_FOLLOWING = ENTITY_REP + ".countAllFollowing";
    String Q_REP_COUNT_ALL_FOLLOWING_DEF = "SELECT COUNT(r) FROM Report AS r LEFT JOIN Follow AS f ON r.employee = f.followed WHERE f.follower = :" + JPQL_PARM_EMPLOYEE;
    //指定した従業員のフォローデータの件数を取得する
    String Q_FOL_COUNT_ALL_MINE = ENTITY_FOL + ".countAllMine";
    String Q_FOL_COUNT_ALL_MINE_DEF = "SELECT COUNT(f) FROM Follow AS f WHERE f.follower = :" + JPQL_PARM_FOLLOWER + " AND f.followed = :" + JPQL_PARM_FOLLOWED;
    //指定した従業員の当月の出退勤データを取得する
    String Q_ATT_GET_ALL_MINE = ENTITY_ATT + ".getAllMine";
    String Q_ATT_GET_ALL_MINE_DEF = "SELECT a FROM Attendance AS a WHERE a.employee = :" + JPQL_PARM_EMPLOYEE + " AND a.workDate >= :" + JPQL_PARM_FIRST + " AND a.workDate <= :" + JPQL_PARM_LAST + " ORDER BY a.id";
    //指定した従業員の当日の出退勤データを取得する
    String Q_ATT_GET_TODAY = ENTITY_ATT + ".getToday";
    String Q_ATT_GET_TODAY_DEF = "SELECT a FROM Attendance AS a WHERE a.employee = :" + JPQL_PARM_EMPLOYEE + " AND a.workDate = :" + JPQL_PARM_DATE;
}
