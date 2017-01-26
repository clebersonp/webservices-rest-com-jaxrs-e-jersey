package br.com.alura.loja;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Projeto;

public class ProjetoTest {

	private HttpServer server;

	@Before
	public void init() throws Exception {
		server = Servidor.startaServidor();
	}
	
	@After
	public void mataServidor() {
		server.stop();
	}
	
	@Test
	public void testaQueBuscaUmProjetoEsperado() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = target.path("/projetos/1").request().get(String.class);
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
		String nome = projeto.getNome();
		assertTrue(nome.contains("Minha loja"));
	}
	
	@Test
	public void testaQueAdicionaProjeto() {
		Projeto projeto = new Projeto("Java", 4, 2017);
		
		String xml = projeto.toXML();
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		Response response = target.path("/projetos").request().post(entity);
		
		assertEquals("<status>Sucesso</status>", response.readEntity(String.class));
	}
	
}

























