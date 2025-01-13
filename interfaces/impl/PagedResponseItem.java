package br.com.branetlogistica.core.interfaces.impl;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.branetlogistica.core.interfaces.ActionLink;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PagedResponseItem<T> {
    private List<T> content;  // Conteúdo da página (os itens da página)
    private long totalElements;  // Total de elementos na coleção
    private int totalPages;  // Total de páginas
    private int pageSize;  // Tamanho da página
    private int pageNumber;  // Número da página atual
   private List<ActionLink> links;  // Links para ações

    // Construtores, Getters e Setters
    public PagedResponseItem(Page<T> page, List<ActionLink> links) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.pageSize = page.getSize();
        this.pageNumber = page.getNumber();       
        this.links = links;
    }

  
}