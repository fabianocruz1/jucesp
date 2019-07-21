package com.fabianocruz.jucesp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabianocruz.jucesp.entity.Funcionario;
import com.fabianocruz.jucesp.entity.Protocolo;
import com.fabianocruz.jucesp.service.FuncionarioService;
import com.fabianocruz.jucesp.service.ProtocoloService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private ProtocoloService protocoloService;

    @GetMapping("/")
	public List<Funcionario> listarTodosFuncionarios() throws Exception {
		return funcionarioService.findAll();
	}

    @GetMapping(value = "/{id}" )
	public Funcionario listarFuncionario(@PathVariable Long id) throws Exception {
		Funcionario funcionario = funcionarioService.findById(id);
		return funcionario;
	}

	@PostMapping
	public Protocolo criarFuncionario(@RequestBody Funcionario funcionario ) throws Exception {
//		try {
//			Funcionario funcionario2 = funcionarioService.create(funcionario);
//			return funcionario2;			
//		} catch (Exception e) {
//			throw new Exception("Já existe funcionário com esse CPF "+funcionario.getCpf());
//		}
		
		if (funcionarioService.findByCpf(funcionario.getCpf()) != null) {
			throw new Exception("Já existe funcionário com esse CPF "+funcionario.getCpf());
		}

		String json = getFuncionarioAsJson(funcionario);
		Protocolo protocolo = protocoloService.createProtocoloNewFuncionario(json);
		return protocolo;
	}
	
	@PutMapping("/{funcionarioId}")
	public Protocolo atualizarFuncionario(@PathVariable Long funcionarioId, @RequestBody Funcionario funcionario ) throws Exception {
//		Funcionario funcionario2 = funcionarioService.findById(funcionarioId);
//		funcionario.setId(funcionario2.getId());
//		funcionario2 = funcionarioService.update(funcionario);
//		return funcionario;

		if (funcionarioService.findById(funcionarioId) == null) {
			throw new Exception("Registro não encontrado "+funcionarioId);
		}
		
		String json = getFuncionarioAsJson(funcionario);
		Protocolo protocolo = protocoloService.createProtocoloUpdateFuncionario(json);
		return protocolo;
	}

	@DeleteMapping("/{funcionarioId}")
	public Protocolo removerFuncionario(@PathVariable Long funcionarioId) throws Exception {
//		Funcionario funcionario2 = funcionarioService.findById(funcionarioId);
//		funcionarioService.delete(funcionario2);
		
		Funcionario funcionario = funcionarioService.findById(funcionarioId);
		if (funcionario == null) {
			throw new Exception("Registro não encontrado "+funcionarioId);
		}
		
		Funcionario funcionario2 = new Funcionario();
		funcionario2.setId(funcionarioId);
		
		String json = getFuncionarioAsJson(funcionario2);
		Protocolo protocolo = protocoloService.createProtocoloRemoveFuncionario(json);
		return protocolo;
	}

	private String getFuncionarioAsJson(Funcionario funcionario) {
		Gson gson = new Gson();
		String json = gson.toJson(funcionario);
		return json;
	}

}
