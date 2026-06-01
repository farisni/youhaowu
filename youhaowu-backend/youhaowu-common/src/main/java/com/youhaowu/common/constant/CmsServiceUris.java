package com.youhaowu.common.constant;

public final class CmsServiceUris {

    private CmsServiceUris() {
    }

    public static final class CmsBanner {
        public static final String BASE_URL = "/api/cms/banner";
        public static final String LIST = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String PAGE = BASE_URL + "/page";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete";
    }

    public static final class CmsNews {
        public static final String BASE_URL = "/api/cms/news";
        public static final String LIST = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String PAGE = BASE_URL + "/page";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete";
    }
}
