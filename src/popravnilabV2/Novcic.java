package popravnilabV2;

import java.awt.Color;
import java.awt.Graphics;

public class Novcic extends Figura {

	public Novcic(Polje p) {
		super(p);
	}

	@Override
	public void iscrtajFiguru() {
		if(this != null) {
			Graphics g = this.polje.getGraphics();
			g.setColor(Color.yellow);
			g.fillOval((this.polje.getWidth())/4, (this.polje.getHeight())/4, this.polje.getWidth()/2, this.polje.getHeight()/2);
		}
		
	}

}
