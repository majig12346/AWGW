package majig12346.CO;

import majig12346.PassiveFlag.COFlag;
import majig12346.PassiveFlag.UnitType;

/**
 * Commanding Officer: other classes are COs and implement methods. CO itself cannot be instantiated.
 */
public abstract class CO {
	//mandatory
	public abstract void power();
	public abstract void superPower();
	public abstract int getPowerCost();
	public abstract int getSuperPowerCost();
	public abstract double passive(double number, COFlag tag, UnitType unitType);
}
