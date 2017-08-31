package com.onegini.examples.resourcegateway.model;

import java.util.List;

public class Devices {

  private List<Device> devices;

  public Devices() {
  }

  public Devices(final List<Device> devices) {
    this();
    this.devices = devices;
  }

  public List<Device> getDevices() {
    return devices;
  }

  public void setDevices(final List<Device> devices) {
    this.devices = devices;
  }
}