/**
 *  Virtual Garage Door 
 *
 *  Copyright 2017 C Steele
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Change History:
 *
 *    Date        Who            What
 *    ----        ---            ----
 *    2018-07-18  C Steele       Revamped into Garage Door
 *    2018-07-18  Dan Ogorchock  Original Creation as "Virtual Illuminance Sensor"
 *
 * 
 */
 
metadata {
	definition (name: "Virtual GarageDoor Hybrid", namespace: "csteele", author: "C Steele") 
	{
		capability "Garage Door Control"
		capability "Sensor"
		capability "Contact Sensor"
//		capability "Switch"
 
       
	}

      preferences 
      {
	    input name: "transition", type: "enum", title: "Transition Time", options: [5:"5 seconds", 30:"30 seconds", 90:"90 seconds"], defaultValue: "5"

          //standard logging options
          input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
      }
}


def logsOff()
{
    log.warn "debug logging disabled..."
    device.updateSetting("logEnable",[value:"false",type:"bool"])
}


/* 
   Everything for a Switch is commented out.
*/
def parse(String description) 
{
	//log.debug "parse: ${description}"
//	createEvent(name: "switch", value: description)
//	if (description == "on") { open() } else { close() }
}


/*
   Switches don't have transition states, but it's here just in case.
*/
def nextState(toState) 
{
	if (logEnable) log.debug "nextState: ${toState}"
	sendEvent(name: "door", value: "${toState[0]}")
	sendEvent(name: "contact", value: "${toState[0]}")
//	sendEvent(name: "switch", value: "${toState[1]}")
}


def open() 
{
	sendEvent(name: "door", value:"opening")
	runIn(transition.toInteger(), nextState, [data:["open","on"]])
	if (logEnable) log.debug("Opening")
}


def close() 
{
	sendEvent(name: "door", value:"closing")
	runIn(transition.toInteger(), nextState, [data:["closed","off"]])
	if (logEnable) log.debug("Closing")
}


def on() 
{
	sendEvent(name: "door", value:"open")
	sendEvent(name: "contact", value:"open")
//	sendEvent(name: "switch", value:"on")
	if (logEnable) log.debug("on")
}


def off() 
{
	sendEvent(name: "door", value:"closed")
	sendEvent(name: "contact", value:"closed")
//	sendEvent(name: "switch", value:"off")
	if (logEnable) log.debug("off")
}


def installed(){}


def configure() {}


def updated() 
{
    log.trace "Updated()"
    log.warn "debug logging is: ${logEnable == true}"
    log.warn "description logging is: ${txtEnable == true}"
    if (logEnable) runIn(1800,logsOff)    
}
