package popravnilabV2;

import java.awt.*;
import java.awt.event.*;

public class Polje extends Canvas{
	
	private Mreza mreza;
	protected int[] pozicijaPolja = new int[2];
	protected int x,y;
	protected boolean mozeFigura;
	
	public Polje(Mreza mreza, int x, int y) {
		this.mreza=mreza;
		this.x=x;
		this.y=y;
		pozicijaPolja[0]=x;
		pozicijaPolja[1]=y;
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(!mreza.igra.rezimIgranjaAktivan) {
					mreza.izmenaTravaZid(pozicijaPolja);
				}
			}
		});
	}
	
	public Mreza getMreza() {
		return mreza;
	}

	public int[] dohvatiPozicijuPolja(Polje polje) {
		return polje.pozicijaPolja;
	}
	
	public Polje dohvatiPolje(int[] pomeraj) {
		return this.mreza.dohvatiPolje(this.x+pomeraj[0], this.y+pomeraj[1]);
	}
	
	@Override
	public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Polje other = (Polje) obj;
			if (pozicijaPolja[0] != other.pozicijaPolja[0])
				return false;
			if (pozicijaPolja[1] != other.pozicijaPolja[1])
				return false;
			return true;
	}
	
	
	
}
