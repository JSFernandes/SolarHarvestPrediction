COMPONENT=CloudCoverageHarvesterAppC
BUILD_EXTRA_DEPS += CloudCoverageBase.class HarvestAnswerSerialMsg.class
CLEAN_EXTRA = *.class CloudCoverageSerialMsg.java HarvestAnswerSerialMsg.java

CFLAGS += -I$(TOSDIR)/lib/T2Hack

CloudCoverageBase.class: $(wildcard *.java) CloudCoverageSerialMsg.java
	javac -target 1.4 -source 1.4 *.java

HarvestAnswerSerialMsg.class: $(wildcard *.java) HarvestAnswerSerialMsg.java
	javac -target 1.4 -source 1.4 *.java

CloudCoverageSerialMsg.java:
	mig java -target=null $(CFLAGS) -java-classname=CloudCoverageSerialMsg CloudCoverageBase.h cloud_coverage_serial_msg -o $@

HarvestAnswerSerialMsg.java:
	mig java -target=null $(CFLAGS) -java-classname=HarvestAnswerSerialMsg CloudCoverageBase.h harvest_answer_serial_msg -o $@


include $(MAKERULES)

