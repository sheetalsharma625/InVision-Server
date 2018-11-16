package com.invision.server.repository.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwitchRepository extends MongoRepository<Switch,String> {

}
