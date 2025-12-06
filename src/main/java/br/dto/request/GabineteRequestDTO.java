package br.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record GabineteRequestDTO(

    @NotBlank(message = "Nome de exibição é obrigatório")
    String nomeExibicao,

    @NotBlank(message = "Marca é obrigatória")
    String marca,

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    Double preco,

    @NotBlank(message = "Cor é obrigatória")
    String cor,

    String formato,

    @NotNull(message = "Altura é obrigatória")
    @PositiveOrZero(message = "Altura não pode ser negativa")
    Integer altura,

    @NotNull(message = "Largura é obrigatória")
    @PositiveOrZero(message = "Largura não pode ser negativa")
    Integer largura,

    @NotNull(message = "Peso é obrigatório")
    @PositiveOrZero(message = "Peso não pode ser negativo")
    Double peso,

    @PositiveOrZero(message = "Tamanho máximo da GPU não pode ser negativo")
    Integer tamanhoMaxGpu,

    @PositiveOrZero(message = "Altura máxima do cooler não pode ser negativa")
    Integer alturaMaxCooler,

    @PositiveOrZero(message = "Quantidade de RGB não pode ser negativa")
    Integer qtdRgb,

    @PositiveOrZero(message = "Número de USB não pode ser negativo")
    Integer usb,

    @PositiveOrZero(message = "Número de USB-C não pode ser negativo")
    Integer usbc,

    String descricao,

    String imagemKey,

    List<Long> categoriasIds
) {}
