package br.com.itau.robo.likert.api.robolikertapi.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.aspose.email.MapiMessage;
import com.aspose.email.PersonalStorage;

import br.com.itau.robo.likert.api.robolikertapi.model.LikertModel;
import br.com.itau.robo.likert.api.robolikertapi.repository.LikertModelRepository;

@Service
public class LikertService {

	@Autowired
	private LikertModelRepository repository;

	@Async
	public CompletableFuture<String> extrairLikert(String file, String id) throws InterruptedException {

		PersonalStorage pstFile = PersonalStorage.fromFile(file);

		pstFile.getFolderById(id).getContents().stream().forEach(x -> {
			MapiMessage message = pstFile.extractMessage(x);
			repository.insert(new LikertModel(message.getBody()));
		});

		return CompletableFuture.completedFuture("finalizou");
	}

}
