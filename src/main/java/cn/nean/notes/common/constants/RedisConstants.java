package cn.nean.notes.common.constants;

public class RedisConstants {

    /*
    * Redis key 的设计规范
    * [业务名称]:[数据名称]:[id]
    * 长度不超过 44 字节
    * 不包含特殊字符
    * 例如 login:user:1
    * */
    // 缓存击穿 空值有效期
    public static final Long CACHE_NULL_TTL = 2L;

    public static final String AUTH_CODE_KEY = "code:";
    public static final Long AUTH_CODE_TTL =2L;

    public static final String AUTH_LOGIN_KEY = "auth:login:";
    public static final Long AUTH_LOGIN_TTL = 30L;

    public static final String AUTH_USER_KEY = "auth:user:";
    public static final Long AUTH_USER_TTL = 30L;

    public static final String CACHE_GOODS_KEY = "cache:good:";
    public static final Long CACHE_GOODS_TTL = 30L;

    public static final String CACHE_ORDER_KEY = "cache:order:";
    public static final Long CACHE_ORDER_TTL = 30L;

    public static final String ACTION_LIKE_KEY = "action:like:";

    public static final String USER_LIKE_ACTION_KEY = "user:like:action:";

    public static final  String FOLLOWS_ALL_KEY = "follows:";

    public static final String FEED_FANS_KEY = "feed:fans:";

    public static final String HOT_SEARCH_RANK_SYS_KEY = "hot_search:sys:";

    public static final String HOT_SEARCH_RANK_OWN_KEY = "hot_search:own:";
}
