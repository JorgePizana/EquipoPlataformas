import android.os.Bundle; 
import android.content.Intent;  

import ketai.net.bluetooth.*;
import netP5.*;
import ketai.ui.*;
import ketai.net.*;
import oscP5.*;

OscP5 oscP5;
NetAddress remoteLocation;

int num = 0;

KetaiBluetooth bt;  
KetaiList connectionList;  

String remoteAddress = "192.168.1.74";
String myIPAddress;

void setup()
{
   initNetworkConnection();
   bt.start(); 
   bt.makeDiscoverable();  
   orientation(LANDSCAPE);
}

void draw()
{ 
  background(78, 93, 75);
  
  textSize(45);
  // Diferentes tipos de Alarmas
  switch(num)
  {
    case 1:
      text("Se estan robando las copas de tu carro", 100, 150);
      break;
    case 2:
      text("Estan intentando romper los cristales de tu auto", 100, 150);
      break;
    case 3:
      text("Se estan asomando por las ventanas de tu carro", 100, 150);
      break;
    case 4:
      text("CUIDADO: Alguien entro a tu auto", 100, 150);
      break;
  }
}

// Presiona la pantalla para conectar el otro telefono
void mousePressed()
{
  if (bt.getDiscoveredDeviceNames().size() > 0)
      connectionList = new KetaiList(this, bt.getDiscoveredDeviceNames());  
  else if (bt.getPairedDeviceNames().size() > 0)
      connectionList = new KetaiList(this, bt.getPairedDeviceNames());  
}


// Metodo que recibe el mensaje por Bluetooth
void onBluetoothDataEvent(String who, byte[] data) 
{
  print("Recibo");
   KetaiOSCMessage m = new KetaiOSCMessage(data); 
   if (m.isValid())
  {
    if (m.checkAddrPattern("/count/"))
    {  
        num = m.get(0).intValue();
        
        // Mandamos el mensaje de alerta a la computadora
        OscMessage myMessage = new OscMessage("Alarmas");
        myMessage.add(num);
        oscP5.send(myMessage, remoteLocation);
    }
  }
}  

void onKetaiListSelection(KetaiList connectionList)  
{
  String selection = connectionList.getSelection();  
  bt.connectToDeviceByName(selection);  
  connectionList = null;  
}

void initNetworkConnection()
{
  oscP5 = new OscP5(this, 12000);
  remoteLocation = new NetAddress(remoteAddress, 12000); 
  myIPAddress = KetaiNet.getIP(); // Verifica la dirección Ip del dispositivo Android
}
