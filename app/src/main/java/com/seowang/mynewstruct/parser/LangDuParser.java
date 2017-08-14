package com.seowang.mynewstruct.parser;

import com.google.gson.Gson;
import com.seowang.mynewstruct.domain.LangDu;
import com.seowang.mynewstruct.net.base.BaseParser;

/**
 * Created by hm on 2016/3/28.
 */
public class LangDuParser extends BaseParser<LangDu> {
    @Override
    public LangDu parseJSON(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, LangDu.class);
    }
}
