package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.repository.CandidateRepository;
import ru.job4j.dreamjob.repository.MemoryCandidateRepository;
import ru.job4j.dreamjob.repository.VacancyRepository;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleCandidateService implements CandidateService {

//    private static final SimpleCandidateService INSTANCE = new SimpleCandidateService();
//
//    private final CandidateRepository candidateRepository = MemoryCandidateRepository.getInstance();

    private final CandidateRepository candidateRepository;

    private final FileService fileService;

    public SimpleCandidateService(CandidateRepository sql2oCandidateRepository, FileService fileService) {
        this.candidateRepository = sql2oCandidateRepository;
        this.fileService = fileService;
    }

//    private SimpleCandidateService() { }

//    public static SimpleCandidateService getInstance() {
//        return INSTANCE;
//    }

    @Override
    public Candidate save(Candidate candidate, FileDto image) {
        saveNewFile(candidate, image);
        return candidateRepository.save(candidate);
    }

    private void saveNewFile(Candidate candidate, FileDto image) {
        var file = fileService.save(image);
        candidate.setFileId(file.getId());
    }

//    @Override
//    public boolean deleteById(int id) {
//        return candidateRepository.deleteById(id);
//    }

    @Override
    public boolean deleteById(int id) {
//        var fileOptional = findById(id);
//        if (fileOptional.isPresent()) {
////            candidateRepository.deleteById(id);
//            fileService.deleteById(fileOptional.get().getFileId());
//        }
//        return candidateRepository.deleteById(id);
        var candidateOptional = findById(id);
        if (candidateOptional.isEmpty()) {
            return false;
        }
        var isDeleted = candidateRepository.deleteById(id);
        fileService.deleteById(candidateOptional.get().getFileId());
        return isDeleted;
    }

//    @Override
//    public boolean update(Candidate candidate) {
//        return candidateRepository.update(candidate);
//    }

    @Override
    public boolean update(Candidate candidate, FileDto image) {
        var isNewFileEmpty = image.getContent().length == 0;
        if (isNewFileEmpty) {
            return candidateRepository.update(candidate);
        }
        /* esli peredan novyj ne pustoj fajl, to staryj udaljaem, a novyj sohranjaem */
        var oldFileId = candidate.getFileId();
        saveNewFile(candidate, image);
        var isUpdated = candidateRepository.update(candidate);
        fileService.deleteById(oldFileId);
        return isUpdated;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return candidateRepository.findById(id);
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidateRepository.findAll();
    }

}