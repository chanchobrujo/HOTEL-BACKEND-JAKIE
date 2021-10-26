package com.demo.hotelbackend.constants;

public enum enums {
    ROLE_ADMIN,
    ROLE_RECP,
    ROLE_HUESPED;

    public static class Messages {

        public static final String VALID_DATA = "Datos validados correctamente.";
        public static final String CORRECT_DATA = "Datos registrados correctamente.";
        public static final String INCORRECT_DATA = "Datos incorrectos.";
        public static final String INVALID_DATA = "Datos inválidos.";
        public static final String DELETE_DATA = "Datos eliminados correctamente.";
        public static final String REPET_DATA = "Datos repetidos.";

        public static final String PHOTO_NULL = "Sin foto.";
    }
}
