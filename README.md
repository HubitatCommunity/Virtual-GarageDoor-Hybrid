# Virtual-GarageDoor-Hybrid

To help with debugging, this Virtual Garage Door Opener (GDO) hybrid driver has been created. Per the documentation, a GDO has
a single Attribute: Door with an Enumerated set of states/values: unknown, open, closed, opening, closing. This hybrid driver
adds a rather standard Contact Sensor and keeps the Attribute in sync. In addition, an optional Switch is coded, although 
it's not normally found in a functioning, physical, GDO.

