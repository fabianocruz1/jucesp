package com.fabianocruz.jucesp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fabianocruz.jucesp.entity.Banco;
import com.fabianocruz.jucesp.entity.DadosBancarios;
import com.fabianocruz.jucesp.entity.Endereco;
import com.fabianocruz.jucesp.entity.Funcionario;
import com.fabianocruz.jucesp.entity.Telefone;
import com.fabianocruz.jucesp.enums.Cargo;
import com.fabianocruz.jucesp.enums.ContaTipo;
import com.fabianocruz.jucesp.enums.EstadoCivil;
import com.fabianocruz.jucesp.enums.TelefoneTipo;
import com.fabianocruz.jucesp.service.FuncionarioService;
import com.fabianocruz.jucesp.service.impl.FuncionarioServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FuncionarioServiceTest {

	private static final String NOME = "fabiano";
	private static final String CPF = "98765432112";
	private static final String NOME2 = "fabiano2";
	private static final String CPF2 = "12345678912";
	
	@Autowired
	FuncionarioService funcionarioService;
	
	private Funcionario funcionario;
	private DadosBancarios dadosBancarios;
	private Endereco endereco;
	private EstadoCivil estadoCivil;
	private List<Telefone> telefones;
	private Banco banco;

	@TestConfiguration
    static class FuncionarioServiceImplTestContextConfiguration {
  
        @Bean
        public FuncionarioService employeeService() {
            return new FuncionarioServiceImpl();
        }
    }
	
	@Before
	public void setUp() throws Exception {
		funcionario = criaFuncionario(NOME, CPF);
	}
	
	private Funcionario criaFuncionario(String nome, String cpf) {
		
		endereco = new Endereco();
		endereco.setBairro("bairro 1");
		endereco.setCep(0204000);
		endereco.setMunicipio("São Paulo");
		endereco.setRua("Rua São Bento, 111");
		endereco.setUf("SP");
		
		estadoCivil = EstadoCivil.Casado;
		
		banco = new Banco();
		banco.setNome("BB");
		banco.setNumero(1);
		
		dadosBancarios = new DadosBancarios();
		dadosBancarios.setAgencia(123);
		dadosBancarios.setBanco(banco);
		dadosBancarios.setConta(123456);
		dadosBancarios.setDigito("8");
		dadosBancarios.setTipo(ContaTipo.Conta_Corrente);
		
		Telefone tel1 = new Telefone();
		tel1.setDdd("11");
		tel1.setNumero("99998888");
		tel1.setTipo(TelefoneTipo.Celular);
		
		Telefone tel2 = new Telefone();
		tel2.setDdd("11");
		tel2.setNumero("30662598");
		tel2.setTipo(TelefoneTipo.Comercial);
		
		telefones = new ArrayList<Telefone>();
		telefones.add(tel1);
		telefones.add(tel2);
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setCargo(Cargo.Analista);
		funcionario.setDadosBancarios(dadosBancarios);
		funcionario.setEndereco(endereco);
		funcionario.setEstadoCivil(estadoCivil);
		Integer salario = 1000000;
		funcionario.setSalario(salario);
		funcionario.setTelefones(telefones);

		return funcionario;
	}

	@Test
	public void whenFuncionarioNovo_thenCriaFuncionario() {
		try {
			Funcionario funcionario2 = funcionarioService.create(funcionario);
			assertNotNull(funcionario2);
			assertNotNull(funcionario2.getId());
			assertTrue(0 < funcionario2.getId());
			assertEquals(NOME, funcionario2.getNome());
			assertEquals(telefones.size(), funcionario2.getTelefones().size());
			assertEquals(dadosBancarios, funcionario2.getDadosBancarios());
			assertEquals(estadoCivil, funcionario2.getEstadoCivil());
			assertEquals(endereco, funcionario2.getEndereco());
			assertEquals(banco, funcionario2.getDadosBancarios().getBanco());
			assertEquals(Cargo.Analista, funcionario2.getCargo());
			
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void whenCpfJaExiste_thenThrowsException() {
		try {
			funcionarioService.create(funcionario);
			funcionarioService.create(funcionario);
			fail("Criou funcionário com cpf já existente");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
//	@Test
	public void whenFaltaCampoObrigatorio_thenThrowsException() {
		try {

			Funcionario funcionario = new Funcionario();
//			funcionario.setNome(NOME);
//			funcionario.setCpf(CPF);
			funcionario.setCargo(Cargo.Analista);
			funcionario.setDadosBancarios(dadosBancarios);
			funcionario.setEndereco(endereco);
			funcionario.setEstadoCivil(estadoCivil);
			Integer salario = 1000000;
			funcionario.setSalario(salario);
			funcionario.setTelefones(telefones);
			
			Funcionario funcionario2 = funcionarioService.create(funcionario);
			
			System.out.println(funcionario2);
			
			if (funcionario2 != null) {
				assertNotNull(funcionario2);
				assertEquals(NOME, funcionario2.getNome());
				fail("Criou funcionário com dados incompletos");				
			} else {
				assertTrue(true);
			}
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testFindAll() {
		try {
			Funcionario func1 = criaFuncionario(NOME, CPF);
			funcionarioService.create(func1);
			
			Funcionario func2 = criaFuncionario(NOME2, CPF2);
			funcionarioService.create(func2);

			List<Funcionario> findAll = funcionarioService.findAll();
			
			assertNotNull("Retornou lista null", findAll);
			assertTrue("Retornou lista com tamanho "+findAll.size(), findAll.size() == 2);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(e.getMessage(), false);
		}
	}

	@Test
	public void testFindByCpf() {
		try {
			Funcionario func1 = criaFuncionario(NOME, CPF);
			funcionarioService.create(func1);
			
			Funcionario func2 = criaFuncionario(NOME2, CPF2);
			funcionarioService.create(func2);

			Funcionario findByCpf = funcionarioService.findByCpf(CPF);			
			assertNotNull("Retornou null", findByCpf);
			assertTrue("Retornou registro errado "+CPF, findByCpf.getCpf().equalsIgnoreCase(CPF));
			
			Funcionario findByCpf2 = funcionarioService.findByCpf(CPF2);			
			assertNotNull("Retornou null", findByCpf2);
			assertTrue("Retornou registro errado "+CPF2, findByCpf2.getCpf().equalsIgnoreCase(CPF2));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(e.getMessage(), false);
		}
	}

	@Test
	public void testFindById() {
		try {
			Funcionario func1 = criaFuncionario(NOME, CPF);
			Funcionario createdFunc = funcionarioService.create(func1);
			Long id1 = createdFunc.getId();
			
			Funcionario func2 = criaFuncionario(NOME2, CPF2);
			createdFunc = funcionarioService.create(func2);
			Long id2 = createdFunc.getId();

			Funcionario findBy = funcionarioService.findById(id1);
			assertNotNull("Retornou null", createdFunc);
			assertTrue("Retornou registro errado id1 ", findBy.getId() == id1);

			findBy = funcionarioService.findById(id2);
			assertNotNull("Retornou null", createdFunc);
			assertTrue("Retornou registro errado id2 ", findBy.getId() == id2);
			
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(e.getMessage(), false);
		}
	}

	@Test
	public void testUpdate() {

		try {
			Funcionario func1 = criaFuncionario(NOME, CPF);
			funcionarioService.create(func1);
	
			func1.setNome(NOME2);
			
			Funcionario updatedFunc = funcionarioService.update(func1);
			
			assertNotNull("Retornou null", updatedFunc);
			assertTrue("Não atualizou nome ", updatedFunc.getNome().equalsIgnoreCase(NOME2));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	
	}

	@Test
	public void testDelete() {
		try {
			Funcionario func1 = criaFuncionario(NOME, CPF);
			funcionarioService.create(func1);
			funcionarioService.delete(func1);
			Funcionario findByCpf = funcionarioService.findByCpf(CPF);
			assertTrue("Retornou registro apagado ", findByCpf == null);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	
	}

}
