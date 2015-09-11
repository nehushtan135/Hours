import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by elijahvarga on 8/27/2015.
 */
public class MainFrame extends JFrame {
    public String startHours = "7"; // time employee started hours
    public String startMins = "0";   // time employee started minutes
    public String lunch = "0";      // lenght of lunch time
    public String eTime;      // quittin time
    public String deHours = "8"; // hours desired
    public Boolean AMSelected = true; // AM or PM

    private JPanel workTime;
    private JLabel startedLabel;
    private JCheckBox lunchBoolean;
    private JSlider lunchChooser;
    private JSpinner hoursDesired;
    private JSpinner startHour;
    private JButton calculateButton;
    private JLabel endTime;
    private JLabel lunchTaken;
    private JLabel timeToWork;
    private JSpinner StartMin;
    private JSplitPane lunchSplitter;
    private JRadioButton amOption;
    private JRadioButton pmOption;
    private JSplitPane ampm;
    private JLabel sliderTime;

    public MainFrame() {

        // when window closes the program closes
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        workTime.getComponents();
        this.add(workTime);
        addListeners();
        setTitle("Hour Calculator");
        setSize(450, 350);
        //TODO: get the screen size of the device and put the location into the middle
        setLocation(450, 210);
    }
    public void addListeners() {
        //whether they took lunch or not
        lunchBoolean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lunchBoolean.isSelected()) {
                    lunchChooser.setEnabled(true);
                } else {
                    lunchChooser.setEnabled(false);
                    lunch = "0";
                }
            }
        });

        //calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doCalculations();
            }
        });
        //hourSpinner
        SpinnerNumberModel hourModel = new SpinnerNumberModel(7, 1, 12, 1);
        startHour.setModel(hourModel);
        JSpinner.DefaultEditor hourEditor = (JSpinner.DefaultEditor) startHour.getEditor();
        final JTextField hours = hourEditor.getTextField();
        hours.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                startHours = hours.getText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                startHours = hours.getText();


            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                startHours = hours.getText();
            }
        });

        //minuteSpinner
        SpinnerNumberModel minuteModel = new SpinnerNumberModel(0, 0, 59, 1);
        StartMin.setModel(minuteModel);
        JSpinner.DefaultEditor minEditor = (JSpinner.DefaultEditor) StartMin.getEditor();
        final JTextField minutes = minEditor.getTextField();
        minutes.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                startMins = minutes.getText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                startMins = minutes.getText();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                startMins = minutes.getText();
            }
        });

        //Hours desired Spinner
        SpinnerNumberModel desiredModel = new SpinnerNumberModel(8, 0, 12, 0.5);
        hoursDesired.setModel(desiredModel);
        JSpinner.DefaultEditor desiredEditor = (JSpinner.DefaultEditor) hoursDesired.getEditor();
        final JTextField desired = desiredEditor.getTextField();
        desired.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                deHours = desired.getText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                deHours = desired.getText();


            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                deHours = desired.getText();


            }
        });

        //am/pm buttons
        ButtonGroup bg = new ButtonGroup();
        amOption.setSelected(true);
        bg.add(amOption);
        bg.add(pmOption);
        pmOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AMSelected = false;
            }
        });
        amOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AMSelected = true;
            }
        });

        //get Lunch if taken
        lunchChooser.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Integer temp  = lunchChooser.getValue();
                sliderTime.setText(temp.toString());
                lunch = temp.toString();
            }
        });
    }
    public void doCalculations(){
        Double CarryMin = 0.0;
        Boolean eAM = false;
        Double mLunch = Double.parseDouble(lunch);
        Double dHours = Math.floor(Double.parseDouble(deHours));
        Double dMinutes = Double.parseDouble(deHours) - dHours;
        dMinutes *= 60;
        Double sHours = Double.parseDouble(startHours);
        if(AMSelected == false){
            sHours += 12;
        }
        Double sMinutes = Double.parseDouble(startMins);
        if(sHours + dHours >= 24.0) {
            eAM = true;
        }
        Double endMinutes = (sMinutes + dMinutes) % 60;
        if(endMinutes + mLunch >= 60.0){
            endMinutes = (endMinutes + mLunch) %60;
            CarryMin++;
        }
        else{
            endMinutes = (endMinutes + mLunch) % 60;
        }
        Double endHour = (sHours + dHours + CarryMin) % 12;
        int fHours = endHour.intValue();
        int fMinutes = endMinutes.intValue();
        String rMinutes = "";
        if(fMinutes <=9) {
            rMinutes = "0";
        }
        String isAM = "AM";
        if(eAM == false) {
            isAM = "PM";
        }
        String Ftime = String.format("End Time: \t \t " + fHours +":" + rMinutes + fMinutes + "  " + isAM);
        endTime.setText(Ftime);

    }
    public static void main(String[] args) {
        WindowAdapter windowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {
                super.windowIconified(e);
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                super.windowDeiconified(e);
            }

            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
            }

            @Override
            public void windowStateChanged(WindowEvent e) {
                super.windowStateChanged(e);
            }
        };
        JFrame f = new MainFrame();
        f.setVisible(true);
    }



}
