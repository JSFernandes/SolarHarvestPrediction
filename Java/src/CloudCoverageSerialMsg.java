/**
 * This class is automatically generated by mig. DO NOT EDIT THIS FILE.
 * This class implements a Java interface to the 'CloudCoverageSerialMsg'
 * message type.
 */

public class CloudCoverageSerialMsg extends net.tinyos.message.Message {

    /** The default size of this message type in bytes. */
    public static final int DEFAULT_MESSAGE_SIZE = 1;

    /** The Active Message type associated with this message. */
    public static final int AM_TYPE = 137;

    /** Create a new CloudCoverageSerialMsg of size 1. */
    public CloudCoverageSerialMsg() {
        super(DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /** Create a new CloudCoverageSerialMsg of the given data_length. */
    public CloudCoverageSerialMsg(int data_length) {
        super(data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new CloudCoverageSerialMsg with the given data_length
     * and base offset.
     */
    public CloudCoverageSerialMsg(int data_length, int base_offset) {
        super(data_length, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new CloudCoverageSerialMsg using the given byte array
     * as backing store.
     */
    public CloudCoverageSerialMsg(byte[] data) {
        super(data);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new CloudCoverageSerialMsg using the given byte array
     * as backing store, with the given base offset.
     */
    public CloudCoverageSerialMsg(byte[] data, int base_offset) {
        super(data, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new CloudCoverageSerialMsg using the given byte array
     * as backing store, with the given base offset and data length.
     */
    public CloudCoverageSerialMsg(byte[] data, int base_offset, int data_length) {
        super(data, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new CloudCoverageSerialMsg embedded in the given message
     * at the given base offset.
     */
    public CloudCoverageSerialMsg(net.tinyos.message.Message msg, int base_offset) {
        super(msg, base_offset, DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new CloudCoverageSerialMsg embedded in the given message
     * at the given base offset and length.
     */
    public CloudCoverageSerialMsg(net.tinyos.message.Message msg, int base_offset, int data_length) {
        super(msg, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
    /* Return a String representation of this message. Includes the
     * message type name and the non-indexed field values.
     */
    public String toString() {
      String s = "Message <CloudCoverageSerialMsg> \n";
      try {
        s += "  [slot=0x"+Long.toHexString(get_slot())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      return s;
    }

    // Message-type-specific access methods appear below.

    /////////////////////////////////////////////////////////
    // Accessor methods for field: slot
    //   Field type: short, unsigned
    //   Offset (bits): 0
    //   Size (bits): 8
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'slot' is signed (false).
     */
    public static boolean isSigned_slot() {
        return false;
    }

    /**
     * Return whether the field 'slot' is an array (false).
     */
    public static boolean isArray_slot() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'slot'
     */
    public static int offset_slot() {
        return (0 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'slot'
     */
    public static int offsetBits_slot() {
        return 0;
    }

    /**
     * Return the value (as a short) of the field 'slot'
     */
    public short get_slot() {
        return (short)getUIntBEElement(offsetBits_slot(), 8);
    }

    /**
     * Set the value of the field 'slot'
     */
    public void set_slot(short value) {
        setUIntBEElement(offsetBits_slot(), 8, value);
    }

    /**
     * Return the size, in bytes, of the field 'slot'
     */
    public static int size_slot() {
        return (8 / 8);
    }

    /**
     * Return the size, in bits, of the field 'slot'
     */
    public static int sizeBits_slot() {
        return 8;
    }

}