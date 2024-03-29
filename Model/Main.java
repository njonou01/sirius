import java.lang.reflect.Field;

class Personne {
    private String nom;
    private int age;

    // Constructeur et autres méthodes...

    public Personne(String nom, int age) {
        this.nom = nom;
        this.age = age;
    }


    public void afficherChamp(String champ) {
        try {
            Field champReflexion = this.getClass().getDeclaredField(champ);
            champReflexion.setAccessible(true); // Rendre accessible le champ privé
            Object valeur = champReflexion.get(this);
            System.out.println("La valeur du champ " + champ + " est : " + valeur);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Personne personne = new Personne("Alice", 30);
        personne.afficherChamp("nomr");
        personne.afficherChamp("age");
    }
}
