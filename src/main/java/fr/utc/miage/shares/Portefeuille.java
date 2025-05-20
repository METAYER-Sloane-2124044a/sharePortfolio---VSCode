package fr.utc.miage.shares;

public class Portefeuille {

    private double value;

    public Portefeuille() {
        this.value = 0;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String VisuPortefeuille() {
        return "Valeur du portefeuille : " + this.value + " €";
    }
}