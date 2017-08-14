package com.seowang.mynewstruct.parser;

import com.google.gson.Gson;
import com.seowang.mynewstruct.domain.Result;
import com.seowang.mynewstruct.net.base.BaseParser;

/**
 * Created by hm on 2016/3/29.
 */
public class ResultParser extends BaseParser<Result>{
    @Override
    public Result parseJSON(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, Result.class);
    }
}
