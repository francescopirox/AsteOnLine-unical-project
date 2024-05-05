package AsteOnLine.client;

import AsteOnLine.client.guicommand.CommandHandler;
import AsteOnLine.client.guicommand.specificcommand.*;
import AsteOnLine.shared.AstaBuilderConcreto;
import AsteOnLine.client.models.Pannello;
import AsteOnLine.client.renderers.AstaRenderer;
import AsteOnLine.client.renderers.AstaUtenteRenderer;
import AsteOnLine.shared.*;
import AsteOnLine.shared.exceptions.AstaBuilderException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

public final class GUIconcreta implements GUI {

    private ServerRemoteInterface serverRemoteInterface;
    private RMIClientConcreto callbackObj;
    private JFrame f;
    private final CommandHandler commandHandler;
    private boolean loggedIn = false;
    private Utente user;
    private List<Asta> asteSeguite;
    private List<Asta> asteAttive;
    private Pannello pannelloCorrente=Pannello.NULL;
    public static GUI istanza;

    public static GUI getGui( ServerRemoteInterface serverRemoteInterface , RMIClientConcreto callbackObj ){
        if(istanza==null)
            istanza=new GUIconcreta(serverRemoteInterface,callbackObj);
            return istanza;
    }

    private GUIconcreta( ServerRemoteInterface serverRemoteInterface , RMIClientConcreto callbackObj ) {
        this.serverRemoteInterface = serverRemoteInterface;
        this.callbackObj = callbackObj;
        f = new JFrame();
        f.setLayout(new BorderLayout());
        commandHandler = new CommandHandler();
    }

    @Override
    public void start() {
        menu();
        if( !loggedIn ) loginPanel();
        else
            aste();
        frameOptions();
        f.setVisible(true);//making the frame visible
    }

    private void aste() {
        f.getContentPane().removeAll();
        pannelloCorrente=Pannello.ASTE;
        JPanel astepanel = new JPanel(new GridLayout(2 , 1));

        try {
            asteAttive=serverRemoteInterface.getAllAsteAttive();
        } catch ( RemoteException e ) {
            e.printStackTrace();
        }
        if( asteAttive.size() == 0 ) {
                JPanel prodotti = new JPanel(new GridLayout(1 , 1));
                prodotti.add(new JLabel("Non sono presenti aste attive"));
                astepanel.add(prodotti);
        } else {
                JPanel asteAttivePanel=AstePanel( asteAttive);
                asteAttivePanel.add(new JLabel("Aste Attive:"), BorderLayout.NORTH);
                astepanel.add(asteAttivePanel);
        }

        if( asteSeguite.size() == 0 ) {
                JPanel prodottiSeguiti = new JPanel(new GridLayout(1 , 1));
                prodottiSeguiti.add(new JLabel("Non sono presenti aste seguite"));
                astepanel.add(prodottiSeguiti);
        }
        else {
                JPanel asteSeguitePanel=asteSeguitePanel();
                asteSeguitePanel.add(new JLabel("Aste Seguite:"),BorderLayout.NORTH);
                astepanel.add(asteSeguitePanel);
        }

        f.getContentPane().add(astepanel);
        f.revalidate();
        f.repaint();
    }

    private JPanel AstePanel( List<Asta> asteAttive ) {
        JPanel ret=new JPanel(new BorderLayout());
        JList<Asta> jlist = getAsteDisplay(asteAttive);
        jlist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        JScrollPane listScroller = new JScrollPane(jlist);
        jlist.setCellRenderer(new AstaRenderer());

        JButton infoButton = new JButton("Informazioni");
        infoButton.addActionListener(infoBtn -> {
            if(jlist.getSelectedValue() != null)
                info(jlist.getSelectedValue());
        });
        ret.add(listScroller, BorderLayout.CENTER);
        ret.add(infoButton,BorderLayout.SOUTH);
        return ret;
    }

