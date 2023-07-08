package popravnilabV2;

public abstract class Figura {
	
	protected Polje polje;
	protected Polje prethodnoPolje;
	
	public Figura(Polje p/*, int x, int y*/) {
		polje=p;
		/*polje.pozicijaPolja[0]=x;
		polje.pozicijaPolja[1]=y;*/
	}

	public Polje getPolje() {
		return polje;
	}
	
	//public abstract void pomeriFiguru();
	
	public void setPolje(Polje polje) {
		this.prethodnoPolje=this.polje;
		this.polje=polje;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(this.getPolje()==((Figura)obj).getPolje()) return true;
		else return false;
	}

	public abstract void iscrtajFiguru();
	
}
