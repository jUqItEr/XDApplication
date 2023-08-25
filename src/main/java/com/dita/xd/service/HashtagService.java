package com.dita.xd.service;

import com.dita.xd.model.HashtagBean;
import com.dita.xd.repository.HashtagRepository;

import java.util.Vector;

public interface HashtagService {
    boolean create(String content);
    HashtagBean getHashtag(HashtagRepository repository, int index);
    Vector<HashtagBean> getHashtags(HashtagRepository repository);
}
