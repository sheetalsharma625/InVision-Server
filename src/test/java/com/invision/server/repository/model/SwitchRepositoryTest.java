package com.invision.server.repository.model;

import com.invision.server.model.SwitchState;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class SwitchRepositoryTest {

    @Autowired
    SwitchRepository switchRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void shouldInsertSwitchRecord() {
        Switch sw = new Switch("1", SwitchState.ON, "192.168.1.1", "TestDevice", 12345L);
        switchRepository.insert(sw);

        List<Switch> switches = mongoTemplate.findAll(Switch.class, "switch");

        assertThat(switches, hasSize(1));
        assertThat(switches, contains(sw));
    }
}