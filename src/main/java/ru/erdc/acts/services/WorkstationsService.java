package ru.erdc.acts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.erdc.acts.entities.Workstation;
import ru.erdc.acts.repositories.WorkstationRepository;

import java.io.IOException;
import java.util.List;

@Service
public class WorkstationsService {
    private FileStorageService fileStorageService;
    private WorkstationRepository workstationRepository;

//    @Autowired
//    public void setWorkstationRepository(WorkstationRepository workstationRepository) {
//        this.workstationRepository = workstationRepository;
//    }
    public WorkstationsService(WorkstationRepository workstationRepository, FileStorageService fileStorageService) {
        this.workstationRepository = workstationRepository;
        this.fileStorageService = fileStorageService;
    }

    public Workstation addWorkstation(Workstation workstation, MultipartFile file) throws IOException {
        String filePath = fileStorageService.storeFile(file);
        workstation.setFile_Path(filePath);
        return workstationRepository.save(workstation);
    }

    public Workstation addFileToWorkstation(Long id, MultipartFile file) throws IOException {
        Workstation workstation = workstationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid workstation Id:" + id));
        String filePath = fileStorageService.storeFile(file);
        workstation.setFile_Path(filePath);
        return workstationRepository.save(workstation);
    }

    public List<Workstation> findAll(){
        return (List<Workstation>) workstationRepository.findAll();
    }

    public List<Workstation> findAllBySpec(Specification<Workstation> specification) {
        return workstationRepository.findAll(specification);
    }
    public Workstation save(Workstation workstation) {
        return workstationRepository.save(workstation);
    }

    public Workstation findById(Long id) {
        return workstationRepository.findById(id).get();
    }
}
