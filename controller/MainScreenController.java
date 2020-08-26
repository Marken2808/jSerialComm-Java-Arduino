package controller;

import com.fazecast.jSerialComm.SerialPort;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML
    private ComboBox comboPorts;

    @FXML
    private Button connectBtn;

    private SerialPort serialPort;

    @FXML
    void makeConnect(MouseEvent event) {
        if(connectBtn.getText().equals("Connect")){

            serialPort = SerialPort.getCommPort(comboPorts.getSelectionModel().getSelectedItem().toString());
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

            if (serialPort.openPort()) {
                InputStream inputStream = serialPort.getInputStream();
                BufferedReader readBuffer = new BufferedReader(new InputStreamReader(inputStream));
                System.out.println("STATUS: LED BLINKING!!");

                connectBtn.setText("Disconnect");
                comboPorts.setDisable(true);
            }


        } else {
            serialPort.closePort();
            comboPorts.setDisable(false);
            connectBtn.setText("Connect");
        }
    }

    public void createPort() {

        SerialPort[] portNames = SerialPort.getCommPorts();

        for (SerialPort portName : portNames) {
            comboPorts.getItems().add(portName.getSystemPortName());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createPort();

    }
}
