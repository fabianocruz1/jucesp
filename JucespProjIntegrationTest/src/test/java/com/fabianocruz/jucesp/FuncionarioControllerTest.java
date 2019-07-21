//package com.fabianocruz.jucesp;
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
//import com.fabianocruz.jucesp.repository.FuncionarioRepository;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//
//import org.json.JSONException;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.*;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//import java.util.ArrayList;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
//@ActiveProfiles("test")
//public class FuncionarioControllerTest {
//
//    private static final ObjectMapper om = new ObjectMapper();
//	private static final String NOME = "fabiano";
//	private static final String CPF = "98765432112";
//	private static final String NOME2 = "fabiano2";
//	private static final String CPF2 = "12345678912";
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @MockBean
//    private FuncionarioRepository mockRepository;
//
//    /*
//        {
//            "timestamp":"2019-03-05T09:34:13.280+0000",
//            "status":400,
//            "errors":["Author is not allowed.","Please provide a price","Please provide a author"]
//        }
//     */
//    @Test
//    public void save_emptyAuthor_emptyPrice_400() throws JSONException {
//
//        Funcionario funcionario = criaFuncionario(NOME, CPF);
//        Gson gson = new Gson();
//        String json = gson.toJson(funcionario);
//        
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<>(json, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity("/funcionarios", entity, String.class);
//
//        String expectedJson = "{\"status\":400,\"errors\":[\"Author is not allowed.\",\"Please provide a price\",\"Please provide a author\"]}";
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        JSONAssert.assertEquals(expectedJson, response.getBody(), false);
//
//        verify(mockRepository, times(0)).save(any(Funcionario.class));
//
//    }
//
//    /*
//        {
//            "timestamp":"2019-03-05T09:34:13.207+0000",
//            "status":400,
//            "errors":["Author is not allowed."]
//        }
//     */
//    @Test
//    public void save_invalidAuthor_400() throws JSONException {
//
//        String bookInJson = "{\"name\":\" Spring REST tutorials\", \"author\":\"abc\",\"price\":\"9.99\"}";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<>(bookInJson, headers);
//
//        //Try exchange
//        ResponseEntity<String> response = restTemplate.exchange("/books", HttpMethod.POST, entity, String.class);
//
//        String expectedJson = "{\"status\":400,\"errors\":[\"Author is not allowed.\"]}";
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        JSONAssert.assertEquals(expectedJson, response.getBody(), false);
//
//        verify(mockRepository, times(0)).save(any(Funcionario.class));
//
//    }
//
//    private static void printJSON(Object object) {
//        String result;
//        try {
//            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
//            System.out.println(result);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
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
