#ifndef CLOUD_COVERAGE_BASE_H
#define CLOUD_COVERAGE_BASE_H

typedef nx_struct cloud_coverage_serial_msg {
  nx_uint8_t slot;
} cloud_coverage_serial_msg_t;

typedef nx_struct harvest_answer_serial_msg {
  nx_uint32_t result;
} harvest_answer_serial_msg_t;

enum {
  AM_CLOUD_COVERAGE_SERIAL_MSG = 0x89,
  AM_HARVEST_ANSWER_SERIAL_MSG = 0x90
};

#endif
