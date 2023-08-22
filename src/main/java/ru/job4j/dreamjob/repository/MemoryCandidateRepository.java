package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemoryCandidateRepository implements CandidateRepository {

//    private static final MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();

    //    private int nextId = 1;
    private final AtomicInteger nextId = new AtomicInteger(1);

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    public MemoryCandidateRepository() {
        save(new Candidate(0, "John", "Learning java for 1 month", LocalDateTime.now()));
        save(new Candidate(0, "Bill", "Learning java for 6 months", LocalDateTime.now()));
        save(new Candidate(0, "Sally", "Learning java for 1 year", LocalDateTime.now()));
    }

//    public static MemoryCandidateRepository getInstance() {
//        return INSTANCE;
//    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId.getAndIncrement());
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
//        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate) -> new Candidate(oldCandidate.getId(), candidate.getName(), candidate.getDescription(), candidate.getCreationDate())) != null;
        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate) -> {
            return new Candidate(oldCandidate.getId(), candidate.getName(), candidate.getDescription(), candidate.getCreationDate(), candidate.getCityId()
            );
        }) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
