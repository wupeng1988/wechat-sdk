package org.singledog.wechat.sdk.message.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by adam on 1/4/16.
 */
@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveMessage(MessageEntity message) {
        this.mongoTemplate.save(message);
    }

}
