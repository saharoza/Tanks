package popravnilabV2;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends Figura{

	public Igrac(Polje p) {
		super(p);
	}

	@Override
	public void iscrtajFiguru() {
		if(this.prethodnoPolje != null) prethodnoPolje.repaint();
		Graphics g = this.polje.getGraphics();
		g.setColor(Color.red);
		g.drawLine((this.polje.getWidth())/2, 0, this.polje.getWidth()/2, this.polje.getHeight());
		g.drawLine(0, this.polje.getHeight()/2, this.polje.getWidth(), this.polje.getHeight()/2);
	}

}
