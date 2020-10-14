package br.com.mascarenhas.jmlogs.controller;

import br.com.mascarenhas.jmlogs.exception.ErrorDetails;
import br.com.mascarenhas.jmlogs.model.Log;
import br.com.mascarenhas.jmlogs.repository.LogRepository;
import br.com.mascarenhas.jmlogs.service.FileSystemStorageService;
import br.com.mascarenhas.jmlogs.service.LogService;
import br.com.mascarenhas.jmlogs.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LogController {
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private FileSystemStorageService fileSystemStorageService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private LogService logService;

    @CrossOrigin
    @GetMapping("/logs")
    public ResponseEntity<List<Log>> getAllL(){
        List<Log> logs = logRepository.findAll();
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/logs/{id}")
    public ResponseEntity<Log> getById(@PathVariable(value="id") long logId){
        Log log = logRepository.findById(logId).orElseThrow(()-> new ResourceNotFoundException("Log not found for this id: " + logId));
        return new ResponseEntity<>(log, HttpStatus.OK);
    }

    @GetMapping("/logs/ip")
    public ResponseEntity<List<Log>> getByIp(@RequestParam String ip){
        List<Log> logs = null;
        logs = logRepository.finByIp(ip);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping("/logs/request")
    public ResponseEntity<List<Log>> getByRequest(@RequestParam String request){
        List<Log> logs = null;
        logs = logRepository.finByRequest(request);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @PostMapping("/logs")
    public ResponseEntity<Log> create(@Valid @RequestBody Log log){
        logRepository.save(log);
        return new ResponseEntity<>(log, HttpStatus.OK);
    }

    @PutMapping("/logs/{id}")
    public ResponseEntity<Log> update(@PathVariable(value="id") long logId, @Valid @RequestBody Log log){
        Log logPersisted = logRepository.findById(logId).orElseThrow(()-> new ResourceNotFoundException("Log not found fot this id: " + logId));

        logPersisted.setDate(log.getDate());
        logPersisted.setIp(log.getIp());
        logPersisted.setRequest(log.getRequest());
        logPersisted.setStatus(log.getStatus());
        logPersisted.setUserAgent(log.getUserAgent());

        logRepository.save(logPersisted);
        return new ResponseEntity<>(log, HttpStatus.OK);
    }

    @DeleteMapping("/logs/{id}")
    public ResponseEntity<Log> delete(@PathVariable (value = "id") long logId){
        Log log = logRepository.findById(logId).orElseThrow(()-> new ResourceNotFoundException("Log not found for this id: " + logId));
        logRepository.delete(log);
        return new ResponseEntity<>(log, HttpStatus.OK);
    }

    @PostMapping(value="/logs/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<List<Log>> uploadLog(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException {
        String fileName;
        String pathStorage;

        List<Log> logs;
        fileName = file.getOriginalFilename();
        pathStorage = fileSystemStorageService.getPath();

        storageService.store(file);
        file.getOriginalFilename();

        try{
            logs = logService.deserializeLog(pathStorage, fileName);
            logRepository.saveAll(logs);
            return new ResponseEntity(logs, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>((List<Log>) new ErrorDetails(new Date(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}