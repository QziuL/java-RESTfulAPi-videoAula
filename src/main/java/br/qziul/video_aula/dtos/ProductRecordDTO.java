package br.qziul.video_aula.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 *  Classe do tipo Record (Java 16), sendo imutável e possuindo Getters pré-definidos.
 *  Recebe os seguintes parâmetros estáticos no construtor:
 *  @param name Nome do produto.
 *  @param value Valor do produto
 *  '@NotBlank' e '@NotNull' são anotações para validação dos dados.
 */

public record ProductRecordDTO(@NotBlank String name,@NotNull BigDecimal value) {
}
