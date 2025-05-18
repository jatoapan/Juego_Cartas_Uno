package ec.edu.espol.proyecto2p;

public enum TipoCarta {

    R_0(R.drawable.r_0),
    R_1(R.drawable.r_1),
    R_2(R.drawable.r_2),
    R_3(R.drawable.r_3),
    R_4(R.drawable.r_4),
    R_5(R.drawable.r_5),
    R_6(R.drawable.r_6),
    R_7(R.drawable.r_7),
    R_8(R.drawable.r_8),
    R_9(R.drawable.r_9),
    R_MAS_2(R.drawable.r_mas2),
    R_MAS_4(R.drawable.r_mas4),
    R_REVERSO(R.drawable.r_reverso),
    R_BLOQUEO(R.drawable.r_bloqueo),

    A_0(R.drawable.a_0),
    A_1(R.drawable.a_1),
    A_2(R.drawable.a_2),
    A_3(R.drawable.a_3),
    A_4(R.drawable.a_4),
    A_5(R.drawable.a_5),
    A_6(R.drawable.a_6),
    A_7(R.drawable.a_7),
    A_8(R.drawable.a_8),
    A_9(R.drawable.a_9),
    A_MAS_2(R.drawable.a_mas2),
    A_MAS_4(R.drawable.a_mas4),
    A_REVERSO(R.drawable.a_reverso),
    A_BLOQUEO(R.drawable.a_bloqueo),

    V_0(R.drawable.v_0),
    V_1(R.drawable.v_1),
    V_2(R.drawable.v_2),
    V_3(R.drawable.v_3),
    V_4(R.drawable.v_4),
    V_5(R.drawable.v_5),
    V_6(R.drawable.v_6),
    V_7(R.drawable.v_7),
    V_8(R.drawable.v_8),
    V_9(R.drawable.v_9),
    V_MAS_2(R.drawable.v_mas2),
    V_MAS_4(R.drawable.v_mas4),
    V_REVERSO(R.drawable.v_reverso),
    V_BLOQUEO(R.drawable.v_bloqueo),

    Z_0(R.drawable.z_0),
    Z_1(R.drawable.z_1),
    Z_2(R.drawable.z_2),
    Z_3(R.drawable.z_3),
    Z_4(R.drawable.z_4),
    Z_5(R.drawable.z_5),
    Z_6(R.drawable.z_6),
    Z_7(R.drawable.z_7),
    Z_8(R.drawable.z_8),
    Z_9(R.drawable.z_9),
    Z_MAS_2(R.drawable.z_mas2),
    Z_MAS_4(R.drawable.z_mas4),
    Z_REVERSO(R.drawable.z_reverso),
    Z_BLOQUEO(R.drawable.z_bloqueo),

    N_MAS_2(R.drawable.n_mas2),
    N_MAS_4(R.drawable.n_mas4),
    N_CAMBIO_COLOR(R.drawable.n_cambio),
    N_BLANCA(R.drawable.blanca);

    private final int imageId;

    private TipoCarta(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

}
