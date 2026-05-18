package com.example.demo.Pagination;


import java.util.List;

public class PagedResponse <T>{

    private int nbPages;
    private int size;
    private Long nbElementsTotal;
    private int indicePage;
    private List<T> listRecettes;


    public PagedResponse(int nbPages, int size, Long nbElementsTotal, int indicePage, List<T> listRecettes) {
        this.nbPages = nbPages;
        this.size = size;
        this.nbElementsTotal = nbElementsTotal;
        this.indicePage = indicePage;
        this.listRecettes = listRecettes;
    }


    public int getNbPages() {
        return nbPages;
    }

    public void setNbPages(int nbPages) {
        this.nbPages = nbPages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getNbElementsTotal() {
        return nbElementsTotal;
    }

    public void setNbElementsTotal(Long nbElementsTotal) {
        this.nbElementsTotal = nbElementsTotal;
    }

    public int getIndicePage() {
        return indicePage;
    }

    public void setIndicePage(int indicePage) {
        this.indicePage = indicePage;
    }

    public List<T> getListRecettes() {
        return listRecettes;
    }

    public void setListRecettes(List<T> listRecettes) {
        this.listRecettes = listRecettes;
    }

}
