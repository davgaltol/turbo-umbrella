package com.emergencias.util;

public class CoordinateConverter {

    // Constantes del elipsoide WGS84 / GRS80
    private static final double A = 6378137.0; // Radio ecuatorial
    private static final double F = 1 / 298.257223563; // Aplanamiento
    private static final double K0 = 0.9996; // Factor de escala UTM

    /**
     * Convierte coordenadas UTM (ETRS89/WGS84) a Latitud/Longitud geográficas.
     * Asume Huso 30 (España Peninsular).
     *
     * @param utmX Coordenada X (Este)
     * @param utmY Coordenada Y (Norte)
     * @return Array de double: [Latitud, Longitud] en grados decimales.
     */
    public static double[] utmToLatLon(double utmX, double utmY) {
        int zone = 30; // Huso para España Peninsular
        boolean northernHemisphere = true;

        double b = A * (1 - F);
        double e = Math.sqrt(1 - (b * b) / (A * A));
        double e0 = e / Math.sqrt(1 - e * e);

        double e1 = (1 - Math.sqrt(1 - e * e)) / (1 + Math.sqrt(1 - e * e));
        double m0 = (northernHemisphere) ? 0.0 : 10000000.0;
        double m = m0;
        double x = utmX - 500000.0;
        double y = utmY - m;

        double mu = y / (A * (1 - e * e / 4.0 - 3 * Math.pow(e, 4) / 64.0 - 5 * Math.pow(e, 6) / 256.0));

        double phi1Rad = mu + (3 * e1 / 2.0 - 27 * Math.pow(e1, 3) / 32.0) * Math.sin(2 * mu)
                + (21 * Math.pow(e1, 2) / 16.0 - 55 * Math.pow(e1, 4) / 32.0) * Math.sin(4 * mu)
                + (151 * Math.pow(e1, 3) / 96.0) * Math.sin(6 * mu);

        double n = A / Math.sqrt(1 - Math.pow(e * Math.sin(phi1Rad), 2));
        double t = Math.pow(Math.tan(phi1Rad), 2);
        double c = e0 * e0 * Math.pow(Math.cos(phi1Rad), 2);
        double r = A * (1 - e * e) / Math.pow(1 - Math.pow(e * Math.sin(phi1Rad), 2), 1.5);
        double d = x / (n * K0);

        double latRad = phi1Rad - (n * Math.tan(phi1Rad) / r) * (d * d / 2.0 - (5 + 3 * t + 10 * c - 4 * c * c - 9 * e0 * e0) * Math.pow(d, 4) / 24.0
                + (61 + 90 * t + 298 * c + 45 * t * t - 252 * e0 * e0 - 3 * c * c) * Math.pow(d, 6) / 720.0);

        double lonRad = (d - (1 + 2 * t + c) * Math.pow(d, 3) / 6.0 + (5 - 2 * c + 28 * t - 3 * c * c + 8 * e0 * e0 + 24 * t * t) * Math.pow(d, 5) / 120.0)
                / Math.cos(phi1Rad);

        double centralMeridian = (zone * 6 - 183) * Math.PI / 180.0;
        lonRad += centralMeridian;

        double lat = Math.toDegrees(latRad);
        double lon = Math.toDegrees(lonRad);

        return new double[]{lat, lon};
    }
}
