package br.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.dto.request.GabineteRequestDTO;
import br.dto.response.CategoriaResponseDTO;
import br.dto.response.GabineteResponseDTO;
import br.model.Categoria;
import br.model.Estoque;
import br.model.Gabinete;
import br.repository.CategoriaRepository;
import br.repository.EstoqueRepository;
import br.repository.GabineteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GabineteService {

    @Inject
    GabineteRepository repository;

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    EstoqueRepository estoqueRepository;

    @Inject
    FileStorageService fileStorageService;

    // ==========================================
    // SALVAR NOVO GABINETE
    // ==========================================
    @Transactional
    public GabineteResponseDTO salvar(GabineteRequestDTO dto) {
        Gabinete gabinete = new Gabinete();
        gabinete.setNomeExibicao(dto.nomeExibicao());
        gabinete.setMarca(dto.marca());
        gabinete.setPreco(dto.preco());
        gabinete.setCor(dto.cor());
        gabinete.setFormato(dto.formato());
        gabinete.setAltura(dto.altura());
        gabinete.setLargura(dto.largura());
        gabinete.setPeso(dto.peso());
        gabinete.setTamanhoMaxGpu(dto.tamanhoMaxGpu());
        gabinete.setAlturaMaxCooler(dto.alturaMaxCooler());
        gabinete.setQtdRgb(dto.qtdRgb());
        gabinete.setUsb(dto.usb());
        gabinete.setUsbc(dto.usbc());
        gabinete.setDescricao(dto.descricao());
        
        // ✅ MUDADO: Agora é a chave do MinIO
        gabinete.setImagemKey(dto.imagemKey());

        // associa categorias
        if (dto.categoriasIds() != null && !dto.categoriasIds().isEmpty()) {
            List<Categoria> categorias = new ArrayList<>();
            for (Long categoriaId : dto.categoriasIds()) {
                Categoria categoria = categoriaRepository.findById(categoriaId);
                if (categoria != null) {
                    categorias.add(categoria);
                }
            }
            gabinete.setCategorias(categorias);
        }

        repository.persist(gabinete);

        return toResponseDTO(gabinete);
    }

    // ==========================================
    // LISTAR TODOS
    // ==========================================
    public List<GabineteResponseDTO> listarTodos() {
        return repository.listAll()
            .stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    // ==========================================
    // BUSCAR POR ID
    // ==========================================
    public GabineteResponseDTO buscarPorId(Long id) {
        Gabinete gabinete = repository.findById(id);
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado");
        }
        return toResponseDTO(gabinete);
    }

    // ==========================================
    // BUSCAS PERSONALIZADAS
    // ==========================================
    public List<GabineteResponseDTO> buscarPorMarca(String marca) {
        return repository.buscarPorMarca(marca)
            .stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    public List<GabineteResponseDTO> buscarPorFaixaPreco(Double precoMin, Double precoMax) {
        return repository.buscarPorFaixaPreco(precoMin, precoMax)
            .stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    public List<GabineteResponseDTO> buscarPorCor(String cor) {
        return repository.buscarPorCor(cor)
            .stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    public List<GabineteResponseDTO> buscarPorFormato(String formato) {
        return repository.buscarPorFormato(formato)
            .stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    public List<GabineteResponseDTO> buscarPorNome(String nome) {
        return repository.buscarPorNome(nome)
            .stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    public List<GabineteResponseDTO> buscarPorCategoria(Long categoriaId) {
        return repository.buscarPorCategoria(categoriaId)
            .stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    public List<GabineteResponseDTO> listarOrdenadoPorPreco(boolean crescente) {
        return repository.listarOrdenadoPorPreco(crescente)
            .stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    public List<GabineteResponseDTO> listarOrdenadoPorNome() {
        return repository.listarOrdenadoPorNome()
            .stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    // ==========================================
    // ATUALIZAR
    // ==========================================
    @Transactional
    public GabineteResponseDTO atualizar(Long id, GabineteRequestDTO dto) {
        Gabinete gabinete = repository.findById(id);
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado");
        }

        // Se está atualizando a imagem, deleta a antiga do MinIO
        if (dto.imagemKey() != null && !dto.imagemKey().equals(gabinete.getImagemKey())) {
            if (gabinete.getImagemKey() != null) {
                fileStorageService.deleteFile(gabinete.getImagemKey());
            }
        }

        gabinete.setNomeExibicao(dto.nomeExibicao());
        gabinete.setMarca(dto.marca());
        gabinete.setPreco(dto.preco());
        gabinete.setCor(dto.cor());
        gabinete.setFormato(dto.formato());
        gabinete.setAltura(dto.altura());
        gabinete.setLargura(dto.largura());
        gabinete.setPeso(dto.peso());
        gabinete.setTamanhoMaxGpu(dto.tamanhoMaxGpu());
        gabinete.setAlturaMaxCooler(dto.alturaMaxCooler());
        gabinete.setQtdRgb(dto.qtdRgb());
        gabinete.setUsb(dto.usb());
        gabinete.setUsbc(dto.usbc());
        gabinete.setDescricao(dto.descricao());
        gabinete.setImagemKey(dto.imagemKey());

        if (dto.categoriasIds() != null) {
            List<Categoria> categorias = new ArrayList<>();
            for (Long categoriaId : dto.categoriasIds()) {
                Categoria categoria = categoriaRepository.findById(categoriaId);
                if (categoria != null) {
                    categorias.add(categoria);
                }
            }
            gabinete.setCategorias(categorias);
        }

        return toResponseDTO(gabinete);
    }

    // ==========================================
    // ATUALIZAR IMAGEM DO GABINETE
    // ==========================================
    @Transactional
    public GabineteResponseDTO atualizarImagem(Long gabineteId, String novaImagemKey) {
        Gabinete gabinete = repository.findById(gabineteId);
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado");
        }

        // Se já tinha uma imagem, deleta a antiga do MinIO
        if (gabinete.getImagemKey() != null) {
            fileStorageService.deleteFile(gabinete.getImagemKey());
        }

        // Atualiza com a nova chave
        gabinete.setImagemKey(novaImagemKey);

        return toResponseDTO(gabinete);
    }

    // ==========================================
    // DELETAR
    // ==========================================
    @Transactional
    public void deletar(Long id) {
        Gabinete gabinete = repository.findById(id);
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado");
        }

        // Deleta a imagem do MinIO se existir
        if (gabinete.getImagemKey() != null) {
            fileStorageService.deleteFile(gabinete.getImagemKey());
        }

        // Deleta o gabinete do banco
        repository.delete(gabinete);
    }

    // ==========================================
    // CONVERSÃO PARA RESPONSE DTO
    // ==========================================
private GabineteResponseDTO toResponseDTO(Gabinete gabinete) {
    List<CategoriaResponseDTO> categoriasDTO = gabinete.getCategorias()
        .stream()
        .map(c -> new CategoriaResponseDTO(c.getId(), c.getNome(), c.getDescricao()))
        .collect(Collectors.toList());

    Estoque estoque = estoqueRepository.findByGabineteId(gabinete.getId()).orElse(null);
    Integer quantidadeEstoque = (estoque != null) ? estoque.getQuantidadeDisponivel() : 0;

    // ✅ CORRIGIDO: Gerar URL da imagem usando FileStorageService
    String imagemUrl = null;
    if (gabinete.getImagemKey() != null && !gabinete.getImagemKey().isEmpty()) {
        imagemUrl = fileStorageService.getFileUrl(gabinete.getImagemKey());
    }

    // ✅ Usando o novo construtor com imagemUrl
    return GabineteResponseDTO.valueOf(
        gabinete,
        quantidadeEstoque,
        imagemUrl
    );
}
}