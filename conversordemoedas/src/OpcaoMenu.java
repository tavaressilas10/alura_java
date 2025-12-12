public enum OpcaoMenu {

    USDTOBRL(1, "USD", "BRL"),
    BRLTOUSD(2,"BRL", "USD"),
    USDTOARL(3,"USD", "ARS"),
    ARLTOUSD(4,"ARS", "USD"),
    BRLTOARL(5,"BRL", "ARS"),
    ARLTOBRL(6,"ARS", "BRL"),
    SAIR(7, "SAIR", "SAIR");

    private final int codigo;
    private final String base;
    private final String target;

    OpcaoMenu(int codigo, String base, String target) {
        this.codigo = codigo;
        this.base = base;
        this.target = target;
    }

    public int getCodigo() {
        return codigo;
    }
    public String getBase() {
        return base;
    }

    public String getTarget() {
        return target;
    }

    public static OpcaoMenu fromCodigo(int codigo) {
        for (OpcaoMenu opcao : OpcaoMenu.values()) {
            if (opcao.codigo == codigo) {
                return opcao;
            }
        }
        return null;
    }
}