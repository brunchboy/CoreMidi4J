/**
 * Title:        CoreMIDI4J
 * Description:  Core MIDI Device Provider for Java on OS X
 * Copyright:    Copyright (c) 2015
 * Company:      x.factory Librarians
 * @author       Derek Cook
 * 
 * CREDITS - This library uses principles established by OSXMIDI4J, but converted so it operates at the JNI level with no additional libraries required
 *
 */

package com.xfactoryLibrarians;

/**
 * CoreMidiInputPort class
 *
 */

public class CoreMidiInputPort {

	/** The OSX MIDI port reference */
  private final int midiPortReference;
  
  /** For each connection to an OSX EndPoint, some data is allocated on the memory side. This handle tracks the allocation so that it can be returned when this port is disconnected */
  private long memoryHandle;

  /**
   * Constructor
   * 
   * @param clientReference	The client reference
   * @param portName 				The name of the input port
   * @throws 								CoreMidiException if the input port cannot be created
   * 
   */
  
  public CoreMidiInputPort(final int clientReference, String portName) throws CoreMidiException {

  	this.midiPortReference = this.createInputPort(clientReference, portName);

  }

  /**
   * Connects a source to this input port
   * 
   * @param sourceDevice		The source device that wishes to connect to the port
   * 
   * @throws 								CoreMidiException
   * 
   */
  
  public void connectSource(final CoreMidiSource sourceDevice) throws CoreMidiException {
  	
  	memoryHandle = midiPortConnectSource(midiPortReference, sourceDevice);
  			
  }

  /**
   * Disconnects a source from input port
   * 
   * @param sourceDevice	The source device that wishes to disconnect from the port
   * 
   * @throws 							CoreMidiException
   * 
   */
  
  public void disconnectSource(final CoreMidiSource sourceDevice) throws CoreMidiException {
  
  	midiPortDisconnectSource(midiPortReference, memoryHandle, sourceDevice);
  	
  }

  //////////////////////////////
	///// JNI Interfaces
  //////////////////////////////
	
	/**
	 * Static method for loading the native library 
	 * 
	 */
	
  static {
  	
    System.loadLibrary("CoreMIDI4J");
      
  }
  
  /**
   * Creates a CoreMIDI input port
   * 
   * @param clientReference	The MIDI client reference
   * @param portName 				The name of the output port
   * 
   * @return								A reference to the created input port
   * 
   * @throws 								CoreMidiException if the port cannot be created
   * 
   */
  
  private native int createInputPort(int clientReference, String portName) throws CoreMidiException;
  
  /**
   * Connects a source end point to a MIDI input
   * 
   * @param inputPortReference			The reference to an input port
   * @param sourceDevice						The source device that wishes to connect to the port
   * 
   * @return												A memory handle for the parameters the native side has associated with this call 
   * 
   * @throws 												CoreMidiException 
   * 
   */
  
  private native long midiPortConnectSource(int inputPortReference, CoreMidiSource sourceDevice) throws CoreMidiException;
  
  /**
   * Disconnects a source end point to a MIDI input
   * 
   * @param inputPortReference			The reference to an input port
   * @param memoryReference 				The memory handle that can now be released.
   * @param sourceDevice						The source device that wishes to disconnect from the port
   * 
   * @throws 												CoreMidiException 
   * 
   */
  
  private native void midiPortDisconnectSource(int inputPortReference, long memoryReference, CoreMidiSource sourceDevice) throws CoreMidiException;
  
}
