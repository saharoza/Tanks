package popravnilabV2;

import java.awt.*;
import java.awt.event.*;

import javax.swing.GroupLayout.Alignment;

public class Igra extends Frame{
	
	private Mreza mreza;
	private MenuBar rezimiIgre;
	protected boolean igraUToku=false;
	protected int brojNovcica, brojTenkova;
	protected boolean rezimIgranjaAktivan = false;
	private Panel panelGornji, panelDonji, panelLevi, panelDesni, panelTrava, panelZid;
	private Label labelaNovcici, labelaPoeni, labelaPodloga;
	private Button dugme;
	private TextField kutijaZaNovcice;
	private int brojPoena=0;
	protected boolean proveraPodlogeTrava;
	
	
	public Igra() {
		super("Igra");
		igraUToku=true;
		mreza = new Mreza(this);
		this.setBounds(200, 50, 680, 680);
		this.setBackground(Color.green);
		add(mreza,BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(mreza.nitMreze != null)
					mreza.zaustaviMrezu();
				dispose();
			}
		});
		rezimiIgre = new MenuBar();
		Menu meni = new Menu("Rezim");
		meni.setLabel("Rezim");
		MenuItem rezimIzmene = new MenuItem();
		MenuItem rezimIgranja = new MenuItem();
		rezimIzmene.setLabel("Rezim izmene");
		rezimIgranja.setLabel("Rezim igranja");
		rezimIzmene.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				rezimIgranjaAktivan=false;
				//System.out.println(rezimIgranjaAktivan);
				mreza.zaustaviMrezu();
			}
		});
		rezimIgranja.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				rezimIgranjaAktivan=true;
				//System.out.println(rezimIgranjaAktivan);
			}
		});
		meni.add(rezimIgranja);
		meni.add(rezimIzmene);
		rezimiIgre.add(meni);
		setMenuBar(rezimiIgre);
		napraviPanele();
		this.setVisible(true);
	}
	
	private void napraviPanele() {
		//System.out.println("Pravim panele");
		panelDonji = new Panel();
		panelDonji.setBackground(Color.lightGray);
		panelDonji.setLayout(new GridLayout(1,5/*, 15, 15*/));
		labelaNovcici = new Label("Novcici: ");
		labelaNovcici.setText("Novcici");
		labelaNovcici.setVisible(true);
		panelDonji.add(labelaNovcici, Alignment.CENTER);
		labelaNovcici.setAlignment(Label.RIGHT);
		kutijaZaNovcice = new TextField();
		panelDonji.add(kutijaZaNovcice);
		labelaPoeni = new Label("Poeni " +brojPoena);
		panelDonji.add(labelaPoeni, Alignment.CENTER);
		labelaPoeni.setAlignment(Label.CENTER);
		dugme = new Button("Pocni");
		dugme.setSize(40, 70);
		dugme.addActionListener(e ->{
			if(this.dugme.getLabel()=="Pocni") {
				String noviNovcici = kutijaZaNovcice.getText();
				try{
					this.brojNovcica=Integer.parseInt(noviNovcici);
				} catch (NumberFormatException ex) {
				
				}
				if(brojNovcica>0) {
					brojPoena=0;
					this.mreza.pokreniMrezu();
					mreza.setFocusable(true);
				} else System.out.println("Unet neispravan broj novcica!");
				}
		});
		panelDonji.add(dugme);
		
		panelGornji = new Panel();
		panelGornji.setLayout(new GridLayout(1,2));
		CheckboxGroup odabirPodloge = new CheckboxGroup();
		Checkbox trava = new Checkbox("Trava",odabirPodloge, true);
		trava.addItemListener(e -> {
			if(trava.getState()) proveraPodlogeTrava=true;
		});
		Checkbox zid = new Checkbox("Zid", odabirPodloge, false);
		zid.addItemListener(e -> {
			if(zid.getState()) proveraPodlogeTrava=false;
		});
		panelLevi = new Panel(); //u njega cu da stavim labelu za podlogu
		panelDesni = new Panel(); //u njega cu da stavim dva panela za zid i travu
		panelLevi.setLayout(new BorderLayout());
		panelLevi.setBackground(Color.lightGray);
		labelaPodloga = new Label("Podloga: ");
		panelLevi.add(labelaPodloga, BorderLayout.CENTER);
		panelDesni.setLayout(new GridLayout(2,1));
		panelTrava = new Panel();
		panelTrava.setBackground(Color.green);
		panelTrava.add(trava, BorderLayout.CENTER);
		panelZid = new Panel();
		panelZid.setBackground(Color.gray);
		panelZid.add(zid, BorderLayout.CENTER);
		panelDesni.add(panelTrava);
		panelDesni.add(panelZid);
		panelGornji.add(panelLevi);
		panelGornji.add(panelDesni);
		panelGornji.setVisible(true);
		panelDonji.setVisible(true);
		panelLevi.setVisible(true);
		panelDesni.setVisible(true);
		//this.add(panelDesni);
		//this.add(panelLevi);
		this.add(panelGornji,BorderLayout.EAST);
		this.add(panelDonji, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		new Igra();
	}

	public void azurirajIgru() {
		labelaPoeni.setText("Poeni: "+ ++brojPoena);
		
	}
}
