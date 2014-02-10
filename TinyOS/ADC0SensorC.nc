generic configuration ADC0SensorC() {
  provides interface Read<uint16_t>;
}
implementation {
  components new AdcReadClientC();
  Read = AdcReadClientC;
  components ADC0SensorP;
  AdcReadClientC.AdcConfigure -> ADC0SensorP;
}
