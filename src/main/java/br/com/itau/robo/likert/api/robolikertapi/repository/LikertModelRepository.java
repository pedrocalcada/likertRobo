package br.com.itau.robo.likert.api.robolikertapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.itau.robo.likert.api.robolikertapi.model.LikertModel;

public interface LikertModelRepository extends MongoRepository<LikertModel, String> {

}
