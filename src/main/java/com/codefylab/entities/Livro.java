package com.codefylab.entities;


import java.util.Objects;

public class Livro {

    private int id;
    private String titulo;
    private double valorCredito;
    private boolean disponivel;
    private Usuario emprestadoPara;

    public Livro() {
    }

    public Livro(int id, String titulo, double valorCredito) {
        this.id = id;
        this.titulo = titulo;
        this.valorCredito = valorCredito;
        this.disponivel = true;
    }

    //Getter e Setters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public double getValorCredito() {
        return valorCredito;
    }

    public Usuario getEmprestadoPara() {
        return emprestadoPara;
    }

    public void setEmprestadoPara(Usuario usuario) {
        this.emprestadoPara = usuario;
    }

    //Verifica se o Livro está Disponível
    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return id == livro.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}