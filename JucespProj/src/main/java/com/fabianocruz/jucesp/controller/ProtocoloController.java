package com.fabianocruz.jucesp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fabianocruz.jucesp.entity.Funcionario;
import com.fabianocruz.jucesp.entity.Protocolo;
import com.fabianocruz.jucesp.enums.AcaoProtocolo;
import com.fabianocruz.jucesp.enums.StatusProtocolo;
import com.fabianocruz.jucesp.service.FuncionarioService;
import com.fabianocruz.jucesp.service.ProtocoloService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/protocolos")
public class ProtocoloController {

	@Autowired
	private ProtocoloService protocoloService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
    @GetMapping("/")
	public List<Protocolo> listarTodosProtocolo() throws Exception {
		return protocoloService.findAll();
	}

    @GetMapping(value = "/{id}" )
	public Protocolo listarProtocolo(@PathVariable Long id) throws Exception {
    	Protocolo protocolo = protocoloService.findById(id);
		return protocolo;
	}

    @GetMapping()
	public List<Protocolo> listarProtocoloPorStatus(@RequestParam String status) throws Exception {
    	List<Protocolo> protocolos = protocoloService.findByStatus(status);
		return protocolos;
	}
    
    @PutMapping("/{protocoloId}")
	public Protocolo atualizarProtocolo(@PathVariable Long protocoloId, @RequestBody Protocolo protocoloRequest) throws Exception {
    	Protocolo protocolo = protocoloService.findById(protocoloId);
    	if (protocolo == null) {
    		throw new Exception("Protocolo não encontrado");
    	}
    	
    	if (!protocolo.getStatus().equals(StatusProtocolo.Em_Analise)) {
    		throw new Exception("Protocolo já executado");
    	}
    	
    	if (protocoloRequest.getStatus().equals(StatusProtocolo.Aprovado)) {
    		String funcionarioJson = protocolo.getFuncionarioJson();
    		Funcionario funcionario = getFuncionarioFromJson(funcionarioJson);
    		if (protocolo.getAcao().equals(AcaoProtocolo.Alterar)) {
    			funcionarioService.update(funcionario);
    		}    		
    		
    		if (protocolo.getAcao().equals(AcaoProtocolo.Incluir)) {
    			funcionarioService.create(funcionario);
    		}    		
    		
    		if (protocolo.getAcao().equals(AcaoProtocolo.Remover)) {
    			funcionarioService.delete(funcionario);
    		}    		
    	}
		Protocolo protocolo2 = protocoloService.updateStatus(protocoloId, protocoloRequest.getStatus());
		return protocolo2;
    }

    @DeleteMapping("/{protocoloId}")
    public Protocolo removerProtocolo(@PathVariable Long protocoloId) throws Exception {
    	Protocolo protocolo = protocoloService.findById(protocoloId);
    	if (protocolo == null) {
    		throw new Exception("Protocolo não encontrado");
    	}
    	
    	if (!protocolo.getStatus().equals(StatusProtocolo.Em_Analise)) {
    		throw new Exception("Protocolo já executado");
    	}

    	protocoloService.delete(protocolo);
    	return protocolo;
    }

    private Funcionario getFuncionarioFromJson(String funcionarioJson) {
		Gson gson = new Gson();
		return gson.fromJson(funcionarioJson, Funcionario.class);
	}



}

