package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.repository.MemoryVacancyRepository;
import ru.job4j.dreamjob.repository.VacancyRepository;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleVacancyService implements VacancyService {

    private final VacancyRepository vacancyRepository;

    private final FileService fileService;

    public SimpleVacancyService(VacancyRepository sql2oVacancyRepository, FileService fileService) {
        this.vacancyRepository = sql2oVacancyRepository;
        this.fileService = fileService;
    }

//    private static final SimpleVacancyService INSTANCE = new SimpleVacancyService();

//    private final VacancyRepository vacancyRepository = MemoryVacancyRepository.getInstance();

//    private SimpleVacancyService() { }

//    public static SimpleVacancyService getInstance() {
//        return INSTANCE;
//    }

    @Override
    public Vacancy save(Vacancy vacancy, FileDto image) {
        saveNewFile(vacancy, image);
        return vacancyRepository.save(vacancy);
    }

    private void saveNewFile(Vacancy vacancy, FileDto image) {
        var file = fileService.save(image);
        vacancy.setFileId(file.getId());
    }

//    @Override
//    public boolean deleteById(int id) {
//        var fileOptional = findById(id);
//        if (fileOptional.isPresent()) {
//            vacancyRepository.deleteById(id);
//            fileService.deleteById(fileOptional.get().getFileId();
//        }
//        return vacancyRepository.deleteById(id);
//    }

    @Override
    public boolean deleteById(int id) {
//        var fileOptional = findById(id);
//        if (fileOptional.isPresent()) {
////            vacancyRepository.deleteById(id);
//            fileService.deleteById(fileOptional.get().getFileId());
//        }
//        return vacancyRepository.deleteById(id);
        var vacancyOptional = findById(id);
        if (vacancyOptional.isEmpty()) {
            return false;
        }
        var isDeleted = vacancyRepository.deleteById(id);
        fileService.deleteById(vacancyOptional.get().getFileId());
        return isDeleted;
    }

    @Override
    public boolean update(Vacancy vacancy, FileDto image) {
        var isNewFileEmpty = image.getContent().length == 0;
        if (isNewFileEmpty) {
            return vacancyRepository.update(vacancy);
        }
        /* esli peredan novyj ne pustoj fajl, to staryj udaljaem, a novyj sohranjaem */
        var oldFileId = vacancy.getFileId();
        saveNewFile(vacancy, image);
        var isUpdated = vacancyRepository.update(vacancy);
        fileService.deleteById(oldFileId);
        return isUpdated;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return vacancyRepository.findById(id);
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

}
