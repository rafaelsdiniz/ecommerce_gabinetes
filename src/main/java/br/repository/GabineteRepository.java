package br.repository;

import java.util.List;

import br.model.Gabinete;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GabineteRepository implements PanacheRepository<Gabinete> {
    
    public List<Gabinete> buscarPorMarca(String marca) {
        return find("LOWER(marca) LIKE LOWER(?1)", "%" + marca + "%").list();
    }
    
    public List<Gabinete> buscarPorFaixaPreco(Double precoMin, Double precoMax) {
        return find("preco BETWEEN ?1 AND ?2", precoMin, precoMax).list();
    }
    
    public List<Gabinete> buscarPorCor(String cor) {
        return find("LOWER(cor) LIKE LOWER(?1)", "%" + cor + "%").list();
    }
    
    public List<Gabinete> buscarPorFormato(String formato) {
        return find("LOWER(formato) LIKE LOWER(?1)", "%" + formato + "%").list();
    }
    
    public List<Gabinete> buscarPorNome(String nome) {
        return find("LOWER(nomeExibicao) LIKE LOWER(?1)", "%" + nome + "%").list();
    }
    
    public List<Gabinete> buscarPorCategoria(Long categoriaId) {
        return find("SELECT g FROM Gabinete g JOIN g.categorias c WHERE c.id = ?1", categoriaId).list();
    }
    
    public List<Gabinete> listarOrdenadoPorPreco(boolean crescente) {
        String ordem = crescente ? "ASC" : "DESC";
        return find("ORDER BY preco " + ordem).list();
    }
    
    public List<Gabinete> listarOrdenadoPorNome() {
        return find("ORDER BY nomeExibicao ASC").list();
    }
}
