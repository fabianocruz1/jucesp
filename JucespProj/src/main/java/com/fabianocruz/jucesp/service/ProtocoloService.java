package com.fabianocruz.jucesp.service;

import java.util.List;

import com.fabianocruz.jucesp.entity.Protocolo;
import com.fabianocruz.jucesp.enums.StatusProtocolo;

public interface ProtocoloService {
	Protocolo createProtocoloNewFuncionario(String funcionario);
	Protocolo createProtocoloUpdateFuncionario(String funcionario2);
	Protocolo createProtocoloRemoveFuncionario(String funcionario);
	Protocolo updateStatus(Long protocoloId, StatusProtocolo novoStatus);
	
	Protocolo findById(Long id);

	List<Protocolo> findAll();
	List<Protocolo> findByStatus(String status);
	
	void delete(Protocolo protocolo);
}
