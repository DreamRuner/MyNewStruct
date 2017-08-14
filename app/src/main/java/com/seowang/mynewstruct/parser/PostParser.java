package com.seowang.mynewstruct.parser;

import com.google.gson.Gson;
import com.seowang.mynewstruct.domain.PostPost;
import com.seowang.mynewstruct.net.base.BaseParser;

/**
 * Created by hm on 2016/3/28.
 */
public class PostParser extends BaseParser<PostPost> {
    @Override
    public PostPost parseJSON(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, PostPost.class);
    }
}
