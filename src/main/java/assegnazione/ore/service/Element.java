package assegnazione.ore.service;

import org.springframework.http.ResponseEntity;

public interface Element<T>{

    ResponseEntity<?> addElement(T element) throws Exception;

    ResponseEntity<?> getInformations(int id) throws Exception;




}
