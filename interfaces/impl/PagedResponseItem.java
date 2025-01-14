package br.com.branetlogistica.core.interfaces.impl;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PagedResponseItem<T> {
    private List<T> content;  
    private long totalElements; 
    private int totalPages;  
    private int pageSize;  
    private int pageNumber;  
    private List<String> _actions; 

  
   public PagedResponseItem(Page<T> page, List<String> actions) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.pageSize = page.getSize();
        this.pageNumber = page.getNumber();           
        this._actions = actions;
    }

  
}