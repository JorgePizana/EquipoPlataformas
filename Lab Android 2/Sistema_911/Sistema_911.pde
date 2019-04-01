import oscP5.*;   
import netP5.*;   
import ddf.minim.*;

Minim minim;
AudioPlayer player1, player2, player3, player4;
  
OscP5 oscP5;
NetAddress myRemoteLocation;


void setup() {
  oscP5 = new OscP5(this,12000);
  size(512, 200, P3D);
  
  minim = new Minim(this);
  
  player1 = minim.loadFile("copas.mp3");
  player2 = minim.loadFile("cristales.mp3");
  player3 = minim.loadFile("asomando.mp3");
  player4 = minim.loadFile("entro.mp3");
}


void draw() 
{

}


void oscEvent(OscMessage theOscMessage) {
  
  switch(theOscMessage.get(0).intValue())
  {
    case 1:
      player1.rewind();
      player1.play();
      println(theOscMessage.get(0));
      break;
    case 2:
      player2.rewind();
      player2.play();
      println(theOscMessage.get(0));
      break;
    case 3:
      player3.rewind();
      player3.play();
      println(theOscMessage.get(0));
      break;
    case 4:
      player4.rewind();
      player4.play();
      println(theOscMessage.get(0));
      break;
  }
}
