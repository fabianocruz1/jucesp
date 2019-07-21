//package com.fabianocruz.jucesp;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.fabianocruz.jucesp.entity.Banco;
//import com.fabianocruz.jucesp.entity.DadosBancarios;
//import com.fabianocruz.jucesp.entity.Endereco;
//import com.fabianocruz.jucesp.entity.Funcionario;
//import com.fabianocruz.jucesp.entity.Telefone;
//import com.fabianocruz.jucesp.enums.Cargo;
//import com.fabianocruz.jucesp.enums.ContaTipo;
//import com.fabianocruz.jucesp.enums.EstadoCivil;
//import com.fabianocruz.jucesp.enums.TelefoneTipo;
// 
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//public class SpringBootDemoApplicationTests
//{
//    @Autowired
//    private TestRestTemplate restTemplate;
//     
//    @LocalServerPort
//    int randomServerPort;
// 
//	private static final String NOME = "fabiano";
//	private static final String CPF = "98765432112";
//	private static final String NOME2 = "fabiano2";
//	private static final String CPF2 = "12345678912";
//
//    @Test
//    public void testAddEmployeeSuccess() throws URISyntaxException
//    {
//        final String baseUrl = "http://localhost:"+8888+"/funcionarios/";
//        URI uri = new URI(baseUrl);
//        Funcionario funcionario = criaFuncionario(NOME, CPF);
//        
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("X-COM-PERSIST", "true");     
// 
//        HttpEntity<Funcionario> request = new HttpEntity<>(funcionario, headers);
//         
//        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
//         
//        //Verify request succeed
//        Assert.assertEquals(201, result.getStatusCodeValue());
//    }
//     
//    @Test
//    public void testAddEmployeeMissingHeader() throws URISyntaxException
//    {
//        final String baseUrl = "http://localhost:"+8888+"/funcionarios/";
//        URI uri = new URI(baseUrl);
//        Funcionario funcionario = criaFuncionario(NOME, CPF);
//         
//        HttpHeaders headers = new HttpHeaders();
// 
//        HttpEntity<Funcionario> request = new HttpEntity<>(funcionario, headers);
//         
//        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
//         
//        //Verify bad request and missing header
//        Assert.assertEquals(400, result.getStatusCodeValue());
//        Assert.assertEquals(true, result.getBody().contains("Missing request header"));
//    }
// 
//	private Funcionario criaFuncionario(String nome, String cpf) {
//		
//		Endereco endereco = new Endereco();
//		endereco.setBairro("bairro 1");
//		endereco.setCep(0204000);
//		endereco.setMunicipio("São Paulo");
//		endereco.setRua("Rua São Bento, 111");
//		endereco.setUf("SP");
//		
//		EstadoCivil estadoCivil = EstadoCivil.Casado;
//		
//		Banco banco = new Banco();
//		banco.setNome("BB");
//		banco.setNumero(1);
//		
//		DadosBancarios dadosBancarios = new DadosBancarios();
//		dadosBancarios.setAgencia(123);
//		dadosBancarios.setBanco(banco);
//		dadosBancarios.setConta(123456);
//		dadosBancarios.setDigito("8");
//		dadosBancarios.setTipo(ContaTipo.ContaCorrente);
//		
//		Telefone tel1 = new Telefone();
//		tel1.setDdd("11");
//		tel1.setNumero("99998888");
//		tel1.setTipo(TelefoneTipo.Celular);
//		
//		Telefone tel2 = new Telefone();
//		tel2.setDdd("11");
//		tel2.setNumero("30662598");
//		tel2.setTipo(TelefoneTipo.Comercial);
//		
//		ArrayList<Telefone> telefones = new ArrayList<Telefone>();
//		telefones.add(tel1);
//		telefones.add(tel2);
//		
//		Funcionario funcionario = new Funcionario();
//		funcionario.setNome(nome);
//		funcionario.setCpf(cpf);
//		funcionario.setCargo(Cargo.Analista);
//		funcionario.setDadosBancarios(dadosBancarios);
//		funcionario.setEndereco(endereco);
//		funcionario.setEstadoCivil(estadoCivil);
//		Integer salario = 1000000;
//		funcionario.setSalario(salario);
//		funcionario.setTelefones(telefones);
//
//		return funcionario;
//	}
//
//}
