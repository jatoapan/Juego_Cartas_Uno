package ec.edu.espol.proyecto2p.clases;

public enum TipoColor {
    R(0xFFC62828),
    A(0xFFFDD835),
    V(0xFF43A047),
    Z(0xFF1E88E5),
    N(0xFF000000);

    private final int colorId;

    private TipoColor(int colorId) {
        this.colorId = colorId;
    }

    public int getColorId() {
        return colorId;
    }
}