    private JPanel asteSeguitePanel() {
        JPanel ret= new JPanel(new BorderLayout());
        JList<Asta> jlistSeguiti = getAsteDisplay(asteSeguite);
        jlistSeguiti.setCellRenderer(new AstaRenderer());
        jlistSeguiti.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        JScrollPane listScroller = new JScrollPane(jlistSeguiti);

        ret.add(listScroller,BorderLayout.CENTER);
        JPanel offertaDettagli=new JPanel(new GridLayout(1,2));
        JButton offertaButton = new JButton("Offerta");
        offertaButton.addActionListener(infoBtn -> {
            if(jlistSeguiti.getSelectedValue() != null)
                offertaModalCreator( jlistSeguiti.getSelectedValue());
        });

        JButton dettagliButton = new JButton("Dettagli prodotto");
        dettagliButton.addActionListener(dettagliBtn -> {
            if(jlistSeguiti.getSelectedValue() != null)
                info(jlistSeguiti.getSelectedValue(

                ));
        });
        offertaDettagli.add(offertaButton);
        offertaDettagli.add(dettagliButton);
        ret.add(offertaDettagli,BorderLayout.SOUTH);
        return ret;
    }

    private void userPanel(){
        f.getContentPane().removeAll();
        pannelloCorrente=Pannello.USER;

        JPanel astevinte= new JPanel(new BorderLayout());
        JPanel asteInCorsoPanel= new JPanel(new BorderLayout());
        JPanel userPanel=new JPanel(new GridLayout(3,1));
        JPanel userInfoPanel=new JPanel(new GridLayout(2,2));

        try {
            List<AstaUtente>asteVinte=serverRemoteInterface.getAllAsteVinteByUser(user);
            JList jlist = getAsteUtenteDisplay(asteVinte);
            jlist.setCellRenderer(new AstaUtenteRenderer());
            JScrollPane listScroller =   new JScrollPane(jlist);
                    astevinte.add(listScroller,BorderLayout.CENTER);
        } catch ( RemoteException e ) {
            e.printStackTrace();
        }

        try {
            List<Asta>asteInCorso=serverRemoteInterface.getAllAsteAttiveByUser(user);
            JList jlist = getAsteDisplay(asteInCorso);
            jlist.setCellRenderer(new AstaRenderer());
            JScrollPane listScroller = new JScrollPane(jlist);
                    asteInCorsoPanel.add(listScroller,BorderLayout.CENTER);
        } catch ( RemoteException e ) {
            e.printStackTrace();
        }

        astevinte.add(new JLabel("asteVinte"), BorderLayout.NORTH);
        asteInCorsoPanel.add(new JLabel("asteInCorso"), BorderLayout.NORTH);

        userInfoPanel.add(new JLabel("Username:"));
        userInfoPanel.add(new JLabel(user.getNome()));
        userInfoPanel.add(new JLabel("Mail"));
        userInfoPanel.add(new JLabel(user.getEmail()));

        userPanel.add(userInfoPanel);
        userPanel.add(asteInCorsoPanel);
        userPanel.add(astevinte);

        f.add(userPanel);
        f.revalidate();
        f.repaint();
    }

    private JList getAsteDisplay( List<Asta> aste ) {
        Asta[] asteVect = new Asta[aste.size()];
        int i = 0;
        for (Asta a : aste) {
            asteVect[i] = a;
            i++;
        }
        JList<Asta> jlist = new JList<Asta>(asteVect);
        jlist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        return jlist;
    }

    private JList getAsteUtenteDisplay( List<AstaUtente> aste ) {
        AstaUtente[] asteVect = new AstaUtente[aste.size()];
        int i = 0;
        for (AstaUtente au : aste) {
            asteVect[i] = au;
            i++;
        }
        JList<AstaUtente> jlist = new JList<AstaUtente>(asteVect);
        jlist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        return jlist;
    }

    private void offertaModalCreator( Asta asta) {
        if( asta != null){
            JDialog dialog = new JDialog(f , true);
            dialog.setLayout(new GridLayout(2,2));

            NumberFormat paymentFormat = NumberFormat.getCurrencyInstance();
            JFormattedTextField offertaCorrenteField = new JFormattedTextField(paymentFormat);
            offertaCorrenteField.setEditable(false);
            offertaCorrenteField.setValue(asta.getValore());

            JTextField paymentField=new JTextField();

            JButton offri= new JButton("Offri");
            offri.addActionListener(evt ->
                    {
                        dialog.dispose();
                        try {
                            commandHandler.handle(new OffertaCommand(asta , paymentField.getText() , user , serverRemoteInterface , callbackObj));
                        } catch ( NumberFormatException NFE) {
                            JOptionPane.showMessageDialog(f,"Offerta rifiutata, inserisci un numero utilizzando il punto per i decimali");
                        }
                    });

            JButton indietro=new JButton("Indietro");
            indietro.addActionListener(e -> {dialog.dispose();});

            dialog.add(offertaCorrenteField);
            dialog.add(paymentField);

            dialog.add(offri);
            dialog.add(indietro);
            dialog.pack();
            dialog.setVisible(true);
        }
    }

