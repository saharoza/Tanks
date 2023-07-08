package popravnilabV2;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Mreza extends Panel implements Runnable {

	private Polje[][] matricaPolja;
	private ArrayList<Figura> listaNovcica = new ArrayList<Figura>();
	private ArrayList<Figura> listaTenkova = new ArrayList<Figura>();
	private int dimenzija;
	protected Igra igra;
	private Igrac igrac;
	protected Thread nitMreze;
	private boolean mrezaAktivna = false;
	protected int[] levo = { -1, 0 };
	protected int[] desno = { 1, 0 };
	protected int[] gore = { 0, 1 };
	protected int[] dole = { 0, -1 };

	public Mreza(int dimenzija, Igra igra) {
		this.setSize(500, 500);
		this.igra = igra;
		this.setLayout(new GridLayout(dimenzija, dimenzija));
		matricaPolja = new Polje[dimenzija][dimenzija];
		for (int i = 0; i < dimenzija; i++) {
			for (int j = 0; j < dimenzija; j++) {
				double broj = Math.random();
				if (broj < 0.8) {
					matricaPolja[i][j] = new Trava(this, i, j);
					add(matricaPolja[i][j]);
				} else {
					matricaPolja[i][j] = new Zid(this, i, j);
					add(matricaPolja[i][j]);
				}
			}
		}
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
					if (igrac.polje.dohvatiPolje(gore) != null && igrac.polje.dohvatiPolje(gore).mozeFigura) {
						igrac.setPolje(igrac.polje.dohvatiPolje(gore));
						break;
					}
				case KeyEvent.VK_S:
					if (igrac.polje.dohvatiPolje(dole) != null && igrac.polje.dohvatiPolje(dole).mozeFigura) {
						igrac.setPolje(igrac.polje.dohvatiPolje(dole));
						break;
					}
				case KeyEvent.VK_A:
					if (igrac.polje.dohvatiPolje(levo) != null && igrac.polje.dohvatiPolje(levo).mozeFigura) {
						igrac.setPolje(igrac.polje.dohvatiPolje(levo));
						break;
					}
				case KeyEvent.VK_D:
					if (igrac.polje.dohvatiPolje(desno) != null && igrac.polje.dohvatiPolje(desno).mozeFigura) {
						igrac.setPolje(igrac.polje.dohvatiPolje(desno));
						break;
					}
				}
			}
		});
	}

	public Mreza(Igra igra) {
		this(17, igra);
	}

	@Override
	public void run() {
		while (this.mrezaAktivna) {
			try {
				iscrtajMrezu();
				azurirajMrezu();
				Thread.sleep(40);
			} catch (InterruptedException e) {
				break;
			}
		}
		this.zaustaviMrezu();
	}

	private void azurirajMrezu() {
		for (int i = 0; i < listaTenkova.size(); i++)
			if (this.igrac.getPolje() == listaTenkova.get(i).getPolje()) {
				this.zaustaviMrezu();
			}

		for (int i = 0; i < listaNovcica.size(); i++)
			if (this.igrac.getPolje() == listaNovcica.get(i).getPolje()) {
				listaNovcica.remove(i);
				this.igra.azurirajIgru();
				if (listaNovcica.size() == 0)
					this.zaustaviMrezu();
			}
	}

	private void iscrtajMrezu() {
		if (this.igrac != null)
			this.igrac.iscrtajFiguru();
		for (int i = 0; i < listaTenkova.size(); i++)
			if(listaTenkova.get(i) != null)
			listaTenkova.get(i).iscrtajFiguru();
		for (int i = 0; i < listaNovcica.size(); i++)
			if(listaNovcica.get(i) != null)
			listaNovcica.get(i).iscrtajFiguru();
	}

	public ArrayList<Figura> getListaNovcica() {
		return listaNovcica;
	}

	public ArrayList<Figura> getListaTenkova() {
		return listaTenkova;
	}

	public Polje dohvatiPolje(int koordinataX, int koordinataY) {
		if(koordinataX>=this.matricaPolja.length || koordinataX<0 || koordinataY<0 || koordinataY>=this.matricaPolja[0].length) return null;
		return this.matricaPolja[koordinataX][koordinataY];
	}

	public void pokreniMrezu() {
		this.mrezaAktivna = true;
		this.igra.igraUToku = true;
		listaNovcica = new ArrayList<Figura>();
		listaTenkova = new ArrayList<Figura>();
		this.igra.brojTenkova = this.igra.brojNovcica / 3;
		int brojacNovcica = 0;
		while (brojacNovcica < this.igra.brojNovcica) {
			Random rand = new Random();
			int pozX = rand.nextInt(this.matricaPolja.length);
			int pozY = rand.nextInt(this.matricaPolja[0].length);
			if (!this.matricaPolja[pozX][pozY].mozeFigura)
				continue;
			if (this.listaNovcica.size() == 0)
				listaNovcica.add(new Novcic(this.matricaPolja[pozX][pozY]));
			else {
				for (int i = 0; i < listaNovcica.size(); i++) {
					if (listaNovcica.get(i).polje == matricaPolja[pozX][pozY])
						continue;
					listaNovcica.add(new Novcic(matricaPolja[pozX][pozY]));
				}
			}
			brojacNovcica++;
		}
		int brojacTenkova = 0;
		while (brojacTenkova < this.igra.brojTenkova) {
			Random rand = new Random();
			int pozX = rand.nextInt(this.matricaPolja.length);
			int pozY = rand.nextInt(this.matricaPolja[0].length);
			if (!this.matricaPolja[pozX][pozY].mozeFigura)
				continue;
			listaTenkova.add(new Tenk(matricaPolja[pozX][pozY]));
			brojacTenkova++;
		}
		while (true) {
			Random rand = new Random();
			int pozX = rand.nextInt(this.matricaPolja.length);
			int pozY = rand.nextInt(this.matricaPolja[0].length);
			if (!this.matricaPolja[pozX][pozY].mozeFigura)
				continue;
			igrac = new Igrac(matricaPolja[pozX][pozY]);
			break;
		}
		/*if (this.nitMreze == null)*/
			nitMreze = new Thread(this);
		for (int i = 0; i < listaTenkova.size(); i++)
			((Tenk) listaTenkova.get(i)).pokreniTenk();
		nitMreze.start();
	}

	public void zaustaviMrezu() {
		this.mrezaAktivna = false;
		this.igra.igraUToku = false;
		if (this.nitMreze != null) {
			for (int i = 0; i < this.listaTenkova.size(); i++)
				((Tenk) listaTenkova.get(i)).zaustaviTenk();
			 if(igrac.polje != null) igrac.polje.repaint();
			if(igrac.prethodnoPolje != null) igrac.prethodnoPolje.repaint();
			igrac = null;
			nitMreze.interrupt();
		}
		for (int i = 0; i < matricaPolja.length; i++)
			for (int j = 0; j < matricaPolja[0].length; j++) {
				matricaPolja[i][j].repaint();
			}
	}
	
	public void izmenaTravaZid(int[] pozicija) {
		if(igra.proveraPodlogeTrava) {
			remove(matricaPolja[pozicija[0]][pozicija[1]]);
			matricaPolja[pozicija[0]][pozicija[1]] = new Zid(this, pozicija[0], pozicija[1]);
			repaint();
		} else {
			remove(matricaPolja[pozicija[0]][pozicija[1]]);
			matricaPolja[pozicija[0]][pozicija[1]] = new Trava(this, pozicija[0], pozicija[1]);
			repaint();
		}
	}

}
