package org.missao.roxa.missaoroxabackend.core.shared.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public final class PageableUtils {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    private PageableUtils() {
    }

    // ------------------------------
    // Métodos de criação
    // ------------------------------

    /**
     * Cria Pageable com página, tamanho, campo de ordenação e direção.
     */
    public static Pageable createPageable(int page, int size, String sortField, String sortDirection) {
        return PageRequest.of(validatePage(page), validateSize(size), getSort(sortField, sortDirection));
    }

    /**
     * Cria Pageable com ordenação ascendente por padrão.
     */
    public static Pageable createPageable(int page, int size, String sortField) {
        return PageRequest.of(validatePage(page), validateSize(size), Sort.by(Sort.Direction.ASC, sortField));
    }

    /**
     * Cria Pageable apenas com page e size, sem ordenação.
     */
    public static Pageable createPageable(int page, int size) {
        return PageRequest.of(validatePage(page), validateSize(size));
    }

    /**
     * Cria Pageable com múltiplos campos de ordenação e direção.
     */
    public static Pageable createPageable(int page, int size, List<String> sortFields, String sortDirection) {
        Sort sort = Sort.by(getDirection(sortDirection), sortFields.toArray(new String[0]));
        return PageRequest.of(validatePage(page), validateSize(size), sort);
    }

    /**
     * Cria Pageable com valores defaults se nulos ou inválidos.
     */
    public static Pageable createPageable(Integer page, Integer size, String sortField, String sortDirection) {
        int safePage = (page != null) ? page : DEFAULT_PAGE;
        int safeSize = (size != null) ? size : DEFAULT_SIZE;
        return createPageable(safePage, safeSize, sortField, sortDirection);
    }

    // ------------------------------
    // Ajuste após consulta
    // ------------------------------

    /**
     * Ajusta o Pageable se a página requisitada for maior que a última existente.
     */
    public static <T> Pageable adjustPageable(Page<T> pageResult, Pageable requested) {
        int totalPages = pageResult.getTotalPages();
        if (totalPages == 0) {
            return PageRequest.of(DEFAULT_PAGE, requested.getPageSize(), requested.getSort());
        }
        int lastPage = totalPages - 1;
        if (requested.getPageNumber() > lastPage) {
            return PageRequest.of(lastPage, requested.getPageSize(), requested.getSort());
        }
        return requested;
    }


    private static int validatePage(int page) {
        return (page < 0) ? DEFAULT_PAGE : page;
    }

    private static int validateSize(int size) {
        return (size <= 0) ? DEFAULT_SIZE : size;
    }

    private static Sort getSort(String sortField, String sortDirection) {
        return Sort.by(getDirection(sortDirection), sortField);
    }

    private static Sort.Direction getDirection(String sortDirection) {
        return "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

}