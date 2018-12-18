package br.com.casadocodigo.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.wesleyegberto.alura.springmvc.config.JPAFactoryConfig;
import com.github.wesleyegberto.alura.springmvc.dao.ProdutoDAO;
import com.github.wesleyegberto.alura.springmvc.model.Produto;
import com.github.wesleyegberto.alura.springmvc.model.TipoPreco;

import br.com.casadocodigo.loja.builders.ProdutoBuilder;
import br.com.casadocodigo.loja.config.DataSourceConfigurationTest;

// Rota no container de teste do Spring
@RunWith(SpringJUnit4ClassRunner.class)
// Carrega as classes de configuração para inicar o contexto
@ContextConfiguration(classes = {
	JPAFactoryConfig.class,
	ProdutoDAO.class,
	DataSourceConfigurationTest.class // DataSource de Profile de test
})
// Profile de test para carregar configurações específicas
@ActiveProfiles("test")
public class ProdutoDAOTest {

	@Autowired
	private ProdutoDAO produtoDao;

	@Test
	@Transactional // o Spring fará rollback
	public void deveSomarTodosOsPrecosPorTipoLivro() {

		List<Produto> livrosImpressos = ProdutoBuilder
				.newProduto(TipoPreco.IMPRESSO, BigDecimal.TEN)
				.more(3).buildAll();
		List<Produto> livrosEbook = ProdutoBuilder
				.newProduto(TipoPreco.EBOOK, BigDecimal.TEN)
				.more(3).buildAll();

		livrosImpressos.stream().forEach(produtoDao::salva);
		livrosEbook.stream().forEach(produtoDao::salva);

		BigDecimal valor = produtoDao.somaPrecosPorTipo(TipoPreco.EBOOK);
		Assert.assertEquals(new BigDecimal(40).setScale(2), valor);
	}

}