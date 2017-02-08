package majig12346.CO;

import majig12346.PassiveFlag.COFlag;
import majig12346.PassiveFlag.UnitType;

public class TestCO extends CO {
	@Override
	public void power() {
		//Nothing

	}

	@Override
	public void superPower() {
		//nothing

	}

	@Override
	public int getPowerCost() {
		return 99;
	}

	@Override
	public int getSuperPowerCost() {
		return 99;
	}

	@Override
	public double passive(double number, COFlag tag, UnitType unitType) {
		return number;
	}

}
