package br.com.alura.screenmatch.model;

public enum Categoria {
    ACAO("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime");

    private String categoriaOmbd;

    Categoria(String categoriaOmbd) {
        this.categoriaOmbd = categoriaOmbd;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmbd.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
            throw new IllegalArgumentException("Nenhuma categoria encontrada");
    }
}
