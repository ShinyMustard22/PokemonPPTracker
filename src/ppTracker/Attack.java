package ppTracker;

public class Attack {

    private final String attackName;
    private int powerPoints;
    private final int totalPowerPoints;

    public Attack(String attackName, int powerPoints) {

        this.attackName = attackName;
        this.powerPoints = powerPoints;
        this.totalPowerPoints = this.powerPoints; // Original power points, this never changes
    }

    public String getAttackName() { return this.attackName; }

    public int getPowerPoints() {
        return this.powerPoints;
    }

    public int getTotalPowerPoints() { return this.totalPowerPoints; }

    public void raisePowerPoint() {
        powerPoints++;
    }

    public void lowerPowerPoint() {
        powerPoints--;
    }

}
