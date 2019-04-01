import android.os.Bundle; 
import android.content.Intent; 

import ketai.sensors.*;
import ketai.net.bluetooth.*;
import ketai.ui.*;
import ketai.net.*;
import oscP5.*;

KetaiSensor sensor;
KetaiBluetooth bt;  
KetaiList connectionList;  

float accelerometerX, accelerometerY, accelerometerZ, light, light2, proximity;

void setup()
{
  sensor = new KetaiSensor(this);
  sensor.start();
  bt.start(); 
  bt.makeDiscoverable(); 
  
  // Muestra conectar el otro telefono al inicio de la aplición
  if (bt.getDiscoveredDeviceNames().size() > 0)
      connectionList = new KetaiList(this, bt.getDiscoveredDeviceNames());  
  else if (bt.getPairedDeviceNames().size() > 0)
      connectionList = new KetaiList(this, bt.getPairedDeviceNames()); 
}

void draw()
{ 
  textSize(100);
  background(78, 93, 75);
  text(bt.getConnectedDeviceNames().toString(), 100,100);
}

void onLightEvent(float v) 
{
  light2 = light;
  light = v;
}

void mousePressed()
{
    OscMessage m2 = new OscMessage("/count/"); 
    m2.add(2);
    bt.broadcast(m2.getBytes());  
    print("Mandado");
    
    if (light - light2 > 5)
    {
      OscMessage m = new OscMessage("/count/"); 
      m.add(4);
      bt.broadcast(m.getBytes());  
      print("Mandado");
    }
}

void onProximityEvent(float v)
{
  if (v - proximity > 5)
  {
    OscMessage m = new OscMessage("/count/"); 
    m.add(3);
    bt.broadcast(m.getBytes());  
    print("Mandado");
  }
  
  proximity = v;
}

void onAccelerometerEvent(float x, float y, float z)
{
  if (x - accelerometerX > 1 || y - accelerometerY > 1 || z - accelerometerZ > 1)
  {
    OscMessage m = new OscMessage("/count/"); 
    m.add(1);
    bt.broadcast(m.getBytes());  
    print("Mandado");
  }
   
  accelerometerX = x;
  accelerometerY = y;
  accelerometerZ = z;
}

void onKetaiListSelection(KetaiList connectionList)  
{
  String selection = connectionList.getSelection();  
  bt.connectToDeviceByName(selection);  
  connectionList = null;  
}
