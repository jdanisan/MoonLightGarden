package com.example.moonlightgarden.fragment.viewsFragment;

public class AstronomyResponse {

    public Astronomy astronomy;

    public static class Astronomy {
        public Astro astro;
    }

    public static class Astro {
        public String moon_phase;
        public String moon_illumination;
    }
}