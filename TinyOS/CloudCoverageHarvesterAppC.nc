#include "CloudCoverageBase.h"
#include "Timer.h"

configuration CloudCoverageHarvesterAppC {}
implementation {
  components CloudCoverageHarvesterC as App, MainC, LedsC;
  components new TimerMilliC();
  components ActiveMessageC;
  components new AMSenderC(AM_HARVEST_ANSWER_SERIAL_MSG) as ASender;
  components new AMReceiverC(AM_CLOUD_COVERAGE_SERIAL_MSG) as AReceiver;
  components new ADC0SensorC() as Sensor;

  App.Boot -> MainC.Boot;
  App.Leds -> LedsC;
  App.MilliTimer -> TimerMilliC;

  App.HarvestPacket -> ASender;
  App.CloudPacket -> AReceiver;
  App.AMHarvestPacket -> ASender;
  App.AMCloudPacket -> AReceiver;
  App.Sender -> ASender;
  App.AMControl -> ActiveMessageC;
  App.Receiver -> AReceiver;
  App.Read -> Sensor;
}


