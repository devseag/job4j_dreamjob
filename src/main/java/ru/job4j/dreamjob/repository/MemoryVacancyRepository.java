package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemoryVacancyRepository implements VacancyRepository {

//    private static final MemoryVacancyRepository INSTANCE = new MemoryVacancyRepository();

    //    private int nextId = 1;
    private final AtomicInteger nextId = new AtomicInteger(1);

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

//    public MemoryVacancyRepository() {
//        save(new Vacancy(0, "Intern Java Developer", "test", LocalDateTime.now()));
//        save(new Vacancy(0, "Junior Java Developer", "test", LocalDateTime.now()));
//        save(new Vacancy(0, "Junior+ Java Developer", "test+", LocalDateTime.now()));
//        save(new Vacancy(0, "Middle Java Developer", "test", LocalDateTime.now()));
//        save(new Vacancy(0, "Middle+ Java Developer", "test+", LocalDateTime.now()));
//        save(new Vacancy(0, "Senior Java Developer", "test", LocalDateTime.now()));
//    }

    public MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Stazher Java razrabotchik", LocalDateTime.now(), true, 1, 0));
        save(new Vacancy(0, "Junior Java Developer", "Mladshij Java razrabotchik", LocalDateTime.now(), true, 1, 0));
        save(new Vacancy(0, "Junior+ Java Developer", "Java razrabotchik", LocalDateTime.now(), true, 2, 0));
        save(new Vacancy(0, "Middle Java Developer", "Starshij Java razrabotchik", LocalDateTime.now(), true, 2, 0));
        save(new Vacancy(0, "Middle+ Java Developer", "Vedushhij Java razrabotchik", LocalDateTime.now(), true, 2, 0));
        save(new Vacancy(0, "Senior Java Developer", "Glavnyj Java razrabotchik", LocalDateTime.now(), true, 3, 0));
    }

//    public static MemoryVacancyRepository getInstance() {
//        return INSTANCE;
//    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId.getAndIncrement());
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
//        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> new Vacancy(oldVacancy.getId(), vacancy.getTitle(), vacancy.getDescription(), vacancy.getCreationDate())) != null;
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> {
            return new Vacancy(
                    oldVacancy.getId(), vacancy.getTitle(), vacancy.getDescription(),
                    vacancy.getCreationDate(), vacancy.getVisible(), vacancy.getCityId(), vacancy.getFileId()
            );
        }) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }

}
