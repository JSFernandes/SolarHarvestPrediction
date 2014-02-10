#include "Timer.h"
#include "CloudCoverageBase.h"

module CloudCoverageHarvesterC {
  uses {
    interface Boot;
    interface Timer<TMilli> as MilliTimer;
    interface Leds;

    interface Packet as HarvestPacket;
    interface Packet as CloudPacket;
    interface AMPacket as AMHarvestPacket;
    interface AMPacket as AMCloudPacket;
    interface AMSend as Sender;
    interface SplitControl as AMControl;
    interface Receive as Receiver;

    interface Read<uint16_t>;
  }
}
implementation {
  message_t packet_radio;

  bool radio_locked = FALSE;
  uint8_t current_hour_slot = 0;
  uint16_t hour_readings [12];
  uint16_t day_readings [24];
  
  event void Boot.booted() {
    call AMControl.start();
    call MilliTimer.startPeriodic(300000);
    //call MilliTimer.startPeriodic(1000);
  }
  
  event void MilliTimer.fired() {
    call Read.read();
  }

  void sendValue(uint16_t val) {
    harvest_answer_serial_msg_t* sending = (harvest_answer_serial_msg_t*)call HarvestPacket.getPayload(&packet_radio, sizeof(harvest_answer_serial_msg_t));
    sending->result = val;
    if (call Sender.send(AM_BROADCAST_ADDR, &packet_radio, sizeof(harvest_answer_serial_msg_t)) == SUCCESS) {
	    radio_locked = TRUE;
    }
  }
  
  event message_t* Receiver.receive(message_t* bufPtr, void* payload, uint8_t len) {
    //call Leds.led1On();
    if(len != sizeof(cloud_coverage_serial_msg_t))
      return bufPtr;
    else {
      cloud_coverage_serial_msg_t* rcm = (cloud_coverage_serial_msg_t*)payload;
      uint8_t first_slot = 24 - rcm->slot;
      sendValue(day_readings[first_slot]);

      //call Leds.led2On();
      return bufPtr;
    }
  }

  event void Sender.sendDone(message_t* bufPtr, error_t error) {
    if(&packet_radio == bufPtr)
      radio_locked = FALSE;
  }

  event void AMControl.startDone(error_t err) {
    if (err == SUCCESS) {
    }
    else {
      call AMControl.start();
    }
  }
  event void AMControl.stopDone(error_t err) {}

  event void Read.readDone(error_t result, uint16_t data) {
    float real_val = data*2.5/4095;
    uint16_t real_val_int = real_val*100;
    hour_readings[current_hour_slot] = real_val_int;
    ++current_hour_slot;

    if(current_hour_slot >= 12) {
      uint16_t hour_average = 0;
      uint8_t i = 0;
      while(i < 12) {
        hour_average += hour_readings[i];
        ++i;
      }
      hour_average /= 12;
      i = 0;
      while(i < 23) {
        day_readings[i] = day_readings[i+1];
        ++i;
      }
      day_readings[23] = hour_average;
      current_hour_slot = 0;
    }
  }
}




