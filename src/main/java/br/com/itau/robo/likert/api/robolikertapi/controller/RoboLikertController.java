package br.com.itau.robo.likert.api.robolikertapi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aspose.email.FolderInfo;
import com.aspose.email.PersonalStorage;

import br.com.itau.robo.likert.api.robolikertapi.service.LikertService;
import br.com.itau.robo.likert.api.robolikertapi.viewmodel.PastasViewModel;
import br.com.itau.robo.likert.api.robolikertapi.viewmodel.SubPastaViewModel;

@RestController
public class RoboLikertController {

	private static final String FILE = "C:\\Users\\pedro\\AppData\\Local\\Microsoft\\Outlook\\pedropaulo63@hotmail.com.ost";

	@Autowired
	private LikertService likertService;

	@Autowired
	HttpSession session;

	@GetMapping("/pastasOutlook")
	public PastasViewModel pastasOutlook() throws InterruptedException, IOException {
		PersonalStorage pstFile = PersonalStorage.fromFile(FILE);
		return processFolder(pstFile.getRootFolder());
	}

	@GetMapping("/extrairLikert")
	public String extrairLikert(@RequestBody String id) throws InterruptedException {
		CompletableFuture<String> promisse = likertService.extrairLikert(FILE, id);
		session.setAttribute("promisse", promisse);
		return "Extraindo...";
	}

	@GetMapping("/cancelar")
	public String cancelar() {
		CompletableFuture<String> promisse = (CompletableFuture<String>) session.getAttribute("promisse");

		if (promisse != null) {
			if (promisse.isDone()) {
				return "terminou";
			} else {
				promisse.cancel(true);
				return "cancelado";
			}
		}
		return "processo não executado";

	}

	public PastasViewModel processFolder(FolderInfo folder) throws java.io.IOException {
		PastasViewModel pasta = new PastasViewModel();

		System.out.println(folder.getDisplayName());
		pasta.setNome(folder.getDisplayName());
		pasta.setId(folder.getEntryIdString());

		if (folder.hasSubFolders()) {
			pasta.setSubPastas(new ArrayList<>());
			pasta.getSubPastas().addAll(getSubPastas(folder));
		}

		return pasta;
	}

	private List<SubPastaViewModel> getSubPastas(FolderInfo childFolder) {
		List<SubPastaViewModel> subPastasViewModel = new ArrayList<>();
		for (FolderInfo childFolderProximoNivel : childFolder.getSubFolders()) {
			SubPastaViewModel subPastaViewModel = new SubPastaViewModel();
			subPastaViewModel.setId(childFolderProximoNivel.getEntryIdString());
			subPastaViewModel.setNome(childFolderProximoNivel.getDisplayName());
			if (childFolderProximoNivel.hasSubFolders()) {
				subPastaViewModel.setSubPastas(new ArrayList<>());
				subPastaViewModel.getSubPastas().addAll(getSubPastas(childFolderProximoNivel));
			}
			subPastasViewModel.add(subPastaViewModel);
		}
		return subPastasViewModel;
	}

}
