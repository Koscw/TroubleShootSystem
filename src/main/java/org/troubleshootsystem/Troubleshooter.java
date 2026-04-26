import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Troubleshooter extends JFrame {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Map<String, String> status;
    private String currentDevice;

    public Troubleshooter() {
        setTitle("Router & Printer Troubleshooter");
        setSize(1000, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        status = new HashMap<>();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        initStartScreen();

        add(mainPanel);
    }

    // Start Screen UI

    private void initStartScreen() {
        JPanel startPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        startPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        startPanel.setBackground(Color.decode("#191970"));

        JLabel label = new JLabel("Which device would you like to troubleshoot?", SwingConstants.CENTER);
        label.setForeground(Color.decode("#ffffff"));
        label.setFont(new Font("Arial", Font.BOLD, 35));

        JButton routerButton = new JButton("ROUTER");
        JButton printerButton = new JButton("PRINTER");
        Font buttonFont = new Font("SansSerif", Font.BOLD, 25);

        routerButton.setBackground(Color.decode("#070049"));
        routerButton.setForeground(Color.WHITE);
        routerButton.setFocusPainted(false);
        routerButton.setFont(buttonFont);

        printerButton.setBackground(Color.decode("#070049"));
        printerButton.setForeground(Color.WHITE);
        printerButton.setFont(buttonFont);

        routerButton.addActionListener(e -> startTroubleshooting("Router"));
        printerButton.addActionListener(e -> startTroubleshooting("Printer"));

        startPanel.add(label);
        startPanel.add(routerButton);
        startPanel.add(printerButton);

        mainPanel.add(startPanel, "StartScreen");
    }

    private void startTroubleshooting(String device) {
        currentDevice = device;
        status.clear();
        askOutletWorks();
    }

    // Questions:

    private void askOutletWorks() {
        showQuestionScreen("Does the wall outlet work?", "Yes", "No", result -> {
            status.put("outlet_works", result);
            if (result.equals("No")) evaluateAndShowResult();
            else askCablePlugged();
        });
    }

    private void askCablePlugged() {
        showQuestionScreen("Is the cable plugged in?", "Yes", "No", result -> {
            status.put("cable_plugged", result);
            if (result.equals("No")) evaluateAndShowResult();
            else askLedPower();
        });
    }

    private void askLedPower() {
        showQuestionScreen("State of the Power LED?", "on", "off", result -> {
            status.put("led_power", result);
            if (result.equals("off")) evaluateAndShowResult();
            else askLedInternet();
        });
    }

    private void askLedInternet() {
        showQuestionScreen("Color of Internet/Network LED?", "Green", "Red", result -> {
            status.put("led_internet", result);
            if (currentDevice.equals("Router") || result.equals("Red")) {
                evaluateAndShowResult();
            } else {
                askPaperJammed(); // Continue to printer specific questions
            }
        });
    }

    private void askPaperJammed() {
        showQuestionScreen("Is there a paper jam?", "Yes", "No", result -> {
            status.put("paper_jammed", result);
            if (result.equals("Yes")) evaluateAndShowResult();
            else askImageStreaks();
        });
    }

    private void askImageStreaks() {
        showQuestionScreen("Are there streaks on the printed image?", "Yes", "No", result -> {
            status.put("printed_image_streaks", result);
            evaluateAndShowResult();
        });
    }

    // Question Screen and Result Screen UI

    private void showQuestionScreen(String questionText, String opt1, String opt2, java.util.function.Consumer<String> onAnswer) {
        JPanel qPanel = new JPanel();
        qPanel.setLayout(new BoxLayout(qPanel, BoxLayout.Y_AXIS));
        qPanel.setBackground(Color.decode("#191970"));

        JLabel qLabel = new JLabel(questionText);
        qLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        qLabel.setForeground(Color.decode("#ffffff"));
        qLabel.setFont(new Font("Arial", Font.BOLD, 40));

        JButton button1 = new JButton(opt1);
        JButton button2 = new JButton(opt2);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 25);
        Dimension bigButtonSize = new Dimension(225, 200);

        button1.setBackground(Color.decode("#070049"));
        button1.setForeground(Color.WHITE);
        button1.setFont(buttonFont);
        button1.setPreferredSize(bigButtonSize);

        button2.setBackground(Color.decode("#070049"));
        button2.setForeground(Color.WHITE);
        button2.setFont(buttonFont);
        button2.setPreferredSize(bigButtonSize);

        button1.addActionListener(e -> onAnswer.accept(opt1));
        button2.addActionListener(e -> onAnswer.accept(opt2));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        qPanel.add(Box.createVerticalGlue());
        qPanel.add(qLabel);
        qPanel.add(Box.createVerticalStrut(150));
        qPanel.add(buttonPanel);

        mainPanel.add(qPanel, "QuestionScreen");
        cardLayout.show(mainPanel, "QuestionScreen");
    }

    private void evaluateAndShowResult() {
        sendToProlog(status);
        sendToLisp(status);

        String issue = getMockDiagnosis(status);

        JPanel resultPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        resultPanel.setBackground(Color.decode("#191970"));

        JLabel titleLabel = new JLabel("Troubleshooting Complete", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.decode("#ffffff"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));

        JLabel issueLabel = new JLabel("Diagnosis: " + issue, SwingConstants.CENTER);
        issueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        issueLabel.setForeground(Color.decode("#ff0000"));
        issueLabel.setFont(new Font("Arial", Font.BOLD, 25));

        JButton restartButton = new JButton("Restart");
        Font buttonFont = new Font("SansSerif", Font.BOLD, 35);
        restartButton.setBackground(Color.decode("#070049"));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFont(buttonFont);
        restartButton.addActionListener(e -> cardLayout.show(mainPanel, "StartScreen"));

        resultPanel.add(titleLabel);
        resultPanel.add(issueLabel);
        resultPanel.add(restartButton);

        mainPanel.add(resultPanel, "ResultScreen");
        cardLayout.show(mainPanel, "ResultScreen");
    }

    // Prolog and Lisp

    private void sendToProlog(Map<String, String> currentStatus) {
        System.out.println("*Sending Data to Prolog*");
        for (Map.Entry<String, String> entry : currentStatus.entrySet()) {
            System.out.println("Prolog State -> " + entry.getKey() + ": " + entry.getValue());
        }
    }

    private void sendToLisp(Map<String, String> currentStatus) {
        System.out.println("*Sending Data to Lisp*");
        System.out.print("Lisp State -> (");
        for (Map.Entry<String, String> entry : currentStatus.entrySet()) {
             System.out.print("(" + entry.getKey() + " . " + entry.getValue() + ") ");
        }
        System.out.println(")");
    }

    // Backend just in case

    private String getMockDiagnosis(Map<String, String> s) {
        if ("No".equals(s.get("outlet_works"))) return "Outlet Issue";
        if ("Yes".equals(s.get("outlet_works")) && "No".equals(s.get("cable_plugged"))) return "Cable is disconnected";
        if ("Yes".equals(s.get("cable_plugged")) && "off".equals(s.get("led_power"))) return "Power Adapter is Dead";
        if ("on".equals(s.get("led_power")) && "Red".equals(s.get("led_internet"))) return "ISP Connection Issue";

        if ("Printer".equals(currentDevice)) {
            if ("Yes".equals(s.get("paper_jammed"))) return "Printer Tray is Overloaded";
            if ("No".equals(s.get("paper_jammed"))) {
                if ("No".equals(s.get("printed_image_streaks"))) return "Error with Printer Drivers";
                if ("Yes".equals(s.get("printed_image_streaks"))) return "Printer Head is Dirty";
            }
        }

        return "Issue is unknown / No problems found";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Troubleshooter app = new Troubleshooter();
            app.setVisible(true);
        });
    }
}