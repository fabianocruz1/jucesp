package com.fabianocruz.jucesp;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RestApiTest {

	private static final String COD_ITAU_341 = "341";
	private static final String _3333 = "3333";
	private static final String BANCO_ITAU = "Banco Itau";
	private static final String NOVO_NOME = "novo nome";
	private static final String NOVO_ESTADO_CIVIL = "Solteiro";
	static final String CPF1 = "12345678911";
	static final String CPF2 = "12345678922";
//	private int numDeProtocolos = 0;
//	private int numDeProtocolosAprovados = 0;
//	private int numDeProtocolosRejeitados = 0;
	
	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8888;		
	}

	/**
	 * Cria Funcionario
	 * 
	 */
	@Test
	void a1() {
		HashMap<String, Object> funcionario = criarFuncionario(CPF1);				
		
		Response response = given().
		contentType("application/json").
		accept("application/json").
		body(funcionario).
		when().
		post("/funcionarios").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		String idStr = response.jsonPath().getString("id");
		assertNotNull(idStr);

		Integer id = Integer.parseInt(idStr);
		assertNotNull(idStr);
		assertTrue(id > 0);

		String acao = response.jsonPath().getString("acao");
		assertNotNull(acao);

		String status = response.jsonPath().getString("status");
		assertNotNull(status);

		String funcionarioJson = response.jsonPath().getString("funcionarioJson");
		assertNotNull(funcionarioJson);

	}
	
	/**
	 * Tenta lista funcionario mas ainda não foi criado
	 * Retorna erro
	 */
	@Test
	void a2() {
		Response response = given().
		contentType("application/json").
		accept("application/json").
		when().
		get("/funcionarios/1").
		then().
		statusCode(500).
		contentType("application/json").
		extract().
		response();
		
		String idStr = response.jsonPath().getString("id");
		assertTrue(idStr == null);

	}

	/**
	 * Aprova protocolo de criacao de funcionario
	 * 
	 */
	@Test
	void a3() {
		
		HashMap<String, Object> protocolo = new HashMap<String, Object>();
		protocolo.put("status", "Aprovado");

		Response response = given().
		contentType("application/json").
		accept("application/json").
		body(protocolo).
		when().
		put("/protocolos/1").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		String idStr = response.jsonPath().getString("id");
		assertTrue(idStr != null);

	}

	
	/**
	 * Atualiza dados do funcionario
	 * Retorna protocolo de atualização
	 * 
	 */
	@Test
	void b1() {
		HashMap<String,Object> funcionario = criarFuncionario(CPF1);
		funcionario.replace("nome", NOVO_NOME);
		
		Response response = given().
		contentType("application/json").
		accept("application/json").
		body(funcionario).
		when().
		put("/funcionarios/1").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		String idStr = response.jsonPath().getString("id");
		assertNotNull(idStr);

		assertEquals(2, Integer.parseInt(idStr));
	}

	/**
	 * Aprova protocolo de atualização de funcionario
	 * 
	 */
	@Test
	void b2() {
		
		HashMap<String, Object> protocolo = new HashMap<String, Object>();
		protocolo.put("status", "Aprovado");

		Response response = given().
		contentType("application/json").
		accept("application/json").
		body(protocolo).
		when().
		put("/protocolos/"+2).
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		String idStr = response.jsonPath().getString("id");
		assertTrue(idStr != null);

	}


	/**
	 * Verifica nome alterado do funcionario
	 * 
	 */
	@Test
	void b3() {
		
		Response response = given().
		contentType("application/json").
		accept("application/json").
//		body(protocolo).
		when().
		get("/funcionarios/1").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		String idStr = response.jsonPath().getString("id");
		assertTrue(idStr != null);

		String nome = response.jsonPath().getString("nome");
		assertTrue(nome != null);
		assertEquals(NOVO_NOME, nome);

	}

	/**
	 * Atualiza dados do funcionario
	 * Retorna protocolo de atualização
	 * 
	 */
	@Test
	void c1() {
		HashMap<String,Object> funcionario = criarFuncionario(CPF1);
		funcionario.replace("estadoCivil", NOVO_ESTADO_CIVIL);
		
		Response response = given().
		contentType("application/json").
		accept("application/json").
		body(funcionario).
		when().
		put("/funcionarios/1").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		String idStr = response.jsonPath().getString("id");
		assertNotNull(idStr);

		assertEquals(3, Integer.parseInt(idStr));
	}

	/**
	 * Rejeita protocolo de atualização de funcionario
	 * 
	 */
	@Test
	void c2() {
		
		HashMap<String, Object> protocolo = new HashMap<String, Object>();
		protocolo.put("status", "Rejeitado");
		
		Response response = given().
		contentType("application/json").
		accept("application/json").
		body(protocolo).
		when().
		put("/protocolos/"+3).
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		String idStr = response.jsonPath().getString("id");
		assertTrue(idStr != null);

	}


	/**
	 * Verifica nome alterado do funcionario
	 * 
	 */
	@Test
	void c3() {
		
		Response response = given().
		contentType("application/json").
		accept("application/json").
//		body(protocolo).
		when().
		get("/funcionarios/1").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		String idStr = response.jsonPath().getString("id");
		assertTrue(idStr != null);

		String estadoCivil = response.jsonPath().getString("estadoCivil");
		assertTrue(estadoCivil != null);
		assertNotEquals(NOVO_ESTADO_CIVIL, estadoCivil);

	}

	/**
	 * Atualiza dados bancarios do funcionario
	 * Retorna protocolo de atualização
	 * 
	 */
	@Test
	void d1() {
		HashMap<String, Object> banco = new HashMap<String, Object>();
		banco.put("nome", BANCO_ITAU);
		banco.put("numero", COD_ITAU_341);

		HashMap<String, Object> dadosBancarios = new HashMap<String, Object>();
		dadosBancarios.put("banco", banco);
		dadosBancarios.put("tipo", "Conta_Corrente");
		dadosBancarios.put("agencia", _3333);
		dadosBancarios.put("conta", "444444");
		dadosBancarios.put("digito", "5");

		HashMap<String,Object> funcionario = criarFuncionario(CPF1);
		funcionario.replace("dadosBancarios", dadosBancarios);
		
		Response response = given().
		contentType("application/json").
		accept("application/json").
		body(funcionario).
		when().
		put("/funcionarios/1").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		String idStr = response.jsonPath().getString("id");
		assertNotNull(idStr);

		assertEquals(4, Integer.parseInt(idStr));
	}

	/**
	 * Aprova protocolo de atualização de funcionario
	 * 
	 */
	@Test
	void d2() {
		
		HashMap<String, Object> protocolo = new HashMap<String, Object>();
		protocolo.put("status", "Aprovado");

		Response response = given().
		contentType("application/json").
		accept("application/json").
		body(protocolo).
		when().
		put("/protocolos/"+4).
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		String idStr = response.jsonPath().getString("id");
		assertTrue(idStr != null);

	}


	/**
	 * Verifica dados bancarios alterado do funcionario
	 * 
	 */
	@Test
	void d3() {
		
		Response response = given().
		contentType("application/json").
		accept("application/json").
//		body(protocolo).
		when().
		get("/funcionarios/1").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		String idStr = response.jsonPath().getString("id");
		assertTrue(idStr != null);

		String dadosBancarios = response.jsonPath().getString("dadosBancarios");
		assertTrue(dadosBancarios != null);

		String agencia = response.jsonPath().getString("dadosBancarios.agencia");
		assertTrue(agencia != null);
		assertEquals(_3333, agencia);

		String banco = response.jsonPath().getString("dadosBancarios.banco");
		assertTrue(banco != null);

		String nomeBanco = response.jsonPath().getString("dadosBancarios.banco.nome");
		assertTrue(nomeBanco != null);
		assertEquals(BANCO_ITAU, nomeBanco);

		
	}


	/**
	 * Remove funcionario
	 * Retorna protocolo de atualização
	 * 
	 */
	@Test
	void z1() {
		Response response = given().
		contentType("application/json").
		accept("application/json").
//		body(funcionario).
		when().
		delete("/funcionarios/1").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		String idStr = response.jsonPath().getString("id");
		assertNotNull(idStr);

		assertEquals(5, Integer.parseInt(idStr));
	}

	/**
	 * Aprova protocolo de remoção de funcionario
	 * 
	 */
	@Test
	void z2() {
		
		HashMap<String, Object> protocolo = new HashMap<String, Object>();
		protocolo.put("status", "Aprovado");

		Response response = given().
		contentType("application/json").
		accept("application/json").
		body(protocolo).
		when().
		put("/protocolos/"+5).
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		String idStr = response.jsonPath().getString("id");
		assertTrue(idStr != null);

	}


	/**
	 * Verifica remoção do funcionario
	 * 
	 */
	@Test
	void z3() {
		
		Response response = given().
		contentType("application/json").
		accept("application/json").
//		body(protocolo).
		when().
		get("/funcionarios/").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		List<Object> list = response.jsonPath().getList("");
		assertTrue(list != null);
		assertTrue(list.isEmpty());

	}

	/**
	 * Verifica lista de protocolos Rejeitados
	 * 
	 */
	@Test
	void z4() {
		
		Response response = given().
		contentType("application/json").
		accept("application/json").
//		body(protocolo).
		when().
		get("/protocolos?status=Rejeitado").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		List<Object> list = response.jsonPath().getList("");
		assertTrue(list != null);
		assertTrue(list.size() == 1);

	}
	
	/**
	 * Verifica lista de protocolos Aprovados
	 * 
	 */
	@Test
	void z5() {
		
		Response response = given().
		contentType("application/json").
		accept("application/json").
//		body(protocolo).
		when().
		get("/protocolos?status=Aprovado").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		List<Object> list = response.jsonPath().getList("");
		assertTrue(list != null);
		assertTrue(list.size() == 4);

	}

	/**
	 * Verifica lista de protocolos 
	 * 
	 */
	@Test
	void z6() {
		
		Response response = given().
		contentType("application/json").
		accept("application/json").
//		body(protocolo).
		when().
		get("/protocolos/").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		List<Object> list = response.jsonPath().getList("");
		assertTrue(list != null);
		assertTrue(list.size() == 5 );

	}

	/**
	 * Remove protocolo "em analise"
	 * 
	 */
	@Test
	void z7() {
		HashMap<String,Object> funcionario = criarFuncionario(CPF1);
		funcionario.replace("nome", NOVO_NOME);
		
		Response responseCriaProtocolo = given().
		contentType("application/json").
		accept("application/json").
		body(funcionario).
		when().
		post("/funcionarios/").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		String idStr = responseCriaProtocolo.jsonPath().getString("id");
		assertNotNull(idStr);
		assertEquals(6, Integer.parseInt(idStr));
	
		
		
//		HashMap<String, Object> protocolo = new HashMap<String, Object>();
//		protocolo.put("id", numDeProtocolos);

		Response responseRemoveProtocolo = given().
		contentType("application/json").
		accept("application/json").
//		body(protocolo).
		when().
		delete("/protocolos/6").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		
		
		
		

		
		Response responseVerifica = given().
		contentType("application/json").
		accept("application/json").
//		body(protocolo).
		when().
		get("/protocolos/").
		then().
		statusCode(200).
		contentType("application/json").
		extract().
		response();
		
		List<Object> list = responseVerifica.jsonPath().getList("");
		assertTrue(list != null);
		assertTrue(list.size() == 5 );

	}

