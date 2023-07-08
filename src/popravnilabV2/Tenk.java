package popravnilabV2;

import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;

public class Tenk extends Figura implements Runnable {
	
	private Thread nitTenka;
	private boolean pokrenutTenk = false;
	protected int[] levo = {-1,0};
	protected int[] desno = {1,0};
	protected int[] gore = {0,1};
	protected int[] dole = {0,-1};

	public Tenk(Polje p) {
		super(p);
		pokreniTenk();
	}

	@Override
	public void iscrtajFiguru() {
		if(this.prethodnoPolje != null) prethodnoPolje.repaint();
		Graphics g = this.polje.getGraphics();
		g.setColor(Color.black);
		//System.out.println("iks koordinata tenka"+this.polje.getX());
		g.drawLine(0, 0, this.polje.getWidth(), this.polje.getHeight());
		g.drawLine(0, this.polje.getHeight(), this.polje.getWidth(), 0);
	}

	@Override
	public void run() {
		while(!Thread.interrupted() && pokrenutTenk) {
			Random rand = new Random();
			switch(rand.nextInt(4)) {
			case 0: if(this.polje.dohvatiPolje(levo) != null && this.polje.dohvatiPolje(levo).mozeFigura) {
				prethodnoPolje=polje;
				this.setPolje(polje.dohvatiPolje(levo));
				break;
			};
			case 1: if(this.polje.dohvatiPolje(desno) != null && this.polje.dohvatiPolje(desno).mozeFigura) {
				prethodnoPolje=polje;
				this.setPolje(polje.dohvatiPolje(desno));
				break;
			};
			case 2: if(this.polje.dohvatiPolje(gore) != null && this.polje.dohvatiPolje(gore).mozeFigura) {
				prethodnoPolje=polje;
				this.setPolje(polje.dohvatiPolje(gore));
				break;
			};
			case 3: if(this.polje.dohvatiPolje(dole) != null && this.polje.dohvatiPolje(dole).mozeFigura) {
				prethodnoPolje=polje;
				this.setPolje(polje.dohvatiPolje(dole));
				break;
			};
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				break;
			}
		}
		this.zaustaviTenk();
	}
	
	/*public void pomeriTenk() {
		Random rand = new Random();
		int smer = rand.nextInt(4);
		Polje novoPolje = this.polje.
		switch(smer) {
		case 0: this.pomeriFiguru();
		}
	}*/

	public void pokreniTenk() {
		/*if(nitTenka == null)*/ nitTenka = new Thread(this);
		nitTenka.start();
		pokrenutTenk = true;
	}
	
	public void zaustaviTenk() {
		pokrenutTenk = false;
		if(nitTenka != null) {
			nitTenka.interrupt();
			nitTenka=null;
		}
		if (this.prethodnoPolje != null) this.prethodnoPolje.repaint();
		if(this.polje != null) this.polje.repaint();
		this.polje.getMreza().getListaTenkova().remove(this);
	}
}