    private void info( Asta asta ) {
        f.getContentPane().removeAll();
        pannelloCorrente=Pannello.INFO;
        Asta astaView = asteAttive.get(asteAttive.indexOf(asta));
        JPanel infoPanel = new JPanel(new GridLayout(8, 2));
        infoPanel.add(new JLabel("Nome"));
        infoPanel.add(new JLabel(astaView.getNomeArticolo()));
        infoPanel.add(new JLabel("Descrizione"));
        infoPanel.add(new JLabel(astaView.getDescrizione()));
        infoPanel.add(new JLabel("ID:"));
        infoPanel.add(new JLabel(astaView.getId()+""));
        infoPanel.add(new JLabel("Inzio Asta"));
        infoPanel.add(new JLabel(astaView.getInizio().toString()));
        infoPanel.add(new JLabel("Fine Asta"));
        infoPanel.add(new JLabel(astaView.getFine().toString()));
        infoPanel.add(new JLabel("Prezzo Attuale"));
        infoPanel.add(new JLabel(astaView.getValore() + " €"));
        infoPanel.add(new JLabel("Prezzo di partenza"));
        infoPanel.add(new JLabel(astaView.getValorePartenza() + " €"));
        JButton indietro = new JButton("Indietro");
        indietro.addActionListener(indietroEvt -> {
            aste();
        });
        infoPanel.add(indietro);

        JButton seguiAsta = new JButton("Segui Asta");
        seguiAsta.addActionListener(indietroEvt -> {
            new SeguiAstaCommand(asta , user , serverRemoteInterface , callbackObj);
        });
        infoPanel.add(seguiAsta);

        f.getContentPane().add(infoPanel);
        f.revalidate();
        f.repaint();

    }

    private void loginPanel() {
        f.getContentPane().removeAll();
        JPanel jp = new JPanel(new BorderLayout());
        JLabel jta = new JLabel("Benvenuto nel sistema di aste on line AOL, " +
                "\nfai login se sei già un utente, sign up altrimenti");

        jp.add(jta , BorderLayout.NORTH);

        JPanel login = new JPanel(new GridLayout(3 , 2));
        login.add(new JLabel("Email"));
        JTextField nomeField = new JTextField();
        nomeField.setColumns(0);
        nomeField.setSize(40 , 20);
        login.add(nomeField);
        JTextField passwordField = new JPasswordField();
        passwordField.setSize(40 , 20);
        login.add(new JLabel("Password"));
        login.add(passwordField , BorderLayout.SOUTH);
        JButton loginButton = new JButton("login");
        loginButton.addActionListener(evt -> {

            commandHandler.handle(new LogInCommand(nomeField.getText() , passwordField.getText() , serverRemoteInterface , callbackObj));

        });
        login.add(loginButton);
        JButton signupButton = new JButton("signup");
        signupButton.addActionListener(evt -> {
            signupDialog();
        });
        login.add(signupButton);
        login.setSize(400 , 100);
        jp.add(login , BorderLayout.CENTER);
        jp.setSize(720 , 600);
        f.getContentPane().add(jp);

        f.revalidate();
        f.repaint();

    }

    private void signupDialog() {
        JDialog dialog = new JDialog(f , true);
        dialog.setLayout(new GridLayout(4 , 2));
        dialog.add(new JLabel("nome"));
        JTextField nomeField1 = new JTextField();
        dialog.add(nomeField1);
        dialog.add(new JLabel("email"));
        JTextField email1 = new JTextField();
        dialog.add(email1);
        dialog.add(new JLabel("password"));
        JPasswordField password = new JPasswordField();
        dialog.add(password);
        JButton invia = new JButton("invia");
        invia.addActionListener(evt1 -> {
            dialog.dispose();
            if(!email1.getText().isBlank() && !nomeField1.getText().isBlank() && password.getPassword().length>0)
                commandHandler.handle(new SignUpCommand(email1.getText() , nomeField1.getText() , new String(password.getPassword()) , serverRemoteInterface , callbackObj));
            else
                JOptionPane.showMessageDialog(f,"Inserisci tutti i campi");
        });
        dialog.add(invia);
        dialog.setSize(300 , 150);
        dialog.setVisible(true);
    }

