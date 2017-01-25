package br.com.alura.loja;

import static junit.framework.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;

public class ProjetoTest {

	@Test
	public void testaQueAConexaoComOServidorFunciona() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = target.path("/projetos").request().get(String.class);
		assertTrue(conteudo.contains("Minha loja"));
	}
	
}
