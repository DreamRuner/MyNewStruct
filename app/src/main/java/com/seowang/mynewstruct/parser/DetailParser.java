package com.seowang.mynewstruct.parser;

import com.google.gson.Gson;
import com.seowang.mynewstruct.domain.LessonList;
import com.seowang.mynewstruct.net.base.BaseParser;

/**
 * Created by hm on 2016/3/28.
 */
public class DetailParser extends BaseParser<LessonList> {
    @Override
    public LessonList parseJSON(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, LessonList.class);
    }
}
