package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
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

    public SimpleCandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

//    private SimpleCandidateService() { }

//    public static SimpleCandidateService getInstance() {
//        return INSTANCE;
//    }

    @Override
    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public boolean deleteById(int id) {
        return candidateRepository.deleteById(id);
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidateRepository.update(candidate);
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