//	/**
//	 * Retorna erro ao criar funcionario já exixtente
//	 */
//	@Test
//	void b() {
//		HashMap<String, Object> funcionario = criarFuncionario(CPF1);				
//		 
//		Response response = given().
//		contentType("application/json").
//		accept("application/json").
//		body(funcionario).
//		when().
//		post("/funcionarios").
//		then().
//		statusCode(200).
//		contentType("application/json").
//		extract().
//		response();
//		
//		String idStr = response.jsonPath().getString("id");
//		assertTrue(null == idStr);
//
//	}
//	
//	/**
//	 * Retorna erro ao tentar criar funcionario com dados incompletos
//	 */
//	@Test
//	void c() {
//		HashMap<String, Object> funcionario = criarFuncionario(null);				
//		
//		Response response = given().
//		contentType("application/json").
//		accept("application/json").
//		body(funcionario).
//		when().
//		post("/funcionarios").
//		then().
//		statusCode(500).
//		contentType("application/json").
//		extract().
//		response();
//		
//		String idStr = response.jsonPath().getString("id");
//		assertTrue(null == idStr);
//
//	}
//
//	/**
//	 * Lista Funcionarios/1
//	 */
//	@Test
//	void d() {
//		Response response = given().
//		contentType("application/json").
//		accept("application/json").
//		when().
//		get("/funcionarios/1").
//		then().
//		statusCode(200).
//		contentType("application/json").
//		extract().
//		response();
//		
//		String idStr = response.jsonPath().getString("id");
//		assertNotNull(idStr);
//
//		Integer id = Integer.parseInt(idStr);
//		assertNotNull(idStr);
//		assertTrue(id > 0);
//
//		String dadosBancariosStr = response.jsonPath().getString("dadosBancarios");
//		assertNotNull(dadosBancariosStr);
//
//		String telefonesStr = response.jsonPath().getString("telefones");
//		assertNotNull(telefonesStr);
//
//		String enderecoStr = response.jsonPath().getString("endereco");
//		assertNotNull(enderecoStr);
//
//		
//	}
//
//
//	/**
//	 * Atualiza funcionario 1
//	 */
//	@Test
//	void e() {
//		HashMap<String, Object> funcionario = criarFuncionario(CPF1);				
//		funcionario.replace("nome", NOVO_NOME);
//		
//		Response response = given().
//		contentType("application/json").
//		accept("application/json").
//		body(funcionario).
//		when().
//		put("/funcionarios/1").
//		then().
//		statusCode(200).
//		contentType("application/json").
//		extract().
//		response();
//		
//		String idStr = response.jsonPath().getString("id");
//		assertNotNull(idStr);
//
//		Integer id = Integer.parseInt(idStr);
//		assertNotNull(idStr);
//		assertTrue(id == 1);
//
//		String nome = response.jsonPath().getString("nome");
//		assertNotNull(nome);
//		assertTrue(NOVO_NOME.equalsIgnoreCase(nome));
//		
//	}
//
//	/**
//	 * Remove funcionario 1
//	 */
//	@Test
//	void f() {
//		HashMap<String, Object> funcionario = criarFuncionario(CPF1);				
//		funcionario.replace("nome", NOVO_NOME);
//		
//		Response response = given().
//		contentType("application/json").
//		accept("application/json").
//		body(funcionario).
//		when().
//		delete("/funcionarios/1").
//		then().
//		statusCode(200).
//		contentType("application/json").
//		extract().
//		response();
//		
//		String idStr = response.jsonPath().getString("id");
//		assertTrue(idStr == null);
//	}


	private HashMap<String, Object> criarFuncionario(Object cpf) {
		List<HashMap<String, Object>> telefones = new ArrayList<>();
		
		HashMap<String, Object> telefone = new HashMap<String, Object>();
		telefone.put("ddd", "11");
		telefone.put("numero", "999999999");
		telefone.put("tipo", "Celular");
		telefone.put("complemento", "pessoal");
		telefones.add(telefone);
		
		HashMap<String, Object> banco = new HashMap<String, Object>();
		banco.put("nome", "Banco do Brasil");
		banco.put("numero", "001");

		HashMap<String, Object> dadosBancarios = new HashMap<String, Object>();
		dadosBancarios.put("banco", banco);
		dadosBancarios.put("tipo", "Conta_Corrente");
		dadosBancarios.put("agencia", "2222");
		dadosBancarios.put("conta", "21212");
		dadosBancarios.put("digito", "X");

		HashMap<String, Object> endereco = new HashMap<String, Object>();
		endereco.put("rua", "rua são bento, 111");
		endereco.put("bairro", "centro");
		endereco.put("uf", "sp");
		endereco.put("municipio", "são paulo");
		endereco.put("cep", "12345000");
		
		HashMap<String, Object> funcionario = new HashMap<String, Object>();
		funcionario.put("nome", "luiz3");
		funcionario.put("cpf", cpf);
		funcionario.put("estadoCivil", "Casado");
		funcionario.put("cargo", "Analista");
		funcionario.put("salario", 100000);
		funcionario.put("dadosBancarios", dadosBancarios);
		funcionario.put("telefones", telefones);
		funcionario.put("endereco", endereco);
		return funcionario;
	}

}