    private void menu() {
        JMenuBar jmb = new JMenuBar();
        JMenuItem compraMenu = new JMenuItem("Compra");
        compraMenu.addActionListener((compra)->{if(loggedIn)aste();});
        jmb.add(compraMenu);
        JMenuItem vendi = new JMenuItem("Vendi");
        vendi.addActionListener(( evt ) -> {
            if(loggedIn)
            vendiDialogCreation();
        });
        jmb.add(vendi);
        jmb.add(new JMenuItem("About"));
        JMenuItem personalmenu=new JMenuItem("Area Personale");
        personalmenu.addActionListener((pers)->{if(loggedIn) userPanel();});
        jmb.add(personalmenu);
        JMenuItem logoutmenu = new JMenuItem("Logout");
        logoutmenu.addActionListener((evt1)->{ logout();loggedIn=false; asteSeguite=null; user=null;  start();});
        jmb.add(logoutmenu);
        f.setJMenuBar(jmb);
    }

    private void logout() {
        try {
            serverRemoteInterface.logOut(user);
        } catch ( RemoteException e ) {
            e.printStackTrace();
        }
    }

    private void vendiDialogCreation() {
        JDialog dialog = new JDialog(f , true);
        dialog.setLayout(new GridLayout(6 , 2));
        dialog.add(new JLabel("nome articolo"));
        JTextField nomeArticoloField1 = new JTextField();
        dialog.add(nomeArticoloField1);

        dialog.add(new JLabel("descrizione"));
        JTextField descrizione = new JTextField();
        dialog.add(descrizione);

        dialog.add(new JLabel("costo"));
        JTextField costoIniziale = new JTextField();
        dialog.add(costoIniziale);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY , 24);
        calendar.set(Calendar.MINUTE , 0);
        calendar.set(Calendar.SECOND , 0);

        SpinnerDateModel modelFine = new SpinnerDateModel();
        modelFine.setValue(calendar.getTime());

        dialog.add(new JLabel("Fine Asta"));
        JSpinner spinner = new JSpinner(modelFine);
        dialog.add(spinner);

        SpinnerDateModel modelInizio = new SpinnerDateModel();
        modelFine.setValue(calendar.getTime());

