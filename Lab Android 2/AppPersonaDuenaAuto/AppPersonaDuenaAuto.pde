import android.os.Bundle;  // 1
import android.content.Intent;  // 2

import ketai.net.bluetooth.*;
import ketai.ui.*;
import ketai.net.*;
import oscP5.*;

KetaiBluetooth bt;  // 3

KetaiList connectionList;  // 4
boolean isConfiguring = true;


void setup(){
  orientation(PORTRAIT);
  textAlign(CENTER, CENTER);
  textSize(50);
  bt.start(); // 6
}


void draw(){
   background(78, 93, 75);
  /*if(isConfiguring){
  
  }else{
   

  }*/

}

void onCreate(Bundle savedInstanceState) { // 1
  super.onCreate(savedInstanceState);
  bt = new KetaiBluetooth(this); // 2
}

void onActivityResult(int requestCode, int resultCode, Intent data) {
  bt.onActivityResult(requestCode, resultCode, data); // 3
}
