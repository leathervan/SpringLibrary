package my.service;

import my.entity.File;
import my.entity.User;
import my.repo.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    private final FileRepo fileRepo;

    @Autowired
    public FileService(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }
    public List<File> getAll(){
        return fileRepo.findAll();
    }

    public File get(int id){
        Optional<File> file = fileRepo.findById(id);
        return file.orElse(null);
    }

    @Transactional
    public File update(int id, File file){
        file.setBookId(id);
        return fileRepo.save(file);
    }
    @Transactional
    public void delete(int id){
        fileRepo.deleteById(id);
    }
}
