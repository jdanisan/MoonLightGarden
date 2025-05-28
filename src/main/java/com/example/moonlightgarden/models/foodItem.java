package com.example.moonlightgarden.models;

import com.google.firebase.database.PropertyName; // Importar si decides usar la anotación, aunque prefiero coincidir nombres

public class foodItem { // Buena práctica usar mayúscula al inicio para nombres de clase
    // Estos nombres de campo deben coincidir con las claves en tu JSON
    // O deben tener getters con nombres que sigan la convención Java Bean (getNombreCampo)
    public String food_name;
    public String url_food_image;
    public String food_description;
    public double food_price; // Añadimos el campo para el precio

    // Constructor vacío requerido por Firebase para deserialización
    // Es crucial que este constructor exista y sea público
    public foodItem() {}

    // Constructor con argumentos (útil para crear objetos en tu código, no estrictamente necesario para Firebase)
    public foodItem(String food_name, String url_food_image, String food_description, double food_price) {
        this.food_name = food_name;
        this.url_food_image = url_food_image;
        this.food_description = food_description;
        this.food_price = food_price;
    }

    // Métodos getter públicos - Firebase puede usarlos para mapear si los campos no son públicos
    // o si quieres usar nombres de getter diferentes a los del campo (no recomendado si buscas simplicidad)
    // Si los campos son públicos, los getters son opcionales para la deserialización básica.
    public String getFood_name() { return food_name; }
    public String getUrl_food_image() { return url_food_image; }
    public String getFood_description() { return food_description; }
    public double getFood_price() { return food_price; }

    // Opcional: Si quisieras mantener tus nombres originales de campo (name, description, imageUrl)
    // y aún así mapearlos a las claves JSON (food_name, url_food_image, food_description),
    // tendrías que usar la anotación @PropertyName en los getters:
    /*
    private String name; // Campo privado
    @PropertyName("food_name") // Anotación para mapear este getter a la clave JSON "food_name"
    public String getName() { return name; }

    private String description; // Campo privado
     @PropertyName("food_description")
    public String getDescription() { return description; }

    private String imageUrl; // Campo privado
    @PropertyName("url_food_image")
    public String getImageUrl() { return imageUrl; }

    private double price; // Campo privado para el precio
    @PropertyName("food_price")
    public double getPrice() { return price; }
    */
    // Sin embargo, para la simplicidad y si no tienes una razón fuerte para mantener nombres diferentes,
    // es más directo simplemente hacer que los nombres de campo (si son públicos) o los getters
    // coincidan directamente con las claves JSON como en el primer ejemplo.
}
