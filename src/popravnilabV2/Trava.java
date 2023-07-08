package popravnilabV2;

import java.awt.Color;

public class Trava extends Polje{

	public Trava(Mreza mreza, int a, int b) {
		super(mreza, a , b);
		this.setBackground(Color.green);
		this.mozeFigura=true;
	}

}