        dialog.add(new JLabel("Inizio Asta"));
        JSpinner spinnerInizio = new JSpinner(modelInizio);
        dialog.add(spinnerInizio);
        JButton invia = new JButton("Aggiungi");
        JButton annulla = new JButton("Annulla");
        invia.addActionListener(evt1 -> {
            dialog.dispose();
            AstaBuilder astaBuilder = null;
            try {
                if(nomeArticoloField1.getText().isBlank())
                    throw new IllegalArgumentException();

                astaBuilder = new AstaBuilderConcreto(nomeArticoloField1.getText() , (Date) spinner.getValue() , user);
                //inserire ifs//
                if(!descrizione.getText().isBlank())
                    astaBuilder.descrizione(descrizione.getText());
                if(!costoIniziale.getText().isBlank() && Double.valueOf(costoIniziale.getText())>0.0)
                    astaBuilder.valorePartenza(Double.valueOf(costoIniziale.getText()));
                Date inizio=(Date)spinnerInizio.getValue();
                if(inizio.before( (Date) spinner.getValue()))
                    astaBuilder.dataInizio(inizio);

            } catch ( AstaBuilderException | NumberFormatException e  ) {
                JOptionPane.showMessageDialog(f , "Errore nella costruzione dell'asta");
            }
            catch ( IllegalArgumentException IAE ){
                JOptionPane.showMessageDialog(f, "Inserci un nome valido");
            }
            commandHandler.handle(new AddAsta(astaBuilder , serverRemoteInterface , callbackObj));
            JOptionPane.showMessageDialog(f , "Asta inserita.");
        });
        annulla.addActionListener(evt2 -> {
            dialog.dispose();
        });
        dialog.add(invia);
        dialog.add(annulla);
        dialog.setSize(300 , 200);
        dialog.setVisible(true);
    }

    private void frameOptions() {
        f.setName("AstaOnLine");
        f.setSize(720 , 840);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            //I skipped unused callbacks for readability

            @Override
            public void windowClosing( WindowEvent e) {
                    if(user != null) {
                        try {
                            serverRemoteInterface.logOut(user);
                        } catch ( RemoteException remoteException ) {
                            remoteException.printStackTrace();
                        }
                    }
                    f.setVisible(false);
                    f.dispose();

            }
        });


        //f.setLayout(null);//using no layout managers
    }

    @Override
    public void aggiornaAsta( Asta asta ) {
        Double valoreAstaVecchia=asteAttive.get(asteAttive.indexOf(asta)).getValore();
        asteAttive.remove(asta);
        asteAttive.add(asta);
        asteSeguite.remove(asta);
        asteSeguite.add(asta);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(f , "L'ogetto" + asta.getNomeArticolo() +" è cambiato di prezzo da"+valoreAstaVecchia+" a "+asta.getValore() , "success" ,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
       aggiornaVista();
    }

    private void aggiornaVista() {
        if(pannelloCorrente==Pannello.ASTE){
            aste();
        }
        else if(pannelloCorrente==Pannello.USER){
            if(user != null)
                userPanel();
        }
    }

    @Override
    public void aggiungiAsta( Asta asta ) {
        try {
            asteAttive=serverRemoteInterface.getAllAsteAttive();
            asteAttive.add(asta);
        } catch ( RemoteException e ) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    asteAttive=serverRemoteInterface.getAllAsteAttive();
                } catch ( RemoteException e ) {
                    e.printStackTrace();
                }
                aggiornaVista();
            }
        });
    }

    @Override
    public void rimuoviAsta( Asta asta ) {
        try {
            asteAttive=serverRemoteInterface.getAllAsteAttive();
            asteAttive.remove(asta);
            if(asteSeguite.contains(asta))
                asteSeguite.remove(asta);
        } catch ( RemoteException e ) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    asteAttive=serverRemoteInterface.getAllAsteAttive();
                    aggiornaVista();
                } catch ( RemoteException e ) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void notificaLogIn( boolean b , Utente u ){
        this.loggedIn=b;
        this.user=u;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(b) {
                    JOptionPane.showMessageDialog(f , "il login ha avuto successo" , "success" ,
                            JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(f , "alert" , "alert" ,
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        try {
            asteSeguite=serverRemoteInterface.getAllAsteSeguiteByUser(user);
            asteAttive = serverRemoteInterface.getAllAsteAttive();
            if(b)
                aste();
        } catch ( RemoteException e ) {
            e.printStackTrace();
        }
        f.revalidate();
        f.repaint();
    }

    @Override
    public void notificaVincitore( Asta asta ) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(asta != null)
                JOptionPane.showMessageDialog(f , "Complimenti, hai vinto l'asta relativa a "+ asta.getNomeArticolo()+ "per "+ asta.getValore()+ "€" , "success" ,
                        JOptionPane.INFORMATION_MESSAGE);
                    aggiornaVista();
            }
        });

    }

    @Override
    public void notificaOgettoVenduto( Asta asta , Utente acquirenteMax ) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(acquirenteMax != null)
                JOptionPane.showMessageDialog(f , "Complimenti, hai venduto l'ogetto "+ asta.getNomeArticolo()+ "per "+ asta.getValore()+ "€" + "all'utente "+ acquirenteMax.getNome()+" con mail: "+ acquirenteMax.getEmail() , "success" ,
                        JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(f , "L'asta relativa a  "+ asta.getNomeArticolo()+ "è andata deserta" ,"fail asta",JOptionPane.INFORMATION_MESSAGE);
                aggiornaVista();
            }
        });


    }

    @Override
    public void notificaSignUp( boolean b ) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(b)
                JOptionPane.showMessageDialog(f , "il sigup ha avuto successo" , "success" ,
                        JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(f , "il signup non ha avuto successo, l'account potrebbe essere già registrato con questa mail" , "alert" ,
                                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void notificaOfferta( boolean b ) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(b) {
                    JOptionPane.showMessageDialog(f , "Offerta Accettata" , "success" ,
                            JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(f , "Offerta rifiutata" , "error" ,
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        aggiornaVista();
    }

    @Override
    public void notificaPartecipazioneAsta( boolean b , Asta asta ) {
        if(b)
            asteSeguite.add(asta);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(b)
                JOptionPane.showMessageDialog(f , "Partecipazione all'asta confermata" , "success" ,
                        JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(f , "Errore" , "error" ,
                            JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}