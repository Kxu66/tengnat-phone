package com.tengnat.elasticsearch.utils.data.constants;

public class DataConstants {

    /**
     * 分页的时候，默认每页显示的数量
     */
    public static final String PAGE_SIZE = "20";

    /**
     * 登录后的令牌 保存在Redis里的key值
     */
    public static final String LOGIN_TOKEN = "YYT:IM:TOKEN:";

    public static final Long LOGIN_TOKEN_EXPIRE = 43200L;
}
