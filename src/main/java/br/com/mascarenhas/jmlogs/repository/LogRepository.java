package br.com.mascarenhas.jmlogs.repository;

import br.com.mascarenhas.jmlogs.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface LogRepository extends JpaRepository<Log,Long>{

    @Query("from Log l where l.ip = :ip")
    List<Log> finByIp(String ip);

    @Query("from Log l where l.request like CONCAT('%',:request,'%')")
    List<Log> finByRequest(String request);
}
