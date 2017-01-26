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

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

public class ClienteTest {
	
	private HttpServer server;

	@Before
	public void init() throws Exception {
		server = Servidor.startaServidor();
	}
	
	@After
	public void finalize() {
		server.stop();
	}
	
	@Test
	public void testaQueBuscaUmCarrinhoEsperado() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		String rua = carrinho.getRua();
		
		assertTrue(rua.contains("Rua Vergueiro 3185, 8 andar"));
	}
	
	@Test
	public void testaQueAdicionaUmCarrinho() {
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(3, "Bicicleta", 500.0, 1));
		carrinho.adiciona(new Produto(4, "Caderno", 45.60, 1));
		carrinho.setId(6);
		carrinho.setRua("Rua das esmeraldas");
		carrinho.setCidade("Santo André");
		
		String xml = carrinho.toXML();
		
		Client cliente = ClientBuilder.newClient();
		WebTarget target = cliente.target("http://localhost:8080");
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		Response response = target.path("/carrinhos").request().post(entity);
		
		assertEquals("<status>Sucesso</status>", response.readEntity(String.class));
		
	}
	
}

















