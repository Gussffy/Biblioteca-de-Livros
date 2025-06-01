package com.codefylab.entities;

//Entidade Aluno
public class Aluno extends Usuario {

    private int creditos;

    public Aluno() {
    }

    public Aluno(int id, String nome, int creditos) {
        super(id, nome);
        this.creditos = creditos;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public void debitarCreditos() {

        if (creditos > 0) {
            creditos--;
        }
    }

    public void creditarCreditos() {
        creditos++;
    }

}
