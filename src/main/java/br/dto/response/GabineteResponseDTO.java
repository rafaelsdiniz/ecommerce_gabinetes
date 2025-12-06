package br.dto.response;

import java.util.List;

import br.model.Gabinete;

public record GabineteResponseDTO(
    Long id,
    String nomeExibicao,
    String marca,
    Double preco,
    String cor,
    String formato,
    Integer altura,
    Integer largura,
    Double peso,
    Integer tamanhoMaxGpu,
    Integer alturaMaxCooler,
    Integer qtdRgb,
    Integer usb,
    Integer usbc,
    String descricao,
    String imagemKey, 
    String imagemUrl, 
    List<CategoriaResponseDTO> categorias,
    Integer quantidadeEstoque
) {


    public static GabineteResponseDTO valueOf(Gabinete gabinete, Integer quantidadeEstoque, String imagemUrl) {
        return new GabineteResponseDTO(
            gabinete.getId(),
            gabinete.getNomeExibicao(),
            gabinete.getMarca(),
            gabinete.getPreco(),
            gabinete.getCor(),
            gabinete.getFormato(),
            gabinete.getAltura(),
            gabinete.getLargura(),
            gabinete.getPeso(),
            gabinete.getTamanhoMaxGpu(),
            gabinete.getAlturaMaxCooler(),
            gabinete.getQtdRgb(),
            gabinete.getUsb(),
            gabinete.getUsbc(),
            gabinete.getDescricao(),
            gabinete.getImagemKey(),  
            imagemUrl,  
            gabinete.getCategorias() != null
                ? gabinete.getCategorias().stream().map(CategoriaResponseDTO::valueOf).toList()
                : null,
            quantidadeEstoque
        );
    }
    

    public static GabineteResponseDTO valueOf(Gabinete gabinete, Integer quantidadeEstoque) {
        return valueOf(gabinete, quantidadeEstoque, null);
    }
}