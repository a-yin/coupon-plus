package com.lingb.couponplus.passbook.constant;

/**
 * HBase数据表
 *
 * @author lingb
 * @date 2018.11.18 14:00
 */
public class HBaseTable {

    /**
     * HBase中 User表
     */
    public static class UserTable {
        /**
         * UserVO HBase 表名
         */
        public static final String TABLE_NAME = "pb:user";

        /**
         * 基本信息列族
         */
        public static final String FAMILY_B = "b";
        public static final byte[] FAMILY_B_BYTE = FAMILY_B.getBytes();

        /**
         * 用户名
         */
        public static final String NAME = "name";
        public static final byte[] NAME_BYTE = NAME.getBytes();

        /**
         * 用户年龄
         */
        public static final String AGE = "age";
        public static final byte[] AGE_BYTE = AGE.getBytes();

        /**
         * 用户性别
         */
        public static final String SEX = "sex";
        public static final byte[] SEX_BYTE = SEX.getBytes();

        /**
         * 额外信息列族
         */
        public static final String FAMILY_O = "o";
        public static final byte[] FAMILY_O_BYTE = FAMILY_O.getBytes();

        /**
         * 用户电话
         */
        public static final String PHONE = "phone";
        public static final byte[] PHONE_BYTE = PHONE.getBytes();

        /**
         * 用户地址
         */
        public static final String ADDRESS = "address";
        public static final byte[] ADDRESS_BYTE = ADDRESS.getBytes();

    }

    /**
     * HBase中 PassTemplate表
     */
    public static class PassTemplateTable {

        /**
         * PassTemplateVO HBase 表名
         */
        public static final String TABLE_NAME = "pb:passtemplate";

        /**
         * 基本信息列族
         */
        public static final String FAMILY_B = "b";
        public static final byte[] FAMILY_B_BYTE = FAMILY_B.getBytes();

        /**
         * 商户 id
         */
        public static final String ID = "id";
        public static final byte[] ID_BYTE = ID.getBytes();

        /**
         * 优惠券标题
         */
        public static final String TITLE = "title";
        public static final byte[] TITLE_BYTE = TITLE.getBytes();

        /**
         * 优惠券摘要信息
         */
        public static final String SUMMARY = "summary";
        public static final byte[] SUMMARY_BYTE = SUMMARY.getBytes();

        /**
         * 优惠券详细信息
         */
        public static final String DESC = "desc";
        public static final byte[] DESC_BYTE = DESC.getBytes();

        /**
         * 优惠券是否有 token
         */
        public static final String HAS_TOKEN = "has_token";
        public static final byte[] HAS_TOKEN_BYTE = HAS_TOKEN.getBytes();

        /**
         * 优惠券背景色
         */
        public static final String BACKGROUND = "background";
        public static final byte[] BACKGROUND_BYTE = BACKGROUND.getBytes();

        /**
         * 约束信息列族
         */
        public static final String FAMILY_C = "c";
        public static final byte[] FAMILY_C_BYTE = FAMILY_C.getBytes();

        /**
         * 最大个数限制
         */
        public static final String LIMIT = "limit";
        public static final byte[] LIMIT_BYTE = LIMIT.getBytes();

        /**
         * 优惠券开始时间
         */
        public static final String START = "start";
        public static final byte[] START_BYTE = START.getBytes();

        /**
         * 优惠券结束时间
         */
        public static final String END = "end";
        public static final byte[] END_BYTE = END.getBytes();
    }

    /**
     * HBase中 Pass表
     */
    public static class PassTable {

        /**
         * PassVO HBase 表名
         */
        public static final String TABLE_NAME = "pb:pass";

        /**
         * 信息列族
         */
        public static final String FAMILY_I = "i";
        public static final byte[] FAMILY_I_BYTE = FAMILY_I.getBytes();

        /**
         * 用户 id
         */
        public static final String USER_ID = "user_id";
        public static final byte[] USER_ID_BYTE = USER_ID.getBytes();

        /**
         * 优惠券 id
         */
        public static final String TEMPLATE_ID = "template_id";
        public static final byte[] TEMPLATE_ID_BYTE = TEMPLATE_ID.getBytes();

        /**
         * 优惠券识别码
         */
        public static final String TOKEN = "token";
        public static final byte[] TOKEN_BYTE = TOKEN.getBytes();

        /**
         * 领取日期
         */
        public static final String ASSIGNED_DATE = "assigned_date";
        public static final byte[] ASSIGNED_DATE_BYTE = ASSIGNED_DATE.getBytes();

        /**
         * 消费日期
         */
        public static final String CON_DATE = "con_date";
        public static final byte[] CON_DATE_BYTE = CON_DATE.getBytes();
    }

    /**
     * HBase中 Feedback表
     */
    public static class Feedback {
        /**
         * FeedbackVO HBase 表名
         */
        public static final String TABLE_NAME = "pb:feedback";

        /**
         * 信息列族
         */
        public static final String FAMILY_I = "i";
        public static final byte[] FAMILY_I_BYTE = FAMILY_I.getBytes();

        /**
         * 用户 id
         */
        public static final String USER_ID = "user_id";
        public static final byte[] USER_ID_BYTE = USER_ID.getBytes();

        /**
         * 反馈类型
         */
        public static final String TYPE = "type";
        public static final byte[] TYPE_BYTE = TYPE.getBytes();

        /**
         * PassTemplateVO RowKey, 若是 app 反馈, 则为 -1
         */
        public static final String TEMPLATE_ID = "template_id";
        public static final byte[] TEMPLATE_ID_BYTE = TEMPLATE_ID.getBytes();

        /**
         * 反馈详细信息
         */
        public static final String COMMENT = "comment";
        public static final byte[] COMMENT_BYTE = COMMENT.getBytes();
    }

}
