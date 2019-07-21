package com.fabianocruz.jucesp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fabianocruz.jucesp.entity.Protocolo;
import com.fabianocruz.jucesp.enums.StatusProtocolo;

public interface ProtocoloRepository extends CrudRepository<Protocolo, Long>{

	Protocolo findByStatus(String status);

	List<Protocolo> findAllByStatus(StatusProtocolo status);


}
