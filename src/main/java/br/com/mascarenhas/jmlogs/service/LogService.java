package br.com.mascarenhas.jmlogs.service;

import br.com.mascarenhas.jmlogs.exception.ErrorDetails;
import br.com.mascarenhas.jmlogs.model.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Service
public class LogService {

    public List<Log> deserializeLog(String filePath, String fileName) throws IOException {
        Log log = null;
        List<Log> logs = new ArrayList();

        try (FileInputStream file = new FileInputStream(filePath +"/" + fileName);
             Scanner fileLog = new Scanner(file);){
            while(fileLog.hasNextLine()){
                String line = fileLog.nextLine();
                log = lineToLog(line);
                logs.add(log);
            }

        } catch (FileNotFoundException e) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage());
            new ResponseEntity(errorDetails , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return logs;
    }

    public Log lineToLog(String line){
        Log log = new Log();

        try {
            String[] arrayLog = line.split("\\|");

            Date dateLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                    .parse(arrayLog[0]);
            log.setDate(dateLog);
            log.setIp(arrayLog[1]);
            log.setRequest(arrayLog[2]);
            log.setStatus(new Integer(arrayLog[3]));
            log.setUserAgent(arrayLog[4]);
        } catch (Exception e){
            ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage());
            new ResponseEntity(errorDetails , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return log;
    }
}




